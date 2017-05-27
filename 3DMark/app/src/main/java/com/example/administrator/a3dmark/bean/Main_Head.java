package com.example.administrator.a3dmark.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/13.
 */

public class Main_Head {
    String goods_id;
    String img;
    String name;
    String z_goods_type_id;

    public String getZ_goods_type_id() {
        return z_goods_type_id;
    }

    public void setZ_goods_type_id(String z_goods_type_id) {
        this.z_goods_type_id = z_goods_type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Main_Head(String goods_id, String img) {
        this.goods_id = goods_id;
        this.img = img;
    }

    public Main_Head() {
    }
}
