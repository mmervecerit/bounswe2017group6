from django.contrib import admin
from .models import *

# Register your models here.
class UsersInline(admin.TabularInline):
    model = Tag.users.through
    verbose_name = "User"
    verbose_name_plural = "Users"

class ContentsInline(admin.TabularInline):
    model = Tag.contents.through
    verbose_name = "Content"
    verbose_name_plural = "Contents"

class GroupsInline(admin.TabularInline):
    model = Tag.groups.through
    verbose_name = "Group"
    verbose_name_plural = "Groups"

class TagAdmin(admin.ModelAdmin):
    fields = ['label', 'description', 'url']
    list_display = ["label"]
    inlines = [GroupsInline, ContentsInline, UsersInline]

admin.site.register(Tag, TagAdmin)