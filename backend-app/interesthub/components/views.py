from django.shortcuts import render
from rest_framework import viewsets
from components.models import *
from components.serializers import *
from rest_framework.views import APIView
from .utils import *

# Create your views here.
# class TitleViewSet(viewsets.ModelViewSet):
#     queryset = Title.objects.all()
#     serializer_class = TitleSerializer

# class SubtitleViewSet(viewsets.ModelViewSet):
#     queryset = Subtitle.objects.all()
#     serializer_class = SubtitleSerializer

# class TextAreaViewSet(viewsets.ModelViewSet):
#     queryset = TextArea.objects.all()
#     serializer_class = TextAreaSerializer

# class ImageViewSet(viewsets.ModelViewSet):
#     queryset = Image.objects.all()
#     serializer_class = ImageSerializer

# class VideoViewSet(viewsets.ModelViewSet):
#     queryset = Video.objects.all()
#     serializer_class = VideoSerializer

class ComponentViewSet(viewsets.ModelViewSet):
    queryset = Component.objects.all()
    serializer_class = ComponentSerializer

class Component2ViewSet(APIView):
    def get(self, request, pk, format=None):
        component = Component.objects.get(pk=pk)
        result = retrieve_component(component)
        return Response(result)
    # def post(self, request, pk, format=None):
    #     data = request.data
    #     result, error = create_component(data)
    #     if error == None:
    #         return Response(result)


class ComponentAllViewSet(APIView):
    # def get(self, request, pk, format=None):
    #     component = Component.objects.get(pk=pk)
    #     result = retrieve_component(component)
    #     return Response(result)
    def post(self, request, format=None):
        data = request.data
        result, error = create_component(data)
        if error == None:
            return Response(result)