from .models import Title, Subtitle, TextArea, Image, Video
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

