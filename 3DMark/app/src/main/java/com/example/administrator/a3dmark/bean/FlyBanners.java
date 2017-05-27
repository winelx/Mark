package com.example.administrator.a3dmark.bean;

import java.io.Serializable;

/**
 * Created by Stability.Yang on 2017/3/27.
 */

public class FlyBanners implements Serializable {

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

    @Override
    public String toString() {
        return "FlyBanners{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
