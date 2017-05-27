package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.LogisticsAdapter;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.ListView_Demo;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.example.administrator.a3dmark.util.Util;
import com.lljjcoder.citypickerview.widget.CityPickerView;
import com.loopj.android.image.SmartImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 新增地址
 * Created by Administrator on 2017/3/23.
 */
public class EditAddr extends Activity {
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
    @BindView(R.id.tv_newaddr_default)
    CheckBox tv_newaddr_default;
    String detail;
    String name;
    String phone;
    String street;
    String province;
    String district;
    String city;
    String isdefault = "0";
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnew_addr);
        ButterKnife.bind(this);
        userid = (String) SharedUtil.getParam(this, "userid", "");
        initView();
    }

    private void initView() {
        tvTitleText.setText("添加新地址");
        tvTitleVice.setVisibility(View.VISIBLE);
        tvTitleVice.setText("保存");
        tvNewaddrDelete.setVisibility(View.GONE);
        tv_newaddr_default.setVisibility(View.VISIBLE);
        tv_newaddr_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isdefault = "0";
                } else {
                    isdefault = "1";
                }
            }
        });
    }

    @OnClick({R.id.image_title_back, R.id.Localization, R.id.tv_title_vice})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_title_back:
                finish();
                break;
            case R.id.Localization:
                CityPickerView cityPickerView = new CityPickerView(EditAddr.this);
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
                        Toast.makeText(EditAddr.this, province + "-" + city + "-" + district, Toast.LENGTH_LONG).show();
                    }
                });
                cityPickerView.show();
                break;
            case R.id.tv_title_vice:
                detail = edtNewaddrDetail.getText().toString().trim();
                name = edtNewaddrName.getText().toString().trim();
                phone = edtNewaddrMobile.getText().toString().trim();
                street = tvNewaddrStreet.getText().toString().trim();
                province = tvNewaddrProvince.getText().toString().trim();
                district = tvNewaddrArea.getText().toString().trim();
                city = tvNewaddrCity.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(EditAddr.this, "请填写姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Util.isPhone(phone)) {
                    Toast.makeText(EditAddr.this, "请填写正确的联系方式", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(province)) {
                    Toast.makeText(EditAddr.this, "请填写省份", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(street)) {
                    Toast.makeText(EditAddr.this, "请填写街道", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(district)) {
                    Toast.makeText(EditAddr.this, "请填写区域", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(EditAddr.this, "请填写县/市", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(detail)) {
                    Toast.makeText(EditAddr.this, "请填写详细地址", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (null != userid) {
                    initData(userid);
                }
                break;
        }
    }

    private void initData(String userid) {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.post(Contants.ADD_ADDR)
                .params("userId", userid)
                .params("usersAddress.name", name)
                .params("usersAddress.telephone", phone)
                .params("usersAddress.area", province)
                .params("usersAddress.district", district)
                .params("usersAddress.street", street)
                .params("usersAddress.city", city)
                .params("usersAddress.address", detail)
                .params("usersAddress.is_default", isdefault)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (jsonObject.getString("success").equals("新增成功")) {
                                Toast.makeText(EditAddr.this, "新增成功", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            } else if (jsonObject.getString("error").equals("设置失败")) {
                                Toast.makeText(EditAddr.this, "新增失败，请稍后重试", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(EditAddr.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }


}
