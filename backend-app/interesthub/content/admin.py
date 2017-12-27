from django.contrib import admin
from .models import *
from components.models import Component

# class ComponentsInline(admin.TabularInline):
#     model = Content.components.through
#     verbose_name = "component"
#     verbose_name_plural = "components"

class ContentAdmin(admin.ModelAdmin):
    fields = ["content_type", "owner"]
    readonly_fields = ("id", "created_date", "modified_date",)
    list_display = ["id", "content_type"]
    # inlines = [ComponentsInline,]

class ContentTypeAdmin(admin.ModelAdmin):
    fields = ["name","components", "component_names", "is_default"]
    readonly_fields = ("id", "created_date", "modified_date")
    list_display = ["id", "name"]

class CommentAdmin(admin.ModelAdmin):
    fields = ["text", "owner", "content"]
    list_display = ["id", "owner", "content"]

class UpDownAdmin(admin.ModelAdmin):
    fields = ["isUp", "owner", "content"]
    list_display = ["id", "isUp", "owner", "content"]

admin.site.register(ContentType, ContentTypeAdmin)
admin.site.register(Content, ContentAdmin)
admin.site.register(Comment, CommentAdmin)
admin.site.register(UpDown, UpDownAdmin)
