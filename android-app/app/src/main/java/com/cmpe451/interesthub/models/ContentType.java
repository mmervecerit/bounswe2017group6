package com.cmpe451.interesthub.models;

import java.util.List;

/**
 * Created by eren on 1.11.2017.
 */

public class ContentType {

    private  Long id;
    String name;
    List<String> components;



    List<String> component_names;
    List<DropdownItems> dropdowns;
    List<CheckboxItems> checkboxes;

    public List<DropdownItems> getDropdowns() {
        return dropdowns;
    }

    public void setDropdowns(List<DropdownItems> dropdowns) {
        this.dropdowns = dropdowns;
    }

    public List<CheckboxItems> getCheckboxes() {
        return checkboxes;
    }

    public void setCheckboxes(List<CheckboxItems> checkboxes) {
        this.checkboxes = checkboxes;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
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
    public List<String> getComponent_names() {
        return component_names;
    }

    public void setComponent_names(List<String> component_names) {
        this.component_names = component_names;
    }


}
