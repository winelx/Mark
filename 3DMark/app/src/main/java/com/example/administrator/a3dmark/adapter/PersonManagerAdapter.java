package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.ManPhoto;

import java.util.List;

/**
 * Created by Administrator on 2017/2/28.
 */

public class PersonManagerAdapter extends BaseAdapter {
    Context context;
    List<ManPhoto> list;

    public PersonManagerAdapter(Context context, List<ManPhoto> deta) {
        this.context = context;
        this.list = deta;
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
    public View getView(int position, View convert, ViewGroup parent) {
        PersonManagerAdapter.ViewHolder viewHolder = null;
        if (convert == null) {
            convert = LayoutInflater.from(context).inflate(R.layout.person_manager_adapter, parent, false);
            viewHolder = new PersonManagerAdapter.ViewHolder();
            viewHolder.photoImg = (ImageView) convert.findViewById(R.id.photoImg);
            viewHolder.delete = (TextView) convert.findViewById(R.id.tv_person_manager_delete);
            viewHolder.project = (TextView) convert.findViewById(R.id.tv_person_manager_project);
            viewHolder.tv_person_manager_name = (TextView) convert.findViewById(R.id.tv_person_manager_name);
            viewHolder.tv_person_manager_height = (TextView) convert.findViewById(R.id.tv_person_manager_height);
            viewHolder.tv_person_manager_dimensional = (TextView) convert.findViewById(R.id.tv_person_manager_dimensional);
            viewHolder.tv_person_manager_time = (TextView) convert.findViewById(R.id.tv_person_manager_time);
            viewHolder.ll_person_manager = (LinearLayout) convert.findViewById(R.id.ll_person_manager);
            convert.setTag(viewHolder);
        } else if (convert != null) {
            viewHolder = (PersonManagerAdapter.ViewHolder) convert.getTag();
        }
        String sex = list.get(position).getSex();
        if (sex.equals("1")) {
            Drawable drawable = context.getResources().getDrawable(R.mipmap.sex_boy);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            viewHolder.tv_person_manager_name.setCompoundDrawables(null, null, drawable, null);
        }
        if (sex.equals("0")) {
            Drawable drawable = context.getResources().getDrawable(R.mipmap.sex_girl);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(),
                    drawable.getMinimumHeight());
            viewHolder.tv_person_manager_name.setCompoundDrawables(null, null, drawable, null);
        }
        String is_self = list.get(position).getIsSelf();
        if (is_self.equals("1")) {
            viewHolder.delete.setVisibility(View.INVISIBLE);
        } else if (is_self.equals("0")) {
            viewHolder.delete.setVisibility(View.VISIBLE);
        }
        String is_fit = list.get(position).getIsFit();
        if (is_fit.equals("0")) {
            viewHolder.ll_person_manager.setBackgroundColor(context.getResources().getColor(R.color.White));
        } else if (is_fit.equals("1")) {
            viewHolder.ll_person_manager.setBackgroundColor(context.getResources().getColor(R.color.bg_white));
        }
        viewHolder.tv_person_manager_name.setText(list.get(position).getNickname());
        viewHolder.tv_person_manager_height.setText(list.get(position).getHeight() + "/" + list.get(position).getWeight());
        viewHolder.tv_person_manager_dimensional.setText(list.get(position).getBust() + "/" +
                list.get(position).getWaistline() + "/" + list.get(position).getHipline());
        viewHolder.tv_person_manager_time.setText(list.get(position).getTime());
        Glide.with(context).load(list.get(position).getImg()).fitCenter().placeholder(R.drawable.icon_empty).into(viewHolder.photoImg);
        //点击事件
        viewHolder.delete.setTag(position);
        viewHolder.delete.setOnClickListener(mypersonlistener);
        return convert;
    }

    private View.OnClickListener mypersonlistener;

    public void setlistener(View.OnClickListener mypersonlistener) {
        this.mypersonlistener = mypersonlistener;
    }

    class ViewHolder {
        ImageView photoImg;
        TextView delete, project;
        TextView tv_person_manager_name;
        TextView tv_person_manager_height;
        TextView tv_person_manager_dimensional;
        TextView tv_person_manager_time;
        LinearLayout ll_person_manager;
    }
}
