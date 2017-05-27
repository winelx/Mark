package com.example.administrator.a3dmark.detail_shop;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/29.
 */
public class MyGridViewAdapter extends BaseAdapter {
    List<Map<String, Object>> listems;
    Context context;

    public MyGridViewAdapter(List<Map<String, Object>> listems, Context context) {
        this.listems = listems;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listems.size();
    }

    @Override
    public Object getItem(int position) {
        return listems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.layout_tv, null);
////        final TextView textView = (TextView) convertView.findViewById(R.id.tv_item);
//        RadioButton checkBox = (RadioButton) convertView.findViewById(R.id.CheckBox);
//        checkBox.setText(listems.get(position).get("name").toString());
//        checkBox.setTag(position);
//        checkBox.setOnClickListener(myGridlistener);
        return convertView;
    }

    //OnclickLitener
    private View.OnClickListener myGridlistener;

    public void setlistener(View.OnClickListener mylistener) {
        this.myGridlistener = mylistener;
    }
}
