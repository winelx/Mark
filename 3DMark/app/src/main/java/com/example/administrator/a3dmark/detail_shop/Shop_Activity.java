package com.example.administrator.a3dmark.detail_shop;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.BaseActivity;
import com.example.administrator.a3dmark.Activity.RecommActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.ShopAdapter;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.bean.ShopGoods;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.ItemDivider;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 店铺
 * Created by Administrator on 2017/3/8.
 */
public class Shop_Activity extends BaseActivity implements View.OnClickListener{

    @BindView(R.id.image_title_white_back)
    ImageView imageTitleWhiteBack;
    @BindView(R.id.tv_title_white)
    TextView tvTitleWhite;
    @BindView(R.id.tv_title_white_vice)
    TextView tvTitleWhiteVice;
    Intent intent;
    @BindView(R.id.tv_collection_title)
    TextView tvCollectionTitle;
    @BindView(R.id.tv_title_num)
    TextView tvTitleNum;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    private String bussiness_id;
    private String userid;

    //轮播图List<bean>对象
    private ArrayList<Banners> Flybanners = new ArrayList<>();
    //商品List<bean>对象
    private ArrayList<ShopGoods> goodses = new ArrayList<ShopGoods>();

    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);
        ButterKnife.bind(this);
        intent = getIntent();
        bussiness_id = intent.getStringExtra("bussiness_id");
        userid = (String) SharedUtil.getParam(this, "userid", "");
        llTitle.setVisibility(View.VISIBLE);

        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ItemDivider().setDividerWith(this,20));
        adapter = new ShopAdapter(Shop_Activity.this);
        initGradData();
        recyclerView.setAdapter(adapter);
        //item的点击事件
        adapter.setOnItemClickListener(new ShopAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                ShopGoods shopGoods = null;
                int position = recyclerView.getChildAdapterPosition(view);
                if (position <= goodses.size()) {
                    shopGoods = goodses.get(position-1);
                }
                startActivity(new Intent(Shop_Activity.this,Boutique_Detail.class)
                        .putExtra("goods_id",shopGoods.getId())
                        .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                );
            }
        });


        tvCollectionTitle.setOnClickListener(this);

        imageTitleWhiteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //店铺信息
    private void initGradData() {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.get(Contants.DTORE)
                .params("bussinessId", bussiness_id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code===initGradData", s);
                        mDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(Shop_Activity.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            JSONObject success = json.getJSONObject("success");
                            tvTitleWhite.setText(success.getString("shopName"));
                            String id = success.getString("bussinessId").substring((success.getString("bussinessId").length()-5)+1,bussiness_id.length());
                            tvTitleNum.setText("店铺号："+id);

                            JSONArray info = success.getJSONArray("goodsInfoArray");
                            for (int i = 0; i < info.length(); i++) {
                                JSONObject obj = info.getJSONObject(i);
                                ShopGoods shopgoods = new ShopGoods();
                                shopgoods.setGoodsname(obj.getString("goodsName"));
                                shopgoods.setId(obj.getString("goodsId"));
                                shopgoods.setImages(obj.getString("img"));
                                shopgoods.setPricenow(obj.getString("priceNow"));
                                goodses.add(shopgoods);
                            }
                            adapter.setGoods(goodses);
                            JSONArray showPage = success.getJSONArray("showPage");
                            for (int j = 0; j < showPage.length(); j++){
                                JSONObject page = showPage.getJSONObject(j);
                                Banners banners = new Banners();
                                //banners.setId(page.getString("id"));
                                banners.setImg(page.getString("img"));
                                Flybanners.add(banners);
                            }
                            adapter.setFlybanners(Flybanners);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Shop_Activity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == tvCollectionTitle.getId()){
            if (!"".equals(isLogin())) {
                collection();
            } else {

                Toast.makeText(Shop_Activity.this, "收藏失败!未登录", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /*收藏*/
    private void collection() {
        OkGo.post(Contants.COLLECTION_SHTORE)
                .connTimeOut(5000)
                .params("bussinessId", bussiness_id)
                .params("userId", isLogin())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject jsobj = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                if ("-1".equals(jsobj.getString("error"))){
                                    Toast.makeText(Shop_Activity.this, "收藏失败!", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Toast.makeText(Shop_Activity.this, jsobj.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(Shop_Activity.this, "收藏成功", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }
}
