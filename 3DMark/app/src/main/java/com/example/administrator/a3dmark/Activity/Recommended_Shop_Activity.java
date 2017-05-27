package com.example.administrator.a3dmark.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.MyGridView;
import com.example.administrator.a3dmark.adapter.Recommended_shop_Adapter;
import com.example.administrator.a3dmark.bean.RelevanceGoods;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐商品/商品详情
 * Created by Administrator on 2017/3/7.
 */

public class Recommended_Shop_Activity extends BaseActivity {


    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.gv_recomment_shop)
    GridView gvRecommentShop;
    Recommended_shop_Adapter adapter;
    ArrayList<RelevanceGoods> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        list = (ArrayList<RelevanceGoods>) getIntent().getSerializableExtra("GoodsList");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recomment_shop);
        ButterKnife.bind(this);
        tvTitleText.setText("推荐商品");


        Log.d("relevanceGoodsList",list.toString());

        adapter = new Recommended_shop_Adapter(list, this);
        gvRecommentShop.setAdapter(adapter);
        gvRecommentShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(Recommended_Shop_Activity.this, Boutique_Detail.class).putExtra("goods_id",list.get(position).getGoodsId()).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });

        imageTitleBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
