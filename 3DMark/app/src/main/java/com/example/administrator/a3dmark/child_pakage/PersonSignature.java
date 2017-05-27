package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 个性签名
 * Created by Administrator on 2017/3/2.
 */
public class PersonSignature extends Activity {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.edt_user_agreement)
    EditText edtUserAgreement;//个性签名
    @BindView(R.id.tv_signature_save)
    TextView tvSignatureSave;//保存内容
    String signature;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_agreement);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleText.setText("个性签名");
        tvTitleVice.setVisibility(View.GONE);
    }

    @OnClick({R.id.image_title_back, R.id.tv_signature_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_title_back:
                finish();
                break;
            case R.id.tv_signature_save:
                signature = edtUserAgreement.getText().toString();//获取个性签名
                userID = (String) SharedUtil.getParam(PersonSignature.this, "userid", "");
                if (TextUtils.isEmpty(signature)) {
                    Toast.makeText(PersonSignature.this, "内容不能不为", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkGo.post(Contants.Signature)
                        .params("userId", userID)
                        .params("signature", signature)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                Toast.makeText(PersonSignature.this, s.toString(), Toast.LENGTH_SHORT);
                                finish();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                            }
                        });

                break;
        }
    }
}
