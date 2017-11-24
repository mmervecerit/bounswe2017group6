from django.db import models
from content.models import Content, ContentType
from django.contrib.auth.models import User

class InterestGroup(models.Model):

    owner = models.ForeignKey(User, on_delete=models.CASCADE, null=True, related_name="owned_groups")

    created_date = models.DateTimeField(auto_now_add=True)
    modified_date = models.DateTimeField(auto_now=True)

    name = models.CharField(max_length=60)
    members = models.ManyToManyField(User, related_name="interest_groups")
    waitings = models.ManyToManyField(User, related_name="group_requests")
    is_public = models.BooleanField(default=True)
    logo = models.URLField(null=True, blank=True)
    cover_photo = models.URLField(null=True, blank=True)
    contents = models.ManyToManyField(Content, related_name="groups")
    content_types = models.ManyToManyField(ContentType, related_name="groups")
    description = models.TextField(null=True, blank=True)

    def __str__(self):
        return str(self.id)
