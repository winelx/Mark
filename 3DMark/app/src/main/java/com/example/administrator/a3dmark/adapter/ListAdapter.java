package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Goods_Main;
import com.loopj.android.image.SmartImageView;

import java.util.List;

/**
 * Created by 10942 on 2017/2/28 0028.
 */

public class ListAdapter extends BaseAdapter {
    private List<Goods_Main> mainlist;
    private Context mContext;

    public ListAdapter(List<Goods_Main> mainlist, Context mContext) {
        this.mainlist = mainlist;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mainlist.size();

    }

    @Override
    public Object getItem(int position) {
        Goods_Main info = null;
        info = mainlist.get(position);
        return info;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    //绑定数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_itme, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.goods_image = (SmartImageView) convertView.findViewById(R.id.image);
            viewHolder.textBrief = (TextView) convertView.findViewById(R.id.Brief);
            viewHolder.textprice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.textpeople = (TextView) convertView.findViewById(R.id.people);
            viewHolder.textfitting = (TextView) convertView.findViewById(R.id.tv_fitting);
            convertView.setTag(viewHolder);
        } else if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.goods_image.setImageUrl(mainlist.get(position).getImg());
        Glide.with(mContext).load(mainlist.get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(viewHolder.goods_image);
        viewHolder.textpeople.setText("" + mainlist.get(position).getCount() + "人付款");
        viewHolder.textprice.setText("" + mainlist.get(position).getPrice());
        viewHolder.textBrief.setText(mainlist.get(position).getName());
        return convertView;
    }

    class ViewHolder {
        SmartImageView goods_image;//商品图片
        TextView textBrief;//商品简介
        TextView textprice;//商品价格
        TextView textpeople;//付款人数
        TextView textfitting;//马上试衣
    }
}
