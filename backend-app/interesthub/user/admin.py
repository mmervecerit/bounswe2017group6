from django.contrib import admin
from .models import *

# Register your models here.
class ProfileAdmin(admin.ModelAdmin):
    fields = ['owner', 'name', 'lastname', 'birthdate', 'gender', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account']
    # fields = ['owner', 'name', 'lastname', 'birthdate', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account']
    read_only_fields = ['created_date', 'modified_date']
    list_display = ["id", "name", "lastname"]

admin.site.register(UserProfile, ProfileAdmin)