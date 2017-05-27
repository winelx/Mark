package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/28.
 */
public class WebViewActivity extends Activity {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.webView)
    WebView webView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);
        initView();
        webView.loadUrl("file:///android_asset/yhxy.html");
    }

    private void initView() {
        tvTitleText.setText("3D商城用户协议");
        tvTitleVice.setVisibility(View.GONE);
    }

    @OnClick({R.id.image_title_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_title_back:
                finish();
                break;

        }
    }


}
