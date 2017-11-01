from django.db import models
from content.models import Content, ContentType
from django.contrib.auth.models import User

# Create your models here.
class InterestGroup(models.Model):
    name = models.CharField(max_length=60)
    image = models.URLField(null=True, blank=True)
    description = models.TextField(null=True, blank=True)
    members = models.ManyToManyField(User)
    contents = models.ManyToManyField(Content, related_name="group_content")
    content_types = models.ManyToManyField(ContentType, related_name="group_contenttype")

    def __str__(self):
        return self.name
