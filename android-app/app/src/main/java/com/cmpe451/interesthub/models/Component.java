package com.cmpe451.interesthub.models;

/**
 * Created by eren on 1.11.2017.
 */

public class Component {

    long id;
    String component_type;
    int order;
    TypeData type_data;

    public TypeData getType_data() {
        return type_data;
    }

    public void setType_data(TypeData type_data) {
        this.type_data = type_data;
    }

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

   }
