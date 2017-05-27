package com.example.administrator.a3dmark.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.MyApplication;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.PasswordEditText;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.example.administrator.a3dmark.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;


import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Stability.Yang on 2017/3/8.
 * 登录
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private EditText edt_username;
    private PasswordEditText edt_pwd;
    private Button btn_login;
    private ImageView btn_qq, btn_weibo, btn_weixin;
    private TextView btn_register, btn_overPwd;
    private ProgressDialog mDialog;
    private boolean flag = false;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_pwd = (PasswordEditText) findViewById(R.id.edt_pwd);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (TextView) findViewById(R.id.btn_register);
        btn_overPwd = (TextView) findViewById(R.id.btn_overPwd);
        btn_qq = (ImageView) findViewById(R.id.btn_qq);
        btn_weibo = (ImageView) findViewById(R.id.btn_weibo);
        btn_weixin = (ImageView) findViewById(R.id.btn_weixin);

        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_overPwd.setOnClickListener(this);
        btn_qq.setOnClickListener(this);
        btn_weibo.setOnClickListener(this);
        btn_weixin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btn_login.getId()) {//登录
            final String userName = edt_username.getText().toString().trim();
            final String pwd = edt_pwd.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(this, "用户名为空！", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(pwd)) {
                Toast.makeText(this, "密码为空！", Toast.LENGTH_SHORT).show();
                return;
            }
            mDialog = ProgressDialog.show(LoginActivity.this, "用户登录", "登录中...");
            OkGo.post(Contants.MESSAGE_LOGIN)
                    .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                    .cacheKey("loginStatus")
                    .params("username", userName)
                    .params("pwd", pwd)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {

                            try {
                                JSONObject json = new JSONObject(s);
                                //buyanjing//再次判断
                                if (s.indexOf("error") != -1) {//有错误
                                    mDialog.dismiss();
                                    CacheManager.INSTANCE.remove("loginStatus");//去掉错误缓存
                                    Toast.makeText(LoginActivity.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //登录环信
//                                if(logoinEasemob(userName, pwd) != false) {
//                                    mDialog.dismiss();
//                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
//                                    SharedUtil.setParam(LoginActivity.this, "psw", pwd);
//                                    finish();
//                                }
                                mDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                SharedUtil.setParam(LoginActivity.this, "psw", pwd);
                                finish();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mDialog.dismiss();
                            }
                            Log.d("Logoing", s);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            mDialog.dismiss();
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else if (v.getId() == btn_register.getId()) {//立即注册
            startActivity(new Intent(this, RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            this.finish();
        } else if (v.getId() == btn_overPwd.getId()) {//忘记密码
            startActivity(new Intent(this, ForgetActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            this.finish();
        } else if (v.getId() == btn_qq.getId()) {//qq登录

        } else if (v.getId() == btn_weibo.getId()) {//weibo登录

        } else if (v.getId() == btn_weixin.getId()) {//weixin登录

        }
    }


    /**
     * 登录环信
     */
    private boolean logoinEasemob(String currentUsername, String currentPassword) {
       flag = false;

        return flag;
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (!TextUtils.isEmpty(isLogin())) {//登录过,finish当前页面进入对应的操作
                    this.finish();
                    return true;
                }
                //未登录,去主页面
                Utils.sendIntentWithClearTop(this, MainActivity.class);
                return true;
            }
        }
        return false;
    }

}
