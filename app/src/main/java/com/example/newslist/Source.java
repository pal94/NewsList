package com.example.newslist;

import java.io.Serializable;

public class Source implements Serializable {

    String id, name;

    @Override
    public String toString() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Source() {
    }
}
