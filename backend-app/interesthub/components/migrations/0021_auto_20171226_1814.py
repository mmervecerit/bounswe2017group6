# -*- coding: utf-8 -*-
# Generated by Django 1.11.6 on 2017-12-26 15:14
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    dependencies = [
        ('components', '0020_auto_20171226_1736'),
    ]

    operations = [
        migrations.AlterField(
            model_name='component',
            name='checkbox',
            field=models.OneToOneField(null=True, on_delete=django.db.models.deletion.CASCADE, related_name='component', to='components.CheckboxComponent'),
        ),
    ]