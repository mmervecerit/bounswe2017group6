from django.shortcuts import render
from rest_framework import viewsets
from components.models import Title, Subtitle, TextArea
from components.serializers import TitleSerializer, SubtitleSerializer, TextAreaSerializer

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