from django.shortcuts import render
from .models import *
from .serializers import *
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework.response import Response
from django.shortcuts import render
from rest_framework import status
from rest_framework import viewsets, mixins
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.permissions import IsAuthenticated
from django.contrib.auth.models import User, Group
from content.serializers import ContentSerializer

class UserViewSet(mixins.RetrieveModelMixin,mixins.ListModelMixin,mixins.UpdateModelMixin,viewsets.GenericViewSet):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    queryset = User.objects.all().order_by('-date_joined')
    serializer_class = UserSerializerFull

class GroupViewSet(viewsets.ModelViewSet):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    queryset = Group.objects.all()
    serializer_class = GroupSerializer

class UserRegisterView(APIView):
    def post(self, request, format=None):
        data = request.data
        password = data.get("password", None)
        serializer = UserSerializerFull(data=data)
        if password is None:
            return Response({"error":"no given password"}, status=status.HTTP_400_BAD_REQUEST)    
        if serializer.is_valid():
            user = serializer.create(serializer.validated_data)
            user.set_password(password)
            user.save()
            print(type(user))
            serializer = UserSerializerFull(user, context={'request': request})
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class FollowerView(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, foramt=None):
        user = request.user
        serializer = UserSerializer(user.profile.followers.all(), many=True)
        return Response(serializer.data)
        

class FollowingView(APIView):
    def get(self, request, format=None):
        user = request.user
        serializer = UserSerializer(user.profile.followings, many=True)
        return Response(serializer.data)
    def post(self, request, format=None):
        user = request.user
        data = request.data
        if 'id' in data:
            try:
                followed_user = User.objects.get(pk=data["id"])
                user.followings.add(followed_user.profile)
                user.profile.followings.add(followed_user)
                serializer = UserSerializer(followed_user)
                return Response({"followed_user":serializer.data})
            except Exception as e:
                return Response({"error":str(e)}, status=status.HTTP_400_BAD_REQUEST)
        
        return Response({"error":"you should specify and id to follow"}, status=status.HTTP_400_BAD_REQUEST)

class UserContentsList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        user = User.objects.get(pk=pk)
        serializer = ContentSerializer(user.content_owner.all(), many=True)
        return Response(serializer.data)

class UserFollowersList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        user = User.objects.get(pk=pk)
        serializer = UserSerializer(user.profile.followers, many=True)
        return Response(serializer.data)

class UserFollowingsList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, pk, format=None):
        user = User.objects.get(pk=pk)
        serializer = UserSerializer(user.profile.followings, many=True)
        return Response(serializer.data)

class MeView(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        user = request.user
        serializer = UserSerializerFull(user, many=False)
        return Response(serializer.data)

