from django.contrib import admin
from .models import Component

class ComponentInline(admin.TabularInline):
    fk_name = 'content'
    model = Component
    fields = ["component_type", "order"]
    readonly_fields = []
    verbose_name = "Component"
    verbose_name_plural = "Components"
    show_change_link = True

# class ComponentAdmin(admin.ModelAdmin):
#     fields = ["component_type", "order", "small_text", "long_text", "url"]
#     readonly_fields = ("id", "created_date", "modified_date",)
#     list_display = ["id", "component_type"]

# admin.site.register(Component, ComponentAdmin)