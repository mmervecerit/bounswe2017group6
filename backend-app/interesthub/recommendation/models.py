from django.db import models
from content.models import Content
from group.models import InterestGroup
from user.models import UserProfile

class Tag(models.Model):
    label = models.CharField(max_length=30, null=False, blank=False, unique=True)
    description = models.TextField(default="")
    url = models.CharField(max_length=100, null=True, blank=True)
    contents = models.ManyToManyField(Content, related_name="tags")
    groups = models.ManyToManyField(InterestGroup, related_name="tags")
    users = models.ManyToManyField(UserProfile, related_name="interests")
    related_group = models.ManyToManyField(InterestGroup, related_name="content_tags")

    def __str__(self):
        return str(self.label)