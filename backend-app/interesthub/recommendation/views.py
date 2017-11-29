from django.shortcuts import render
from .models import *
from .serializers import *
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.permissions import IsAuthenticated
from user.serializers import UserSerializerFull, UserSerializer
from group.serializers import InterestGroupSerializer
import operator
from django.contrib.auth.models import User, Group
from user.models import UserProfile
from group.models import InterestGroup
from rest_framework.response import Response
from components.models import TextComponent, LongTextComponent

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
        
class SearchGroup(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        query = self.request.query_params.get('q', None)
        # tag = self.request.query_params.get('tag', None)

        # if tag is None:
        groups = InterestGroup.objects.filter(name__contains=query)
        return Response(InterestGroupSerializer(groups,context={'request':request}, many=True).data)
        

class SearchUser(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        query = self.request.query_params.get('q', None)
        users = User.objects.filter(username__contains=query)
        profiles = UserProfile.objects.filter(name__contains=query)
        ids = []
        for user in users:
            ids.append(user.id)
        for profile in profiles:
            ids.append(profile.owner.id)
        ids = list(set(ids))
        users = User.objects.filter(pk__in=ids)
        ids = ids[:10]
        return Response(UserSerializer(users,context={'request':request}, many=True).data)


# class SearchContent(APIView):
#     authentication_classes = (JSONWebTokenAuthentication, )
#     permission_classes = (IsAuthenticated,)
#     def get(self, request, foramt=None):
#         user = request.user
#         query = self.request.query_params.get('q', None)
#         ids = []
#         texts = TextComponent.objects.filter(data__contains=query)
#         long_texts = LongTextComponent.objects.filter(data__contains=query)
#         for text in texts:
#             ids.append(text.component.content.id)
#         for text in long_texts:
#             ids.append(text.component.content.id)

#         ids = list(set(ids))
#         ids = ids[:10]


