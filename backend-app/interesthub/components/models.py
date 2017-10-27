from django.db import models

# Create your models here.
class Title(models.Model):
    text = models.CharField(max_length=100)

class Subtitle(models.Model):
    text = models.CharField(max_length=100)

class TextArea(models.Model):
    text = models.TextField()

class Video(models.Model):
    url = models.CharField(max_length=500)

class Image(models.Model):
    url = models.CharField(max_length=500)