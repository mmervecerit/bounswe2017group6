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

class ImageSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = ImageComponent
        fields = ('id', 'data')

class DropdownItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CheckBoxItem
        fields = ('id', 'is_selected', 'title')

class DropdownSerializer(serializers.HyperlinkedModelSerializer):
    items = DropdownItemSerializer(many=True)
    class Meta:
        model = DropdownComponent
        fields = ('id', 'data', 'items')

    def create(self, validated_data):
        data = validated_data.pop('items')
        dropdown = DropdownComponent.objects.create(**validated_data)
        for item in data:
            DropdownItem.objects.create(dropdown = dropdown, **item)
        return dropdown

class CheckBoxItemSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = CheckBoxItem
        fields = ('id', 'is_selected', 'title')

class CheckBoxSerializer(serializers.HyperlinkedModelSerializer):
    items = CheckBoxItemSerializer(many=True)
    class Meta:
        model = CheckBoxComponent
        fields = ('id', 'data', 'items')
    
    def create(self, validated_data):
        data = validated_data.pop('items')
        checkbox = CheckBoxComponent.objects.create(**validated_data)
        for item in data:
            CheckBoxItem.objects.create(checkbox = checkbox, **item)
        return checkbox

class ComponentSerializer(serializers.ModelSerializer):
    class Meta:
        model = Component
        fields = ("id", "component_type", "order")
    
class ComponentSerializer2(ComponentSerializer):
    serializer_mapping = {
        'text': TextSerializer,
        'longtext': LongTextSerializer,
        'number': NumberSerializer,
        'datetime': DateTimeSerializer,
        'video': VideoSerializer,
        'image': ImageSerializer,
        'checkbox': CheckBoxSerializer,
        'dropdown': DropdownSerializer,
    }

    def to_representation(self, obj):
        response = super(ComponentSerializer2, self).to_representation(obj)
        serializer = self.serializer_mapping[obj.component_type](getattr(obj, obj.component_type))
        response['type_data'] = serializer.to_representation(getattr(obj, obj.component_type))
        return response

    def to_internal_value(self, data):
        print(data)
        data = data.copy()
        print("WTF")
        type_data = data.pop("type_data")
        validated_data = super(ComponentSerializer2, self).to_internal_value(data)
        serializer = self.serializer_mapping[data['component_type']](many=False, data=type_data)
        print("WTF")
        if not serializer.is_valid():
            raise serializers.ValidationError(serializer.errors)
        print("WTF")
        validated_data["type_data"] = serializer.validated_data
        print(validated_data)
        return validated_data

    def create(self, validated_data):
        validated_data = validated_data.copy()
        type_data = validated_data.pop('type_data')
        component = Component.objects.create(**validated_data)
        serializer = self.serializer_mapping[component.component_type](many=False, data=type_data)
        type_comp = serializer.create(type_data)
        setattr(component, component.component_type, type_comp)
        component.save()
        return component

    def update(self, instance, validated_data):
        validated_data = validated_data.copy()
        type_data = validated_data.pop('type_data', None)
        if type_data != None:
            type_comp = getattr(instance, instance.component_type)
            serializer = self.serializer_mapping[instance.component_type](type_comp,many=False, data=type_data)
            serializer.update(type_comp, type_data)
        super(ComponentSerializer2, self).update(instance, validated_data)
        return instance
