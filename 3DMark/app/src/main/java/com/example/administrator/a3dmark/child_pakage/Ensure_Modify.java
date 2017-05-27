package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.detail_shop.Ensure_Order;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/18.
 */
public class Ensure_Modify extends Activity {
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
    @BindView(R.id.edt_password_ensure_modify)
    EditText edtPasswordEnsureModify;
    @BindView(R.id.image_username)
    ImageView imageUsername;
    @BindView(R.id.edt_repassword_ensure_modify)
    EditText edtRepasswordEnsureModify;
    @BindView(R.id.btn_password_ensure_modify)
    Button btnPasswordEnsureModify;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ensure_modify);
        intent = getIntent();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitle.setText("修改密码");
    }

    @OnClick({R.id.image_finish, R.id.btn_password_ensure_modify})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_finish:
                finish();
                break;
            case R.id.btn_password_ensure_modify:
                String new_psw = edtPasswordEnsureModify.getText().toString();
                String re_psw = edtRepasswordEnsureModify.getText().toString();
                if (TextUtils.isEmpty(new_psw)) {
                    Toast.makeText(Ensure_Modify.this, "请填写密码", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (TextUtils.isEmpty(re_psw)) {
                    Toast.makeText(Ensure_Modify.this, "请再次填写密码", Toast.LENGTH_SHORT).show();
                    break;
                }
                if (new_psw == intent.getStringExtra("psw")) {
                    Toast.makeText(Ensure_Modify.this, "新密码和原密码一样，请重新填写", Toast.LENGTH_SHORT).show();
                    edtPasswordEnsureModify.setFocusable(true);
                    new_psw = null;
                    re_psw = null;
                    break;
                }
                if (!new_psw.equals(re_psw)) {
                    Toast.makeText(Ensure_Modify.this, "两次填写密码不一致，请重新填写", Toast.LENGTH_SHORT).show();
                    edtPasswordEnsureModify.setFocusable(true);
                    new_psw = null;
                    re_psw = null;
                    break;
                }
                initsubmit();
        }
    }

    private void initsubmit() {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.post(Contants.UPDATE_PASSWORD)
                .params("userId", SharedUtil.getParam(Ensure_Modify.this, "userid", "").toString())
                .params("oldPassword", intent.getStringExtra("psw"))
                .params("newPassword", edtRepasswordEnsureModify.getText().toString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            boolean is_succese = jsobj.isNull("success");
                            boolean is_error = jsobj.isNull("error");
                            if (!is_succese) {
//                                JSONObject obj = new JSONObject("success");
                                Toast.makeText(Ensure_Modify.this, jsobj.getString("success"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Ensure_Modify.this, Submit_Modify.class);
                                finish();
                                startActivity(intent);
                                return;
                            }
                            if (!is_error) {
                                Toast.makeText(Ensure_Modify.this, jsobj.getString("error"), Toast.LENGTH_SHORT).show();
//                                edtPasswordEnsureModify.setFocusable(true);
                                edtPasswordEnsureModify.requestFocus();
                                edtPasswordEnsureModify.setText("");
                                edtRepasswordEnsureModify.setText("");
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        mDialog.dismiss();
                        Toast.makeText(Ensure_Modify.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
