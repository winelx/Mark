package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.child_pakage.Address;

import java.util.List;

/**
 * Created by Administrator on 2017/2/27.
 */

public class PersonAddr_Adapter extends BaseAdapter {
    Context context;
    List<Address> list;

    public PersonAddr_Adapter(List<Address> list, Context context) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.personaddr_adapter, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_addr_name = (TextView) convertView.findViewById(R.id.tv_addr_name);
            viewHolder.tv_addr_phone = (TextView) convertView.findViewById(R.id.tv_addr_phone);
            viewHolder.tv_detail_addr = (TextView) convertView.findViewById(R.id.tv_detail_addr);
            viewHolder.tv_default_addr = (CheckBox) convertView.findViewById(R.id.tv_default_addr);
            viewHolder.tv_edit_addr = (TextView) convertView.findViewById(R.id.tv_edit_addr);
            viewHolder.tv_delete_addr = (TextView) convertView.findViewById(R.id.tv_delete_addr);
            convertView.setTag(viewHolder);
        } else if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //添加Tag和Onclick
        viewHolder.tv_default_addr.setTag(position);//默认地址
        viewHolder.tv_edit_addr.setTag(position);//编辑地址
        viewHolder.tv_delete_addr.setTag(position);//删除地址
        viewHolder.tv_detail_addr.setTag(position);//详细地址

        viewHolder.tv_default_addr.setOnClickListener(mylistener);
        viewHolder.tv_edit_addr.setOnClickListener(mylistener);
        viewHolder.tv_delete_addr.setOnClickListener(mylistener);
        viewHolder.tv_detail_addr.setOnClickListener(mylistener);

        //获取数据
        viewHolder.tv_detail_addr.setText(list.get(position).getArea() + list.get(position).getCity()
                + list.get(position).getDistrict() + list.get(position).getStreet() + list.get(position).getAddr());
        viewHolder.tv_addr_phone.setText(list.get(position).getPhone());
        viewHolder.tv_addr_name.setText(list.get(position).getName());
        if (list.get(position).getDefaultaddress() == 0) {
            viewHolder.tv_default_addr.setButtonDrawable(R.mipmap.default_addr_red);
            viewHolder.tv_default_addr.setText("默认地址");
        } else if (list.get(position).getDefaultaddress() == 1) {
            viewHolder.tv_default_addr.setButtonDrawable(R.mipmap.default_addr_white);
            viewHolder.tv_default_addr.setText("设为默认");
        }
//        viewHolder.tv_default_addr.setButtonDrawable();
        return convertView;
    }

    //OnclickLitener
    private View.OnClickListener mylistener;

    public void setlistener(View.OnClickListener mylistener) {
        this.mylistener = mylistener;
    }

    class ViewHolder {
        TextView tv_addr_name;
        TextView tv_addr_phone;
        TextView tv_detail_addr;
        CheckBox tv_default_addr;
        TextView tv_edit_addr;
        TextView tv_delete_addr;
    }
}
