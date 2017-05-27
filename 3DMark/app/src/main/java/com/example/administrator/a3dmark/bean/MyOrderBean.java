package com.example.administrator.a3dmark.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class MyOrderBean {
    public String buinessName;
    public String bussinessId;
    public String logo;
    public String allNum;
    public String allTotalPrice;
    public String allMail;
    public String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAllMail() {
        return allMail;
    }

    public void setAllMail(String allMail) {
        this.allMail = allMail;
    }

    public String getAllNum() {
        return allNum;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public String getAllTotalPrice() {
        return allTotalPrice;
    }

    public void setAllTotalPrice(String allTotalPrice) {
        this.allTotalPrice = allTotalPrice;
    }

    public List<Goods_order> goodsOrders;

    public String getBuinessName() {
        return buinessName;
    }

    public void setBuinessName(String buinessName) {
        this.buinessName = buinessName;
    }

    public String getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(String bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Goods_order> getGoodsOrders() {
        return goodsOrders;
    }

    public void setGoodsOrders(List<Goods_order> goodsOrders) {
        this.goodsOrders = goodsOrders;
    }
}
