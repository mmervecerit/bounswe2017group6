from rest_framework import serializers
from components.serializers import ComponentSerializer
from .models import Content, ContentType
from django.contrib.auth.models import User
from group.serializers import UserSerializer
from components.models import Component

class ContentTypeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = ContentType
        fields = ("id", "name", "components")

class ContentSerializer(serializers.HyperlinkedModelSerializer):
    components = ComponentSerializer(many=True)
    owner = UserSerializer(read_only=True, allow_null=False, many=False)
    content_type = ContentTypeSerializer(read_only=True, allow_null=True, many=False)
    # owner = serializers.HyperlinkedRelatedField(read_only=True, view_name='content_owner', allow_null=False)
    # content_type = serializers.HyperlinkedRelatedField(read_only=True, view_name='content-contenttype', allow_null=True)
    # content_type = ContentTypeSerializer(many=False)
    # components = serializers.PrimaryKeyRelatedField(many=True, read_only=True)

    class Meta:
        model = Content
        fields = ("id", "content_type", "created_date", "modified_date", "components", "owner")
    
    def create(self, validated_data):
        print('val:', validated_data)
        components_data = validated_data.pop('components')
        content = Content.objects.create(**validated_data)
        for component_data in components_data:
            Component.objects.create(content=content, **component_data)
        return content

    def update(self, instance, validated_data):
        components_data = validated_data.pop('components')
        print(instance)
        # instance.owner = validated_data.get('owner_id', instance.owner)
        instance.content_type = validated_data.get('content_type', instance.content_type)
        for component_data in components_data:
            if component_data.get('id', -1) == -1:
                Component.objects.create(content=content, **component_data)
            else:
                comp = Component.objects.get(pk=component_data.get('id'))
                comp.component_type = component_data.get('component_type', comp.component_type)
                comp.small_text = component_data.get('small_text', comp.small_text)
                comp.order = component_data.get('order', comp.order)
                comp.long_text = component_data.get('long_text', comp.long_text)
                comp.url = component_data.get('url', comp.url)
                comp.save()

