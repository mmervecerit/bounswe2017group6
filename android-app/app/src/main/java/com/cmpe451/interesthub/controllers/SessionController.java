package com.cmpe451.interesthub.controllers;

import com.cmpe451.interesthub.models.User;

/**
 * Session infos such as User model will be kept here
 */

public class SessionController {
    boolean isLoggedIn = false;
    User user ;

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
}
