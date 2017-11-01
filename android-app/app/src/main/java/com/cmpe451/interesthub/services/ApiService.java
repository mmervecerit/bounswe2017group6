package com.cmpe451.interesthub.services;

import com.cmpe451.interesthub.models.Dummy;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<Group> addGroup (@Field("name") String name);
}
