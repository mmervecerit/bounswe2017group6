from rest_framework import serializers
from components.serializers import ComponentSerializer, ComponentSerializer2
from .models import *
from django.contrib.auth.models import User
from group.serializers import UserSerializer
from components.models import Component
from collections import OrderedDict
from django.contrib.auth.models import User

class ContentTypeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = ContentType
        fields = ("id", "name", "components", "component_names")

class ContentSerializer(serializers.HyperlinkedModelSerializer):
    components = ComponentSerializer2(many=True)
    owner = UserSerializer(read_only=True, allow_null=False, many=False)
    content_type = ContentTypeSerializer(read_only=True, allow_null=True, many=False)

    class Meta:
        model = Content
        fields = ("id", "content_type", "created_date", "modified_date", "owner", 'components')
    
    def to_representation(self, obj):
        response = OrderedDict()
        response['owner'] = UserSerializer(obj.owner, context=self.context).data
        response['content_type'] = ContentTypeSerializer(obj.content_type, context=self.context).data
        response['components'] = ComponentSerializer2(obj.components.all(), many=True, context=self.context).data
        response['created_date'] = str(obj.created_date)
        response['modified_date'] = str(obj.modified_date)
        return response
    
    def to_internal_value(self, data):
        data = data.copy()
        validated_data = OrderedDict()

        if not User.objects.filter(id=data['owner_id']).exists():
            raise serializers.ValidationError("No user with given owner_id.")
        validated_data["owner_id"] = data["owner_id"]
        
        if not ContentType.objects.filter(id=data['content_type_id']).exists():
            raise serializers.ValidationError("No content type with given content_type_id.")
        content_type = ContentType.objects.get(pk=data['content_type_id'])
        validated_data["content_type_id"] = data["content_type_id"]

        validated_data["components"] = []

        if len(data["components"]) != len(content_type.components):
            raise serializers.ValidationError("Number of components does not match with content type.")

        for component in data["components"]:
            serializer = ComponentSerializer2(data=component, context=self.context)
            if not serializer.is_valid():
                error = serializer.errors
                raise serializer.ValidationError(error)
            if component["component_type"] != content_type.components[component["order"]-1]:
                raise serializers.ValidationError("Order of the components does not match with content type")

            validated_data["components"].append(serializer.validated_data)

        return validated_data
    
    def create(self, validated_data):
        data = validated_data.copy()
        content = Content.objects.create(owner_id=data["owner_id"], content_type_id=data["content_type_id"])
        for comp_data in data["components"]:
            serializer = ComponentSerializer2(data=comp_data, context=self.context)
            if serializer.is_valid():
                comp = serializer.create(serializer.validated_data)
                comp.save()
                content.components.add(comp)
        content.save()
        return content
    
    def update(self, instance, validated_data):
        data = validated_data.copy()
        instance.owner_id = data["owner_id"]
        instance.content_type_id = data["content_type_id"]
        instance.save()

        if "components" in data:
            for comp_data in data["components"]:
                comp = instance.components.filter(order = comp_data["order"]).first()
                serializer = ComponentSerializer2(comp, many=False, data=comp_data, context=self.context)
                if serializer.is_valid():
                    comp = serializer.update(comp, serializer.validated_data)
        instance.save()
        return instance

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

class UpDownSerializer(serializers.HyperlinkedModelSerializer):
    owner = UserSerializer(read_only=True, allow_null=False, many=False)
    owner_id = serializers.IntegerField()
    content = ContentSerializer(read_only=True, allow_null=False, many=False)
    content_id = serializers.IntegerField()

    class Meta:
        model = UpDown
        fields = ('id', 'isUp', 'owner', 'owner_id', 'content', 'content_id')
