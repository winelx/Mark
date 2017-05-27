package com.example.administrator.a3dmark.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Detail_images;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 */
public class Graphic_Adapter extends BaseAdapter {

    private Activity mContext;
    private LayoutInflater mInflater;
    ArrayList<Detail_images> list_image;

    private int width;

    public Graphic_Adapter(Activity mContext, ArrayList<Detail_images> list_image) {
        this.mContext = mContext;
        this.list_image = list_image;
        mInflater = LayoutInflater.from(mContext);

        DisplayMetrics dm = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(dm);

        width = dm.widthPixels;
    }

    @Override
    public int getCount() {
        return list_image.size();
    }

    @Override
    public Object getItem(int position) {
        return list_image.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Graphic_Holder graphic_holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_graphic, null);
            graphic_holder = new Graphic_Holder();
            graphic_holder.image_graphic_item = (ImageView) convertView.findViewById(R.id.image_graphic_item);
//            int screenWidth = width;
//            ViewGroup.LayoutParams lp = graphic_holder.image_graphic_item.getLayoutParams();
//            lp.width = screenWidth;
//            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            graphic_holder.image_graphic_item.setLayoutParams(lp);
//            graphic_holder.image_graphic_item.setMaxWidth(screenWidth);
//            graphic_holder.image_graphic_item.setMaxHeight((int) (screenWidth * 5));// 这里其实可以根据需求而定，我这里测试为最大宽度的1.5倍
            convertView.setTag(graphic_holder);
        } else if (convertView != null) {
            graphic_holder = (Graphic_Holder) convertView.getTag();
        }
        Glide.with(mContext).load(list_image.get(position).getDetail_image()).fitCenter()
                .placeholder(R.mipmap.default_image_goods).into(graphic_holder.image_graphic_item);
        Log.d("image1111=======", list_image.get(position).getDetail_image());
//        Toast.makeText(mContext, "" + list_image.size(), Toast.LENGTH_SHORT).show();
        return convertView;
    }

    class Graphic_Holder {
        ImageView image_graphic_item;
    }
}
