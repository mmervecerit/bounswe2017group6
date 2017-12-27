package com.cmpe451.interesthub.models;

import java.util.List;

/**
 * Created by eren on 27.12.2017.
 */

public class CheckboxItems {
    String name;
    List<SingleCheckboxItems> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SingleCheckboxItems> getItems() {
        return items;
    }

    public void setItems(List<SingleCheckboxItems> items) {
        this.items = items;
    }
}
