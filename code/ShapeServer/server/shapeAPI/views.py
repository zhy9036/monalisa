from shapeAPI.models import WallConfiguration
from shapeAPI.models import WallConfigurationCoord
from shapeAPI.models import Color
from shapeAPI.serializers import WallConfigurationSerializer
from shapeAPI.serializers import CoordWallConfigurationSerializer
from shapeAPI.serializers import ColorSerializer
from rest_framework.decorators import api_view
from rest_framework.response import Response
from rest_framework import status
from django.db.models import Count
import collections
import json




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

@api_view(['GET', 'POST'])
def color(request):
    """
    List all snippets, or create a new snippet.
    """
    if request.method == 'GET':
        current = Color.objects.latest('timeChanged')
        serializer = ColorSerializer(current)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = ColorSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

@api_view(['GET', 'POST'])
def currentWallCoord(request):
    """
    List all snippets, or create a new snippet.
    """
    if request.method == 'GET':
        current = WallConfigurationCoord.objects.latest('timeChanged')
        serializer = CoordWallConfigurationSerializer(current)
        return Response(serializer.data)

    elif request.method == 'POST':
        serializer = CoordWallConfigurationSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=status.HTTP_201_CREATED)
        return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)

#Note: this is for ML and would be better arranged as an SQL query.. but its dead week. AKA This is rapid prototype code.
@api_view(['GET'])
def statistics(request):
    if request.method == 'GET':
        results = mostCommon();
        configs = WallConfiguration.objects.all();
        mCnt = collections.Counter()
        aCnt = collections.Counter()
        eCnt = collections.Counter()
        nCnt = collections.Counter()

        mCount = 0;
        aCount = 0;
        eCount = 0;
        nCount = 0;

        for current in configs:
            hour = int(str(current.timeChanged)[11:13])
            if hour >= 6 and hour < 12:
                mCnt[current.currentWall] += 1;
                mCount += 1;
            elif hour >= 12 and hour < 18:
                aCnt[current.currentWall] += 1;
                aCount += 1;
            elif hour >= 18 and hour < 24:
                eCnt[current.currentWall] += 1;
                eCount += 1;
            elif hour >= 00 and hour < 6:
                nCnt[current.currentWall] += 1;
                nCount += 1;
        if (mCount > 0):
            results.morning = mCnt.most_common(1)[0][0]
        else:
            results.morning = 0
        if (aCount > 0):
            results.afternoon = aCnt.most_common(1)[0][0]
        else:
            results.afternoon = 0
        if (eCount > 0):
            results.evening = eCnt.most_common(1)[0][0]
        else:
            results.evening = 0
        if (nCount > 0):
            results.night = nCnt.most_common(1)[0][0]
        else:
            results.night = 0

    return Response(results.toJSON());

class mostCommon():
    morning = 0
    afternoon = 0
    evening = 0
    night = 0
    def toJSON(self):
        return json.dumps(self.__dict__)
