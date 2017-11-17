from django.db import models
from django.contrib.auth.models import User
from content.models import Content

class Comment(models.Model):
    text = models.TextField(null=False, blank=True)
    owner = models.ForeignKey(User, blank=False, null=False, on_delete=models.CASCADE, related_name="comment_owner")
    content = models.ForeignKey(Content, blank=False, null=False, on_delete=models.CASCADE)
		
    def __str__(self):
        return str(self.id)
