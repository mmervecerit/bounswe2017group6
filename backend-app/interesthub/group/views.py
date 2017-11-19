from django.shortcuts import render
from .models import InterestGroup
from .serializers import *
from rest_framework.views import APIView
from rest_framework.viewsets import ModelViewSet
from rest_framework.response import Response
from content.serializers import ContentSerializer, ContentTypeSerializer
from django.contrib.auth.models import User, Group
from rest_framework import viewsets
from rest_framework import status
from content.utils import retrieve_content_set, create_content
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.permissions import IsAuthenticated

# Create your views here.
class InterestGroupViewSet(ModelViewSet):
    queryset = InterestGroup.objects.all()
    serializer_class = InterestGroupSerializer

class GroupContentList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        print(igroup)
        serializer = ContentSerializer(igroup.contents.all(), many=True)
        return Response(serializer.data)
    
    def post(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        serializer = ContentSerializer(igroup.contents, many=False, data=request.data, context={"request": request})
        if not serializer.is_valid():
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        content = serializer.create(serializer.validated_data)
        igroup.contents.add(content)
        igroup.save()
        return Response(ContentSerializer(content,context={'request':request}).data)

class GroupContentTypeList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        serializer = ContentTypeSerializer(igroup.content_types, many=True, context={'request': request})
        return Response(serializer.data)

    def post(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        serializer = ContentTypeSerializer(igroup.content_types, many=False, data=request.data, context={"request": request})
        if serializer.is_valid():
            s = serializer.create(request.data)
            igroup.content_types.add(s)
            return Response(ContentTypeSerializer(s, many=False, context={"request": request}).data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class GroupMembersList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        serializer = UserSerializer(igroup.members, many=True, context={'request': request})
        print(serializer.data)
        return Response(serializer.data)

class UserGroupList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        # groups = InterestGroup.objects.all()
        user = request.user
        groups = user.interest_groups.all()
        serializer = InterestGroupSerializer(groups, many=True, context={'request': request})
        print(serializer.data)
        return Response(serializer.data)