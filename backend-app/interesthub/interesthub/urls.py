"""interesthub URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.11/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.conf.urls import url, include
from rest_framework import routers
from dummy import views
from group.views import GroupContentList
from content.views import ContentViewSet, ContentTypeViewSet
from components.views import ComponentViewSet, Component2ViewSet
from group.views import *
from rest_framework.urlpatterns import format_suffix_patterns
from rest_framework_jwt.views import obtain_jwt_token

router = routers.DefaultRouter()
# router.register(r'content', ContentViewSet)
router.register(r'content-type', ContentTypeViewSet)
router.register(r'users', UserViewSet)
router.register(r'group', InterestGroupViewSet)
router.register(r'dummy', views.DummyTextViewSet)
router.register(r'component',ComponentViewSet)

urlpatterns = [   
    # url(r'^group-content/', GroupContentList.as_view()),
    url(r'user-groups/(?P<pk>[0-9]+)/$', UserGroupList.as_view()),
    url(r'group-contents/(?P<pk>[0-9]+)/$', GroupContentList.as_view()),
    url(r'group-members/(?P<pk>[0-9]+)/$', GroupMembersList.as_view()),
    url(r'group-ctypes/(?P<pk>[0-9]+)/$', GroupContentTypeList.as_view()),
    url(r'content/(?P<pk>[0-9]+)/$', ContentViewSet.as_view()),
    url(r'component2/(?P<pk>[0-9]+)/$', Component2ViewSet.as_view()),
    url(r'^login/', obtain_jwt_token),
    url(r'^admin/', admin.site.urls),
    url(r'^api-auth/', include('rest_framework.urls'))
]
urlpatterns = format_suffix_patterns(urlpatterns)
urlpatterns.append(url(r'^', include(router.urls)))
