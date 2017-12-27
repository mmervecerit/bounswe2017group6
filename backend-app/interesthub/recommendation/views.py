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
from .recommenders import get_recommended_users, get_recommended_groups
from .search import *
from content.serializers import *

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
        limit = int( request.GET.get("limit", 5) )
        recom_users = get_recommended_users(request.user, int(limit))
        return Response(UserSerializerFull(recom_users,context={'request':request}, many=True).data)


class RecommendGroup(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        limit = int( request.GET.get("limit", 5) )
        recom_groups = get_recommended_groups(request.user, int(limit))
        return Response(InterestGroupSerializer(recom_groups,context={'request':request}, many=True).data)
        
class SearchGroup(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        query = self.request.query_params.get('q', None)
        limit = self.request.query_params.get('limit', 5)
        groups = search_group(query, int(limit))
        return Response(InterestGroupSerializer(groups,context={'request':request}, many=True).data)
        

class SearchUser(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        query = self.request.query_params.get('q', None)
        limit = self.request.query_params.get('limit', 5)
        users = search_user(query, int(limit))
        return Response(UserSerializer(users,context={'request':request}, many=True).data)

class SearchContent(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        query = self.request.query_params.get('q', None)
        limit = self.request.query_params.get('limit', 10)
        contents = search_content(query, limit=int(limit))
        return Response(ContentSerializer(contents, context={'request':request}, many=True).data)

class SearchContentInGroup(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        group = None
        try:
            group = InterestGroup.objects.get(pk=pk)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)            
        
        query = self.request.query_params.get('q', None)
        limit = self.request.query_params.get('limit', 10)
        contents = search_content(query, limit=int(limit), group=group)
        return Response(ContentSerializer(contents, context={'request':request}, many=True).data)


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


