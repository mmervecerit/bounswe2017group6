# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-10-29 14:07
from __future__ import unicode_literals

import django.contrib.postgres.fields
from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('content', '0007_auto_20171029_0234'),
    ]

    operations = [
        migrations.AlterField(
            model_name='contenttype',
            name='components',
            field=django.contrib.postgres.fields.ArrayField(base_field=models.CharField(blank=True, max_length=10), size=15),
        ),
    ]
