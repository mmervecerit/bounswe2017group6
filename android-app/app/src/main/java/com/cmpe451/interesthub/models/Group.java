package com.cmpe451.interesthub.models;

import java.util.List;

/**
 * Created by eren on 28.10.2017.
 */

public class Group {
    String name;
    List<User> users;
    List<Content> contents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }
}
