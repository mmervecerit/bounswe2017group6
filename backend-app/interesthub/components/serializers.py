from .models import Title, Subtitle, TextArea
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
