package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/23.
 */

public class ShopingCardAdapter extends BaseAdapter {
    Context context;
    List<String> list;

    public ShopingCardAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
//        Log.i("test", "---->" + list.size());
//        Toast.makeText(context, "" + list.size(), Toast.LENGTH_SHORT).show();
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
        Util_SHopping util_sHopping;
        if (convertView == null) {
            util_sHopping = new Util_SHopping();
            convertView = View.inflate(context, R.layout.shoppingcardadapter, null);
            ButterKnife.bind(context, convertView);
        }
        return convertView;
    }

    class Util_SHopping {

    }
}
