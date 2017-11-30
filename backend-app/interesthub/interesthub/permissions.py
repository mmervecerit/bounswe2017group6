from rest_framework import permissions
from django.contrib.auth.models import User, Group
from content.models import Content, ContentType
from group.models import InterestGroup

class CanSeeUser(permissions.BasePermission):
    """
    Global permission check for blacklisted IPs.
    """

    def has_permission(self, request, view):
        return True
        # l = request.path.split("/")
        # user_id = l[2]

        # try:
        #     user = User.objects.get(pk=user_id)
        # except Exception as e:
        #     print("no user")
        #     return True

        # try:
        #     profile = user.profile
        # except Exception as e:
        #     print("no user profile")
        #     return False
        
        # if profile.is_public:
        #     print("public")
        #     return True

        # if request.user in profile.followers.all():
        #     print("authenticated user in followers of user")
        #     return True
            
        # if request.user.id == user_id:
        #     print("same user")
        #     return True

        # return False

class canSeeContent(permissions.BasePermission):
    def has_permission(self, request, view):
        return True
        # l = request.path.split("/")
        # user = request.user
        # print(user.id)

        # if len(l)<2:
        #     return True

        # content_id = l[2]
        # content = None
        # cowner = None
        # print(content_id)
        # profile = None
        
        # try:
        #     content = Content.objects.get(pk=content_id)
        # except Exception as e:
        #     print("no content")
        #     return True

        # try:
        #     cowner = content.owner
        # except Exception as e:
        #     print("no owner")
        #     return False
        
        # if cowner.id == user.id:
        #     print("content owner is request user")
        #     return True
        # print(user, content)
        # for group in user.interest_groups.all():
        #     print('--', group)
        #     for cgroup in content.groups.all():
        #         print(group, cgroup)
        #         if cgroup.id == group.id:
        #             return True

        # try:
        #     profile = cowner.profile
        # except Exception as e:
        #     print("no user profile")
        #     return False
        
        # if profile.is_public:
        #     print("public")
        #     return True

        # if user in cowner.profile.followers.all():
        #     print("authenticated user in followers of content owner")
        #     return True

        # # print(cowner.id, user.id)

        # return False

class canSeeGroup(permissions.BasePermission):
    def has_permission(self, request, view):
        return True
        # l = request.path.split("/")
        # user = request.user
        # print(user.id)

        # print(l)
        # if len(l)<2:
        #     return True

        # group_id = l[2]
        # group = None

        # print(user, group, group_id)
        
        # try:
        #     group = InterestGroup.objects.get(pk=group_id)
        # except Exception as e:
        #     print("no group")
        #     return True
        
        # if group.is_public:
        #     print("group public")
        #     return True

        # if user in group.members.all():
        #     print("user is a member of the group")
        #     return True

        # try:
        #     if user.id == group.owner.id:
        #         print("user is the admin of the group")
        #         return True
        # except Exception as e:
        #     pass
        
        # return False

class IsAdminOf(permissions.BasePermission):
    def has_permission(self, request, view):
        return True
        # l = request.path.split("/")
        # user = request.user
        # print(user.id)

        # print(l)
        # if len(l)<2:
        #     return False
        # group_id = l[2]

        # try:
        #     group = InterestGroup.objects.get(pk=group_id)
        # except Exception as e:
        #     print("no group")
        #     return True
    
        # try:
        #     if user.id == group.owner.id:
        #         print("user is the admin of the group")
        #         return True
        # except Exception as e:
        #     pass
        
        # return False

class IsOwner(permissions.BasePermission):
    def has_permission(self, request, view):
        return True

class IsComponentOwner(permissions.BasePermission):
    def has_permission(self, request, view):
        return True
        # l = request.path.split("/")
        # user = request.user
        # print(user.id)

        # print(l)
        # if len(l)<2:
        #     return False
        # group_id = l[2]

        # try:
        #     group = InterestGroup.objects.get(pk=group_id)
        # except Exception as e:
        #     print("no group")
        #     return True
    
        # try:
        #     if user.id == group.owner.id:
        #         print("user is the admin of the group")
        #         return True
        # except Exception as e:
        #     pass
        
        # return False