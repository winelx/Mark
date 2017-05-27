package com.example.administrator.a3dmark.detail_shop;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.Activity.BaseActivity;
import com.example.administrator.a3dmark.Activity.MyOrder;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.alipay.PayResult;
import com.example.administrator.a3dmark.child_pakage.Address;
import com.example.administrator.a3dmark.child_pakage.Person_Addr;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.a3dmark.util.SharedUtil.getParam;

/**
 * Created by Administrator on 2017/4/7.
 */
public class Ensure_Order extends BaseActivity {
    Intent intent;
    @BindView(R.id.tv_ensure_order_name)
    TextView tvEnsureOrderName;
    @BindView(R.id.tv_ensure_order_phone)
    TextView tvEnsureOrderPhone;
    @BindView(R.id.tv_ensure_order_addr)
    TextView tvEnsureOrderAddr;
    @BindView(R.id.image_ensure_order_mores)
    LinearLayout imageEnsureOrderMore;
    @BindView(R.id.image_ensure_order_shop_head)
    ImageView imageEnsureOrderShopHead;
    @BindView(R.id.tv_ensure_order_shop_name)
    TextView tvEnsureOrderShopName;
    @BindView(R.id.image_ensure_order_goods_image)
    ImageView imageEnsureOrderGoodsImage;
    @BindView(R.id.tv_ensure_order_goods_detail)
    TextView tvEnsureOrderGoodsDetail;
    @BindView(R.id.tv_ensure_order_goods_price)
    TextView tvEnsureOrderGoodsPrice;
    @BindView(R.id.tv_ensure_order_goods_num)
    TextView tvEnsureOrderGoodsNum;
    @BindView(R.id.tv_ensure_order_goods_time)
    TextView tvEnsureOrderGoodsTime;
    @BindView(R.id.tv_ensure_order_goods_size)
    TextView tvEnsureOrderGoodsSize;
    @BindView(R.id.tv_ensure_order_goods_color)
    TextView tvEnsureOrderGoodsColor;
    @BindView(R.id.tv_ensure_order_goods_mode)
    TextView tvEnsureOrderGoodsMode;
    @BindView(R.id.tv_ensure_order_goods_message)
    EditText tvEnsureOrderGoodsMessage;
    @BindView(R.id.tv_ensure_order_goods_goodsnum)
    TextView tvEnsureOrderGoodsGoodsnum;
    @BindView(R.id.tv_ensure_order_goods_Subtotal)
    TextView tvEnsureOrderGoodsSubtotal;
    @BindView(R.id.tv_ensure_order_goods_totalpeice)
    TextView tvEnsureOrderGoodsTotalpeice;
    @BindView(R.id.btn_ensure_order_submit)
    TextView btnEnsureOrderSubmit;
    @BindView(R.id.back_ensure_order)
    Button backEnsureOrder;
    @BindView(R.id.title_ensure_order)
    TextView titleEnsureOrder;
    @BindView(R.id.layout_title)
    LinearLayout layoutTitle;

    private String color;
    private String size;
    private double pricetotal;
    private double price;
    private String goods_id;
    //    private String business_id;
    private String userid;
    //    private Boolean IsDefault = false;
    private String is_addr;
    private String addr_id;
    private String num;
    private String bussinessId;
    private String transportType;
    private String priceModeNow;
    private String price_sigal;
    String goodid;
    private ProgressDialog mDialog;

    private static final int SDK_PAY_FLAG = 1;
    boolean lean = true;
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
                            JSONObject successObj = json.getJSONObject("success");
                            String id = successObj.getString("outTradeNo");
                            sendService(id);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(Ensure_Order.this, "支付失败", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Ensure_Order.this, MyOrder.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                        Ensure_Order.this.finish();
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ensure_order);
        ButterKnife.bind(this);
        intent = getIntent();
        userid = (String) getParam(this, "userid", "");
        is_addr = intent.getStringExtra("is_addr");
        num = intent.getStringExtra("num");

