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
from interesthub.permissions import canSeeGroup, CanSeeUser, IsAdminOf

# Create your views here.
class InterestGroupViewSet(ModelViewSet):
    queryset = InterestGroup.objects.all()
    serializer_class = InterestGroupSerializer

class GroupContentList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated, canSeeGroup)
    def get(self, request, pk, format=None):
        try:
            igroup = InterestGroup.objects.get(pk=pk)
            print(igroup)
            serializer = ContentSerializer(igroup.contents.all(), many=True)
            return Response(serializer.data)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
    
    def post(self, request, pk, format=None):
        try:
            igroup = InterestGroup.objects.get(pk=pk)
            serializer = ContentSerializer(igroup.contents, many=False, data=request.data, context={"request": request})
            if not serializer.is_valid():
                return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
            content = serializer.create(serializer.validated_data)
            igroup.contents.add(content)
            igroup.save()
            return Response(ContentSerializer(content,context={'request':request}).data)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

class GroupContentTypeList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated, canSeeGroup)
    def get(self, request, pk, format=None):
        try:
            igroup = InterestGroup.objects.get(pk=pk)
            serializer = ContentTypeSerializer(igroup.content_types, many=True, context={'request': request})
            return Response(serializer.data)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

    def post(self, request, pk, format=None):
        try:
            igroup = InterestGroup.objects.get(pk=pk)
            serializer = ContentTypeSerializer(igroup.content_types, many=False, data=request.data, context={"request": request})
            if serializer.is_valid():
                s = serializer.create(request.data)
                igroup.content_types.add(s)
                return Response(ContentTypeSerializer(s, many=False, context={"request": request}).data)
            return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

class GroupMembersList(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated, canSeeGroup)
    def get(self, request, pk, format=None):
        try:
            igroup = InterestGroup.objects.get(pk=pk)
            serializer = UserSerializer(igroup.members, many=True, context={'request': request})
            print(serializer.data)
            return Response(serializer.data)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
    def post(self, request, pk, format=None):
        try:
            igroup = InterestGroup.objects.get(pk=pk)
            igroup.members.add(request.user)
            igroup.save()
            return Response({"message":"user is joined this group."})
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
    def delete(self, request, pk, format=None):
        try:
            igroup = InterestGroup.objects.get(pk=pk)
            if request.user in igroup.members.all():
                igroup.members.remove(request.user)
            igroup.save()
            return Response({"message":"user is removed from this group."})
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)

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

class GroupLogo(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated, IsAdminOf)
    def post(self, request, pk, format=None):
        group = None
        try:
            group = InterestGroup.objects.get(pk=pk)
            print(group)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
        try:
            file = request.data['file']
            file._set_name("logo_%s_%s"%(str(group.id),file._get_name()))
            group.logo_img = file
            group.save()
        except KeyError:
            return Response({"error": "Request has no resource file attached"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "OK"})

class GroupCover(APIView):
    authentication_classes = (JSONWebTokenAuthentication, )
    permission_classes = (IsAuthenticated, IsAdminOf)
    def post(self, request, pk, format=None):
        group = None
        try:
            group = InterestGroup.objects.get(pk=pk)
            print(group)
        except Exception as e:
            return Response({"error": str(e)}, status=status.HTTP_400_BAD_REQUEST)
        try:
            file = request.data['file']
            file._set_name("cover_%s_%s"%(str(group.id),file._get_name()))
            group.cover_img = file
            group.save()
        except KeyError:
            return Response({"error": "Request has no resource file attached"}, status=status.HTTP_400_BAD_REQUEST)
        return Response({"status": "OK"})
