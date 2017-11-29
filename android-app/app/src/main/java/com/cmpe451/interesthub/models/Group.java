package com.cmpe451.interesthub.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eren on 28.10.2017.
 */

public class Group {
    
    @SerializedName("id")
    Long id;
    @SerializedName("name")
    String name;

    @SerializedName("description")
    String desc;

    @SerializedName("logo")
    String logo;
    @SerializedName("is_public")
    boolean is_public;
    @SerializedName("cover_photo")
    String cover_photo;

    List<Tag> tags;

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public boolean is_public() {
        return is_public;
    }

    public void setIs_public(boolean is_public) {
        this.is_public = is_public;
    }

    public String getCover_photo() {
        return cover_photo;
    }

    public void setCover_photo(String cover_photo) {
        this.cover_photo = cover_photo;
    }


    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
