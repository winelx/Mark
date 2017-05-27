package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Bean_News;

import java.util.List;

/**
 * 店铺中的图片播放adapter
 * Created by Administrator on 2017/3/8.
 */
public class MyViewpagerAdapter extends PagerAdapter {
    List<Bean_News> bean_newsList;
    Context context;

    public MyViewpagerAdapter(List<Bean_News> listbeas, Context context) {
        this.bean_newsList = listbeas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bean_newsList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //相当于getview
        View view = View.inflate(context, R.layout.vp_item, null);
        ImageView imageview = (ImageView) view.findViewById(R.id.imageview);
//        Glide.with(context)
//                .load(bean_newsList.get(position).getResid())
//                .into(imageview);
        Glide.with(context).load(bean_newsList.get(position).getResid()).centerCrop().placeholder(R.mipmap.default_image_goods).into(imageview);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
