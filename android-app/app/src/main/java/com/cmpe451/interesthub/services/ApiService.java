package com.cmpe451.interesthub.services;

import com.cmpe451.interesthub.models.Component;
import com.cmpe451.interesthub.models.Content;
import com.cmpe451.interesthub.models.ContentType;
import com.cmpe451.interesthub.models.Dummy;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    Call <User> addUser (@Field("username") String username,@Field("email") String email);

    @GET("group/")
    Call<List<Group>> getGroup();

    @POST("group/")
    @FormUrlEncoded
    Call<Group> addGroup (@Field("name") String name,@Field("description") String desc,@Field("image") String image);

    @GET("component/")
    Call<List<Component>> getComponentList();

    @GET("content-type/")
    Call<List<ContentType>> getContentTypeList();

    @GET("content/")
    Call<List<Content>> getContentList();

    @GET("group-contents/{group_id}/")
    Call<List<Content>> getGroupContents(@Path(value = "group_id", encoded = true) long groupId);

    @GET("group-ctypes/{group_id}/")
    Call<List<ContentType>> getGroupContentTypes(@Path(value = "group_id", encoded = true) long groupId);
}
