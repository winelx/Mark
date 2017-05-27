package com.example.administrator.a3dmark.bean;

/**
 * Created by LGY on 2017/5/15.
 * 热门商品
 */

public class BCGoods {

    private String id;
    private String img;
    private String name;
    private String count;
    private String price;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String cunt) {
        this.count = cunt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "MDGoods{" +
                "id='" + id + '\'' +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", cunt='" + count + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
