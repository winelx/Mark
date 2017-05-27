package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Goods_Main;

import java.util.List;

/**
 * Created by 10942 on 2017/3/7 0007.
 */

public class HotAdapter extends BaseAdapter {
    private List<Goods_Main> mData;
    private Context context;

    public HotAdapter(List<Goods_Main> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convert == null) {
            convert = LayoutInflater.from(context).inflate(R.layout.hot_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.Photo = (ImageView) convert.findViewById(R.id.photo);//商品图片
            viewHolder.name = (TextView) convert.findViewById(R.id.hotcom_name);//商品名称
            viewHolder.price = (TextView) convert.findViewById(R.id.hotcom_price);//商品价格
            viewHolder.house = (TextView) convert.findViewById(R.id.hotcom_house);//商品收藏
            convert.setTag(viewHolder);
        } else if (convert != null) {
            viewHolder = (ViewHolder) convert.getTag();
        }
        Glide.with(context).load(mData.get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(viewHolder.Photo);
        viewHolder.name.setText(mData.get(position).getName());
        viewHolder.price.setText("￥" + mData.get(position).getPrice());
        viewHolder.house.setText(mData.get(position).getCount());
        return convert;
    }

    class ViewHolder {
        ImageView Photo;

        TextView name, price, house;
    }
}
