from django.contrib.auth.models import User, Group
from rest_framework import viewsets
from dummy.models import DummyText
from dummy.serializers import  DummyTextSerializer

class DummyTextViewSet(viewsets.ModelViewSet):
    queryset = DummyText.objects.all()
    serializer_class = DummyTextSerializer