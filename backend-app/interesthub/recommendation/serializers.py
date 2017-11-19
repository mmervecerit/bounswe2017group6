from rest_framework import serializers
from .models import *
from collections import OrderedDict

class TagSerializer(serializers.ModelSerializer):
    class Meta:
        model = Tag
        fields = ['id', 'label', 'description', 'url']

class TagSerializerFull(serializers.ModelSerializer):
    contents = serializers.PrimaryKeyRelatedField(many=True, read_only=True)
    groups = serializers.PrimaryKeyRelatedField(many=True, read_only=True)
    users = serializers.PrimaryKeyRelatedField(many=True, read_only=True)
    
    class Meta:
        model = Tag
        fields = ['id', 'label', 'description', 'url', 'contents', 'groups', 'users']