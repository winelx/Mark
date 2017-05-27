package com.example.administrator.a3dmark.bean;

/**
 * Created by Administrator on 2017/3/17.
 */

public class Color {
    public String color;
    public int id;

    public Color(String color, int id) {
        this.color = color;
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Color() {
    }
}
