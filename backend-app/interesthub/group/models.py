from django.db import models
from content.models import Content, ContentType
from django.contrib.auth.models import User

# Create your models here.
class InterestGroup(models.Model):
    name = models.CharField(max_length=60)
    members = models.ManyToManyField(User)
    contents = models.ManyToManyField(Content)
    content_types = models.ManyToManyField(ContentType)

    def __str__(self):
        return self.name
