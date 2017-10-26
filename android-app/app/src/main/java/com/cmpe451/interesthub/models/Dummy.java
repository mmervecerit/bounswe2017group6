package com.cmpe451.interesthub.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eren on 26.10.2017.
 */

public class Dummy {
    @SerializedName("id")
    int id;
    @SerializedName("text")
    String text;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
