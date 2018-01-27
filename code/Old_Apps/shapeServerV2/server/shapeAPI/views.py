from shapeAPI.models import WallConfiguration
from shapeAPI.serializers import WallConfigurationSerializer
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status


@api_view(['GET', 'POST'])
def currentWall(request):
    """
    List all snippets, or create a new snippet.
    """
    if request.method == 'GET':
        current = WallConfiguration.objects.latest('timeChanged')
        serializer = WallConfigurationSerializer(current)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = WallConfigurationSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)