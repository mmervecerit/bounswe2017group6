package com.cmpe451.interesthub.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eren on 28.10.2017.
 */

public class User {
    boolean loaded = false;

    @SerializedName("id")
    long id;
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
    @SerializedName("groups")
    List<String> groupListResponse;

    List<Group> groupList = new ArrayList<Group>();


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getGroupListResponse() {
        return groupListResponse;
    }

    public void setGroupListResponse(List<String> groupListResponse) {
        this.groupListResponse = groupListResponse;
    }

    public List<Group> getGroupList() {
        return groupList;
    }
    public void addGroupList(Group group) {


       if(groupList == null) groupList = new ArrayList<Group>();

       Log.d("USER",group.getName() + " is adding");
        groupList.add(group);
    }
    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}
