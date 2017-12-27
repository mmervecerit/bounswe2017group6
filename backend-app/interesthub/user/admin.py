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

class FollowRequestsInline(admin.TabularInline):
    model = UserProfile.follower_requests.through
    verbose_name = "Follow Requester"
    verbose_name_plural = "Follow Requesters"

class InterestsInline(admin.TabularInline):
    model = UserProfile.interests.through
    verbose_name = "Interest"
    verbose_name_plural = "Interests"

class ProfileAdmin(admin.ModelAdmin):
    fields = ['owner', 'name', 'lastname', 'birthdate', 'gender', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account', 'photo']
    # fields = ['owner', 'name', 'lastname', 'birthdate', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account']
    read_only_fields = ['created_date', 'modified_date']
    list_display = ["id", "name", "lastname"]
    inlines = [FollowingsInline, FollowersInline, InterestsInline, FollowRequestsInline]

admin.site.register(UserProfile, ProfileAdmin)