package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/4/19.
 */
public class Submit_Modify extends Activity {
    @BindView(R.id.image_finish)
    ImageView imageFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_share)
    TextView tvTitleShare;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.title_image_message)
    ImageView titleImageMessage;
    @BindView(R.id.title_layout)
    RelativeLayout titleLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_modify);
        ButterKnife.bind(this);
        tvTitle.setText("密码修改");
    }

    @OnClick(R.id.image_finish)
    public void onClick() {
        finish();
    }
}
