from django.db import models
from django.contrib.postgres.fields import JSONField
from django.contrib.auth.models import User

# Create your models here.

class ContentType(models.Model):
    created_date = models.DateTimeField(auto_now_add=True)
    modified_date = models.DateTimeField(auto_now=True)
    components = JSONField()

class Content(models.Model):
    created_date = models.DateTimeField(auto_now_add=True)
    modified_date = models.DateTimeField(auto_now=True)
    content_type = models.ForeignKey(ContentType, blank=True, null=True, on_delete=models.CASCADE)
    owner = models.ForeignKey(User, blank=False, null=False, on_delete=models.CASCADE)
    data = JSONField()