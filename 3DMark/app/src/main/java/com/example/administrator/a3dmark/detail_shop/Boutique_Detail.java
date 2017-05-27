package com.example.administrator.a3dmark.detail_shop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.Activity.BaseActivity;
import com.example.administrator.a3dmark.Activity.LoginActivity;
import com.example.administrator.a3dmark.Activity.PopupWindowActivity;
import com.example.administrator.a3dmark.Activity.Recommended_Shop_Activity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.MyBoutiqueAdapter;
import com.example.administrator.a3dmark.adapter.MyServiceAdapter;
import com.example.administrator.a3dmark.bean.Boutique_Goods;
import com.example.administrator.a3dmark.bean.RelevanceGoods;
import com.example.administrator.a3dmark.bean.Service_bean;
import com.example.administrator.a3dmark.easemob.ui.ChatActivity;
import com.example.administrator.a3dmark.fragment.Graphic_details;
import com.example.administrator.a3dmark.fragment.Parameters_fg;
import com.example.administrator.a3dmark.fragment.Recommended_shop;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.MyScrollView;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.example.administrator.a3dmark.util.SnapUpCountDownTimerView;
import com.liji.circleimageview.CircleImageView;
import com.loopj.android.image.SmartImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

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
 * 精品详情页
 * Created by Administrator on 2017/3/3.
 */

public class Boutique_Detail extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.image_boutique_head)
    CircleImageView imageBoutiqueHead;
    @BindView(R.id.tv_boutique_detail)
    TextView tvBoutiqueDetail;
    @BindView(R.id.tv_boutique_money)
    TextView tvBoutiqueMoney;
    @BindView(R.id.tv_boutique_style)
    TextView tvBoutiqueStyle;
    @BindView(R.id.tv_boutique_try)
    TextView tvBoutiqueTry;
    @BindView(R.id.tv_boutique_origle)
    TextView tvBoutiqueOrigle;
    @BindView(R.id.tv_boutique_express)
    TextView tvBoutiqueExpress;
    @BindView(R.id.tv_boutique_sale)
    TextView tvBoutiqueSale;
    @BindView(R.id.tv_boutique_addr)
    TextView tvBoutiqueAddr;
    @BindView(R.id.tv_boutique_goods)
    TextView tvBoutiqueGoods;
    @BindView(R.id.tv_boutique_choice_color)
    TextView tvBoutiqueChoiceColor;
    @BindView(R.id.tv_boutique_evaluate)
    TextView tvBoutiqueEvaluate;
    @BindView(R.id.id_boutique_evaluate_fl)
    TagFlowLayout idBoutiqueEvaluateFl;
    //    @BindView(R.id.lv_boutique_evaluate)
//    ListView lvBoutiqueEvaluate;
    @BindView(R.id.tv_boutique_shopname)
    TextView tvBoutiqueShopname;
    @BindView(R.id.image_boutique_heart1)
    ImageView imageBoutiqueHeart1;
    @BindView(R.id.image_boutique_heart2)
    ImageView imageBoutiqueHeart2;
    @BindView(R.id.image_boutique_heart3)
    ImageView imageBoutiqueHeart3;
    @BindView(R.id.tv_boutique_intoshop)
    TextView tvBoutiqueIntoshop;
    @BindView(R.id.tv_boutique_all_baby)
    TextView tvBoutiqueAllBaby;
    @BindView(R.id.tv_boutique_new_baby)
    TextView tvBoutiqueNewBaby;
    @BindView(R.id.tv_boutique_num_concern)
    TextView tvBoutiqueNumConcern;
    //    @BindView(R.id.ptlm)
