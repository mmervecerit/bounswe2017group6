from django.db import models
from content.models import Content, ContentType, CheckboxItem, DropdownItem

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
    data = models.URLField(null=True)

class ImageComponent(models.Model):
    data = models.ImageField(
        upload_to='content/',
        max_length=254, blank=True, null=True
    )

class DropdownComponent(models.Model):
    data = models.CharField(max_length=100, null=True)
    selected = models.ForeignKey(DropdownItem, on_delete=models.CASCADE, null=True, related_name="selected_dropdowns")

class CheckboxComponent(models.Model):
    data = models.CharField(max_length=100, null=True)
    selecteds = models.ManyToManyField(CheckboxItem, related_name="selected_checkboxes")

class Component(models.Model):
    
    component_type = models.CharField(
        max_length=10,
        choices=ContentType.TYPE_CHOICES,
        default=ContentType.TEXT
    )

    created_date = models.DateTimeField(auto_now_add=True)
    modified_date = models.DateTimeField(auto_now=True)

    order = models.IntegerField(default=0)

    text = models.OneToOneField(TextComponent, on_delete=models.CASCADE, null=True, related_name="component")
    longtext = models.OneToOneField(LongTextComponent, on_delete=models.CASCADE, null=True, related_name="component")
    number = models.OneToOneField(NumberComponent, on_delete=models.CASCADE, null=True, related_name="component")
    datetime = models.OneToOneField(DateTimeComponent, on_delete=models.CASCADE, null=True, related_name="component")
    video = models.OneToOneField(VideoComponent, on_delete=models.CASCADE, null=True, related_name="component")
    dropdown = models.OneToOneField(DropdownComponent, on_delete=models.CASCADE, null=True, related_name="component")
    image = models.OneToOneField(ImageComponent, on_delete=models.CASCADE, null=True, related_name="component")
    checkbox = models.OneToOneField(CheckboxComponent, on_delete=models.CASCADE, null=True, related_name="component")

    content = models.ForeignKey(Content, on_delete=models.CASCADE, null=True, related_name="components")

    def __str__(self):
        return str(self.id) + "("+ self.component_type +")"
