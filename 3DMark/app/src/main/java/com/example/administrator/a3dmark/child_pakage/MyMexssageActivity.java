package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.a3dmark.Activity.BaseActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.MyMessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/9.
 */
public class MyMexssageActivity extends BaseActivity {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;

    @BindView(R.id.lv_mymessage)
    ListView lvMymessage;
    MyMessageAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymessage_layout);
        ButterKnife.bind(this);
        initview();

    }

    private void initview() {
        tvTitleText.setText("我的消息");
        tvTitleVice.setVisibility(View.GONE);
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new MyMessageAdapter(list, this);
        lvMymessage.setAdapter(adapter);
    }


    @OnClick(R.id.image_title_back)
    public void onClick() {
        finish();
    }

}
