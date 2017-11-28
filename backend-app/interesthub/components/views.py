from django.shortcuts import render
from rest_framework import viewsets
from components.models import *
from components.serializers import *
from rest_framework.views import APIView
from .utils import *
from rest_framework_jwt.authentication import JSONWebTokenAuthentication
from rest_framework.permissions import IsAuthenticated
from interesthub.permissions import IsComponentOwner

class ComponentViewSet(viewsets.ModelViewSet):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated,)
    queryset = Component.objects.all()
    serializer_class = ComponentSerializer2

class UploadImage(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated, IsComponentOwner, )
    def post(self, request, pk, format=None):
        comp = None
        img_comp = None
        try:
            comp = Component.objects.get(pk=pk)
            img_comp = comp.image
            print(img_comp)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
        try:
            file = request.data['file']
            file._set_name("image_%s_%s"%(str(img_comp.id),file._get_name()))
            img_comp.data = file
            img_comp.save()
        except KeyError:
            return Response({"error": "Request has no resource file attached"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "OK"})