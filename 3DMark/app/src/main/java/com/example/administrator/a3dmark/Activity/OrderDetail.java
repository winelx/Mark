package com.example.administrator.a3dmark.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.Contants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**订单详情
 * Created by Administrator on 2017/4/17.
 */
public class OrderDetail extends Activity {
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.image_order_detail_box)
    ImageView imageOrderDetailBox;
    @BindView(R.id.btn_order_detail_payment)
    TextView btnOrderDetailPayment;
    @BindView(R.id.tv_order_detail_Consignee_name)
    TextView tvOrderDetailConsigneeName;
    @BindView(R.id.tv_order_detail_Consignee_phone)
    TextView tvOrderDetailConsigneePhone;
    @BindView(R.id.tv_order_detail_Consignee_addr)
    TextView tvOrderDetailConsigneeAddr;
    @BindView(R.id.tv_order_detail_message)
    TextView tvOrderDetailMessage;
    @BindView(R.id.ll_order_detail_logistic)
    TextView llOrderDetailLogistic;
    @BindView(R.id.ll_order_detail_message)
    LinearLayout llOrderDetailMessage;
    @BindView(R.id.image_order_detail_shop)
    ImageView imageOrderDetailShop;
    @BindView(R.id.tv_order_detail_shop_name)
    TextView tvOrderDetailShopName;
    @BindView(R.id.image_order_detail_shop_more)
    ImageView imageOrderDetailShopMore;
    @BindView(R.id.image_order_detail_goods)
    ImageView imageOrderDetailGoods;
    @BindView(R.id.tv_order_detail_goods_detail)
    TextView tvOrderDetailGoodsDetail;
    @BindView(R.id.tv_order_detail_goods_size)
    TextView tvOrderDetailGoodsSize;
    @BindView(R.id.tv_order_detail_goods_price)
    TextView tvOrderDetailGoodsPrice;
    @BindView(R.id.tv_order_detail_goods_num)
    TextView tvOrderDetailGoodsNum;
    @BindView(R.id.btn_order_detail_goods_payment)
    TextView btnOrderDetailGoodsPayment;
    @BindView(R.id.tv_order_detail_Commodity_price)
    TextView tvOrderDetailCommodityPrice;
    @BindView(R.id.tv_order_detail_freight)
    TextView tvOrderDetailFreight;
    @BindView(R.id.tv_order_detail_Order_price)
    TextView tvOrderDetailOrderPrice;
    Intent intent;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_detail);
        intent = getIntent();
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        final ProgressDialog mDialog = ProgressDialog.show(OrderDetail.this, "获取数据", "获取数据中");
        OkGo.post(Contants.ORDER_DETAIL)
                .params("orderId", intent.getStringExtra("orderid"))
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("remove========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            boolean is_null = jsobj.isNull("success");
                            if (!is_null) {
                                JSONObject obj = jsobj.getJSONObject("success");
                                tvOrderDetailConsigneeName.setText("收货人：" + obj.getString("name"));
                                tvOrderDetailConsigneePhone.setText(obj.getString("phone"));
                                tvOrderDetailConsigneeAddr.setText("收货地址：" + obj.getString("area")
                                        + obj.getString("city") + obj.getString("district") + obj.getString("street")
                                        + obj.getString("address"));
                                boolean is_message = obj.isNull("message");
                                if (!is_message) {
                                    tvOrderDetailMessage.setText("买家留言：" + obj.getString("message"));
                                } else if (is_message) {
                                    tvOrderDetailMessage.setText("买家留言：");
                                }
//                                    llOrderDetailLogistic.setText(obj.getString(""));
                                Glide.with(OrderDetail.this).load(obj.getString("logo")).centerCrop().
                                        placeholder(R.drawable.icon_empty).into(imageOrderDetailShop);
                                Glide.with(OrderDetail.this).load(obj.getString("img")).centerCrop().
                                        placeholder(R.drawable.icon_empty).into(imageOrderDetailGoods);
                                tvOrderDetailShopName.setText(obj.getString("bussinessName"));
                                tvOrderDetailGoodsDetail.setText(obj.getString("goodsName"));
                                tvOrderDetailGoodsSize.setText(obj.getString("color") + "    " + obj.getString("size") + "码");
                                tvOrderDetailGoodsPrice.setText("￥" + obj.getString("price"));
                                tvOrderDetailGoodsNum.setText("X" + obj.getString("num"));
                                tvOrderDetailCommodityPrice.setText("￥" + obj.getString("countPrice"));
                                tvOrderDetailOrderPrice.setText("￥" + obj.getString("priceTotal"));
                                tvOrderDetailFreight.setText("￥" + obj.getString("mail"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                    }
                });
    }

    private void initView() {
        tvDetail.setText("订单详情");
        String state = intent.getStringExtra("state");
        Log.d("state", state);
        if (state.equals("1")) {
            btnOrderDetailPayment.setText("买家未付款");
            imageOrderDetailBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.box_one));
            llOrderDetailMessage.setVisibility(View.GONE);
            btnOrderDetailGoodsPayment.setText("付款");
            return;
        }
        if (state != "1") {
            btnOrderDetailPayment.setText("买家已付款");
            imageOrderDetailBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.box_two));
            llOrderDetailMessage.setVisibility(View.VISIBLE);
            btnOrderDetailGoodsPayment.setBackgroundColor(getResources().getColor(R.color.bg_white));
            btnOrderDetailGoodsPayment.setText("退款");
        }
    }

    @OnClick({R.id.tv_back, R.id.btn_order_detail_payment, R.id.btn_order_detail_goods_payment})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_order_detail_payment:
                break;
            case R.id.btn_order_detail_goods_payment:
                intent = new Intent(OrderDetail.this,Refund_Apply.class);
                break;
        }
    }
}
