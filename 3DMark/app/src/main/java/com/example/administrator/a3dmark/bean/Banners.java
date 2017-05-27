package com.example.administrator.a3dmark.bean;

import java.io.Serializable;

/**
 * Created by 10942 on 2017/4/5 0005.
 */

public class Banners implements Serializable {

    private String id;
    private String img;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}