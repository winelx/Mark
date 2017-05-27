package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Collection;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */

public class Collection_babyAdapter extends BaseAdapter {
    Context context;
    List<Collection> list;

    public Collection_babyAdapter(Context context, List<Collection> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.babyadapter, null);
            viewHolder = new ViewHolder();
            viewHolder.image_heads = (ImageView) convertView.findViewById(R.id.image_collection_shop_head);
            viewHolder.tv_delete = (TextView) convertView.findViewById(R.id.tv_collection_shop_delete);
            viewHolder.tv_goodsname = (TextView) convertView.findViewById(R.id.tv_collection_shop_detail);
            viewHolder.tv_money = (TextView) convertView.findViewById(R.id.tv_collection_shop_money);
            viewHolder.tv_collection_shopping = (TextView) convertView.findViewById(R.id.tv_collection_shopping);
//            viewHolder.tv_collection_shop_try = (TextView) convertView.findViewById(R.id.tv_collection_shop_try);
            convertView.setTag(viewHolder);
        } else if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_money.setText("￥" + list.get(position).getMoney());
        viewHolder.tv_goodsname.setText(list.get(position).getName());
        viewHolder.tv_delete.setTag(position);
        Glide.with(context).load(list.get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(viewHolder.image_heads);
        viewHolder.tv_delete.setOnClickListener(collect_goodsListener);
        viewHolder.tv_collection_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = list.get(position).getGoods_id();
                context.startActivity(new Intent(context, Boutique_Detail.class).putExtra("goods_id",id).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
        return convertView;
    }

    private View.OnClickListener collect_goodsListener;

    public void setlistener(View.OnClickListener collect_goodsListener) {
        this.collect_goodsListener = collect_goodsListener;
    }

    class ViewHolder {
        ImageView image_heads;
        TextView tv_goodsname;
        TextView tv_money;
        TextView tv_delete;
        TextView tv_collection_shopping;
//        TextView tv_collection_shop_try;
    }
}
