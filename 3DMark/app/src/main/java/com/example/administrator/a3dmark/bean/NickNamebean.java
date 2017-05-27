package com.example.administrator.a3dmark.bean;

/**
 * @name 3DMark
 * @class name：com.example.administrator.a3dmark.bean
 * @class describe
 * @anthor 10942 QQ:1032006226
 * @time 2017/5/22 0022 14:17
 * @change
 * @chang time
 * @class describe
 */
public class NickNamebean {
    String nickname;
    String photo;
    String signature;

    public NickNamebean() {
    }

    public NickNamebean(String nickname, String photo, String signature) {
        this.nickname = nickname;
        this.photo = photo;
        this.signature = signature;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
