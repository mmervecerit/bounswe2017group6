package com.cmpe451.interesthub.services;

import android.widget.ListView;

import com.cmpe451.interesthub.models.Comment;
import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.ContentType;
import com.cmpe451.interesthub.models.Dummy;
import com.cmpe451.interesthub.models.Following_Followers;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.Message;
import com.cmpe451.interesthub.models.Token;
import com.cmpe451.interesthub.models.UpDown;
import com.cmpe451.interesthub.models.User;
import com.cmpe451.interesthub.models.wikiDataModels.SearchResult;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * Created by eren on 22.10.2017.
 */

public interface ApiService {

    @GET("dummy/")
    Call<List<Dummy>> getDummy();

    @GET("users/")
    Call<List<User>> getUsers();

    @GET("groups/{group_id}/")
    Call<Group> getSpesificGroup(@Path(value = "group_id", encoded = true)  long groupId);

    @GET
    Call<User> getSpesificUser(@Url String url);

    @POST("register/")
    Call <User> addUser (@Body RequestBody content);

    @GET("groups/")
    Call<List<Group>> getGroup();

    @POST("groups/")
    Call<Group> addGroup (@Body RequestBody content);

    @GET("component/")
    Call<List<Component>> getComponentList();

    @GET("contents/")
    Call<List<Content>> getContentList();

    @GET("group/{group_id}/contents/")
    Call<List<Content>> getGroupContents(@Path(value = "group_id", encoded = true) long groupId);

    @GET("group/{group_id}/content-types/")
    Call<List<ContentType>> getGroupContentTypes(@Path(value = "group_id", encoded = true) long groupId);

    @POST("login/")
    @FormUrlEncoded
    Call<Token> login(@Field("username") String name,@Field("password") String password);

    @POST("group/{group_id}/contents/")
    Call<Content> postContent(@Path(value = "group_id", encoded = true)  long groupId, @Body RequestBody content);

    @POST("group/{group_id}/content-types/")
    Call<ContentType> postNewTemplate(@Path(value = "group_id", encoded = true)  long groupId, @Body RequestBody template);

    @GET("group/{group_id}/members/")
    Call<List<User>> getGroupMembers(@Path(value ="group_id",encoded = true) long groupId);

    @GET("user/{user_id}/groups/")
    Call<List<Group>> getUserGroups(@Path(value = "user_id",encoded = true) long userId);

    @POST("group/{group_id}/members/")
    Call<Message> joinGroup(@Path(value ="group_id",encoded = true) long groupId);


    @GET("user/{user_id}/contents/")
    Call<List<Content>> getUserContents(@Path(value = "user_id",encoded = true) long userId);

    @GET("me/")
    Call<User> getMe();

    @GET("content/{group_id}/comments/")
    Call<List<Comment>> getGroupComments(@Path(value ="group_id",encoded = true) long groupId);

    @POST("comments/")
    Call<Comment> postComment(@Body RequestBody body);

    @POST("votes/")
    Call<UpDown> postVote(@Body RequestBody body);

    @GET("content/{group_id}/votes/")
    Call<List<UpDown>> getVotesOfGroup(@Path(value ="group_id",encoded = true) long groupId);

    @GET
    Call<SearchResult> getTags(@Url String url);

    @GET
    Call<List<Group>> searchGroups(@Url String url);

    @GET
    Call<List<User>> searchUser(@Url String url);

    @DELETE("group/{group_id}/members/")
    Call<Message> leaveGroup(@Path(value ="group_id",encoded = true) long groupId);

    @GET("followers/")
    Call<List<User>> getFollowers();

    @GET("followings/")
    Call<List<User>> getFollowings();

    @GET("recommendation/users/")
    Call<List<User>> getRecommUser();

    @GET("recommendation/groups/")
    Call<List<Group>> getRecommGroup();


    @GET("user/{user_id}/followers/")
    Call<List<User>> getFollowersOfSomeone(@Path(value ="user_id",encoded = true) long groupId);

    @GET("user/{user_id}/followers/")
    Call<List<User>> getFollowingsOfSomeone(@Path(value ="user_id",encoded = true) long groupId);

    @HTTP(method = "DELETE", path = "/followings/", hasBody = true)
    Call<User> unfollowSomeone(@Body RequestBody template);

    @HTTP(method = "POST", path = "/followings/", hasBody = true)
    Call<User> followSomeone(@Body RequestBody template);

    @POST("followers/")
    Call<User> approveFollowRequest(@Body RequestBody template);

    @POST("followers/")
    Call<User> deleteFollowRequest(@Body RequestBody template);

}
