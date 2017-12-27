package com.cmpe451.interesthub.models;

import java.util.List;

/**
 * Created by eren on 27.12.2017.
 */

public class DropdownItems {
    String name;
    List<SingleDropdownItems> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SingleDropdownItems> getItems() {
        return items;
    }

    public void setItems(List<SingleDropdownItems> items) {
        this.items = items;
    }
}
