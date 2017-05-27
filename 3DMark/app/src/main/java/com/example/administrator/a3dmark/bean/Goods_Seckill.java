package com.example.administrator.a3dmark.bean;

import java.io.Serializable;

/**
 * Created by Stability.Yang on 2017/3/17.
 */

public class Goods_Seckill implements Serializable {

   // "goods":[{"price_now":100.0,"price_old":400.0,"img":"http://192.168.1.136:8080/3DMark/img?id=18dd6abe-9669-448b-8381-20e0bd45852f",
   // "buynum":0,"name":"手机2","id":"016a0c25-fe26-4f6f-8620-8cb5b335b293","sortnum":4,"endTiem":"3/23 12:00"}
    private String price_now;//抢购价
    private String price_old;//原价
    private String img;//图片
    private String buynum;//已购人数
    private String name;//描述
    private String id;//ID
    private String endTiem;//结束时间
    private double proportion;//已购与库存比值

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }

    public String getPrice_now() {
        return price_now;
    }

    public void setPrice_now(String price_now) {
        this.price_now = price_now;
    }

    public String getPrice_old() {
        return price_old;
    }

    public void setPrice_old(String price_old) {
        this.price_old = price_old;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBuynum() {
        return buynum;
    }

    public void setBuynum(String buynum) {
        this.buynum = buynum;
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

    public String getEndTiem() {
        return endTiem;
    }

    public void setEndTiem(String endTiem) {
        this.endTiem = endTiem;
    }


    @Override
    public String toString() {
        return "Goods_Seckill{" +
                "price_now='" + price_now + '\'' +
                ", price_old='" + price_old + '\'' +
                ", img='" + img + '\'' +
                ", buynum='" + buynum + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", endTiem='" + endTiem + '\'' +
                ", proportion='" + proportion + '\'' +
                '}';
    }
}
