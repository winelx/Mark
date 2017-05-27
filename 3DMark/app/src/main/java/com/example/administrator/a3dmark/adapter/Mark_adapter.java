package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Mark;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/16.
 */
public class Mark_adapter extends BaseAdapter {
    Context context;
    List<Mark> list;

    public Mark_adapter(Context context, List<Mark> list) {
        this.context = context;
        this.list = list;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        Mark_ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.layout_mark_devide, null);
            holder = new Mark_ViewHolder(convertView);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (Mark_ViewHolder) convertView.getTag();
        }
        holder.tvMarkAdapter.setText(list.get(position).getTypeName());
        holder.tvMarkAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myOrderlistener.ClickListener(position);
            }
        });

        return convertView;
    }



    private MarkAdapterOnClickListener myOrderlistener;

    public void setListener(MarkAdapterOnClickListener myOrderlistener) {
        this.myOrderlistener = myOrderlistener;
    }

    public interface MarkAdapterOnClickListener{
        void ClickListener(int position);
    }

    static class Mark_ViewHolder {
        @BindView(R.id.tv_mark_devide)
        TextView tvMarkAdapter;

        Mark_ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
