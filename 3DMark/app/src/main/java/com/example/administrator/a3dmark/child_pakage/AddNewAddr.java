package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lljjcoder.citypickerview.widget.CityPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/28.
 */
public class AddNewAddr extends Activity {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.edt_newaddr_name)
    EditText edtNewaddrName;
    @BindView(R.id.edt_newaddr_mobile)
    EditText edtNewaddrMobile;
    @BindView(R.id.tv_newaddr_province)
    TextView tvNewaddrProvince;
    @BindView(R.id.tv_newaddr_city)
    TextView tvNewaddrCity;
    @BindView(R.id.tv_newaddr_area)
    TextView tvNewaddrArea;
    @BindView(R.id.Localization)
    LinearLayout Localization;
    @BindView(R.id.tv_newaddr_street)
    EditText tvNewaddrStreet;
    @BindView(R.id.edt_newaddr_detail)
    EditText edtNewaddrDetail;
    @BindView(R.id.tv_newaddr_delete)
    TextView tvNewaddrDelete;
    Intent intent;
    String detail;
    String name;
    String phone;
    String street;
    String province;
    String district;
    String city;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew_addr);
        ButterKnife.bind(this);
        intent = getIntent();
        userid = (String) SharedUtil.getParam(this, "userid", "");
        Log.d("area", intent.getStringExtra("name"));
        initView();

    }

    private void initData(String userId) {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.post(Contants.ADD_NEA_ADDR)
                .params("userId", userId)
                .params("id", intent.getStringExtra("id"))
                .params("usersAddress.name", name)
                .params("usersAddress.telephone", phone)
                .params("usersAddress.area", province)
                .params("usersAddress.district", district)
                .params("usersAddress.street", street)
                .params("usersAddress.city", city)
                .params("usersAddress.address", detail)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getString("success").equals("修改成功")) {
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(AddNewAddr.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initView() {
        tvTitleText.setText("编辑地址");
        tvTitleVice.setText("保存");
        tvNewaddrArea.setText(intent.getStringExtra("distract"));
//        Log.d("district", intent.getStringExtra("district"));
        Toast.makeText(AddNewAddr.this, intent.getStringExtra("distract"), Toast.LENGTH_SHORT).show();
        tvNewaddrCity.setText(intent.getStringExtra("city"));
        tvNewaddrProvince.setText(intent.getStringExtra("area"));
        tvNewaddrStreet.setText(intent.getStringExtra("street"));
        edtNewaddrName.setText(intent.getStringExtra("name"));
        edtNewaddrMobile.setText(intent.getStringExtra("phone"));

    }

    @OnClick({R.id.image_title_back, R.id.Localization, R.id.tv_newaddr_street,
            R.id.edt_newaddr_detail, R.id.tv_title_vice,
            R.id.tv_newaddr_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_title_back:
                finish();
                break;
            case R.id.Localization:
                CityPickerView cityPickerView = new CityPickerView(AddNewAddr.this);
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        tvNewaddrProvince.setText(province);
                        //城市
                        String city = citySelected[1];
                        tvNewaddrCity.setText(city);
                        //区县
                        String district = citySelected[2];
                        tvNewaddrArea.setText(district);
                        //邮编
                        String code = citySelected[3];
                        Toast.makeText(AddNewAddr.this, province + "-" + city + "-" + district, Toast.LENGTH_LONG).show();
                    }
                });
                cityPickerView.show();
                break;
            case R.id.tv_newaddr_street:
                break;
            case R.id.edt_newaddr_detail:
                break;
            case R.id.tv_newaddr_delete:
                break;
            case R.id.tv_title_vice:
                detail = edtNewaddrDetail.getText().toString().trim();
                name = edtNewaddrName.getText().toString().trim();
                phone = edtNewaddrMobile.getText().toString().trim();
                street = tvNewaddrStreet.getText().toString().trim();
                province = tvNewaddrProvince.getText().toString().trim();
                district = tvNewaddrArea.getText().toString().trim();
                city = tvNewaddrCity.getText().toString().trim();
                if (TextUtils.isEmpty(detail)) {
                    Toast.makeText(AddNewAddr.this, "请填写详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddNewAddr.this, "请填写姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(AddNewAddr.this, "请填写联系方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(street)) {
                    Toast.makeText(AddNewAddr.this, "请填写街道", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(province)) {
                    Toast.makeText(AddNewAddr.this, "请填写省份", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(district)) {
                    Toast.makeText(AddNewAddr.this, "请填写区域", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(AddNewAddr.this, "请填写县/市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != userid) {
                    initData(userid);
                }
                break;
        }
    }
}
