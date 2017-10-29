from .models import *
from rest_framework import serializers

class TitleSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Title
        fields = ('text',)

class SubtitleSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Subtitle
        fields = ('text',)

class TextAreaSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Title
        fields = ('text',)
        
class ImageSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Image
        fields = ('id', 'url')
class VideoSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Video
        fields = ('id', 'url')

class ComponentSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Component
        fields = ("id", "component_type", "order", "small_text", "long_text", "url")