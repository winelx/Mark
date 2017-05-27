package com.example.administrator.a3dmark.bean;

/**
 * Created by Administrator on 2017/4/28.
 */
public class Fitting {
    public String img;
    public String detail;
    public String name;
    public String time;
    public String delete;
    public String sex;
    public String fittingId;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFittingId() {
        return fittingId;
    }

    public void setFittingId(String fittingId) {
        this.fittingId = fittingId;
    }

    public Fitting() {
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
