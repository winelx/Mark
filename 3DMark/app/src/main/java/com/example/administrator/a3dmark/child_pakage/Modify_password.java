package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.CodePasswordActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.SharedUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/3/1.
 */
public class Modify_password extends Activity {


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
    @BindView(R.id.edt_password_modify)
    EditText edtPasswordModify;
    @BindView(R.id.btn_next_modify)
    Button btnNextModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_password);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("密码修改");
    }

    @OnClick({R.id.image_finish, R.id.btn_next_modify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_finish:
                finish();
                break;
            case R.id.btn_next_modify:
                String psw = edtPasswordModify.getText().toString();
                String is_psw = SharedUtil.getParam(Modify_password.this, "psw", "").toString();
                if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(Modify_password.this, "请填写原密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!psw.equals(is_psw)) {
                    Toast.makeText(Modify_password.this, "密码错误,请重新填写", Toast.LENGTH_SHORT).show();
                    psw = null;
                    return;
                }
                Intent intent = new Intent(Modify_password.this, Ensure_Modify.class);
                intent.putExtra("psw", edtPasswordModify.getText().toString());
                intent.putExtra("is_modify", "1");
                finish();
                startActivity(intent);
                break;
        }
    }
}
