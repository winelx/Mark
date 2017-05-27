package com.example.administrator.a3dmark.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.child_pakage.Address;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.cache.CacheManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Stability.Yang on 2017/3/7.
 */

public class BaseActivity extends AppCompatActivity {

    protected Address address;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    /**
     * 登录判断
     *
     * @return
     */
    protected String isLogin() {
        try {
            String status = (String) CacheManager.INSTANCE.get("loginStatus").getData();
            JSONObject json = new JSONObject(status);
            JSONObject userId = new JSONObject(json.getString("success"));
            SharedUtil.setParam(this, "userid", userId.getString("userId"));
            return userId.getString("userId");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 头像Img
     *
     * @return
     */
    protected String headImg() {
        try {
            String status = (String) CacheManager.INSTANCE.get("loginStatus").getData();
            JSONObject json = new JSONObject(status);
            JSONObject headImg = new JSONObject(json.getString("success"));
            return headImg.getString("headImg");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 昵称
     *
     * @return
     */
    protected String nickName() {
        try {
            String status = (String) CacheManager.INSTANCE.get("loginStatus").getData();
            JSONObject json = new JSONObject(status);
            JSONObject nickName = new JSONObject(json.getString("success"));
            return nickName.getString("nickname");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }



}
