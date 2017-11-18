from django.shortcuts import render
from rest_framework import viewsets
from components.models import *
from components.serializers import *
from rest_framework.views import APIView
from .utils import *
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.permissions import IsAuthenticated

class ComponentViewSet(viewsets.ModelViewSet):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    queryset = Component.objects.all()
    serializer_class = ComponentSerializer2