//    PullUpToLoadMore ptlm;
//    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.ll_boutique_graphic_details)
    LinearLayout llBoutiqueGraphicDetails;
    @BindView(R.id.ll_boutique_parameters)
    LinearLayout llBoutiqueParameters;
    @BindView(R.id.ll_boutique_shop)
    LinearLayout llBoutiqueShop;
    @BindView(R.id.view_graphic_details)
    View viewGraphicDetails;
    @BindView(R.id.view_parameters)
    View viewParameters;
    @BindView(R.id.view_shop)
    View viewShop;
    @BindView(R.id.tv_graphic_details)
    TextView tvGraphicDetails;
    @BindView(R.id.tv_parameters)
    TextView tvParameters;
    @BindView(R.id.tv_shop)
    TextView tvShop;
    @BindView(R.id.fl_boutique)
    FrameLayout flBoutique;
    @BindView(R.id.tv_boutique_service)
    TextView tvBoutiqueService;
    @BindView(R.id.tv_boutique_shop)
    TextView tvBoutiqueShop;
    @BindView(R.id.tv_boutique_collection)
    TextView tvBoutiqueCollection;
    @BindView(R.id.tv_boutique_addshop)
    TextView tvBoutiqueAddshop;
    @BindView(R.id.tv_boutique_current)
    TextView tvBoutiqueCurrent;
    @BindView(R.id.image_boutique_heart4)
    ImageView imageBoutiqueHeart4;
    @BindView(R.id.image_boutique_heart5)
    ImageView imageBoutiqueHeart5;
    @BindView(R.id.image_boutique_services)
    ImageView imageBoutiqueServices;
    @BindView(R.id.image_boutique_services_more)
    ImageView imageBoutiqueServicesMore;
    @BindView(R.id.ll_boutique_services)
    LinearLayout llBoutiqueServices;
    @BindView(R.id.image_boutique_services2)
    ImageView imageBoutiqueServices2;
    @BindView(R.id.tv_boutique_goods2)
    TextView tvBoutiqueGoods2;
    @BindView(R.id.reLayout)
    RelativeLayout reLayout;
    @BindView(R.id.text_time)
    SnapUpCountDownTimerView text_time;
    @BindView(R.id.tv_boutuque_evaluation_head)
    SmartImageView tvBoutuqueEvaluationHead;
    @BindView(R.id.tv_boutuque_evaluation_name)
    TextView tvBoutuqueEvaluationName;
    @BindView(R.id.tv_boutuque_evaluation_time)
    TextView tvBoutuqueEvaluationTime;
    @BindView(R.id.tv_boutuque_evaluation_color)
    TextView tvBoutuqueEvaluationColor;
    @BindView(R.id.tv_boutuque_evaluation_size)
    TextView tvBoutuqueEvaluationSize;
    @BindView(R.id.tv_boutuque_evaluation_main)
    TextView tvBoutuqueEvaluationMain;
    @BindView(R.id.image_boutuque_evaluation_1)
    ImageView imageBoutuqueEvaluation1;
    @BindView(R.id.image_boutuque_evaluation_2)
    ImageView imageBoutuqueEvaluation2;
    @BindView(R.id.image_boutuque_evaluation_3)
    ImageView imageBoutuqueEvaluation3;
    @BindView(R.id.ll_evaluation_images)
    LinearLayout llEvaluationImages;
    @BindView(R.id.tv_boutique_all_evaluate)
    TextView tvBoutiqueAllEvaluate;
    @BindView(R.id.ll_boutique_evaluation)
    LinearLayout llBoutiqueEvaluation;
    @BindView(R.id.image_boutique_shop)
    CircleImageView imageBoutiqueShop;
    @BindView(R.id.vp_boutique)
    ViewPager vpBoutique;
    @BindView(R.id.myScrollView)
    MyScrollView myScrollView;
    @BindView(R.id.myScrollView2)
    MyScrollView myScrollView2;

    private List<String> mVals = new ArrayList<String>();
    private TagFlowLayout fl_boutique_evaluate_size;
    private TagFlowLayout fl_boutique_evaluate_color;
    private TagFlowLayout mFlowLayout;
    FragmentManager fm;
    ArrayList<RelevanceGoods> relevanceGoodsList = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<String>();
    String[] sizes = new String[]{};
    String[] colors = new String[]{};
    private int total;//库存
    private String money;
    private boolean btn_status;

    private Intent intent;
    private String goods_id;
    private String goodsName;
    private String id;
    private String bussiness_id;
    private String storeLogo;
    private String storeName;
    private String mallPrice;
    private String sendTime;
    private String customerService;
    TextView[] Tags;

    private ImageView image_pop_boutique_plus;
    private ImageView image_pop_boutique_reduce;
    //图文详情/图片
    private String detail_image;
    private String service;
    private ArrayList<Service_bean> list = new ArrayList<>();
    private String sizeImgs;
    private RelevanceGoods goods;
    //商品类型
    private String goodsStatus;
    private boolean startActivitystatus = false;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boutique_activity);
        ButterKnife.bind(this);
        intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        initData();

        myScrollView.setScrollListener(new MyScrollView.ScrollListener() {
            @Override
            public void onScrollToBottom() {
                initFragment(detail_image);
                myScrollView.setVisibility(View.GONE);
                myScrollView2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onScrollToTop() {

            }

            @Override
            public void onScroll(int scrollY) {

            }

            @Override
            public void notBottom() {

            }

        });

        myScrollView2.setScrollListener(new MyScrollView.ScrollListener() {
            @Override
            public void onScrollToBottom() {

            }

            @Override
            public void onScrollToTop() {
                myScrollView.setVisibility(View.VISIBLE);
                myScrollView2.setVisibility(View.GONE);
            }

            @Override
            public void onScroll(int scrollY) {

            }

            @Override
            public void notBottom() {

            }


        });


    }


    @Override
    protected void onRestart() {//保证数据不为空
        super.onRestart();
        initData();
    }

    private void initFragment(String detail_image) {
        //该变颜色
        viewGraphicDetails.setVisibility(View.VISIBLE);
        viewParameters.setVisibility(View.INVISIBLE);
        viewShop.setVisibility(View.INVISIBLE);
        tvGraphicDetails.setTextColor(getResources().getColor(R.color.Pink));
        tvParameters.setTextColor(getResources().getColor(R.color.Black));
        tvShop.setTextColor(getResources().getColor(R.color.Black));
        //初始化Fragment
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment detailfg = Graphic_details.newInstance(detail_image);
        ft.replace(R.id.fl_boutique, detailfg);
        tvBoutiqueOrigle.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tvTitleText.setText("精品宝贝");
        imageTitleBack.setVisibility(View.VISIBLE);
        tvTitleVice.setText("");
        ft.commit();
    }


    private void initFlView() {
        mInflater = LayoutInflater.from(Boutique_Detail.this);
        mFlowLayout = (TagFlowLayout) findViewById(R.id.id_boutique_evaluate_fl);
        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {

                TextView tv = (TextView) mInflater.inflate(R.layout.tv, mFlowLayout, false);
                tv.setText(s);
                return tv;
            }

            public boolean setSelected(int position, String s) {
                return s.equals("Android");
            }
        });
    }

    String key;

    private void initData() {
        final ProgressDialog mDialog = ProgressDialog.show(Boutique_Detail.this, "获取数据", "获取数据中");
        OkGo.get(Contants.GOODS)
                .params("goodsId", goods_id)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code----------", s);
                        mDialog.dismiss();
                        try {
//                            mVals.add("测试1");
                            if (s.indexOf("error") != -1) {
                                Toast.makeText(Boutique_Detail.this, new JSONObject(s).getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONObject jsobject = new JSONObject(s);
                            JSONObject obj = jsobject.getJSONObject("success");

                            //店铺客服
//                            customerService = obj.getJSONObject("bussiness").getString("customerService");

                              /*商品图片*/
                            Glide.with(Boutique_Detail.this).load(obj.getString("pictureIsCover")).fitCenter()
                                    .placeholder(R.drawable.icon_empty).into(imageBoutiqueHead);
                             /*店铺头像*/
                            storeLogo = obj.getJSONObject("bussiness").getString("logo");
                            Glide.with(Boutique_Detail.this).
                                    load(obj.getJSONObject("bussiness").getString("logo")).centerCrop().
                                    placeholder(R.mipmap.touxiang_default).into(imageBoutiqueShop);
                            //商品类型
                            goodsStatus = obj.getString("isFit");
                            if ("0".equals(goodsStatus)) {
                                tvBoutiqueTry.setVisibility(View.GONE);
                                tvBoutiqueChoiceColor.setText("请选择");
                            }
                            //倒计时
                            String timeStatus = obj.getString("status");

                            if ("1".equals(timeStatus)) {
                                btn_status = true;
                                reLayout.setVisibility(View.GONE);//("正常商品");
                            } else if ("2".equals(timeStatus)) {
                                String time = obj.getString("countdown");
                                btn_status = true;
                                //00:00:00
                                String[] times = time.split("\\:");
                                int HH = Integer.parseInt(times[0]);
                                int mm = Integer.parseInt(times[1]);
                                int ss = Integer.parseInt(times[2]);

                                text_time.setTime(HH, mm, ss);//倒计时时间
                                text_time.start();//开始倒计时
                            } else if ("3".equals(timeStatus)) {
                                btn_status = false;
                                tvBoutiqueCurrent.setText("活动未开始");
                                reLayout.setVisibility(View.VISIBLE);//("活动未开始");
                            }
                            /*商户Id*/
                            SharedUtil.setParam(Boutique_Detail.this, "business_Id", obj.getString("bussinessId"));
                            //加入购物车的尺寸
                            String size = obj.getString("sizes");
                            sizes = size.split(",");
                            //加入购物车的颜色
                            String color = obj.getString("colors");
                            colors = color.split(",");


                            tvBoutiqueAddr.setText(obj.getJSONObject("bussiness").getString("address"));

                            id = obj.getJSONObject("bussiness").getString("id");
                            bussiness_id = obj.getString("bussinessId");
                            Log.d("bussiness_id", obj.getString("bussinessId"));
                            /*宝贝轮播图*/
                            JSONArray jsonArray = obj.getJSONArray("headImgs");
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsObj = jsonArray.getJSONObject(j);
                                String str = jsObj.getString("img");
                                imgs.add(str);
                            }
                            /*图文详情图片解析*/
                            boolean IS_images = obj.isNull("detailImgs");
                            if (!IS_images) {
                                detail_image = obj.getString("detailImgs");
                                //SharedUtil.setParam(Boutique_Detail.this, "detailImgs", detail_image);
                            }
                            /*宝贝推荐*/
                            JSONArray array = obj.getJSONArray("relevanceGoods");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                goods = new RelevanceGoods();
                                goods.setCount(jsonObject.getString("count"));
                                goods.setGoodsId(jsonObject.getString("goodsId"));
                                goods.setGoodsName(jsonObject.getString("goodsName"));
                                goods.setImg(jsonObject.getString("img"));
                                goods.setPriceModeNew(jsonObject.getString("priceModeNew"));
                                relevanceGoodsList.add(goods);
                            }
                            MyBoutiqueAdapter myBoutiqueAdapter = new MyBoutiqueAdapter(imgs, Boutique_Detail.this);
                            vpBoutique.setAdapter(myBoutiqueAdapter);
                            /*产品参数*/
                            boolean is_sizeImgs = obj.isNull("sizeImgs");
                            if (!is_sizeImgs) {
                                sizeImgs = obj.getString("sizeImgs");
                            }

                            /*发货时间*/
                            sendTime = obj.getString("sendTime");
                            /*宝贝名*/
                            goodsName = obj.getString("goodsName");
                            tvBoutiqueDetail.setText(obj.getString("goodsName"));
                            /*宝贝现价*/
                            tvBoutiqueMoney.setText("￥" + obj.getString("priceModeNew"));
                            /*标签衣服款式*/
                            tvBoutiqueStyle.setText(obj.getString("tags"));
                            /*原件*/
                            tvBoutiqueOrigle.setText(obj.getString("priceMode"));
                            /*月销多少件*/
                            tvBoutiqueSale.setText("月销" + obj.getString("monthlySales") + "笔");
                            /*快递*/
                            tvBoutiqueExpress.setText(obj.getString("mallPrice"));
                            //店铺名
                            storeName = obj.getJSONObject("bussiness").getString("name");
                            tvBoutiqueShopname.setText(obj.getJSONObject("bussiness").getString("name"));
                            //全部商品
                            tvBoutiqueAllBaby.setText(obj.getJSONObject("bussiness").getString("goodsCount"));
                            //关注人数
                            tvBoutiqueNumConcern.setText(obj.getJSONObject("bussiness").getString("collectCount"));
                            //地点
                            tvBoutiqueAddr.setText(obj.getJSONObject("bussiness").getString("address"));
                            //标签是否包邮
//                            tvBoutiqueGoods.setText(obj.getString("transport"));
                            //邮费
                            mallPrice = obj.getString("mallPrice");
                            tvBoutiqueExpress.setText(obj.getString("mallPrice"));
                            
                            /*服务：7天包邮...*/
                            service = obj.getString("services");
                            String[] servieces = service.split(",");
                            if (service.isEmpty()) {
                                llBoutiqueServices.setVisibility(View.GONE);
                            }
                            if (servieces.length < 2) {
                                tvBoutiqueGoods.setText(servieces[0]);
                                tvBoutiqueGoods2.setVisibility(View.GONE);
                                imageBoutiqueServices2.setVisibility(View.GONE);
                            }
                            if (servieces.length >= 2) {
                                tvBoutiqueGoods2.setText(servieces[1]);
                            }

                            //库存
                            total = obj.getInt("total");
                            //价格
                            money = obj.getString("priceModeNew");
                            //评论的星级
                            int star = obj.getJSONObject("bussiness").getInt("level");

                            if (star == 1) {
                                imageBoutiqueHeart1.setVisibility(View.VISIBLE);
                            } else if (star == 2) {
                                imageBoutiqueHeart1.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart2.setVisibility(View.VISIBLE);
                            } else if (star == 3) {
                                imageBoutiqueHeart1.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart2.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart3.setVisibility(View.VISIBLE);
                            } else if (star == 4) {
                                imageBoutiqueHeart1.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart2.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart3.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart4.setVisibility(View.VISIBLE);
                            } else if (star == 5) {
                                imageBoutiqueHeart1.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart2.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart3.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart4.setVisibility(View.VISIBLE);
                                imageBoutiqueHeart5.setVisibility(View.VISIBLE);
                            }

                            //评价
                            boolean IS_EVA = obj.isNull("firstEvalute");
                            if (!IS_EVA) {
                                JSONObject jsonObject = obj.getJSONObject("firstEvalute");
                                if (jsonObject.has("time")) {
                                    llBoutiqueEvaluation.setVisibility(View.VISIBLE);
                                    Glide.with(Boutique_Detail.this).
                                            load(jsonObject.getString("photo")).centerCrop().
                                            placeholder(R.mipmap.touxiang_default).into(tvBoutuqueEvaluationHead);
                                    tvBoutuqueEvaluationColor.setText("颜色：" + jsonObject.getString("color"));
                                    tvBoutuqueEvaluationTime.setText(jsonObject.getString("time"));
                                    tvBoutuqueEvaluationSize.setText("尺寸：" + jsonObject.getString("size"));
                                    tvBoutuqueEvaluationName.setText(jsonObject.getString("nickname"));
                                    tvBoutuqueEvaluationMain.setText(jsonObject.getString("content"));
                                    String images = jsonObject.getString("picture");
                                    String images_eva[] = images.split(",");
                                    if (images_eva.length == 1) {
                                        Glide.with(Boutique_Detail.this).
                                                load(images_eva[0]).centerCrop().
                                                placeholder(R.drawable.icon_empty).into(imageBoutuqueEvaluation1);
                                    } else if (images_eva.length == 2) {
                                        Glide.with(Boutique_Detail.this).
                                                load(images_eva[1]).centerCrop().
                                                placeholder(R.drawable.icon_empty).into(imageBoutuqueEvaluation2);
                                        Glide.with(Boutique_Detail.this).
                                                load(images_eva[0]).centerCrop().
                                                placeholder(R.drawable.icon_empty).into(imageBoutuqueEvaluation1);

                                    } else if (images_eva.length == 3) {
                                        Glide.with(Boutique_Detail.this).
                                                load(images_eva[1]).centerCrop().
                                                placeholder(R.drawable.icon_empty).into(imageBoutuqueEvaluation2);
                                        Glide.with(Boutique_Detail.this).
                                                load(images_eva[0]).centerCrop().
                                                placeholder(R.drawable.icon_empty).into(imageBoutuqueEvaluation1);
                                        Glide.with(Boutique_Detail.this).
                                                load(images_eva[2]).centerCrop().
                                                placeholder(R.drawable.icon_empty).into(imageBoutuqueEvaluation3);
                                    } else if (images_eva.length < 1) {
                                        llEvaluationImages.setVisibility(View.GONE);
                                    }
                                } else {
                                    llBoutiqueEvaluation.setVisibility(View.GONE);
                                }

                            }

                            JSONObject jsObj = obj.getJSONObject("evaluate");
                            if (jsObj.has("total")) {
                                tvBoutiqueEvaluate.setText("宝贝评价（" + jsObj.getString("total") + "）");
                                JSONArray jsArray = jsObj.getJSONArray("evaluations");
                                mVals.clear();
                                for (int j = 0; j < jsArray.length(); j++) {
                                    JSONObject jsonObject = jsArray.getJSONObject(j);
                                    mVals.add(jsonObject.getString("content") + "+（" + jsonObject.getString("num") + "）");
                                }
                                initFlView();
                            } else {
                                llBoutiqueEvaluation.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Boutique_Detail.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    Boutique_Goods goods_bean;
    LayoutInflater mInflater;


    @OnClick({R.id.image_title_back, R.id.tv_boutique_try,
            R.id.ll_boutique_graphic_details,
            R.id.ll_boutique_parameters, R.id.tv_boutique_all_evaluate,
            R.id.ll_boutique_shop, R.id.tv_boutique_choice_color, R.id.tv_boutique_service,
            R.id.tv_boutique_shop, R.id.tv_boutique_collection, R.id.tv_boutique_addshop, R.id.tv_boutique_current, R.id.tv_boutique_intoshop})
    public void onClick(View view) {
        //FragmentTransaction ft = fm.beginTransaction();
        //Intent intent;
        switch (view.getId()) {
            case R.id.image_title_back:
                finish();
                break;
            //马上试衣
            case R.id.tv_boutique_try:
                break;
            //查看全部评价
            case R.id.tv_boutique_all_evaluate:
                startActivity(new Intent(Boutique_Detail.this, Evaluation_Activity.class).putExtra("goods_id", goods_id).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            //图文详情
            case R.id.ll_boutique_graphic_details:
                viewGraphicDetails.setVisibility(View.VISIBLE);
                viewParameters.setVisibility(View.INVISIBLE);
                viewShop.setVisibility(View.INVISIBLE);
                tvGraphicDetails.setTextColor(getResources().getColor(R.color.Pink));
                tvParameters.setTextColor(getResources().getColor(R.color.Black));
                tvShop.setTextColor(getResources().getColor(R.color.Black));
//                Fragment detailfg = Graphic_details.newInstance(detail_image);
                Fragment detailfg = Graphic_details.newInstance(detail_image);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_boutique, detailfg)
                        .commit();
                break;
            //产品参数
            case R.id.ll_boutique_parameters:
                viewParameters.setVisibility(View.VISIBLE);
                viewGraphicDetails.setVisibility(View.INVISIBLE);
                viewShop.setVisibility(View.INVISIBLE);
                tvParameters.setTextColor(getResources().getColor(R.color.Pink));
                tvGraphicDetails.setTextColor(getResources().getColor(R.color.Black));
                tvShop.setTextColor(getResources().getColor(R.color.Black));
                Fragment parameters = Parameters_fg.newInstance(sizeImgs);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fl_boutique, parameters)
                        .commit();
                break;
            //店铺推荐
            case R.id.ll_boutique_shop:
//                viewShop.setVisibility(View.VISIBLE);
//                viewGraphicDetails.setVisibility(View.INVISIBLE);
//                viewParameters.setVisibility(View.INVISIBLE);
//                tvShop.setTextColor(getResources().getColor(R.color.Pink));
//                tvParameters.setTextColor(getResources().getColor(R.color.Black));
//                tvGraphicDetails.setTextColor(getResources().getColor(R.color.Black));
                Intent intent = new Intent(this, Recommended_Shop_Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle bundle = new Bundle();
                bundle.putSerializable("GoodsList", relevanceGoodsList);
                intent.putExtras(bundle);
                startActivity(intent);
//                Recommended_shop shop = Recommended_shop.newInstance(relevanceGoodsList);
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fl_boutique, shop)
//                        .commit();
                break;
            //选择颜色，尺码，其他
            case R.id.tv_boutique_choice_color:
                startActivity(new Intent(this, PopupWindowActivity.class)
                        .putStringArrayListExtra("imgs", imgs).putExtra("sizes", sizes)
                        .putExtra("colors", colors).putExtra("total", total).putExtra("money", money)
                        .putExtra("goods_id", goods_id).putExtra("bussiness_id", bussiness_id)
                        .putExtra("goodsName", goodsName).putExtra("mallPrice", mallPrice).putExtra("sendTime", sendTime)
                        .putExtra("storeName", storeName).putExtra("storeLogo", storeLogo).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            //客服
            case R.id.tv_boutique_service:
                if (TextUtils.isEmpty(isLogin())) {//未登录，去登陆
                    startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                }
                intent = new Intent(this, ChatActivity.class);
                intent.putExtra("userId", customerService);
                startActivity(intent);
                break;
            //进店逛逛
            case R.id.tv_boutique_intoshop:
                intent = new Intent(Boutique_Detail.this, Shop_Activity.class);
                intent.putExtra("bussiness_id", bussiness_id);
                startActivity(intent);
                break;
            //店铺
            case R.id.tv_boutique_shop:
                intent = new Intent(Boutique_Detail.this, Shop_Activity.class);
                intent.putExtra("bussiness_id", bussiness_id);
                startActivity(intent);
                break;
            //收藏
            case R.id.tv_boutique_collection:
                if (TextUtils.isEmpty(isLogin())) {
                    startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                }
                initcollectionData();
                break;
            //加入购物车
            case R.id.tv_boutique_addshop:
                if (TextUtils.isEmpty(isLogin())) {//未登录，去登陆
                    startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                }
                //选择 颜色尺寸等等/去PopupWindowActivity获取值
                startActivity(new Intent(this, PopupWindowActivity.class)
                        .putStringArrayListExtra("imgs", imgs).putExtra("sizes", sizes)
                        .putExtra("colors", colors).putExtra("total", total).putExtra("money", money)
                        .putExtra("goods_id", goods_id).putExtra("bussiness_id", bussiness_id)
                        .putExtra("goodsName", goodsName).putExtra("mallPrice", mallPrice).putExtra("sendTime", sendTime)
                        .putExtra("storeName", storeName).putExtra("storeLogo", storeLogo).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
            //立即购物
            case R.id.tv_boutique_current:
                if (TextUtils.isEmpty(isLogin())) {//未登录，去登陆
                    startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    return;
                } else if (btn_status != true) {
                    Toast.makeText(this, "暂不可购买!", Toast.LENGTH_SHORT).show();
                }
                //选择 颜色尺寸等等/去PopupWindowActivity获取值
                startActivity(new Intent(this, PopupWindowActivity.class)
                        .putStringArrayListExtra("imgs", imgs).putExtra("sizes", sizes)
                        .putExtra("colors", colors).putExtra("total", total).putExtra("money", money)
                        .putExtra("goods_id", goods_id).putExtra("bussiness_id", bussiness_id)
                        .putExtra("goodsName", goodsName).putExtra("mallPrice", mallPrice).putExtra("sendTime", sendTime)
                        .putExtra("storeName", storeName).putExtra("storeLogo", storeLogo).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                break;
        }
    }

    private void initcollectionData() {
        userid = SharedUtil.getParam(this, "userid", "").toString();
        OkGo.post(Contants.COLLECTION_GOODS_BOUTIQUE)
                .params("goodsId", goods_id)
                .params("userId", userid)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code----------", s);
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                if ("-1".equals(jsobj.getString("error"))) {
                                    Toast.makeText(Boutique_Detail.this, "收藏失败!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(Boutique_Detail.this, jsobj.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(Boutique_Detail.this, "收藏成功", Toast.LENGTH_SHORT).show();
                            tvBoutiqueCollection.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.collection), null, null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        //  Toast.makeText(Boutique_Detail.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @OnClick(R.id.ll_boutique_services)
    public void onClick() {
        initservicedata();
//        initpopview();
    }

    private void initservicedata() {
        OkGo.post(Contants.GOODS_SERVICE)
                .params("goodsId", goods_id)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code----------", s);

                        try {
                            JSONObject js = new JSONObject(s);
                            JSONArray jsarray = js.getJSONArray("success");
                            list.clear();
                            for (int i = 0; i < jsarray.length(); i++) {
                                JSONObject obj = jsarray.getJSONObject(i);
                                Service_bean service_bean = new Service_bean();
                                service_bean.setTitle(obj.getString("title"));
                                boolean is_detail = obj.isNull("content");
                                if (!is_detail) {
                                    service_bean.setDetail(obj.getString("content"));
                                }
                                list.add(service_bean);
                            }
                            initpopview();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        //   Toast.makeText(Boutique_Detail.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initpopview() {
        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(this).inflate(
                R.layout.service_list, null);
        ListView servicelist = (ListView) contentView.findViewById(R.id.lv_service);
        TextView tv_service_finish = (TextView) contentView.findViewById(R.id.tv_service_finish);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
//设置点击popupwindow的点击外部消失
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.ll_service);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupWindow != null) {
                    setBackgroundAlpha(Boutique_Detail.this, 1f);
                }
            }
        });
        // 设置好参数之后再show

        int height = layout.getHeight();
        int width = layout.getWidth();
//        popupWindow.showAsDropDown(contentView);
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, contentView.getWidth() - width, contentView.getHeight() - height);
        tv_service_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        servicelist.setAdapter(new MyServiceAdapter(Boutique_Detail.this, list));
    }

    //设置popupwindow的背景半透明；
    public void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

}
