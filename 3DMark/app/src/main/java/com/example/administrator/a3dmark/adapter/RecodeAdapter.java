package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Fitting;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
public class RecodeAdapter extends BaseAdapter {
    List<Fitting> list;
    Context context;

    public RecodeAdapter(List<Fitting> list, Context context) {
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
        Fitting_Holder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.recode_adapter, null);
            holder = new Fitting_Holder();
            holder.image_fitting_adapter_goods = (ImageView) convertView.findViewById(R.id.image_fitting_adapter_goods);
            holder.image_recode_delete = (ImageView) convertView.findViewById(R.id.image_recode_delete);
            holder.tv_fitting_adapter_detail = (TextView) convertView.findViewById(R.id.tv_fitting_adapter_detail);
            holder.tv_fitting_adapter_name = (TextView) convertView.findViewById(R.id.tv_fitting_adapter_name);
            holder.tv_fitting_adapter_time = (TextView) convertView.findViewById(R.id.tv_fitting_adapter_time);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (Fitting_Holder) convertView.getTag();
        }
        holder.image_recode_delete.setTag(position);
        holder.image_recode_delete.setOnClickListener(mylistener);
        Glide.with(context).load(list.get(position).getImg()).fitCenter().placeholder(R.mipmap.default_image_goods).into(holder.image_fitting_adapter_goods);
        holder.tv_fitting_adapter_detail.setText(list.get(position).getDetail());
        holder.tv_fitting_adapter_name.setText(list.get(position).getName());
        holder.tv_fitting_adapter_time.setText(list.get(position).getTime());
        //根据sex的数据来显示是男生还是女生
        String sex = list.get(position).getSex();
        if (sex.equals("1")) {
            Drawable nav_up = context.getResources().getDrawable(R.mipmap.sex_boy);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            holder.tv_fitting_adapter_name.setCompoundDrawables(null, null, nav_up, null);
//            holder.tv_fitting_adapter_name.setCompoundDrawables(null, null, context.getResources().getDrawable(R.drawable.sex_boy), null);
        }
        if (sex.equals("0")) {
            Drawable nav_up = context.getResources().getDrawable(R.mipmap.sex_girl);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            holder.tv_fitting_adapter_name.setCompoundDrawables(null, null, nav_up, null);
        }
        return convertView;
    }

    //OnclickLitener
    private View.OnClickListener mylistener;

    public void setlistener(View.OnClickListener mylistener) {
        this.mylistener = mylistener;
    }

    class Fitting_Holder {
        ImageView image_fitting_adapter_goods;
        TextView tv_fitting_adapter_detail;
        TextView tv_fitting_adapter_name;
        TextView tv_fitting_adapter_time;
        ImageView image_recode_delete;
    }
}
