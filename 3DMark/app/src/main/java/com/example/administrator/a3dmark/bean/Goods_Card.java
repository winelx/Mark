package com.example.administrator.a3dmark.bean;

import java.io.Serializable;

/**
 * Created by Stability.Yang on 2017/3/17.
 */

public class Goods_Card implements Serializable {

    boolean checked;//勾选状态
    String trade_name;//店名
    String edit;//商品编辑
    String logoimage;//商店logo
    String image;//商品图片
    String msg;//商品简介
    String money;//商品价格
    String number;//购买数量

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public void setTrade_name(String trade_name) {
        this.trade_name = trade_name;
    }

    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getLogoimage() {
        return logoimage;
    }

    public void setLogoimage(String logoimage) {
        this.logoimage = logoimage;
    }

    @Override
    public String toString() {
        return "Goods_Card{" +
                "checked=" + checked +
                ", trade_name='" + trade_name + '\'' +
                ", edit='" + edit + '\'' +
                ", logoimage='" + logoimage + '\'' +
                ", image='" + image + '\'' +
                ", msg='" + msg + '\'' +
                ", money='" + money + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
