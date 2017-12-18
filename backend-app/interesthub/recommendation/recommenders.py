from django.contrib.auth.models import User
from group.models import InterestGroup
from .models import Tag
from content.models import Content
from random import randint
import operator

def append_uniquely(target, source, limit, user_id = None):
    added = 0 
    for uid in source:
        print(added, uid)
        if uid[0] != user_id and uid[0] not in target:
            target.append( uid[0] )
            added += 1
        if added >= limit:
            break

def get_recommended_users(user, limit = 10):
    interests = user.profile.interests.all()
    followings = user.profile.followings.all() # User queryset

    tag_related = {}
    content_related = {}
    following_related = {}

    for tag in interests:
        for suser in tag.users.all()[:100]:
            if suser.id in tag_related:
                tag_related[suser.id] += 1
            else:
                tag_related[suser.id] = 1

    for tag in interests:
        contents = tag.contents.all().order_by('-created_date')[:100]
        for content in contents:
            if content.owner.id in content_related:
                content_related[ content.owner.id ] += 1
            else:
                content_related[ content.owner.id ] = 1

    for followed_user in followings:
        inner_followings = followed_user.followings.all() # UserProfile queryset
        for user_profile in inner_followings:
            if user_profile.owner.id in following_related:
                following_related[ user_profile.owner.id ] += 1
            else:
                following_related[ user_profile.owner.id ] = 1

    users = []
    tag_related = sorted(tag_related.items(), key=operator.itemgetter(1))
    content_related = sorted(content_related.items(), key=operator.itemgetter(1))
    following_related = sorted(following_related.items(), key=operator.itemgetter(1))

    append_uniquely(users, tag_related, limit, user.id)
    
    print('append_uniquely content_related')
    append_uniquely(users, content_related, max(limit-len(users), limit//2), user.id)

    print('append_uniquely following_related')
    append_uniquely(users, following_related, max(limit-len(users), limit//2), user.id)

    if len(users) < limit: 
        rand_users = User.objects.all().order_by('?')[:limit]
        for ruser in rand_users:
            if (ruser.id != user.id) and (ruser.id not in users):
                users.append(ruser.id)
            if len(users)>=limit:
                break
    
    if user.id in users:
        users.pop( users.index(user.id) )
        
    print(users)

    while len(users) > limit:
        i = randint(0, len(users))
        users.pop(i)
    
    print(users)

    user_list = []
    for uid in users:
        user_list.append(User.objects.get(pk = uid))
    
    return user_list

def get_recommended_groups(user, limit = 10):
    interests = user.profile.interests.all()
    followings = user.profile.followings.all() # User queryset
    joined_groups = user.interest_groups.all()
    waiting_groups = user.group_requests.all()

    tag_related = {}
    content_related = {}
    following_related = {}

    for tag in interests:
        for group in tag.groups.all():
            if (group not in joined_groups) and (group not in waiting_groups):
                if group.id in tag_related:
                    tag_related[group.id] += 1
                else:
                    tag_related[group.id] = 1

        for group in tag.related_group.all()[:50]:
            if (group not in joined_groups) and (group not in waiting_groups):
                if group.id in content_related:
                    content_related[group.id] += 1
                else:
                    content_related[group.id] = 1

    
    for followed_user in followings:
        groups = followed_user.groups.all()[:50]
        for group in groups:
            if (group not in joined_groups) and (group not in waiting_groups):
                if group.id in following_related:
                    following_related[group.id] += 1
                else:
                    following_related[group.id] = 1
    
    groups = []

    print(tag_related)
    print(content_related)
    print(following_related)

    append_uniquely(groups, tag_related, limit)
    
    append_uniquely(groups, content_related, max(limit-len(groups), limit//2))

    append_uniquely(groups, following_related, max(limit-len(groups), limit//2))

    if len(groups) < limit: 
        rand_groups = InterestGroup.objects.all().order_by('?')[:limit*2]
        print(rand_groups)
        for group in rand_groups:
            print(group, group not in joined_groups, group not in waiting_groups)
            if (group not in joined_groups) and (group not in waiting_groups):
                print("WTF")
                groups.append(group.id)
            if len(groups)>=limit:
                break
    
    group_list = []
    for gid in groups:
        group_list.append(InterestGroup.objects.get(pk = gid))
    
    return group_list