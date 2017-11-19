package com.cmpe451.interesthub.models;

import java.util.Date;

/**
 * Created by eren on 19.11.2017.
 */

public class Comment {

    transient long id;
    Date created_date;
    Date modified_date;
    String text;
    User owner;
    long content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getModified_date() {
        return modified_date;
    }

    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public long getContent() {
        return content;
    }

    public void setContent(long content) {
        this.content = content;
    }
}
