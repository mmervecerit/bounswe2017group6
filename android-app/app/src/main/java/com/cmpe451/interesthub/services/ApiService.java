package com.cmpe451.interesthub.services;

import android.widget.ListView;

import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.ContentType;
import com.cmpe451.interesthub.models.Dummy;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.Message;
import com.cmpe451.interesthub.models.Token;
import com.cmpe451.interesthub.models.User;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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

    @GET
    Call<Group> getSpesificGroup(@Url String url);

    @GET
    Call<User> getSpesificUser(@Url String url);

    @POST("users/")
    @FormUrlEncoded
    Call <User> addUser (@Field("username") String username,@Field("email") String email, @Field("password") String password);

    @GET("groups/")
    Call<List<Group>> getGroup();

    @POST("groups/")
    @FormUrlEncoded
    Call<Group> addGroup (@Field("name") String name,@Field("description") String desc,@Field("image") String image);

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

}
