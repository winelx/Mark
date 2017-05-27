package com.example.administrator.a3dmark.bean;

import java.util.ArrayList;

/**
 * Created by 10942 on 2017/3/7 0007.
 */
public class Pro {
    private String storeName;//商铺名
    private String count;//收藏数
    private String introduce;//商铺签名
    private String bussiness_id;//商铺id
    private String img1, img2, img3;
    private String imgage;//商品图
    private String logo;
    private ArrayList<String> goodsId;

    public Pro(String storeName, String count, String introduce, String bussiness_id, String img1, String img2, String img3, String imgage, String logo, ArrayList<String> goodsId) {
        this.storeName = storeName;
        this.count = count;
        this.introduce = introduce;
        this.bussiness_id = bussiness_id;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
        this.imgage = imgage;
        this.logo = logo;
        this.goodsId = goodsId;
    }

    public String getLogo() {
        return logo;
    }

    public ArrayList<String> getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(ArrayList<String> goodsId) {
        this.goodsId = goodsId;
    }

    public void setLogo(String logo) {

        this.logo = logo;
    }

    public String getBussiness_id() {
        return bussiness_id;
    }

    public void setBussiness_id(String bussiness_id) {
        this.bussiness_id = bussiness_id;
    }

    public Pro() {
    }

    public String getImgage() {
        return imgage;
    }

    public String setImgage(String imgage) {
        this.imgage = imgage;
        return imgage;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }
}
