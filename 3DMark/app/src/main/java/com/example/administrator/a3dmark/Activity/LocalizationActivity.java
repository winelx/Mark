package com.example.administrator.a3dmark.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.OrderAdapter;
import com.example.administrator.a3dmark.bean.OrderGoodsInfo;
import com.example.administrator.a3dmark.bean.OrderStoreInfo;
import com.example.administrator.a3dmark.child_pakage.Address;
import com.example.administrator.a3dmark.child_pakage.Person_Addr;
import com.example.administrator.a3dmark.util.Contants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lljjcoder.citypickerview.widget.CityPickerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 三级联动
 */
public class LocalizationActivity extends AppCompatActivity {
    private LinearLayout Localization;
    private TextView save, citys, xian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localization);
        save = (TextView) findViewById(R.id.save);
        citys = (TextView) findViewById(R.id.city);
        xian = (TextView) findViewById(R.id.xian);
        Localization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CityPickerView cityPickerView = new CityPickerView(LocalizationActivity.this);
                cityPickerView.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        //省份
                        String province = citySelected[0];
                        save.setText(province);
                        //城市
                        String city = citySelected[1];
                        citys.setText(city);
                        //区县
                        String district = citySelected[2];
                        xian.setText(district);
                        //邮编
                        String code = citySelected[3];
                        Toast.makeText(LocalizationActivity.this, province + "-" + city + "-" + district, Toast.LENGTH_LONG).show();
                    }
                });
                cityPickerView.show();
            }
        });
    }

    /**
     * Created by LGY on 2017/4/17.
     * 多个订单
     */

    public static class OrdersActivity extends BaseActivity implements View.OnClickListener, OrderAdapter.EditTextGetTextListener {

        @BindView(R.id.image_title_back)
        ImageView imageTitleBack;
        @BindView(R.id.tv_title_text)
        TextView tvTitleText;
        @BindView(R.id.tv_title_vice)
        TextView tvTitleVice;
        @BindView(R.id.tv_addr)
        LinearLayout tv_addr;
        @BindView(R.id.tv_ensure_order_name)
        TextView tv_ensure_order_name;
        @BindView(R.id.tv_ensure_order_phone)
        TextView tv_ensure_order_phone;
        @BindView(R.id.tv_ensure_order_addr)
        TextView tv_ensure_order_addr;
        @BindView(R.id.tv_order_goods_totalpeice)
        TextView tv_order_goods_totalpeice;
        @BindView(R.id.btn_submit_order)
        TextView btn_submit_order;
        @BindView(R.id.exListView)
        RecyclerView exListView;

        private ProgressDialog mDialog;


        private OrderAdapter selva;
        private List<OrderStoreInfo> groups = new ArrayList<OrderStoreInfo>();// 组元素数据列表
        private Map<String, List<OrderGoodsInfo>> children = new HashMap<String, List<OrderGoodsInfo>>();// 匹配组元素的子元素数据列表
        private Map<String, String> groupsMsg = new HashMap<>();//匹配的组元素留言

        private String addr_id;
        private String userId;
        private String goodsInfos;
        private double totalPrice;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_orders);
            ButterKnife.bind(this);
            userId = getIntent().getStringExtra("userId");
            goodsInfos = getIntent().getStringExtra("goodsInfos");
            initView();
            initData();
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == 200) {
                Address addr = (Address) data.getSerializableExtra("address");
                if (null != addr) {
                    tv_ensure_order_addr.setText("收货地址：" + addr.getArea() +//省
                            addr.getCity() +//市
                            addr.getDistrict() +//县/区
                            addr.getStreet() +//街道
                            addr.getAddr());//详细地址
                    tv_ensure_order_name.setText("收货人：" + addr.getName());//姓名
                    tv_ensure_order_phone.setText(addr.getPhone());//电话
                    addr_id = addr.getId();
                }

            }
        }


        private void initView() {
            tvTitleText.setText("确认订单");
            tvTitleVice.setVisibility(View.GONE);
            imageTitleBack.setOnClickListener(this);
            tv_addr.setOnClickListener(this);
            btn_submit_order.setOnClickListener(this);
            exListView.setLayoutManager(new GridLayoutManager(exListView.getContext(), 1, GridLayoutManager.VERTICAL, false));
            selva = new OrderAdapter(this);
            exListView.setAdapter(selva);
        }


        private void initData() {

            mDialog = new ProgressDialog(this);
            mDialog.show();
            OkGo.post(Contants.DEFAULE_ADDRESS)
                    .params("userId", userId)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            mDialog.dismiss();
                            Log.d("jsonArray", s);
                            try {
                                JSONObject json = new JSONObject(s);
                                //buyanjing//再次判断
                                if (s.indexOf("error") != -1) {//有错误
                                    Toast.makeText(OrdersActivity.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //正确
                                JSONObject success = json.getJSONObject("success");
                                tv_ensure_order_addr.setText("收货地址：" + success.getString("area") +//省
                                        success.getString("city") +//市
                                        success.getString("district") +//县/区
                                        success.getString("street") +//街道
                                        success.getString("address"));//详细地址
                                tv_ensure_order_name.setText("收货人：" + success.getString("name"));//姓名
                                tv_ensure_order_phone.setText(success.getString("telephone"));//电话
                                addr_id = success.getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            e.printStackTrace();
                            mDialog.dismiss();
                        }
                    });


            try {
                JSONArray jsonArray = new JSONArray(goodsInfos);
                OrderStoreInfo storeInfo = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject storeObject = jsonArray.getJSONObject(i);
                    storeInfo = new OrderStoreInfo();
                    storeInfo.setId(storeObject.getString("bussiness_id"));
                    storeInfo.setName(storeObject.getString("storeName"));
                    storeInfo.setStoreLogoImg(storeObject.getString("logo"));
                    groups.add(storeInfo);
                    JSONArray goodsArray = storeObject.getJSONArray("goods");
                    List<OrderGoodsInfo> products = new ArrayList<OrderGoodsInfo>();
                    OrderGoodsInfo goodsInfo = null;
                    for (int j = 0; j < goodsArray.length(); j++) {
                        JSONObject goodsObject = goodsArray.getJSONObject(j);
                        goodsInfo = new OrderGoodsInfo();
                        goodsInfo.setBussinessId(storeObject.getString("bussiness_id"));
                        goodsInfo.setId(goodsObject.getString("goods_id"));
                        goodsInfo.setScartId(goodsObject.getString("scartId"));
                        goodsInfo.setDesc(goodsObject.getString("goodsName"));
                        //                    goodsInfo.setConnect(goodsObject.getString("connect"));
                        goodsInfo.setMail(goodsObject.getDouble("mail"));
                        goodsInfo.setDeliveryTime(goodsObject.getString("sendTime"));
                        goodsInfo.setImageUrl(goodsObject.getString("img"));
                        goodsInfo.setPrice(goodsObject.getDouble("price"));
                        goodsInfo.setCount(goodsObject.getInt("num"));
                        goodsInfo.setColor(goodsObject.getString("color"));
                        goodsInfo.setSize(goodsObject.getString("size"));
                        products.add(goodsInfo);
                    }
                    children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
                    selva.setChildren(children);
                }
                selva.setGroups(groups);
                calculate();

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        /**
         * 总计
         */
        private void calculate() {
            totalPrice = 0.00;
            for (int i = 0; i < groups.size(); i++) {
                OrderStoreInfo group = groups.get(i);
                List<OrderGoodsInfo> childs = children.get(group.getId());
                for (int j = 0; j < childs.size(); j++) {
                    OrderGoodsInfo product = childs.get(j);
                    totalPrice += (product.getPrice() + product.getMail()) * product.getCount();
                }
            }

            tv_order_goods_totalpeice.setText("￥" + totalPrice);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == imageTitleBack.getId()) {
                finish();
            } else if (view.getId() == tv_addr.getId()) {//去选择地址
                startActivityForResult(new Intent(this, Person_Addr.class).putExtra("userid", userId), 100);//.putExtra("userid",userid)
            } else if (view.getId() == btn_submit_order.getId()) {//提交订单
                //调支付接口
                //            String jsonStr = "{\"id\": 2," +
                //                    " \"title\": \"json title\", " +
                //                    "\"config\": {" +
                //                    "\"width\": 34," +
                //                    "\"height\": 35," +
                //                    "}, \"data\": [" +
                //                    "\"JAVA\", \"JavaScript\", \"PHP\"" +
                //                    "]}";

                JsonArray jsonArray = new JsonParser().parse(goodsInfos).getAsJsonArray();
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject json = jsonElement.getAsJsonObject();
                    String bussiness_id = json.get("bussiness_id").getAsString();
                    if (groupsMsg.containsKey(bussiness_id)) {
                        json.addProperty("msg", groupsMsg.get(bussiness_id));
                    } else {
                        json.addProperty("msg", "");

                    }
                }
                initnowData(jsonArray.toString());
            }
        }


        /**
         * 获取对应留言
         *
         * @param position
         * @param editText
         */
        @Override
        public void editText(int position, String editText) {
            String id = groups.get(position).getId();
            groupsMsg.put(id, editText);
        }


        private void initnowData(String infos) {

            Log.d("infos: ", infos);
            mDialog = new ProgressDialog(this);
            mDialog.show();
            OkGo.post(Contants.SCART_STANDBY)
                    .params("infos", infos)
                    .params("addr_id", addr_id)
                    .params("userId", userId)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Log.d("OrdersActivity支付", s);
                            mDialog.dismiss();
                            JSONObject json = null;
                            try {
                                json = new JSONObject(s);
                                if (s.indexOf("error") != -1) {//有错误
                                    Toast.makeText(OrdersActivity.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //body:{"success":"购物车待支付成功"}
                                //调后台支付接口 -->未支付生成未付款订单,支付生成未发货订单


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            mDialog.dismiss();
                            Toast.makeText(OrdersActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }
}
