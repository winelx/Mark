package com.example.administrator.a3dmark.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.Contants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/21.
 * 注册
 */

public class RegisterActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.image_finish)
    ImageView imageFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.btn_code)
    Button btnCode;
    @BindView(R.id.btn_next_register)
    Button btnNext;
    public TimeCount time;
    public String phone;
    public String codeP;
    public String codeR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registercode);
        ButterKnife.bind(this);

        time = new TimeCount(60000, 1000);
        initView();
    }

    private void initView() {
        btnNext.setOnClickListener(this);
        btnCode.setOnClickListener(this);
        imageFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        phone = edtUsername.getText().toString().trim();
        codeR = edtCode.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_code:
                //Contants.MESSAGE_REGIS
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "手机号为空", Toast.LENGTH_SHORT).show();
                    return;
                } else if (phone.toCharArray().length != 11) {
                    Toast.makeText(this, "手机号数错误", Toast.LENGTH_SHORT).show();
                    return;
                }

                OkGo.post(Contants.MESSAGE_CODE)
                        .tag(this)
                        .params("phone", phone)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                //网络请求成功
                                Log.d("code1", s.toString());
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    if(s.indexOf("error") != -1){//有错误
                                        Toast.makeText(RegisterActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    time.start();//计时器
                                    codeP = jsonObject.getString("code");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                    Toast.makeText(RegisterActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.btn_next_register:
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(this, "手机号为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(codeR)) {
                    Toast.makeText(this, "验证码为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (codeP == null || codeP == "") {
                    Toast.makeText(this, "验证失败！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!codeP.equals(codeR)) {
                    Toast.makeText(this, "验证码不正确！", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(this, CodePasswordActivity.class).putExtra("username", phone).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                this.finish();
                break;
            case R.id.image_finish://去登录界面
                startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                this.finish();
                break;
        }
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {// 计时完毕
            btnCode.setText("获取验证码");
            btnCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程
            btnCode.setClickable(false);// 防止重复点击
            btnCode.setText(millisUntilFinished / 1000 + "s" + "后重新获取");
        }
    }

    //点击编辑框以外的位置隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }   // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};     //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
