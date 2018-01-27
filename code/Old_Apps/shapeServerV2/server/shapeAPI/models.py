# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models
from django.utils import timezone


# Create your models here.
class WallConfiguration(models.Model):
    currentWall = models.IntegerField()
    timeChanged = models.DateTimeField(blank=True, default=timezone.now)
