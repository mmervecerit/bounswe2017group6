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

class DropdownSerializer(serializers.ModelSerializer):
    selected = serializers.PrimaryKeyRelatedField(many=False, queryset=DropdownItem.objects)
    # selected = ResourceRelatedField(
    #     queryset=DropdownItem.objects,
    #     many=False,
    # )
    # selected = serializers.SlugRelatedField(
    #     many=False,
    #     read_only=False,
    #     slug_field='id',
    #     queryset=DropdownItem.objects
    # )
    class Meta:
        model = DropdownComponent
        fields = ('id', 'data', 'selected')

class CheckboxSerializer(serializers.ModelSerializer):
    # selecteds = serializers.SlugRelatedField(
    #     many=True,
    #     read_only=False,
    #     slug_field='id',
    #     queryset=CheckboxItem.objects
    # )
    # selecteds = ResourceRelatedField(
    #     queryset=CheckboxItem.objects,
    #     many=True
    # )
    selecteds = serializers.PrimaryKeyRelatedField(many=True, queryset=CheckboxItem.objects)
    class Meta:
        model = CheckboxComponent
        fields = ('id', 'data', 'selecteds')

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
        'checkbox': CheckboxSerializer,
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
        print("TYPE_DATA", type_data)
        validated_data = super(ComponentSerializer2, self).to_internal_value(data)
        print('validated_data', validated_data)
        print('type_data', type_data)
        print(self.serializer_mapping[data['component_type']])
        serializer = self.serializer_mapping[data['component_type']](many=False, data=type_data)
        print("WTF2")
        if not serializer.is_valid():
            print("NOT VALID")
            print(serializer.errors)
            raise serializers.ValidationError(serializer.errors)
        print("WTF3")
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
