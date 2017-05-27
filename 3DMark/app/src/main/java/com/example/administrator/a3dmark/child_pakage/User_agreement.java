package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 */
public class User_agreement extends Activity {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.edt_user_agreement)
    EditText edtUserAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_agreement);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleText.setText("意见反馈");
        tvTitleVice.setText("提交");
    }

    @OnClick(R.id.image_title_back)
    public void onClick() {
        finish();
    }
}
