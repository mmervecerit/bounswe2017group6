from components.models import *
from components.serializers import *
from rest_framework import status
from rest_framework.response import Response

serializer_mapping = {
    'text': TextSerializer,
    'longtext': LongTextSerializer,
    'number': NumberSerializer,
    'datetime': DateTimeSerializer,
    'video': VideoSerializer,
}
model_mapping = {
    'text': TextComponent,
    'longtext': LongTextComponent,
    'number': NumberComponent,
    'datetime': DateTimeComponent,
    'video': VideoComponent,
}

def retrieve_component_by_id(pk, context):
    item = Component.objects.get(pk=pk)
    component_serializer = ComponentSerializer(item, many=False)
    data = component_serializer.data

    serializer = serializer_mapping[item.component_type](getattr(item, item.component_type))
    data["type_data"] = serializer.data

    return data

def retrieve_component(item):
    component_serializer = ComponentSerializer(item, many=False)
    data = component_serializer.data

    serializer = serializer_mapping[item.component_type](getattr(item, item.component_type))
    data["type_data"] = serializer.data

    return data

def create_component(data, perform_create=True, content=None):
    type_data = data.pop("type_data")
    component_serializer = ComponentSerializer(many=False, data=data)
    comp = None
    data["type_data"] = type_data

    if not component_serializer.is_valid():
        return (None, component_serializer.errors)
        
    serializer = serializer_mapping[data['component_type']](many=False, data=type_data)

    if not serializer.is_valid():
        return (None, serializer.errors)

    result = None
    if perform_create:
        comp = component_serializer.create(component_serializer.validated_data)
        type_comp = serializer.create(serializer.validated_data)
        type_comp.save()
        setattr(comp, comp.component_type, type_comp)
        if content != None:
            comp.content = content
        comp.save()

        result = component_serializer.data
        result['type_data'] = serializer.data
    
    return (result, None)


def update_component(pk, data):
    pass