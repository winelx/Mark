package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.administrator.a3dmark.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */
public class MyMessageAdapter extends BaseAdapter {
    List<String> list;
    Context context;

    public MyMessageAdapter(List<String> list, Context context) {
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
        if (convertView==null){
            convertView = View.inflate(context, R.layout.mymessage_adapter,null);
        }
        return convertView;
    }
}
