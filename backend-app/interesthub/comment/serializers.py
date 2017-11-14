from rest_framework import serializers
from .models import Comment
from django.contrib.auth.models import User
from group.serializers import UserSerializer
from content.models import Content
from content.serializers import ContentSerializer

class CommentSerializer(serializers.HyperlinkedModelSerializer):
    text = serializers.CharField()
    owner = UserSerializer(read_only=True, allow_null=False, many=False)
    owner_id = serializers.IntegerField()
    content = ContentSerializer(read_only=True, allow_null=False, many=False)
    content_id = serializers.IntegerField()

    class Meta:
        model = Comment
        fields = ("id", "text", "owner", "owner_id", "content", "content_id")

    def create(self, validated_data):
        print('val:', validated_data)
        comment = Comment.objects.create(**validated_data)
        return comment

    def update(self, instance, validated_data):
        print(instance)
        instance.text = validated_data.get('text', instance.text)
        instance.owner = validated_data.get('owner', instance.owner)
        instance.content = validated_data.get('content', instance.content)
        instance.save()
        return instance
