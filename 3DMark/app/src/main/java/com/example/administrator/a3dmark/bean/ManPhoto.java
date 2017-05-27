package com.example.administrator.a3dmark.bean;

/**
 * @name 3DMark
 * @class name：com.example.administrator.a3dmark.bean
 * @class describe
 * @anthor 10942 QQ:1032006226
 * @time 2017/4/19 0019 17:30
 * @change
 * @chang time
 * @class describe
 */
public class ManPhoto {
    public int bust;
    public int height;
    public int hipline;
    public String img;
    public String nickname;
    public String personalId;
    public String photoId;
    public int waistline;
    public int weight;
    public String time;
    public String sex;
    public String isFit;
    public String isSelf;

    public String msg;

    public ManPhoto(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(String isSelf) {
        this.isSelf = isSelf;
    }

    public String getIsFit() {
        return isFit;
    }

    public void setIsFit(String isFit) {
        this.isFit = isFit;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ManPhoto() {

    }

    public ManPhoto(int bust, int height, int hipline, String img, String nickname, String personalId, String photoId, int waistline, int weight) {
        this.bust = bust;
        this.height = height;
        this.hipline = hipline;
        this.img = img;
        this.nickname = nickname;
        this.personalId = personalId;
        this.photoId = photoId;
        this.waistline = waistline;
        this.weight = weight;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHipline() {
        return hipline;
    }

    public void setHipline(int hipline) {
        this.hipline = hipline;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public int getWaistline() {
        return waistline;
    }

    public void setWaistline(int waistline) {
        this.waistline = waistline;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
