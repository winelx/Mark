package com.example.administrator.a3dmark.bean;

import java.io.Serializable;

/**
 * Created by LGY on 2017/4/13.
 */

public class Today_Goods implements Serializable {

    private String id;
    private String imgUrl;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Today_Goods{" +
                "id='" + id + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
