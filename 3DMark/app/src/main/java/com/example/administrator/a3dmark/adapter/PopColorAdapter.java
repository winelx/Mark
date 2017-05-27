package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.DColorBean;
import com.example.administrator.a3dmark.bean.DColorBean;

import java.util.List;

/**
 * Created by LGY on 2017/4/1.
 */

public class PopColorAdapter extends BaseAdapter {
    private List<DColorBean> list;//数据源
    private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局
    public onItemClickListener itemClickListener;// 接口回调

    public void setItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public PopColorAdapter(List<DColorBean> list, Context context) {
        super();
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(

                    R.layout.gridview_item, null);
            /** 得到各个控件的对象 */
            holder.title = (TextView) convertView.findViewById(R.id.ItemText);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(holder);// 绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
        }
        final DColorBean DColorBean = list.get(position);

        switch (DColorBean.getStates()) {
            // 选中
            case "0":
                holder.layout.setBackgroundResource(R.drawable.shape2);
                holder.title.setTextColor(Color.WHITE);
                break;
            // 未选中
            case "1":
                holder.layout.setBackgroundResource(R.drawable.btn_pale_pink);
                holder.title.setTextColor(Color.BLACK);
                break;
        }
        /** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
        holder.title.setText(DColorBean.getColor());
        holder.layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(DColorBean, position);
                }
            }
        });
        return convertView;
    }

    public final class ViewHolder {
        public TextView title;
        public LinearLayout layout;
    }

    public interface onItemClickListener {
        public void onItemClick(DColorBean DColorBean, int position);
    }
}
