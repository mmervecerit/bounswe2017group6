from django.db import models

# Create your models here.
class Title(models.Model):
    text = models.CharField(max_length=100)

class Subtitle(models.Model):
    text = models.CharField(max_length=100)

class TextArea(models.Model):
    text = models.CharField(max_length=500)
