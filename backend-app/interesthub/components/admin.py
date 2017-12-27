from django.contrib import admin
from .models import Component

class ComponentAdmin(admin.ModelAdmin):
    fields = ["component_type", "order", "content"]
    readonly_fields = ("id", "created_date", "modified_date",)
    list_display = ["id", "component_type"]

admin.site.register(Component, ComponentAdmin)