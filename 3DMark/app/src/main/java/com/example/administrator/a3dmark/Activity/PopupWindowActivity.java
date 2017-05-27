package com.example.administrator.a3dmark.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.MyGridView;
import com.example.administrator.a3dmark.adapter.PopColorAdapter;
import com.example.administrator.a3dmark.adapter.PopSizeAdapter;
import com.example.administrator.a3dmark.bean.DColorBean;
import com.example.administrator.a3dmark.bean.DSizeBean;
import com.example.administrator.a3dmark.detail_shop.Ensure_Order;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.example.administrator.a3dmark.util.SizeOrColorManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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

import static com.example.administrator.a3dmark.util.Contants.ADD_CARD_GOODS;
import static com.example.administrator.a3dmark.util.Contants.CHOICE_GOODS;

/**
 * Created by LGY on 2017/3/31.
 * 商品参数选择
 */

public class PopupWindowActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.layout_pop_dissmiss)
    RelativeLayout layout_pop_dissmiss;
    @BindView(R.id.pop_dissmiss)
    ImageView pop_dissmiss;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_Stock)
    TextView tv_Stock;
    @BindView(R.id.tv_color_size)
    TextView tv_color_size;
    @BindView(R.id.size)
    MyGridView gvSize;
    @BindView(R.id.color)
    MyGridView gvColor;
    @BindView(R.id.image_reduce)
    ImageView image_reduce;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.image_add)
    ImageView image_add;
    @BindView(R.id.btn_addCart)
    TextView btn_addCart;
    @BindView(R.id.btn_runShop)
    TextView btn_runShop;
    @BindView(R.id.btn_sure)
    TextView btn_sure;
                               
    List<DColorBean> mColorList;// 颜色列表
    List<DSizeBean> mSizeList;// 尺码列表
    PopColorAdapter skuColorAdapter;// 颜色适配器
    PopSizeAdapter skuSizeAdapter;// 尺码适配器
    String color;//
    String size;//
    int stock = 0;// 库存
    ArrayList<String> imgs;
    String[] sizes;
    String[] colors;
    int total;//总库存
    String money;
    String goods_id;
    String goodsName;
    String bussiness_id;
    String storeLogo;
    String storeName;
    String mallPrice;
    String sendTime;
    private String UserId;
    private double pricetotal;
    private double price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgs = getIntent().getStringArrayListExtra("imgs");
        sizes = getIntent().getStringArrayExtra("sizes");
        colors = getIntent().getStringArrayExtra("colors");
//        total = getIntent().getStringExtra("total");//总库存
        total = getIntent().getIntExtra("total", 0);
        money = getIntent().getStringExtra("money");
        goods_id = getIntent().getStringExtra("goods_id");
        goodsName = getIntent().getStringExtra("goodsName");
        storeName = getIntent().getStringExtra("storeName");
        storeLogo = getIntent().getStringExtra("storeLogo");
        bussiness_id = getIntent().getStringExtra("bussiness_id");
        mallPrice = getIntent().getStringExtra("mallPrice");
        sendTime = getIntent().getStringExtra("sendTime");
        UserId = (String) SharedUtil.getParam(this, "userid", "");
        setContentView(R.layout.activity_popupwindow);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        image_reduce.setOnClickListener(this);
        image_add.setOnClickListener(this);
        btn_addCart.setOnClickListener(this);
        btn_runShop.setOnClickListener(this);
        layout_pop_dissmiss.setOnClickListener(this);
        pop_dissmiss.setOnClickListener(this);
        layout_pop_dissmiss.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_bottom_in));
