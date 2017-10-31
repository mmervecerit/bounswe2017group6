from django.db import models
from content.models import Content

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

class Component(models.Model):
    TITLE = "title"
    SUBTITLE = "subtitle"
    TEXTAREA = "text"
    VIDEO = "video"
    IMAGE = "image"
    TYPE_CHOICES = (
        (TITLE, "Title"),
        (SUBTITLE, "Subtitle"),
        (TEXTAREA, "Textarea"),
        (VIDEO, "Video"),
        (IMAGE, "Image"),
    )
    component_type = models.CharField(
        max_length=10,
        choices=TYPE_CHOICES,
        default=TEXTAREA
    )

    created_date = models.DateTimeField(auto_now_add=True)
    modified_date = models.DateTimeField(auto_now=True)

    order = models.IntegerField(default=0)
    small_text = models.CharField(max_length=100, null=True, blank=True)
    long_text = models.TextField(null=True, blank=True)
    url = models.URLField(null=True, blank=True)
    content = models.ForeignKey(Content, on_delete=models.CASCADE, null=True, blank=True, related_name="components")

    def __str__(self):
        return self.component_type
