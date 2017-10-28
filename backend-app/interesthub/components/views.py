from django.shortcuts import render
from rest_framework import viewsets
from components.models import Title, Subtitle, TextArea, Image, Video
from components.serializers import TitleSerializer, SubtitleSerializer, TextAreaSerializer, ImageSerializer, VideoSerializer

# Create your views here.
class TitleViewSet(viewsets.ModelViewSet):
    queryset = Title.objects.all()
    serializer_class = TitleSerializer

class SubtitleViewSet(viewsets.ModelViewSet):
    queryset = Subtitle.objects.all()
    serializer_class = SubtitleSerializer

class TextAreaViewSet(viewsets.ModelViewSet):
    queryset = TextArea.objects.all()
    serializer_class = TextAreaSerializer

class ImageViewSet(viewsets.ModelViewSet):
    queryset = Image.objects.all()
    serializer_class = ImageSerializer

class VideoViewSet(viewsets.ModelViewSet):
    queryset = Video.objects.all()
    serializer_class = VideoSerializer