//        overridePendingTransition(R.anim.alphain, R.anim.alphaout); //跳转动画
        tv_money.setText("￥" + money);
        tv_Stock.setText("库存" + total + "件");
        Glide.with(this).load(imgs.get(0)).centerCrop().placeholder(R.mipmap.default_image_goods).into(image);
    }

    /**
     * 获取所有Color--Size数据
     */
    private void initData() {
        mColorList = new ArrayList<DColorBean>();
        mSizeList = new ArrayList<DSizeBean>();

        for (int i = 0; i < colors.length; i++) {
            DColorBean color = new DColorBean();
            color.setColor(colors[i]);
            color.setStates("1");
            mColorList.add(color);
        }
        for (int i = 0; i < sizes.length; i++) {
            DSizeBean size = new DSizeBean();
            size.setSize(sizes[i]);
            size.setStates("1");
            mSizeList.add(size);
        }

        skuColorAdapter = new PopColorAdapter(mColorList, this);
        gvColor.setAdapter(skuColorAdapter);
        skuColorAdapter.setItemClickListener(new PopColorAdapter.onItemClickListener() {

            @Override
            public void onItemClick(DColorBean dColorBean, int position) {
                // TODO Auto-generated method stub
                color = dColorBean.getColor();
                switch (dColorBean.getStates()) {
                    case "0":
                        //清空颜色
                        color = "";
                        mColorList = SizeOrColorManager.clearColorAdapterStates(mColorList);
                        //清空尺码
//                        size = "";
//                        mSizeList = SizeOrColorManager.clearSizeAdapterStates(mSizeList);
                        skuColorAdapter.notifyDataSetChanged();
                        skuSizeAdapter.notifyDataSetChanged();
                        if (!TextUtils.isEmpty(color)) {
                            tv_color_size.setText("请选择尺码");
                            skuColorAdapter.notifyDataSetChanged();
                            skuSizeAdapter.notifyDataSetChanged();
                            SizeOrColorManager.setColorAdapterStates(mColorList, color);
                        } else {
                            if (!TextUtils.isEmpty(size)) {
                                tv_color_size.setText("请选择颜色");
                                skuColorAdapter.notifyDataSetChanged();
                                skuSizeAdapter.notifyDataSetChanged();
                            } else {
                                tv_color_size.setText("请选择尺码，颜色");
                                tv_money.setText("￥" + money);
                                tv_Stock.setText("库存" + total + "件");
                                skuColorAdapter.notifyDataSetChanged();
                                skuSizeAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case "1":
                        // 选中颜色
                        mColorList = SizeOrColorManager.updateColorAdapterStates(mColorList, "0", position);
                        if (!TextUtils.isEmpty(size)) {
                            initchoicedata(color, size);
                            tv_color_size.setText("尺码：" + size + "，颜色：" + color);
                            skuColorAdapter.notifyDataSetChanged();
                            skuSizeAdapter.notifyDataSetChanged();
                        } else {
                            tv_color_size.setText("请选择尺码");
                            skuColorAdapter.notifyDataSetChanged();
                            skuSizeAdapter.notifyDataSetChanged();
                        }
                        break;
                }
            }
        });

        skuSizeAdapter = new PopSizeAdapter(mSizeList, this);
        gvSize.setAdapter(skuSizeAdapter);
        skuSizeAdapter.setItemClickListener(new PopSizeAdapter.onItemClickListener() {

            @Override
            public void onItemClick(DSizeBean dSizeBean, int position) {
                // TODO Auto-generated method stub
                size = dSizeBean.getSize();
                switch (dSizeBean.getStates()) {
                    case "0":
                        //清空尺码
                        size = "";
                        mSizeList = SizeOrColorManager.clearSizeAdapterStates(mSizeList);
                        skuColorAdapter.notifyDataSetChanged();
                        skuSizeAdapter.notifyDataSetChanged();
                        if (!TextUtils.isEmpty(size)) {
                            tv_color_size.setText("请选择颜色");
                            skuColorAdapter.notifyDataSetChanged();
                            skuSizeAdapter.notifyDataSetChanged();
                            SizeOrColorManager.setSizeAdapterStates(mSizeList, size);
                        } else {
                            if (!TextUtils.isEmpty(color)) {
                                tv_color_size.setText("请选择尺码");
                                skuColorAdapter.notifyDataSetChanged();
                                skuSizeAdapter.notifyDataSetChanged();
                            } else {
                                tv_color_size.setText("请选择尺码，颜色");
                                tv_money.setText("￥" + money);
                                tv_Stock.setText("库存" + total + "件");
                                skuColorAdapter.notifyDataSetChanged();
                                skuSizeAdapter.notifyDataSetChanged();
                            }
                        }
                        break;
                    case "1":
                        //选中尺码
                        mSizeList = SizeOrColorManager.updateSizeAdapterStates(mSizeList, "0", position);
                        if (!TextUtils.isEmpty(color)) {
                            tv_color_size.setText("尺码：" + size + "，颜色：" + color);
                            initchoicedata(color, size);
                            skuColorAdapter.notifyDataSetChanged();
                            skuSizeAdapter.notifyDataSetChanged();
                        } else {
                            skuColorAdapter.notifyDataSetChanged();
                            skuSizeAdapter.notifyDataSetChanged();
                            tv_color_size.setText("请选择颜色");
                        }
                        break;
                }
            }
        });

    }

    int a = 1;//购买初始化值

    @Override
    public void onClick(View v) {

        if (v.getId() == layout_pop_dissmiss.getId()) {
            finish();
            return;
        } else if (v.getId() == pop_dissmiss.getId()) {
            finish();
            return;
        }
        if (TextUtils.isEmpty(size) || TextUtils.isEmpty(color)) {
            Toast.makeText(PopupWindowActivity.this, "请选择尺码和颜色", Toast.LENGTH_SHORT).show();
            return;
        }
        if (stock == 0) {
            Toast.makeText(PopupWindowActivity.this, "商品已没有库存了!", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (v.getId()) {
            case R.id.image_reduce://减
                if (a > 1) {
                    a -= 1;
                    tv_total.setText("" + a);
                }
                break;
            case R.id.image_add: //加
                int num = Integer.valueOf(total).intValue();
                if (a < num) {
                    a = a + 1;
                    tv_total.setText("" + a);
                } else if (a >= num) {
                    tv_total.setText("" + total);
                }
                break;
            case R.id.btn_addCart:
                if (TextUtils.isEmpty(size)) {
                    Toast.makeText(PopupWindowActivity.this, "请选择尺码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(color)) {
                    Toast.makeText(PopupWindowActivity.this, "请选择颜色", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(isLogin())) {//未登录,登录
                    startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                }
                //加入购物车
                initAddScart(size, color, tv_total.getText().toString().trim(), isLogin());
                break;
            case R.id.btn_runShop:
                //立即购买
                if (TextUtils.isEmpty(size)) {
                    Toast.makeText(PopupWindowActivity.this, "请选择尺码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(color)) {
                    Toast.makeText(PopupWindowActivity.this, "请选择颜色", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(isLogin())) {//未登录,登录
                    startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                }
//                Intent intent = new Intent(PopupWindowActivity.this, Ensure_Order.class);
//                intent.putExtra("userId", isLogin());
//                intent.putExtra("is_addr", "1");
//                intent.putExtra("price", money);
//                intent.putExtra("size", size);
//                intent.putExtra("color", color);
//                intent.putExtra("num", tv_total.getText().toString().trim());
//                startActivity(intent);
//                finish();


                Map<String, Object> map = new HashMap<>();
                //店铺信息
                JsonObject store = new JsonObject();
                JsonArray goodsArray = new JsonArray();
                store.addProperty("bussiness_id", bussiness_id);
                store.addProperty("logo", storeLogo);
                store.addProperty("storeName", storeName);
                //商品信息
                JsonObject JsonObject = new JsonObject();
                JsonObject.addProperty("color", color);
                JsonObject.addProperty("goodsName", goodsName);
                JsonObject.addProperty("goods_id", goods_id);
                JsonObject.addProperty("img", imgs.get(0));
                JsonObject.addProperty("num", tv_total.getText().toString().trim());
                JsonObject.addProperty("mail", mallPrice);
                JsonObject.addProperty("scartId", "");
                JsonObject.addProperty("price", money);
                JsonObject.addProperty("size", size);
                JsonObject.addProperty("sendTime", sendTime);
                goodsArray.add(JsonObject);
                store.add("goods", goodsArray);
                map.put(bussiness_id, store);

                String goodsInfos=map.values().toString();
                Log.d("Popup订单goodsInfos:",goodsInfos);
                startActivity(new Intent(PopupWindowActivity.this, OrdersActivity.class).putExtra("userId", isLogin()).putExtra("goodsInfos", goodsInfos));
                finish();
                break;
        }
    }


    /**
     * 获取库存，或不同的价格
     *
     * @param color
     * @param size
     */
    private void initchoicedata(String color, String size) {
        final ProgressDialog mDialog = ProgressDialog.show(PopupWindowActivity.this, "", "获取库存中...");
        OkGo.post(CHOICE_GOODS)
                .params("goodsId", goods_id)
                .params("size", size)
                .params("color", color)
                .tag(this)
                .execute(new StringCallback() {
                             @Override
                             public void onSuccess(String s, Call call, Response response) {
                                 Log.d("code----------", s);
                                 mDialog.dismiss();
                                 try {
                                     JSONObject jsobject = new JSONObject(s);
                                     if (s.indexOf("error") != -1) {//有错误
                                         tv_Stock.setText(jsobject.getString("error"));
                                         return;
                                     }
                                     stock = jsobject.getInt("total");
                                     tv_Stock.setText("库存" + jsobject.getString("total") + "件");
                                     SharedUtil.setParam(PopupWindowActivity.this, "stock", jsobject.getString("total"));
                                     boolean is_price = jsobject.isNull("priceModeNew");
                                     if (!is_price) {
                                         tv_money.setText(jsobject.getString("priceModeNew"));
                                     }
                                     boolean Is_img = jsobject.isNull("img");
                                     if (!Is_img) {
                                         Glide.with(PopupWindowActivity.this).load(jsobject.getString("img"))
                                                 .centerCrop().placeholder(R.mipmap.default_image_goods).into(image);
                                     }
                                 } catch (JSONException e) {
                                     e.printStackTrace();
                                 }
                             }

                             @Override
                             public void onError(Call call, Response response, Exception e) {
                                 super.onError(call, response, e);
                                 mDialog.dismiss();
                                 Toast.makeText(PopupWindowActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                             }
                         }

                );
    }


    /**
     * 加入购物车
     *
     * @param size
     * @param color
     * @param total
     */
    private void initAddScart(String size, String color, String total, String userid) {
        final ProgressDialog mDialog = ProgressDialog.show(PopupWindowActivity.this, "添加到购物车", "正在提交...");
        OkGo.post(ADD_CARD_GOODS)
                .params("userId", userid)
                .params("goodsId", goods_id)
                .params("size", size)
                .params("color", color)
                .params("num", total)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code----------", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobject = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                tv_Stock.setText(jsobject.getString("error"));
                                Toast.makeText(PopupWindowActivity.this, jsobject.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(PopupWindowActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setAction("status");        //设置Action
                            intent.putExtra("msg", "添加购物车成功！");    //添加附加信息
                            sendBroadcast(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(PopupWindowActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


}
