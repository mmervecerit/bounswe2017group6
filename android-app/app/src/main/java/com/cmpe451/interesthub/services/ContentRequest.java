package com.cmpe451.interesthub.services;

import com.cmpe451.interesthub.models.Content;
import com.google.gson.annotations.SerializedName;

/**
 * Created by eren on 16.11.2017.
 */

public class ContentRequest {

    @SerializedName("content")
    Content content;

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
