# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from django.utils import timezone


# Create your models here.
class WallConfiguration(models.Model):
    currentWall = models.IntegerField()
    timeChanged = models.DateTimeField(blank=True, default=timezone.now)
    deviceID = models.IntegerField()


class WallConfigurationCoord(models.Model):
    x = models.IntegerField()
    y = models.IntegerField()
    w = models.IntegerField()
    h = models.IntegerField()
    timeChanged = models.DateTimeField(blank=True, default=timezone.now)
    deviceID = models.IntegerField()

# Create your models here.
class Color(models.Model):
    r = models.IntegerField()
    g = models.IntegerField()
    b = models.IntegerField()
    timeChanged = models.DateTimeField(blank=True, default=timezone.now)
