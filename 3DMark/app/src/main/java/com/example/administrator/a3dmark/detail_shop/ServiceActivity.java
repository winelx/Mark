package com.example.administrator.a3dmark.detail_shop;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/8.
 * 客服
 */
public class ServiceActivity extends Activity {
    @BindView(R.id.image_title_white_back)
    ImageView imageTitleWhiteBack;
    @BindView(R.id.tv_title_white)
    TextView tvTitleWhite;
    @BindView(R.id.tv_collection_title)
    TextView tvCollectionTitle;
    @BindView(R.id.tv_title_num)
    TextView tvTitleNum;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_title_white_vice)
    TextView tvTitleWhiteVice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleWhite.setText("客服");
    }

    @OnClick(R.id.image_title_white_back)
    public void onClick() {
        finish();
    }
}
