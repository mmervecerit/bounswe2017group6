from .models import *
from rest_framework import serializers

class TextSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = TextComponent
        fields = ('id', 'data',)

class LongTextSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = LongTextComponent
        fields = ('id', 'data',)

class NumberSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = NumberComponent
        fields = ('id', 'data',)
        
class DateTimeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = DateTimeComponent
        fields = ('id', 'data')

class VideoSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = VideoComponent
        fields = ('id', 'data')

class ComponentSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Component
        fields = ("id", "component_type", "order")