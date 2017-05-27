package com.example.administrator.a3dmark.bean;

/**
 * Created by 10942 on 2017/3/3 0003.
 */

public class recommBean {
    private String portrait;//店名
    private String Thename;//介绍
    private String number;//收藏次数

    private int Image;
    private int image1;
    private int image2;
    private int image3;

    public recommBean(String portrait, String thename, String number, int image, int image1, int image2, int image3) {
        this.portrait = portrait;
        Thename = thename;
        this.number = number;
        Image = image;
        this.image1 = image1;
        this.image2 = image2;
        this.image3 = image3;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getThename() {
        return Thename;
    }

    public void setThename(String thename) {
        Thename = thename;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public int getImage3() {
        return image3;
    }

    public void setImage3(int image3) {
        this.image3 = image3;
    }
}
