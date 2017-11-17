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
from content.views import *
from components.views import ComponentViewSet
from group.views import *
from user.views import *
from rest_framework.urlpatterns import format_suffix_patterns
from rest_framework_jwt.views import obtain_jwt_token
from rest_framework.documentation import include_docs_urls

router = routers.DefaultRouter()
# router.register(r'content', ContentViewSet)
router.register(r'content-type', ContentTypeViewSet)
router.register(r'users', UserViewSet)
router.register(r'group', InterestGroupViewSet)
router.register(r'dummy', views.DummyTextViewSet)

router.register(r'upDown',UpDownViewSet)
router.register(r'comment',CommentViewSet)
# router.register(r'component',ComponentViewSet)

router.register(r'component',ComponentViewSet)
router.register(r'content',ContentViewSet)

urlpatterns = [   
    url(r'user-groups/(?P<pk>[0-9]+)/$', UserGroupList.as_view()),
    url(r'group-contents/(?P<pk>[0-9]+)/$', GroupContentList.as_view()),
    url(r'group-members/(?P<pk>[0-9]+)/$', GroupMembersList.as_view()),
    url(r'group-ctypes/(?P<pk>[0-9]+)/$', GroupContentTypeList.as_view()),
    
    url(r'^test/', TestView.as_view()),
    url(r'^login/', obtain_jwt_token),
    url(r'^register/', UserRegisterView.as_view()),

    url(r'^admin/', admin.site.urls),
    url(r'^api-auth/', include('rest_framework.urls')),

    url(r'^docs/', include_docs_urls(title='My API title', public=True))
]
urlpatterns = format_suffix_patterns(urlpatterns)
urlpatterns.append(url(r'^', include(router.urls)))
