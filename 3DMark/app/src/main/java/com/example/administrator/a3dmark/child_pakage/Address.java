package com.example.administrator.a3dmark.child_pakage;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/3/21.
 */

public class Address implements Serializable {
    public String name;
    public String phone;
    public String addr;
    public String area;
    public int defaultaddress;
    public String street;
    public String city;
    public String id;
    public String district;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getDefaultaddress() {
        return defaultaddress;
    }

    public void setDefaultaddress(int defaultaddress) {
        this.defaultaddress = defaultaddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Address() {
    }
}
