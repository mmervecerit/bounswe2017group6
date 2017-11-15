from django.shortcuts import render
from .models import UpDown
from .serializers import UpDownSerializer
from rest_framework import viewsets

class UpDownViewSet(viewsets.ModelViewSet):
    queryset = UpDown.objects.all()
    serializer_class = UpDownSerializer
