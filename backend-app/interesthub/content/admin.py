from django.contrib import admin
from .models import Content, ContentType
# from components.admin import ComponentInline


# Register your models here.
class ContentAdmin(admin.ModelAdmin):
    fields = ["content_type", "owner"]
    readonly_fields = ("id", "created_date", "modified_date",)
    list_display = ["id", "content_type"]
    # inlines = [ComponentInline]

class ContentTypeAdmin(admin.ModelAdmin):
    fields = ["name", "components"]
    readonly_fields = ("id", "created_date", "modified_date",)
    list_display = ["id", "name"]

admin.site.register(ContentType, ContentTypeAdmin)
admin.site.register(Content, ContentAdmin)