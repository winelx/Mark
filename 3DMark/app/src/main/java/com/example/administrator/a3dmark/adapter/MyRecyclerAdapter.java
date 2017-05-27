package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;

import java.util.List;


public class MyRecyclerAdapter extends BaseAdapter {
    List<String> mData;
    Context context;

    public MyRecyclerAdapter(List<String> mData, Context context) {
        this.mData = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.layout_tv, null);
//        TextView textView = (TextView) convertView.findViewById(R.id.CheckBox);
//        textView.setText(mData.get(position));
        return convertView;
    }
}
