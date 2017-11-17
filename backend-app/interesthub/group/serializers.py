from rest_framework import serializers
from .models import InterestGroup
from django.contrib.auth.models import User, Group
from user.serializers import UserSerializer

class InterestGroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = InterestGroup
        fields = ('id', 'name', 'description', 'image')