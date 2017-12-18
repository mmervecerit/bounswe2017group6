from rest_framework import serializers
from django.contrib.auth.models import User, Group
from .models import *
from collections import OrderedDict
from recommendation.serializers import TagSerializer
from recommendation.models import Tag

class ProfileSerializerBase(serializers.ModelSerializer):
    class Meta:
        model = UserProfile
        fields = ('name', 'lastname', 'birthdate', 'gender', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account', 'photo')

class ProfileSerializer(serializers.ModelSerializer):
    interests = TagSerializer(many=True, read_only=False)
    photo = serializers.SerializerMethodField()

    def get_photo(self, obj):
        if not obj.photo:
            return None
        else:
            return "http://" + self.context["request"].META['HTTP_HOST'] + "/" + obj.photo.url

    class Meta:
        model = UserProfile
        fields = ('name', 'lastname', 'birthdate', 'gender', 'contacts', 'about', 'is_public', 'facebook_account', 'twitter_account', 'instagram_account', 'interests', 'photo')
    
    def to_internal_value(self, data):
        data = data.copy()
        interests_data = data.pop("interests")
        print(self.partial)
        serializer = ProfileSerializerBase(data = data, partial = self.partial)
        validated_data = None
        if serializer.is_valid():
            validated_data = serializer.validated_data
        else:
            raise serializers.ValidationError(serializer.errors)
        if "owner_id" in data:
            validated_data["owner_id"] = data["owner_id"]
        else:
            serializers.ValidationError("No owner id is specified")
        validated_data["interests"] = []
        for interest in interests_data:
            t = Tag.objects.filter(label=interest["label"])
            if t.exists():
                validated_data["interests"].append(interest)
            else:
                serializer = TagSerializer(data = interest)
                if serializer.is_valid():
                    validated_data["interests"].append(serializer.validated_data)
                else:
                    serializers.ValidationError(serializer.errors)
        return validated_data
    
    def create(self, validated_data):
        data = validated_data.copy()
        interests_data = data.pop('interests')
        user = UserProfile.objects.create(**data)
        user.save()
        for interest in interests_data:
            tag = Tag.objects.filter(label=interest["label"])
            if tag.exists():
                tag = tag.first()
            else:
                serializer = TagSerializer(data=interest)
                if serializer.is_valid():
                    tag = serializer.create(serializer.validated_data)
            user.interests.add(tag)
        user.save()
        return user
    
    def update(self, instance, validated_data):
        data = validated_data.copy()
        interests_data = data.pop('interests')
        print(interests_data)
        user = instance
        tag_ids=[]
        for interest in interests_data:
            tag = Tag.objects.filter(label=interest["label"])
            if tag.exists():
                tag = tag.first()
                t = user.interests.filter(id=tag.id)
                if t.exists():
                    tag_ids.append(t.first().id)
                    continue
            else:
                serializer = TagSerializer(data=interest)
                if serializer.is_valid():
                    tag = serializer.create(serializer.validated_data)
            tag_ids.append(tag.id)
            user.interests.add(tag)
        print(tag_ids)
        for tag in user.interests.all():
            if tag.id not in tag_ids:
                user.interests.remove(tag)
        user.save()
        return user
            

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

    def to_internal_value(self, data):
        data = data.copy()
        validated_data = OrderedDict()
        profile_data = data.pop("profile", None)
        serializer = UserSerializer(data=data, partial=self.partial)
        if serializer.is_valid():
            validated_data = serializer.validated_data
        else:
            raise serializers.ValidationError(serializer.errors)

        serializer = ProfileSerializer(data=profile_data, partial=self.partial)
        if serializer.is_valid():
            validated_data["profile"] = serializer.validated_data
        else:
            raise serializers.ValidationError(serializer.errors)    

        return validated_data
        
    def create(self, validated_data):
        data = validated_data.copy()
        profile_data = data.pop("profile")
        user = User.objects.create(**data)
        user.save()
        print(user.id)
        profile_data["owner_id"] = user.id
        serializer = ProfileSerializer(data=profile_data)
        if serializer.is_valid():
            profile = serializer.create(serializer.validated_data)
            profile.owner = user
            profile.save()
        user.save()
        print("end create")
        return user
    
    def update(self, instance, validated_data):
        print("user serializer full update")
        data = validated_data.copy()
        profile_data = data.pop("profile")
        user = instance
        user.username = data.get("username", user.username)
        user.email = data.get("email", user.email)
        if "password" in data:
            user.set_password(data["password"])
        user.save()
        serializer = ProfileSerializer(user.profile, data=profile_data, partial=True)
        if serializer.is_valid():
            serializer.update(user.profile, serializer.validated_data)
        else:
            print(serializer.errors)
        user.save()
        return user

class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ('url', 'id', 'name')