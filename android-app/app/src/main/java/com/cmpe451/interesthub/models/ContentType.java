package com.cmpe451.interesthub.models;

import java.util.List;

/**
 * Created by eren on 1.11.2017.
 */

public class ContentType {

    long id;
    String name;
    List<String> components;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }
}
