package com.example.administrator.a3dmark.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stability.Yang on 2017/3/7.
 */

public class RegisterModel implements Serializable {

    public Map<String,Object> map=new HashMap<String,Object> ();

        public String phone; //手机号

        public String username; //账号
        public String pwd;      //密码
        public String nickname; //呢称
        public String sex;      //性别
        public String weight;   //体重
        public String height;   //身高
        public String bust;     //胸围
        public String waistline;//腰围
        public String hipiine;  //臀围

    public void setphone(String phone){
        map.put("phone",phone);
    }

}
