package com.example.administrator.a3dmark.bean;

import java.io.Serializable;

/**
 * Created by LGY on 2017/4/13.
 */

public class List_Column implements Serializable {

    private String id;
    private int imgSrc;
    private String name;

    public List_Column(String id, int imgSrc, String name){
        this.imgSrc = imgSrc;
        this.name = name;
        this.id = id;
    }

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

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    @Override
    public String toString() {
        return "List_Column{" +
                "id='" + id + '\'' +
                ", imgSrc=" + imgSrc +
                ", name='" + name + '\'' +
                '}';
    }
}
