package com.example.administrator.a3dmark.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.OrderAdapter;
import com.example.administrator.a3dmark.alipay.AuthResult;
import com.example.administrator.a3dmark.alipay.PayDemoActivity;
import com.example.administrator.a3dmark.alipay.PayResult;
import com.example.administrator.a3dmark.alipay.util.OrderInfoUtil2_0;
import com.example.administrator.a3dmark.bean.OrderGoodsInfo;
import com.example.administrator.a3dmark.bean.OrderStoreInfo;
import com.example.administrator.a3dmark.child_pakage.Address;
import com.example.administrator.a3dmark.child_pakage.Person_Addr;
import com.example.administrator.a3dmark.util.Contants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.cache.CacheMode;
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
 * Created by LGY on 2017/4/17.
 * 多个订单
 */

public class OrdersActivity extends BaseActivity implements View.OnClickListener, OrderAdapter.EditTextGetTextListener {

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
    private Map<String,String> groupsMsg = new HashMap<>();//匹配的组元素留言

    private String addr_id;
    private String userId;
    private String goodsInfos;
    private double totalPrice;
    private boolean addState = true;

    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        try {
                            String status = (String) CacheManager.INSTANCE.get("outTradeNo").getData();
                            JSONObject json = new JSONObject(status);
                            JSONObject successObj =  json.getJSONObject("success");
                            String id = successObj.getString("outTradeNo");
                            sendService(id);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(OrdersActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setAction("status");		//设置Action
                        intent.putExtra("msg", "添加购物车成功！");	//添加附加信息
                        sendBroadcast(intent);
                        startActivity(new Intent(OrdersActivity.this, MyOrder.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        OrdersActivity.this.finish();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };



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
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
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
                    addState = true;
                }

            }
        }


        private void initView () {
            tvTitleText.setText("确认订单");
            tvTitleVice.setVisibility(View.GONE);
            imageTitleBack.setOnClickListener(this);
            tv_addr.setOnClickListener(this);
            btn_submit_order.setOnClickListener(this);
            exListView.setLayoutManager(new GridLayoutManager(exListView.getContext(), 1, GridLayoutManager.VERTICAL, false));
            selva = new OrderAdapter(this);
            exListView.setAdapter(selva);
        }


        private void initData () {

            mDialog = new ProgressDialog(this);
            mDialog.show();
            OkGo.post(Contants.DEFAULE_ADDRESS)
                    .params("userId", userId)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            mDialog.dismiss();
                            try {
                                JSONObject json = new JSONObject(s);
                                //buyanjing//再次判断
                                if (s.indexOf("error") != -1) {//有错误
                                    if ("-2".equals(json.getString("error"))){
                                        Toast.makeText(OrdersActivity.this, "没有地址!", Toast.LENGTH_SHORT).show();
                                        addState = false;
                                        return;
                                    }
                                    Toast.makeText(OrdersActivity.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                    addState = false;
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
        private void calculate () {
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
        public void onClick (View view){
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

                if (addState != true){
                    Toast.makeText(this, "没有地址,请添加地址!", Toast.LENGTH_SHORT).show();
                    return;
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
        public void editText ( int position, String editText){
            String id = groups.get(position).getId();
            groupsMsg.put(id, editText);
        }


    /**
     * 提交数据
     * @param infos
     */
    private void initnowData (String infos){
            Log.d("infos: ", infos);
            mDialog = ProgressDialog.show(this,"","");
            OkGo.post(Contants.SCART_STANDBY)
                    .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                    .cacheKey("outTradeNo")
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
                                    CacheManager.INSTANCE.remove("outTradeNo");//去掉错误缓存
                                    Toast.makeText(OrdersActivity.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                //body:{"success":"购物车待支付成功"}
                                //调后台支付接口 -->未支付生成未付款订单,支付生成未发货订单
                                JSONObject successObj =  json.getJSONObject("success");
                                final String orderInfo = successObj.getString("orderInfo");

                                Log.i("orderInfo",orderInfo);

                                Runnable payRunnable = new Runnable() {

                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(OrdersActivity.this);
                                        Map<String, String> result = alipay.payV2(orderInfo, true);
                                        Log.i("msp", result.toString());

                                        Message msg = new Message();
                                        msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };

                                Thread payThread = new Thread(payRunnable);
                                payThread.start();

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


    /*成功回调服务器接口*/
    private void sendService(final String id){
        mDialog = ProgressDialog.show(this,"","");
        OkGo.post(Contants.PAYSUCCESSS)
                .params("outTradeNo", id)
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
                            //成功
                            CacheManager.INSTANCE.remove("outTradeNo");//去掉缓存
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(OrdersActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setAction("status");		//设置Action
                            intent.putExtra("msg", "添加购物车成功！");	//添加附加信息
                            sendBroadcast(intent);
                            startActivity(new Intent(OrdersActivity.this, MyOrder.class).putExtra("framnt","1")
                                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            OrdersActivity.this.finish();

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheManager.INSTANCE.remove("outTradeNo");//去掉缓存
    }
}

