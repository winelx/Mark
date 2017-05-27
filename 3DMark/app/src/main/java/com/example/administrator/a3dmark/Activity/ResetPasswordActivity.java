
package com.example.administrator.a3dmark.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 重置密码
 * Created by Administrator on 2017/2/21.
 */
public class ResetPasswordActivity extends Activity {
    @BindView(R.id.image_finish)
    ImageView imageFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.image_username)
    ImageView imageUsername;
    @BindView(R.id.edt_repassword)
    EditText edtRepassword;
    @BindView(R.id.btn_next_code)
    Button btnNextCode;

    private String pwd;
    private String repwd;
    private String UserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codepassword);
        ButterKnife.bind(this);
        UserName = getIntent().getStringExtra("reset_username");
        initView();
    }

    private void initView() {
        btnNextCode.setText("完成");
        tvTitle.setText("重置密码");
    }


    @OnClick(R.id.image_finish)
    void btn_finish(View v) {
        startActivity(new Intent(ResetPasswordActivity.this, ForgetActivity.class));
        this.finish();
    }

    @OnClick(R.id.btn_next_code)
    void btn_next(View v) {
        pwd = edtPassword.getText().toString().trim();
        repwd = edtRepassword.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(repwd)) {
            Toast.makeText(this, "请再次输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (!pwd.equals(repwd)) {
            Toast.makeText(this, "确认密码不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        ////////////////////调接口，去登陆///////////////////////
        Intent intent = new Intent(ResetPasswordActivity.this, MainActivity.class).putExtra("pwd", repwd).putExtra("username", UserName);
        startActivity(intent);
        finish();
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

