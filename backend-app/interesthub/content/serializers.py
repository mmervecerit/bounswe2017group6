from rest_framework import serializers
from components.serializers import ComponentSerializer, ComponentSerializer2
from .models import *
from django.contrib.auth.models import User
from group.serializers import UserSerializer
from components.models import Component
from collections import OrderedDict
from django.contrib.auth.models import User
from recommendation.serializers import TagSerializer
from recommendation.models import Tag

class ContentTypeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = ContentType
        fields = ("id", "name", "components", "component_names")

class ContentSerializer(serializers.HyperlinkedModelSerializer):
    components = ComponentSerializer2(many=True)
    owner = UserSerializer(read_only=True, allow_null=False, many=False)
    content_type = ContentTypeSerializer(read_only=True, allow_null=True, many=False)
    tags = TagSerializer(many=True, read_only=False)

    class Meta:
        model = Content
        fields = ("id", "content_type", "created_date", "modified_date", "owner", 'components', 'tags')
    
    def to_representation(self, obj):
        response = OrderedDict()
        response['owner'] = UserSerializer(obj.owner, context=self.context).data
        response['content_type'] = ContentTypeSerializer(obj.content_type, context=self.context).data
        response['components'] = ComponentSerializer2(obj.components.all(), many=True, context=self.context).data
        response['tags'] = TagSerializer(obj.tags.all(), many=True, context=self.context).data
        response['created_date'] = str(obj.created_date)
        response['modified_date'] = str(obj.modified_date)
        return response
    
    def to_internal_value(self, data):
        data = data.copy()
        validated_data = OrderedDict()
        print("to_internal_value")
        try:
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
        
        except Exception as e:
            if not self.partial:
                raise serializer.ValidationError(str(e))

        # print("to_internal_value")

        try:
            if "tags" in data:
                validated_data["tags"] = []
            for tag in data["tags"]:
                t = Tag.objects.filter(label=tag["label"])
                if t.exists():
                    validated_data["tags"].append(tag)
                else:
                    serializer = TagSerializer(data = tag)
                    if serializer.is_valid():
                        validated_data["tags"].append(serializer.validated_data)
                    else:
                        ValidationError(serializer.errors)
        except Exception as e:
            if not self.partial:
                raise serializers.ValidationError(str(e))

        return validated_data
    
    def create(self, validated_data):
        data = validated_data.copy()
        tags_data = data.pop("tags")
        content = Content.objects.create(owner=self.context["request"].user, content_type_id=data["content_type_id"])
        for comp_data in data["components"]:
            serializer = ComponentSerializer2(data=comp_data, context=self.context)
            if serializer.is_valid():
                comp = serializer.create(serializer.validated_data)
                comp.save()
                content.components.add(comp)
        content.save()

        for tag_data in tags_data:
            tag = Tag.objects.filter(label=tag_data["label"])
            if tag.exists():
                tag = tag.first()
            else:
                serializer = TagSerializer(data=tag_data)
                if serializer.is_valid():
                    tag = serializer.create(serializer.validated_data)
            content.tags.add(tag)
        content.save()
        return content
    
    def update(self, instance, validated_data):
        print("---update---")
        data = validated_data.copy()

        instance.owner_id = data.get("owner_id",instance.owner_id)
        instance.content_type_id = data.get("content_type_id", instance.content_type_id)
        instance.save()

        if "components" in data:
            for comp_data in data["components"]:
                comp = instance.components.filter(order = comp_data["order"]).first()
                serializer = ComponentSerializer2(comp, many=False, data=comp_data, context=self.context)
                if serializer.is_valid():
                    comp = serializer.update(comp, serializer.validated_data)
        instance.save()

        print(data)
        
        if "tags" in data:
            tag_ids = []
            print("we have some tags")
            tags_data = data["tags"]
            for tag_data in tags_data:
                tag = Tag.objects.filter(label=tag_data["label"])
                if tag.exists():
                    tag = tag.first()
                    t = instance.tags.filter(id=tag.id)
                    if t.exists():
                        tag_ids.append(tag.id)
                        continue
                else:
                    serializer = TagSerializer(data=tag_data)
                    if serializer.is_valid():
                        tag = serializer.create(serializer.validated_data)
                tag_ids.append(tag.id)
                instance.tags.add(tag)
            for tag in instance.tags.all():
                if tag.id not in tag_ids:
                    instance.tags.remove(tag)
        instance.save()
        return instance

class CommentSerializer(serializers.HyperlinkedModelSerializer):
    owner = UserSerializer(read_only=True, allow_null=False, many=False)
    content = serializers.PrimaryKeyRelatedField(many=False, read_only=True)

    class Meta:
        model = Comment
        fields = ("id", "created_date", "modified_date", "text", "owner", "content",)
        read_only_fields = ["owner", "created_date", "modified_date", "content"]

    def to_internal_value(self, data):
        validated_data = OrderedDict()
        if "text" in data:
            validated_data["text"] = data["text"]
        elif not self.partial:
            raise serializers.ValidationError("text field is not found")
        if "content_id" in data:
            validated_data["content_id"] = data["content_id"]
        elif not self.partial:
            raise serializers.ValidationError("content_id is not found")
        return validated_data

    def create(self, validated_data):
        print('val:', validated_data)
        comment = Comment.objects.create(**validated_data, owner=self.context["request"].user)
        return comment

    def update(self, instance, validated_data):
        print(instance)
        instance.text = validated_data.get('text', instance.text)
        instance.owner = self.context["request"].user
        instance.save()
        return instance

class UpDownSerializer(serializers.HyperlinkedModelSerializer):
    owner = serializers.PrimaryKeyRelatedField(many=False, read_only=True)
    content = serializers.PrimaryKeyRelatedField(many=False, read_only=True)

    class Meta:
        model = UpDown
        fields = ('id', 'isUp', 'owner', 'content',)
    
    def to_internal_value(self, data):
        validated_data = OrderedDict()
        if "isUp" in data:
            validated_data["isUp"] = data["isUp"]
        elif not self.partial:
            raise serializers.ValidationError("isUp field is not found")
        if "content_id" in data:
            validated_data["content_id"] = data["content_id"]
        elif not self.partial:
            raise serializers.ValidationError("content_id is not found")
        return validated_data
    
    def create(self, validated_data):
        t = UpDown.objects.filter(owner=self.context["request"].user, content_id=validated_data["content_id"])
        if t.exists():
            return t.first()
        upDown = UpDown.objects.create(**validated_data, owner=self.context["request"].user)
        return upDown
    
    def update(self, instance, validated_data):
        instance.text = validated_data.get('upDown', instance.upDown)
        instance.owner = self.context["request"].user
        instance.save()
        return instance
