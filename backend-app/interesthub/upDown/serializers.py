from rest_framework import serializers
from .models import UpDown
from django.contrib.auth.models import User
from group.serializers import UserSerializer
from content.models import Content
from content.serializers import ContentSerializer

class UpDownSerializer(serializers.HyperlinkedModelSerializer):
    owner = UserSerializer(read_only=True, allow_null=False, many=False)
    owner_id = serializers.IntegerField()
    content = ContentSerializer(read_only=True, allow_null=False, many=False)
    content_id = serializers.IntegerField()

    class Meta:
        model = UpDown
        fields = ('id', 'isUp', 'owner', 'owner_id', 'content', 'content_id')
