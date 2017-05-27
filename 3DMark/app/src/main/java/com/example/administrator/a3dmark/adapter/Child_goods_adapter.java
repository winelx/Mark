package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Child_Goods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/17.
 */
public class Child_goods_adapter extends BaseAdapter {
    List<Child_Goods> childGoodses;
    Context context;

    public Child_goods_adapter(List<Child_Goods> childGoodses, Context context) {
        this.childGoodses = childGoodses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return childGoodses.size();
    }

    @Override
    public Object getItem(int position) {
        return childGoodses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Child_Goods_Holder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.child_goods_adapter, null);
            holder = new Child_Goods_Holder(convertView);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (Child_Goods_Holder) convertView.getTag();
        }
        Glide.with(context).load(childGoodses.get(position).getImg()).fitCenter()
                .placeholder(R.drawable.icon_error).into(holder.imageChildGoodsMark);
        holder.tvChildGoodsMoney.setText("￥" + childGoodses.get(position).getPriceModeNow());
        holder.tvChildGoodsName.setText(childGoodses.get(position).getName());
        return convertView;
    }


    static class Child_Goods_Holder {
        @BindView(R.id.image_child_goods_mark)
        ImageView imageChildGoodsMark;
        @BindView(R.id.tv_child_goods_money)
        TextView tvChildGoodsMoney;
        @BindView(R.id.tv_child_goods_name)
        TextView tvChildGoodsName;

        Child_Goods_Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
