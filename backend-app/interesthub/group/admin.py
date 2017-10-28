from django.contrib import admin
from .models import InterestGroup

class ContentsInline(admin.TabularInline):
    model = InterestGroup.contents.through

class ContentTypesInline(admin.TabularInline):
    model = InterestGroup.content_types.through

# Register your models here.
class InterestGroupAdmin(admin.ModelAdmin):
    fields = ['name',]
    readonly_fields = ('id',)
    list_display = ["id", "name"]
    inlines = [ContentsInline, ContentTypesInline]

admin.site.register(InterestGroup, InterestGroupAdmin)