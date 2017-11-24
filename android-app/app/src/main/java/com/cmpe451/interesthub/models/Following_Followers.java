package com.cmpe451.interesthub.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mmervecerit on 24.11.2017.
 */

public class Following_Followers {
    @SerializedName("id")
    long id;
    @SerializedName("username")
    String username;
    @SerializedName("email")
    String email;
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

}
