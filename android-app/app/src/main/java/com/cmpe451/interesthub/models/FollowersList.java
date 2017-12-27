package com.cmpe451.interesthub.models;

import java.util.List;

/**
 * Created by eren on 27.12.2017.
 */

public class FollowersList {

    List<User> followers;
    List<User> requests;

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getRequests() {
        return requests;
    }

    public void setRequests(List<User> requests) {
        this.requests = requests;
    }
}
