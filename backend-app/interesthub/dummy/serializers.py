
from .models import DummyText
from rest_framework import serializers

class DummyTextSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = DummyText
        fields = ('id', 'text')
