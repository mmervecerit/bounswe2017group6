from rest_framework import serializers
from .models import InterestGroup
from django.contrib.auth.models import User, Group

class InterestGroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = InterestGroup
        fields = ('id', 'name', 'description', 'image')

class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ('url', 'id', 'username', 'email')

class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ('url', 'id', 'name')