package com.example.administrator.a3dmark.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15.
 */

public class RelevanceGoods implements Serializable {
    public String count;
    public String goodsId;
    public String goodsName;
    public String img;
    public String priceModeNew;

    public RelevanceGoods() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getPriceModeNew() {
        return priceModeNew;
    }

    public void setPriceModeNew(String priceModeNew) {
        this.priceModeNew = priceModeNew;
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
}
