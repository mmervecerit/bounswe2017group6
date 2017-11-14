from django.db import models
from django.contrib.auth.models import User
from content.models import Content

class UpDown(models.Model):
    owner = models.ForeignKey(User, blank=False, null=False, on_delete=models.CASCADE)
    content = models.ForeignKey(Content, blank=False, null=False, on_delete=models.CASCADE)
    isUp = models.BooleanField(default=True)
		
    def __str__(self):
        return str(self.id)
