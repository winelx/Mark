package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Detail_images;
import com.example.administrator.a3dmark.bean.SizeImages;

import java.util.List;

/**
 * 产品参数适配器
 * Created by Administrator on 2017/3/7.
 */
public class Parameter_Adapter extends BaseAdapter {
    List<SizeImages> list;
    Context context;

    public Parameter_Adapter(List<SizeImages> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ParameterHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.parameter_adapter, null);
            holder = new ParameterHolder();
            holder.image_parameter_adapter = (ImageView) convertView.findViewById(R.id.image_parameter_adapter);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (ParameterHolder) convertView.getTag();
        }
        Glide.with(context).load(list.get(position).getDetail_image()).fitCenter()
                .placeholder(R.mipmap.default_image_goods).into(holder.image_parameter_adapter);
        return convertView;
    }

    class ParameterHolder {
        ImageView image_parameter_adapter;
    }

}
