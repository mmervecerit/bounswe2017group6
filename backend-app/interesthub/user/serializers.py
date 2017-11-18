from rest_framework import serializers
from django.contrib.auth.models import User, Group
from .models import *
from collections import OrderedDict

class ProfileSerializer(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('name', 'lastname', 'birthdate', 'gender', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account')

class UserSerializer(serializers.ModelSerializer):
    # profile = ProfileSerializer(read_only=False, allow_null=False, many=False)
    class Meta:
        model = User
        fields = ('id', 'username', 'email')

class UserSerializerFull(serializers.ModelSerializer):
    profile = ProfileSerializer(read_only=False, allow_null=False, many=False)
    class Meta:
        model = User
        fields = ('id', 'username', 'email', 'profile')
        write_only_fields = ('password', )

    def create(self, validated_data):
        data = validated_data.copy()
        profile_data = data.pop("profile")
        print(profile_data)
        print(data)
        user = User.objects.create(**data)
        profile = UserProfile.objects.create(**profile_data, owner_id=user.id)
        print("create completed")
        return user

class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ('url', 'id', 'name')