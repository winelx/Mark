package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.LogisticsAdapter;
import com.example.administrator.a3dmark.bean.Logistics;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.ListView_Demo;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 物流信息
 *
 * @date 2013-10-08
 */
public class Logistic_message extends Activity {

    @BindView(R.id.img_Logistics_details_goods)
    ImageView imgLogisticsDetailsGoods;
    @BindView(R.id.tv_Logistics_details_sign)
    TextView tvLogisticsDetailsSign;
    @BindView(R.id.tv_Logistics_details_express)
    TextView tvLogisticsDetailsExpress;
    @BindView(R.id.tv_Logistics_details_num)
    TextView tvLogisticsDetailsNum;
    @BindView(R.id.tv_Logistics_details_phone)
    TextView tvLogisticsDetailsPhone;
    @BindView(R.id.image_Logistics_details_head1)
    ImageView imageLogisticsDetailsHead1;
    @BindView(R.id.lv_Logistics_details)
    ListView_Demo lvLogisticsDetails;
    LogisticsAdapter adapter;
    List<Logistics> list = new ArrayList<Logistics>();
    ScrollView scollview_logistics;
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.ll_logistics)
    LinearLayout llLogistics;
    @BindView(R.id.scollview_logistics)
    ScrollView scollviewLogistics;
    Intent intent;
    @BindView(R.id.image_logistics_circle)
    ImageView imageLogisticsCircle;
    @BindView(R.id.image_logistics_line)
    ImageView imageLogisticsLine;
    @BindView(R.id.tv_logistic_detail)
    TextView tvLogisticDetail;
    @BindView(R.id.tv_logistic_time)
    TextView tvLogisticTime;
    @BindView(R.id.ll_logistics_one)
    LinearLayout llLogisticsOne;
    @BindView(R.id.tv_logistic)
    TextView tvLogistic;
    private String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        intent = getIntent();
        ButterKnife.bind(this);
        scollview_logistics = (ScrollView) findViewById(R.id.scollview_logistics);
        LinearLayout ll_logistics = (LinearLayout) findViewById(R.id.ll_logistics);
        lvLogisticsDetails.setFocusable(false);
//        initView();
        tvDetail.setText("物流信息");
        Glide.with(this).load(intent.getStringExtra("img")).centerCrop().placeholder(R.drawable.icon_empty).into(imgLogisticsDetailsGoods);
        initData();
    }

    private void initData() {
        final ProgressDialog mDialog = ProgressDialog.show(Logistic_message.this, "获取数据", "获取数据中");
        OkGo.post(Contants.LOGISTIC_MESSAGE)
                .params("orderId", intent.getStringExtra("orderid"))
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            boolean is_null = jsobj.isNull("success");
                            if (!is_null) {
                                JSONObject obj = jsobj.getJSONObject("success");

                                state = obj.getString("state");
                                if (state.equals("0")) {
                                    tvLogisticsDetailsSign.setText("未发货");
                                    imageLogisticsCircle.setBackgroundDrawable(getResources().getDrawable(R.mipmap.logistics_grey));
                                    llLogisticsOne.setVisibility(View.GONE);
                                    tvLogistic.setVisibility(View.VISIBLE);
                                    tvLogisticsDetailsExpress.setVisibility(View.GONE);
                                    lvLogisticsDetails.setVisibility(View.GONE);
                                    return;
                                }
                                if (state.equals("2")) {
                                    tvLogisticsDetailsSign.setText("运输中");
                                    tvLogisticsDetailsExpress.setText(obj.getString("shipper"));
                                    imageLogisticsCircle.setBackgroundDrawable(getResources().getDrawable(R.mipmap.logistics_grey));
                                }
                                if (state.equals("3")) {
                                    tvLogisticsDetailsSign.setText("已签收");
                                    tvLogisticsDetailsExpress.setText(obj.getString("shipper"));
                                    imageLogisticsCircle.setBackgroundDrawable(getResources().getDrawable(R.mipmap.logistics_green));
                                }
                                if (state.equals("4")) {
                                    tvLogisticsDetailsSign.setText("问题件");
                                    tvLogisticsDetailsExpress.setText(obj.getString("shipper"));
                                    imageLogisticsCircle.setBackgroundDrawable(getResources().getDrawable(R.mipmap.logistics_grey));
                                }
                                tvLogisticsDetailsNum.setText(obj.getString("logisticCode"));
                                JSONArray jsArray = obj.getJSONArray("traces");
                                if (jsArray.length() >= 1) {
                                    tvLogisticDetail.setText(jsArray.getJSONObject(jsArray.length() - 1).getString("AcceptStation"));
                                    tvLogisticTime.setText(jsArray.getJSONObject(jsArray.length() - 1).getString("AcceptTime"));
                                    Logistics mLogistics = null;
                                    if (jsArray.length() >= 2) {
                                        for (int i = jsArray.length() - 2; i > 0; i--) {
                                            JSONObject jsObject = jsArray.getJSONObject(i);
                                            mLogistics = new Logistics();
                                            mLogistics.setAcceptStation(jsObject.getString("AcceptStation"));
                                            mLogistics.setAcceptTime(jsObject.getString("AcceptTime"));
                                            list.add(mLogistics);
                                        }
                                        adapter = new LogisticsAdapter(list, Logistic_message.this);
                                        lvLogisticsDetails.setAdapter(adapter);
                                    }
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Logistic_message.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @OnClick(R.id.tv_back)
    public void onClick() {
        finish();
    }
}