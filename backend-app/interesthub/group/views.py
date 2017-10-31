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

# Create your views here.
class InterestGroupViewSet(ModelViewSet):
    queryset = InterestGroup.objects.all()
    serializer_class = InterestGroupSerializer

class GroupContentList(APIView):
    def get(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        serializer = ContentSerializer(igroup.contents, many=True, context={'request': request})
        return Response(serializer.data)
    def post(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        # print(request.data)
        serializer = ContentSerializer(igroup.contents, many=False, data=request.data)
        if serializer.is_valid():
            s = serializer.create(request.data)
            igroup.contents.add(s)
            return Response(ContentSerializer(s, many=False, context={"request": request}).data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class GroupContentTypeList(APIView):
    def get(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        serializer = ContentTypeSerializer(igroup.content_types, many=True, context={'request': request})
        return Response(serializer.data)
    def post(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        print(request.data)
        serializer = ContentTypeSerializer(igroup.content_types, many=False, data=request.data)
        if serializer.is_valid():
            print('serializer is valid')
            s = serializer.create(request.data)
            igroup.content_types.add(s)
            return Response(ContentTypeSerializer(s, many=False, context={"request": request}).data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class GroupMembersList(APIView):
    def get(self, request, pk, format=None):
        igroup = InterestGroup.objects.get(pk=pk)
        serializer = UserSerializer(igroup.members, many=True, context={'request': request})
        print(serializer.data)
        return Response(serializer.data)

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all().order_by('-date_joined')
    serializer_class = UserSerializer

class GroupViewSet(viewsets.ModelViewSet):
    queryset = Group.objects.all()
    serializer_class = GroupSerializer