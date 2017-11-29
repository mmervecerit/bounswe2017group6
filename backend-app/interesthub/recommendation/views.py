from django.shortcuts import render
from .models import *
from .serializers import *
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.permissions import IsAuthenticated
from user.serializers import UserSerializerFull
from group.serializers import InterestGroupSerializer
import operator
from django.contrib.auth.models import User, Group
from group.models import InterestGroup
from rest_framework.response import Response

# Create your views here.
class TagViewSet(viewsets.ModelViewSet):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    queryset = Tag.objects.all()
    serializer_class = TagSerializerFull

class RecommendUser(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        recom = {}
        for interest in user.profile.interests.all():
            for u in interest.users.all():
                if u.is_public and u not in user.followings.all():
                    if u.owner.id in recom:
                        recom[u.owner.id] += 1
                    else:
                        recom[u.owner.id] = 1
        sorted_recom = sorted(recom.items(), key=operator.itemgetter(1))
        ids = []
        for u in sorted_recom:
            if u != user.id:
                ids.append(u[0])
            if len(ids)>10:
                break
        print(ids)
        recom_users = User.objects.filter(pk__in=ids)
        return Response(UserSerializerFull(recom_users,context={'request':request}, many=True).data)


class RecommendGroup(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        recom = {}
        for interest in user.profile.interests.all():
            for g in interest.groups.all():
                if g not in user.interest_groups.all():
                    if g.id in recom:
                        recom[g.id] += 1
                    else:
                        recom[g.id] = 1
        sorted_recom = sorted(recom.items(), key=operator.itemgetter(1))
        ids = []
        for g in sorted_recom:
            ids.append(g[0])
            if len(ids)>10:
                break
        recom_groups = InterestGroup.objects.filter(pk__in=ids)
        return Response(InterestGroupSerializer(recom_groups,context={'request':request}, many=True).data)
        


        