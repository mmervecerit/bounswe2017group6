from django.contrib.auth.models import User, Group
from rest_framework import viewsets
from dummy.models import DummyText
from dummy.serializers import UserSerializer, GroupSerializer, DummyTextSerializer

class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all().order_by('-date_joined')
    serializer_class = UserSerializer

class GroupViewSet(viewsets.ModelViewSet):
    queryset = Group.objects.all()
    serializer_class = GroupSerializer

class DummyTextViewSet(viewsets.ModelViewSet):
    queryset = DummyText.objects.all()
    serializer_class = DummyTextSerializer