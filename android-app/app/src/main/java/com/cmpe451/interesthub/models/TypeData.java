package com.cmpe451.interesthub.models;

import java.util.List;

/**
 * Created by eren on 15.11.2017.
 */

public class TypeData {
    long id;
    String data;
    Long selected;
    List<Long> selecteds;

    public Long getSelected() {
        return selected;
    }

    public void setSelected(Long selected) {
        this.selected = selected;
    }

    public List<Long> getSelecteds() {
        return selecteds;
    }

    public void setSelecteds(List<Long> selecteds) {
        this.selecteds = selecteds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
