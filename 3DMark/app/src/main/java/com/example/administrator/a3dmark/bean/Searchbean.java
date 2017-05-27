package com.example.administrator.a3dmark.bean;

/**
 * @name 3DMark
 * @class name：com.example.administrator.a3dmark.bean
 * @class describe
 * @anthor 10942 QQ:1032006226
 * @time 2017/5/18 0018 15:24
 * @change
 * @chang time
 * @class describe
 */
public class Searchbean {
    int count;
    String goodsId;
    String goodsName;
    String img;
    String priceModeNew;

    public Searchbean(int count, String goodsId, String goodsName, String img, String priceModeNew) {
        this.count = count;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.img = img;
        this.priceModeNew = priceModeNew;
    }

    public Searchbean() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPriceModeNew() {
        return priceModeNew;
    }

    public void setPriceModeNew(String priceModeNew) {
        this.priceModeNew = priceModeNew;
    }
}
