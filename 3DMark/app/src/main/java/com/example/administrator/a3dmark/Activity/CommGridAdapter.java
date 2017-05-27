package com.example.administrator.a3dmark.Activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;

import java.io.File;
import java.util.ArrayList;

/**
 * @name 3DMark
 * @class name：com.example.administrator.a3dmark.Activity
 * @class describe
 * @anthor 10942 QQ:1032006226
 * @time 2017/5/2 0002 11:13
 * @change
 * @chang time
 * @class describe
 */
class CommGridAdapter extends BaseAdapter {
    private Comment_submit comment_submit;
    private ArrayList<String> listUrls;

    public CommGridAdapter(Comment_submit comment_submit, ArrayList<String> listUrls) {
        this.comment_submit = comment_submit;
        this.listUrls = listUrls;
    }

    @Override
    public int getCount() {
        return listUrls.size();
    }

    @Override
    public String getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            convertView = comment_submit.getLayoutInflater().inflate(R.layout.item_image, null);
            imageView = (ImageView) convertView.findViewById(R.id.comm_imageView);
            convertView.setTag(imageView);
        } else {
            imageView = (ImageView) convertView.getTag();
        }
        Glide.with(comment_submit)
                .load(new File(getItem(position)))
                .centerCrop()
                .crossFade()
                .into(imageView);
        return convertView;
    }
}
