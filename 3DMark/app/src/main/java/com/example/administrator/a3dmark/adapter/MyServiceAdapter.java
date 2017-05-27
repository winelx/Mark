package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Service_bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class MyServiceAdapter extends BaseAdapter {
    Context context;
    List<Service_bean> list;

    public MyServiceAdapter(Context context, List<Service_bean> list) {
        this.context = context;
        this.list = list;
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
        Service_Holder service_holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_service, null);
            service_holder = new Service_Holder();
            service_holder.tv_detail = (TextView) convertView.findViewById(R.id.tv_service_detail);
            service_holder.tv_title = (TextView) convertView.findViewById(R.id.tv_service_title);
//            convertView.getTag();
            convertView.setTag(service_holder);
        } else if (convertView != null) {
            service_holder = (Service_Holder) convertView.getTag();
        }
        service_holder.tv_title.setText(list.get(position).getTitle());
        if (list.get(position).getDetail() == null) {
            service_holder.tv_detail.setVisibility(View.GONE);
        }
        service_holder.tv_detail.setText(list.get(position).getDetail());
        return convertView;
    }

    class Service_Holder {
        TextView tv_title;
        TextView tv_detail;
    }
}
