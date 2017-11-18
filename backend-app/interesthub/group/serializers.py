from rest_framework import serializers
from .models import InterestGroup
from django.contrib.auth.models import User, Group
from user.serializers import UserSerializer
from recommendation.serializers import TagSerializer
from collections import OrderedDict
from recommendation.models import Tag

class IGroupSerializer(serializers.HyperlinkedModelSerializer):

    class Meta:
        model = InterestGroup
        fields = ('id', 'name', 'is_public', 'description', 'logo', 'cover_photo')

class InterestGroupSerializer(serializers.HyperlinkedModelSerializer):
    tags = TagSerializer(many=True, read_only=False)
    class Meta:
        model = InterestGroup
        fields = ('id', 'name', 'is_public', 'description', 'logo', 'cover_photo', 'tags')
    
    def to_internal_value(self, data):
        data = data.copy()
        tags_data = []
        if "tags" in data:
            tags_data = data.pop("tags")

        serializer = IGroupSerializer(data = data, partial=self.partial)

        validated_data = OrderedDict()
        if serializer.is_valid():
            validated_data = serializer.validated_data
        else:
            raise serializers.ValidationError(serializer.errors)

        try:
            validated_data["tags"] = []
            for tag in tags_data:
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
        serializer = IGroupSerializer(data=data)
        instance = None
        if serializer.is_valid():
            instance = serializer.create(serializer.validated_data)

        for tag_data in tags_data:
            tag = Tag.objects.filter(label=tag_data["label"])
            if tag.exists():
                tag = tag.first()
            else:
                serializer = TagSerializer(data=tag_data)
                if serializer.is_valid():
                    tag = serializer.create(serializer.validated_data)
            instance.tags.add(tag)
        instance.save()
        return instance
    
    def update(self, instance, validated_data):
        data = validated_data.copy()
        tags_data = None
        if "tags" in data:
            tags_data = data.pop("tags")
        print(data)
        serializer = IGroupSerializer(instance, data, partial=self.partial)
        if serializer.is_valid():
            serializer.update(instance, serializer.validated_data)
        # super(InterestGroupSerializer, self).update(instance,data)

        if tags_data is not None:
            tag_ids = []
            print(tags_data)
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
            print('---', tag_ids)
            for tag in instance.tags.all():
                if tag.id not in tag_ids:
                    instance.tags.remove(tag)
        instance.save()
        return instance


    


