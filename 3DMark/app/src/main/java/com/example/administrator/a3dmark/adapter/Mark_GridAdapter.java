package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Main_Head;
import com.example.administrator.a3dmark.bean.Mark_Recommend;
import com.loopj.android.image.SmartImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Stability.Yang on 2017/3/1.
 */

public class Mark_GridAdapter extends BaseAdapter {
    private List<Mark_Recommend> mData;
    private Context mContext;

    public Mark_GridAdapter(List<Mark_Recommend> mData, Context context) {
        this.mData = mData;
        this.mContext = context;
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

    //绑定数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        View view = convertView;
        //通过convertView来判断是否已经加载过了，如果没有就加载
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.mark_grid_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) view.findViewById(R.id.imageView);
            holder.textView = (TextView) view.findViewById(R.id.textView);
//            holder.textView = (TextView) view.findViewById(R.id.textView);
            view.setTag(holder);// 给View添加一个格外的数据
        } else {
            holder = (ViewHolder) view.getTag(); // 把数据取出来
        }

        Glide.with(mContext).load(mData.get(position).getGoodsImg()).fitCenter().placeholder(R.drawable.icon_error).into(holder.imageView);
        holder.textView.setText(mData.get(position).getGoodsName());
        return view;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;//商品描述
    }
}

