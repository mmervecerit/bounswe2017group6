from django.db import models
from content.models import Content, ContentType

# Create your models here.
class TextComponent(models.Model):
    data = models.CharField(max_length=100)

class LongTextComponent(models.Model):
    data = models.TextField()

class NumberComponent(models.Model):
    data = models.DecimalField(decimal_places=5, max_digits=15)

class DateTimeComponent(models.Model):
    data = models.DateTimeField()

class VideoComponent(models.Model):
    data = models.URLField()

# class ImageComponent(models.Model):
#     data = 

# class DropdownComponent(models.Model):
    
# class CheckBoxComponent(models.Model):

class Component(models.Model):
    
    component_type = models.CharField(
        max_length=10,
        choices=ContentType.TYPE_CHOICES,
        default=ContentType.TEXT
    )

    created_date = models.DateTimeField(auto_now_add=True)
    modified_date = models.DateTimeField(auto_now=True)

    order = models.IntegerField(default=0)

    text = models.OneToOneField(TextComponent, on_delete=models.CASCADE, null=True)
    longtext = models.OneToOneField(LongTextComponent, on_delete=models.CASCADE, null=True)
    number = models.OneToOneField(NumberComponent, on_delete=models.CASCADE, null=True)
    datetime = models.OneToOneField(DateTimeComponent, on_delete=models.CASCADE, null=True)
    video = models.OneToOneField(VideoComponent, on_delete=models.CASCADE, null=True)
    content = models.ForeignKey(Content, on_delete=models.CASCADE, null=True, related_name="components")

    def __str__(self):
        return str(self.id) + "("+ self.component_type +")"
