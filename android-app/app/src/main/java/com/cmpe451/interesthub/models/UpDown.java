package com.cmpe451.interesthub.models;

/**
 * Created by eren on 19.11.2017.
 */

public class UpDown {

    transient long id;
    boolean isUp;
    long owner;
    long content;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isUp() {
        return isUp;
    }

    public void setUp(boolean up) {
        isUp = up;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getContent() {
        return content;
    }

    public void setContent(long content) {
        this.content = content;
    }
}
