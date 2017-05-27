package com.example.administrator.a3dmark.test;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Goods_order;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/12.
 */
public class OrderlistAdapter extends BaseAdapter {
    Context context;
    List<Goods_order> list_goods;

    public OrderlistAdapter(Context context, List<Goods_order> list) {
        this.list_goods = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_goods.size();
    }

    @Override
    public Object getItem(int position) {
        return list_goods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Goods_holder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_goods_myorder, null);
            holder = new Goods_holder(convertView);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (Goods_holder) convertView.getTag();
        }
        Glide.with(context).load(list_goods.get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(holder.imageOrderHead);
        holder.tvOrderNum.setText("X" + list_goods.get(position).getNum());
        holder.tvOrderOriginalPrice.setText(list_goods.get(position).getPriceMode());
        holder.tvOrderOriginalPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvOrderPresentPrice.setText(list_goods.get(position).getPriceModeNew());
        holder.tvProductDescription.setText(list_goods.get(position).getGoodsName());
        holder.tvProductSize.setText("规格: "+list_goods.get(position).getSize() + "    分类: " + list_goods.get(position).getColor());
        return convertView;
    }


    static class Goods_holder {
        @BindView(R.id.image_order_head)
        ImageView imageOrderHead;
        @BindView(R.id.tv_product_description)
        TextView tvProductDescription;
        @BindView(R.id.tv_product_size)
        TextView tvProductSize;
        @BindView(R.id.tv_order_present_price)
        TextView tvOrderPresentPrice;
        @BindView(R.id.tv_order_Original_price)
        TextView tvOrderOriginalPrice;
        @BindView(R.id.tv_order_num)
        TextView tvOrderNum;

        Goods_holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
