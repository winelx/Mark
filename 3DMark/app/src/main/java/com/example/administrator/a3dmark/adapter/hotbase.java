package com.example.administrator.a3dmark.adapter;

/**
 * Created by 10942 on 2017/3/7 0007.
 */

public class hotbase {
    private int img;
    private String name;
    private String price;
    private String house;

    public hotbase(int img, String name, String price, String house) {
        this.img = img;
        this.name = name;
        this.price = price;
        this.house = house;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }
}
