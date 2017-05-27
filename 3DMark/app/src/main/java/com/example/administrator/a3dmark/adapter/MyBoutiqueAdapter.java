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
 * Created by Administrator on 2017/3/20.
 */
public class MyBoutiqueAdapter extends PagerAdapter {
    List<String> bean_newsList;
    Context context;

    public MyBoutiqueAdapter(List<String> bean_newsList, Context context) {
        this.bean_newsList = bean_newsList;
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
        Glide.with(context).load(bean_newsList.get(position)).fitCenter().placeholder(R.drawable.icon_empty).into(imageview);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
