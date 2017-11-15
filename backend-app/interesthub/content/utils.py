from .models import *
from .serializers import *
from components.models import *
from components.serializers import *
from rest_framework.response import Response
from rest_framework import status
from components.utils import create_component, retrieve_component, retrieve_component_by_id

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

def retrieve_content(pk, context):
    content = Content.objects.get(pk=pk)
    serializer = ContentSerializer(content, many=False, context=context)
    result = serializer.data
    # print(result)

    components = []
    for component in content.components.all():
        print(component)
        components.append( retrieve_component(component) )

    result['components'] = components

    return result


def create_content(data):
    components_data = data.pop('components')
    # content = ContentSerializer(many=False, data=data)
    content_type = ContentType.objects.get(pk=data["content_type_id"])

    content_serializer = ContentSerializer(many=False, data=data)
    if not content_serializer.is_valid():
        return (None, {"error": "given info about content is not valid. (Not including components!!!)"})

    try:
        for component in components_data:
            print(component)
            if component["component_type"] != content_type.components[component["order"]-1]:
                return (None, {"error": "given order of component data list does not match with order of components in the content type"})
            _, error = create_component(component.copy(), perform_create=False)
            if error!=None:
                error["error2"] = "error at %d th component"%component["order"]
                return Response(error, status=status.HTTP_400_BAD_REQUEST )

    except Exception as e:
        print(str(e))
        return (None, {"error": "given data is some how meaningless"})
    
    content = content_serializer.create(content_serializer.validated_data)
    result = content_serializer.validated_data
    result["components"] = []

    for component_data in components_data:
        comp_result, error = create_component(component_data.copy(),content=content)

        result["components"].append(comp_result)
    
    return (result, None)

def update_content(pk, data):
    pass