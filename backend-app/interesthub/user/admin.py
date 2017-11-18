from django.contrib import admin
from .models import *

class FollowingsInline(admin.TabularInline):
    model = UserProfile.followings.through
    verbose_name = "Following"
    verbose_name_plural = "Followings"

class FollowersInline(admin.TabularInline):
    model = UserProfile.followers.through
    verbose_name = "Follower"
    verbose_name_plural = "Followers"

class ProfileAdmin(admin.ModelAdmin):
    fields = ['owner', 'name', 'lastname', 'birthdate', 'gender', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account']
    # fields = ['owner', 'name', 'lastname', 'birthdate', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account']
    read_only_fields = ['created_date', 'modified_date']
    list_display = ["id", "name", "lastname"]
    inlines = [FollowingsInline, FollowersInline]

admin.site.register(UserProfile, ProfileAdmin)