        Address addr = (Address) intent.getSerializableExtra("address");
        initView();
        if (null != addr) {
            tvEnsureOrderName.setText("收货人：" + addr.getName());
            tvEnsureOrderPhone.setText(addr.getPhone());
            tvEnsureOrderAddr.setText("收货地址：" + addr.getArea() + addr.getCity() +
                    addr.getDistrict() + addr.getStreet() + addr.getAddr());
            addr_id = addr.getId();
            return;
        }
        initData();
    }

    private void initView() {
        titleEnsureOrder.setText("确认订单");
        backEnsureOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(Ensure_Order.this, PopupWindowActivity.class));
                finish();
            }
        });
    }


    private void initData() {
        final ProgressDialog mDialog = ProgressDialog.show(Ensure_Order.this, "", "获取地址中...");
        String tgoodid = getParam(Ensure_Order.this, "goods_id", "").toString();
        OkGo.post(Contants.DEFAULE_ADDR)
                .params("userId", userid)
                .params("goodsId", tgoodid)
                .params("color", intent.getStringExtra("color"))
                .params("size", intent.getStringExtra("size"))
                .params("num", num)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("DEFAULE_ADDR===", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(Ensure_Order.this, "没有地址!", Toast.LENGTH_SHORT).show();
                                if (s.indexOf("error") != -2) {
                                    lean = false;
                                    return;
                                }
                                Toast.makeText(Ensure_Order.this, jsobj.getString("error"), Toast.LENGTH_SHORT).show();
                                lean = false;
                                return;
                            }
                            JSONObject obj = jsobj.getJSONObject("success");
                            addr_id = obj.getJSONObject("uAddress").getString("id");
                            tvEnsureOrderName.setText("收货人：" + obj.getJSONObject("uAddress").getString("name"));
                            tvEnsureOrderPhone.setText(obj.getJSONObject("uAddress").getString("telephone"));
                            tvEnsureOrderAddr.setText("收货地址：" + obj.getJSONObject("uAddress").getString("area")
                                    + obj.getJSONObject("uAddress").getString("city")
                                    + obj.getJSONObject("uAddress").getString("district")
                                    + obj.getJSONObject("uAddress").getString("street"));
                            tvEnsureOrderShopName.setText(obj.getJSONObject("goodsInfo").getString("bussinessName"));
                            Glide.with(Ensure_Order.this).load(obj.getJSONObject("goodsInfo").getString("logo")).
                                    centerCrop().placeholder(R.mipmap.touxiang_default).into(imageEnsureOrderShopHead);
                            Glide.with(Ensure_Order.this).load(obj.getJSONObject("goodsInfo").getString("img")).
                                    centerCrop().placeholder(R.mipmap.default_image_goods).into(imageEnsureOrderGoodsImage);
                            tvEnsureOrderGoodsDetail.setText(obj.getJSONObject("goodsInfo").getString("goodsName"));
                            tvEnsureOrderGoodsPrice.setText("￥" + obj.getJSONObject("goodsInfo").getString("priceModeNow"));
                            price_sigal = obj.getJSONObject("goodsInfo").getString("priceModeNow");
                            tvEnsureOrderGoodsTime.setText("发货时间：" + obj.getJSONObject("goodsInfo").getString("transportTag"));
                            tvEnsureOrderGoodsSize.setText("宝贝尺寸：" + obj.getJSONObject("goodsInfo").getString("size") + "码");
                            tvEnsureOrderGoodsColor.setText("宝贝颜色：" + obj.getJSONObject("goodsInfo").getString("color"));
                            tvEnsureOrderGoodsMode.setText(obj.getJSONObject("goodsInfo").getString("transport"));
                            tvEnsureOrderGoodsNum.setText("X" + obj.getJSONObject("goodsInfo").getString("num"));
                            tvEnsureOrderGoodsGoodsnum.setText("共" + obj.getJSONObject("goodsInfo").getString("num") + "件商品");
                            priceModeNow = obj.getJSONObject("goodsInfo").getString("priceModeNow");
                            bussinessId = obj.getJSONObject("goodsInfo").getString("bussinessId");
                            transportType = obj.getJSONObject("goodsInfo").getString("transportId");
//                            int total_num = Integer.parseInt(tvEnsureOrderGoodsTotal.getText().toString());
                            price = Double.parseDouble(priceModeNow);
                            int num = Integer.parseInt(intent.getStringExtra("num"));
                            tvEnsureOrderGoodsSubtotal.setText("小计：￥" + num * price);
                            tvEnsureOrderGoodsTotalpeice.setText("￥" + num * price);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Ensure_Order.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @OnClick({R.id.image_ensure_order_mores,
            R.id.btn_ensure_order_submit})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.image_ensure_order_mores:
                intent = new Intent(Ensure_Order.this, Person_Addr.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.btn_ensure_order_submit:
                if (lean != true) {
                    Toast.makeText(Ensure_Order.this, "没有地址,请添加地址!", Toast.LENGTH_SHORT).show();
                    return;
                }
                initnowData();
                break;
        }
    }


