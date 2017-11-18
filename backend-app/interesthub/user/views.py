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

class UserViewSet(mixins.RetrieveModelMixin,mixins.ListModelMixin,viewsets.GenericViewSet):
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
        serializer = UserSerializerFull(data=data)
        if serializer.is_valid():
            user = serializer.create(serializer.validated_data)
            print(type(user))
            serializer = UserSerializerFull(user, context={'request': request})
            return Response(serializer.data)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

class TestView(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    def get(self, request, format=None):
        return Response({"res":"ok"})
