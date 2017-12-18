from django.db import models
from content.models import Content, ContentType
from django.contrib.auth.models import User

from django.db.models.signals import pre_delete, post_save
from django.dispatch import receiver

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
    logo_img = models.ImageField(
        upload_to='group/',
        max_length=254, blank=True, null=True
    )
    cover_img = models.ImageField(
        upload_to='group/',
        max_length=254, blank=True, null=True
    )

    contents = models.ManyToManyField(Content, related_name="groups")
    content_types = models.ManyToManyField(ContentType, related_name="groups")
    description = models.TextField(null=True, blank=True)

    def __str__(self):
        return str(self.id)

@receiver(pre_delete, sender=InterestGroup)
def group_deletion(sender, instance, using, **kwargs):
    # print(instance.id)
    for content in instance.contents.all():
        print(content)
        content.delete()

@receiver(post_save, sender=InterestGroup)
def group_save(sender, instance, using, **kwargs):
    # need to implement auto add default templates
    # print(sender, instance, using, kwargs)
    # instance.members.add(instance.owner)
    pass