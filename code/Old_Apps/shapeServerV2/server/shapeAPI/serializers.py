from rest_framework import serializers
from shapeAPI.models import WallConfiguration


class WallConfigurationSerializer(serializers.ModelSerializer):
    class Meta:
        model = WallConfiguration
        fields = ('currentWall', 'timeChanged')
