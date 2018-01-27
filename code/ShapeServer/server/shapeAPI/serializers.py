from rest_framework import serializers
from shapeAPI.models import WallConfiguration
from shapeAPI.models import WallConfigurationCoord
from shapeAPI.models import Color



class WallConfigurationSerializer(serializers.ModelSerializer):
    class Meta:
        model = WallConfiguration
        fields = ('currentWall', 'timeChanged', 'deviceID')


class CoordWallConfigurationSerializer(serializers.ModelSerializer):
    class Meta:
        model = WallConfigurationCoord
        fields = ('x', 'y', 'w', 'h', 'timeChanged', 'deviceID')

class ColorSerializer(serializers.ModelSerializer):
    class Meta:
        model = Color
        fields = ('r', 'g', 'b', 'timeChanged')