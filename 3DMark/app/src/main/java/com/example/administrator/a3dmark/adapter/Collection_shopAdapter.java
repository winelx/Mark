package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Collection;
import com.liji.circleimageview.CircleImageView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/1.
 */
public class Collection_shopAdapter extends BaseAdapter {
    Context context;
    List<Collection> list;

    public Collection_shopAdapter(Context context, List<Collection> list) {
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
        UnitShop unitShop = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.collect_shop_adapter, null);
            unitShop = new UnitShop();
            unitShop.head = (CircleImageView) convertView.findViewById(R.id.image_collection_head);
            unitShop.tv_collection_shop_name = (TextView) convertView.findViewById(R.id.tv_collection_shop_name);
            unitShop.tv_collection_shop_block = (TextView) convertView.findViewById(R.id.tv_collection_shop_block);
            unitShop.image_collection_shop_remove = (ImageView) convertView.findViewById(R.id.image_collection_shop_remove);
            unitShop.tv_collection_shop_remove = (TextView) convertView.findViewById(R.id.tv_collection_shop_remove);
            unitShop.ll_collect_remove = (LinearLayout) convertView.findViewById(R.id.ll_collect_remove);
            unitShop.ll_collect = (LinearLayout) convertView.findViewById(R.id.ll_collect);
            convertView.setTag(unitShop);
        }
        if (convertView != null) {
            unitShop = (UnitShop) convertView.getTag();
        }
        Glide.with(context).load(list.get(position).getImg()).centerCrop().placeholder(R.mipmap.touxiang_default).into(unitShop.head);
        unitShop.tv_collection_shop_name.setText(list.get(position).getName());
        unitShop.tv_collection_shop_block.setText(list.get(position).getIntroduce());
        unitShop.ll_collect_remove.setTag(position);
        unitShop.ll_collect_remove.setOnClickListener(collectListener);
        unitShop.ll_collect.setTag(position);
        unitShop.ll_collect.setOnClickListener(collectListener);
        return convertView;
    }

    //OnclickLitener
    private View.OnClickListener collectListener;

    public void setlistener(View.OnClickListener collectListener) {
        this.collectListener = collectListener;
    }

    class UnitShop {
        CircleImageView head;
        TextView tv_collection_shop_name;
        TextView tv_collection_shop_block;
        ImageView image_collection_shop_remove;
        TextView tv_collection_shop_remove;
        LinearLayout ll_collect_remove;
        LinearLayout ll_collect;
    }
}
