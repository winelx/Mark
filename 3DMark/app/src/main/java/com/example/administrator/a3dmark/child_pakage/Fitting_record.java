package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 试穿记录
 * Created by Administrator on 2017/3/10.
 */
public class Fitting_record extends Activity {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.top_bar)
    RelativeLayout topBar;
    @BindView(R.id.lv_recode)
    ListView lvRecode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recode_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleText.setText("试穿记录");
        tvTitleVice.setVisibility(View.GONE);
    }

    @OnClick(R.id.image_title_back)
    public void onClick() {
        finish();
    }
}
