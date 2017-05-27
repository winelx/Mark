package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Child;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/16.
 */
public class Child_Adapter extends BaseAdapter {
    List<Child> childList;
    Context context;

    public Child_Adapter(List<Child> childList, Context context) {
        this.childList = childList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return childList.size();
    }

    @Override
    public Object getItem(int position) {
        return childList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Child_Holder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.mark_adapter, null);
            holder = new Child_Holder(convertView);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (Child_Holder) convertView.getTag();
        }
        holder.tvMarkAdapter.setText(childList.get(position).getChilderName());
        holder.tvMarkAdapter.setTag(position);
        holder.tvMarkAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOrderlistener.OnClick(position);
            }
        });
        return convertView;
    }

    /*传递点击事件*/
    private ChildAdapterOnClickListener myOrderlistener;

    public void setListener(ChildAdapterOnClickListener myOrderlistener) {
        this.myOrderlistener = myOrderlistener;
    }

    public interface ChildAdapterOnClickListener{
        void OnClick(int position);
    }

    static class Child_Holder {
        @BindView(R.id.tv_mark_adapter)
        TextView tvMarkAdapter;

        public Child_Holder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
