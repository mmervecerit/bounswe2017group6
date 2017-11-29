package com.cmpe451.interesthub.models.wikiDataModels;

/**
 * Created by eren on 29.11.2017.
 */

public class Search {

    String repository;
    String id;
    String concepturi;
    String title;
    String pageid;
    String url;
    String label;
    String description;

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConcepturi() {
        return concepturi;
    }

    public void setConcepturi(String concepturi) {
        this.concepturi = concepturi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
