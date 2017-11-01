package com.cmpe451.interesthub.models;

/**
 * Created by eren on 1.11.2017.
 */

public class Component {

    long id;
    String component_type;
    int order;
    String small_text;
    String long_text;
    String url;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getComponent_type() {
        return component_type;
    }

    public void setComponent_type(String component_type) {
        this.component_type = component_type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getSmall_text() {
        return small_text;
    }

    public void setSmall_text(String small_text) {
        this.small_text = small_text;
    }

    public String getLong_text() {
        return long_text;
    }

    public void setLong_text(String long_text) {
        this.long_text = long_text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