//    private void initadd() {
//        final ProgressDialog mDialog = ProgressDialog.show(Ensure_Order.this, "", "获取库存中...");
//        goodid = SharedUtil.getParam(Ensure_Order.this, "goods_id", "").toString();
    //  OkGo.post(Contants.ADD)
//                .params("goodsId", goodid)
//                .params("color", intent.getStringExtra("color"))
//                .params("size", intent.getStringExtra("size"))
//                .params("num", num_good_add)
//                .tag(this)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        Log.d("initnowData===", s);
//                        mDialog.dismiss();
//                        try {
//                            JSONObject object = new JSONObject(s);
//                            boolean is_success = object.isNull("success");
//                            if (!is_success) {
//                                tvEnsureOrderGoodsNum.setText("X" + num_good_add);
//                                tvEnsureOrderGoodsTotalpeice.setText("￥" + num_good_add * price);
//                                tvEnsureOrderGoodsSubtotal.setText("小计：￥" + num_good_add * price);
//                                tvEnsureOrderGoodsGoodsnum.setText("共" + num_good_add + "件商品");
//                                return;
//                            }
//                            boolean is_error = object.isNull("error");
//                            if (!is_error) {
//                                Toast.makeText(Ensure_Order.this, object.getString("error"), Toast.LENGTH_LONG).show();
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        mDialog.dismiss();
//                        Toast.makeText(Ensure_Order.this, response.message(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    String goodsid;

    private void initnowData() {
        final ProgressDialog mDialog = ProgressDialog.show(Ensure_Order.this, "", "");
        goodsid = SharedUtil.getParam(Ensure_Order.this, "goods_id", "").toString();
        OkGo.post(Contants.PAYMENT_NOW)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("outTradeNo")
                .params("userId", userid)
                .params("goodsId", goodsid)
                .params("color", intent.getStringExtra("color"))
                .params("size", intent.getStringExtra("size"))
                .params("num", num)
                .params("price", price_sigal)
                .params("addressId", addr_id)
                .params("bussinessId", bussinessId)
                .params("message", tvEnsureOrderGoodsMessage.getText().toString().trim())
                .params("transportId", transportType)
                .params("goodsName", tvEnsureOrderGoodsDetail.getText().toString())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();
                        Log.d("initnowData===", s);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                CacheManager.INSTANCE.remove("outTradeNo");//去掉错误缓存
                                Toast.makeText(Ensure_Order.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //body:{"success":"购物车待支付成功"}
                            //调后台支付接口 -->未支付生成未付款订单,支付生成未发货订单
                            JSONObject successObj = json.getJSONObject("success");
                            final String orderInfo = successObj.getString("orderInfo");

                            Log.i("orderInfo", orderInfo);

                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(Ensure_Order.this);
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
                        Toast.makeText(Ensure_Order.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /*成功回调服务器接口*/
    private void sendService(final String id) {
        mDialog = ProgressDialog.show(this, "", "");
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
                                Toast.makeText(Ensure_Order.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //成功
                            CacheManager.INSTANCE.remove("outTradeNo");//去掉缓存
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(Ensure_Order.this, "支付成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Ensure_Order.this, MyOrder.class).putExtra("framnt", "1")
                                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                            Ensure_Order.this.finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Ensure_Order.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @OnClick(R.id.layout_title)
    public void onClick() {
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheManager.INSTANCE.remove("outTradeNo");//去掉缓存
    }
}

