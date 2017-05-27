package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Logistics;

import java.util.List;

/**
 * Created by Administrator on 2017/3/27.
 */
public class LogisticsAdapter extends BaseAdapter {
    List<Logistics> list;
    Context context;

    public LogisticsAdapter(List<Logistics> list, Context context) {
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
        Logistics_Holder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_logistics, null);
            holder = new Logistics_Holder();
            holder.image_logistics_circle = (ImageView) convertView.findViewById(R.id.image_logistics_circle);
            holder.image_logistics_line = (ImageView) convertView.findViewById(R.id.image_logistics_line);
            holder.tv_logistic_detail = (TextView) convertView.findViewById(R.id.tv_logistic_detail);
            holder.tv_logistic_time = (TextView) convertView.findViewById(R.id.tv_logistic_time);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (Logistics_Holder) convertView.getTag();
        }
        holder.tv_logistic_detail.setText(list.get(position).getAcceptStation());
        holder.tv_logistic_time.setText(list.get(position).getAcceptTime());
        return convertView;
    }

    class Logistics_Holder {
        ImageView image_logistics_circle;
        ImageView image_logistics_line;
        TextView tv_logistic_detail;
        TextView tv_logistic_time;

    }
}
