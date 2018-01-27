# -*- coding: utf-8 -*-
from __future__ import unicode_literals
# Register your models here.
from django.contrib import admin
from .models import WallConfiguration
from .models import WallConfigurationCoord
from .models import Color

admin.site.register(WallConfiguration)
admin.site.register(WallConfigurationCoord)
admin.site.register(Color)
