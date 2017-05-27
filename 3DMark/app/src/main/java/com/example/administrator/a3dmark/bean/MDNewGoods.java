package com.example.administrator.a3dmark.bean;

/**
 * Created by LGY on 2017/5/15.
 * 新上商品
 */

public class MDNewGoods {

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
        return "MDNewGoods{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
