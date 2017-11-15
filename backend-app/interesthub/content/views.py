from django.shortcuts import render
from .models import *
from .serializers import *
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework.response import Response
from .utils import *

# Create your views here.
# class ContentViewSet(viewsets.ModelViewSet):
#     queryset = Content.objects.all()
#     serializer_class = ContentSerializer

class ContentTypeViewSet(viewsets.ModelViewSet):
    queryset = ContentType.objects.all()
    serializer_class = ContentTypeSerializer



class ContentViewSet(APIView):
    def get(self, request, pk, format=None):
        result = retrieve_content(pk,{"request": request})
        return Response(result)

    # def post(self, request, pk, format=None):
    #     result, error, item = create_content(request.data)
    #     if error == None:
    #         return Response(result)
    #     else:
    #         return Response(error, status=status.HTTP_400_BAD_REQUEST)

class ContentAllViewSet(APIView):
    def get(self, request, format=None):
        items = Content.objects.all()
        result = retrieve_content_set(items,{"request": request})
        return Response(result)

    def post(self, request, format=None):
        result, error, item = create_content(request.data)
        if error == None:
            return Response(result)
        else:
            return Response(error, status=status.HTTP_400_BAD_REQUEST)

# class ContentViewSet(APIView):
#     def get(self, request, pk, format=None):
#         igroup = InterestGroup.objects.get(pk=pk)
#         serializer = ContentTypeSerializer(igroup.content_types, many=True, context={'request': request})
#         return Response(serializer.data)
#     def post(self, request, pk, format=None):
#         igroup = InterestGroup.objects.get(pk=pk)
#         print(request.data)
#         serializer = ContentTypeSerializer(igroup.content_types, many=False, data=request.data)
#         if serializer.is_valid():
#             print('serializer is valid')
#             s = serializer.create(request.data)
#             igroup.content_types.add(s)
#             return Response(ContentTypeSerializer(s, many=False, context={"request": request}).data)
#         return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)