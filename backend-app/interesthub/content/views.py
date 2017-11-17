from django.shortcuts import render
from .models import *
from .serializers import *
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework.response import Response
from .utils import *
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.permissions import IsAuthenticated

class ContentTypeViewSet(viewsets.ModelViewSet):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    queryset = ContentType.objects.all()
    serializer_class = ContentTypeSerializer

class ContentViewSet(viewsets.ModelViewSet):
    # authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    queryset = Content.objects.all()
    serializer_class = ContentSerializer