package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Pro;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.detail_shop.Shop_Activity;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.a3dmark.R.id.portrait;

/**
 * Created by 10942 on 2017/3/7 0007.
 */
public class MyAdapter extends BaseAdapter {
    private List<Pro> mlist;
    private Context context;
    String str;

    public MyAdapter(List<Pro> mData, Context context) {
        this.mlist = mData;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(context).inflate(R.layout.recomm_itme, parent, false);
            viewHolder.imgList = (LinearLayout) convertView.findViewById(R.id.imgList);
            viewHolder.collection = (TextView) convertView.findViewById(R.id.collection);
            viewHolder.image_recommend_big = (ImageView) convertView.findViewById(R.id.image_recommend_big);
            viewHolder.image_recommend_small_top = (ImageView) convertView.findViewById(R.id.image_recommend_small_top);
            viewHolder.image_recommend_small_bottom = (ImageView) convertView.findViewById(R.id.image_recommend_small_bottom);
            viewHolder.image_head_recommend = (ImageView) convertView.findViewById(R.id.image_head_recommend);
            viewHolder.portrait = (TextView) convertView.findViewById(portrait);
            viewHolder.Thename = (TextView) convertView.findViewById(R.id.Thename);
            viewHolder.number = (TextView) convertView.findViewById(R.id.number);
            viewHolder.golet = (Button) convertView.findViewById(R.id.golet);
            viewHolder.golet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Shop_Activity.class);
                    Bundle b = new Bundle();
                    b.putString("bussiness_id", mlist.get(position).getBussiness_id());
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
            viewHolder.image_recommend_big.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Boutique_Detail.class);
                    Bundle b = new Bundle();

                    b.putString("goods_id", mlist.get(position).getGoodsId().get(0));
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
            viewHolder.image_recommend_small_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Boutique_Detail.class);
                    Bundle b = new Bundle();

                    b.putString("goods_id", mlist.get(position).getGoodsId().get(1));
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
            viewHolder.image_recommend_small_bottom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Boutique_Detail.class);
                    Bundle b = new Bundle();

                    b.putString("goods_id", mlist.get(position).getGoodsId().get(2));
                    intent.putExtras(b);
                    context.startActivity(intent);
                }
            });
            viewHolder.collection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    str = (String) SharedUtil.getParam(context, "userid", "");
                    OkGo.post(Contants.COLLECTION_SHTORE)
                            .connTimeOut(5000)
                            .params("id", mlist.get(position).getBussiness_id())
                            .params("userid", str)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    if (s == "1") {
                                        Toast.makeText(context, "收藏成功" + s, Toast.LENGTH_SHORT).show();
                                    }
//                                    switch (s) {
//                                        case 1 + "":
//                                            Toast.makeText(context, "收藏成功" + s, Toast.LENGTH_SHORT).show();
//                                            break;
//                                        case 0 + "":
//                                            Toast.makeText(context, "收藏失败" + s, Toast.LENGTH_SHORT).show();
//                                            break;
//
//                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                }
                            });
                }
            });
            convertView.setTag(viewHolder);
        } else if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.number.setText("月收藏" + mlist.get(position).getCount() + "次");
        viewHolder.portrait.setText(mlist.get(position).getStoreName());
        Glide.with(context).load(mlist.get(position).getImg1()).centerCrop().placeholder(R.mipmap.default_image_goods).into(viewHolder.image_recommend_big);
        Glide.with(context).load(mlist.get(position).getImg2()).centerCrop().placeholder(R.mipmap.default_image_goods).into(viewHolder.image_recommend_small_top);
        Glide.with(context).load(mlist.get(position).getImg3()).centerCrop().placeholder(R.mipmap.default_image_goods).into(viewHolder.image_recommend_small_bottom);
        Glide.with(context).load(mlist.get(position).getLogo()).centerCrop().placeholder(R.mipmap.touxiang_default).into(viewHolder.image_head_recommend);
        viewHolder.Thename.setText(mlist.get(position).getBussiness_id());
        return convertView;
    }

    class ViewHolder {
        LinearLayout imgList;//包裹图片的布局
        ImageView image_recommend_small_top;
        ImageView image_recommend_small_bottom;
        ImageView image_recommend_big;
        ImageView image_head_recommend;//店铺图片
        TextView portrait;//店铺名
        TextView Thename;//店铺简介
        TextView collection;//收藏
        TextView number;//收藏人数
        Button golet;//进入店铺
    }


}
