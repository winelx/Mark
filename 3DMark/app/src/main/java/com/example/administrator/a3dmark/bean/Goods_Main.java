package com.example.administrator.a3dmark.bean;

public class Goods_Main {
    private String img;//图片
    private String price;//价格
    private String name;//名称
    private String count;//数量
    private String id;
    private String goods_id;//id
    private String sortnum;

    public String getSortnum() {
        return sortnum;
    }

    public void setSortnum(String sortnum) {
        this.sortnum = sortnum;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
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

    public Goods_Main(String img, String id, String count, String name, String price, String sortnum) {
        this.img = img;
        this.id = id;
        this.count = count;
        this.name = name;
        this.price = price;
        this.sortnum = sortnum;
    }

    public Goods_Main() {
    }
}