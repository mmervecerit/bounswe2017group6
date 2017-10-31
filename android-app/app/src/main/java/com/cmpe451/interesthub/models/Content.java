package com.cmpe451.interesthub.models;

/**
 * Created by eren on 28.10.2017.
 */

public class Content {

    public String header;
    public String content;

    public Content(String header){
        this.header=header;
    }
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
