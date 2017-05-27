package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.RelevanceGoods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐店铺适配器
 * Created by Administrator on 2017/3/7.
 */
public class Recommended_shop_Adapter extends BaseAdapter {
    List<RelevanceGoods> list;
    Context context;

    public Recommended_shop_Adapter(List<RelevanceGoods> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Recommend_Holder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.recomment_adapter, null);
            holder = new Recommend_Holder(convertView);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (Recommend_Holder) convertView.getTag();
        }
        holder.tvShopGoodsname.setText(list.get(position).getGoodsName());
        holder.tvShopMoney.setText("￥" + list.get(position).getPriceModeNew());
        Glide.with(context).
                load(list.get(position).getImg()).fitCenter().
                placeholder(R.drawable.icon_empty).into(holder.imageShopGoods);

        return convertView;
    }

    class Recommend_Holder {
        @BindView(R.id.image_shop_goods)
        ImageView imageShopGoods;
        @BindView(R.id.tv_shop_money)
        TextView tvShopMoney;
        @BindView(R.id.tv_shop_goodsname)
        TextView tvShopGoodsname;
        @BindView(R.id.btn_shop_current)
        TextView btnShopCurrent;
        @BindView(R.id.cardView)
        CardView cardView;

        Recommend_Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
