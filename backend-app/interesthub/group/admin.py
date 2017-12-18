from django.contrib import admin
from .models import InterestGroup

class ContentsInline(admin.TabularInline):
    model = InterestGroup.contents.through
    verbose_name = "content"
    verbose_name_plural = "contents"

class ContentTypesInline(admin.TabularInline):
    model = InterestGroup.content_types.through
    verbose_name = "content type"
    verbose_name_plural = "content types"

class MembersInline(admin.TabularInline):
    model = InterestGroup.members.through
    verbose_name = "member"
    verbose_name_plural = "members"

class WaitingsInline(admin.TabularInline):
    model = InterestGroup.waitings.through
    verbose_name = "waiting user"
    verbose_name_plural = "waiting users"

class TagsInline(admin.TabularInline):
    model = InterestGroup.tags.through
    verbose_name = "tag"
    verbose_name_plural = "tags"

# Register your models here.
class InterestGroupAdmin(admin.ModelAdmin):
    fields = ["owner", 'name', "is_public", "logo", "cover_photo", "description", "logo_img", "cover_img"]
    readonly_fields = ('id',)
    list_display = ["id", "name"]
    inlines = [ContentsInline, ContentTypesInline, MembersInline, TagsInline, WaitingsInline]

admin.site.register(InterestGroup, InterestGroupAdmin)