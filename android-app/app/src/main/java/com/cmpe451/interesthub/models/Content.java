package com.cmpe451.interesthub.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by eren on 28.10.2017.
 */

public class Content {

    long id;

    @SerializedName("content_type")
    ContentType contentType;

    @SerializedName("created_date")
    Date createdDate;
    @SerializedName("modified_date")
    Date modifiedDate;
    List<Component> components;
    User owner;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
