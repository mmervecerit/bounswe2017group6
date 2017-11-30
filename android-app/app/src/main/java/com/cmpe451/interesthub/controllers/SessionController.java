package com.cmpe451.interesthub.controllers;

import android.content.Context;
import android.content.Intent;

import com.cmpe451.interesthub.InterestHub;
import com.cmpe451.interesthub.models.Group;
import com.cmpe451.interesthub.models.Interest;
import com.cmpe451.interesthub.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Session infos such as User model will be kept here
 */

public class SessionController {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String token="";

    boolean isLoggedIn = false;
    User user ;
    List<Group> groups;

    public boolean isGroupsSet(){return groups!=null;};

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public void updateGroups(InterestHub hub, final Intent intent, final Context context) {
        groups.clear();
        hub.getApiService().getUserGroups(user.getId()).enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                for(Group g : response.body())
                    groups.add(g);
                context.startActivity(intent);
                return;
            }

            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {

            }
        });
    }
}
