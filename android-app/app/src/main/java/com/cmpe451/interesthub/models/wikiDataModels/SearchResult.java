package com.cmpe451.interesthub.models.wikiDataModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eren on 29.11.2017.
 */

public class SearchResult {

    Searchinfo searchinfo;
    List<Search> search;

    @SerializedName("search-continue")
    String search_continue;
    String success;


    public Searchinfo getSearchinfo() {
        return searchinfo;
    }

    public void setSearchinfo(Searchinfo searchinfo) {
        this.searchinfo = searchinfo;
    }

    public List<Search> getSearch() {
        return search;
    }

    public void setSearch(List<Search> search) {
        this.search = search;
    }

    public String getSearch_continue() {
        return search_continue;
    }

    public void setSearch_continue(String search_continue) {
        this.search_continue = search_continue;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}
