package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Searchbean;

import java.util.List;

/**
 * @name 3DMark
 * @class name：com.example.administrator.a3dmark.adapter
 * @class describe
 * @anthor 10942 QQ:1032006226
 * @time 2017/5/15 0015 10:19
 * @change
 * @chang time
 * @class describe
 */
public class SearchrAdapter extends RecyclerView.Adapter<SearchrAdapter.MyViewHolder> {

    private List<Searchbean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    MyViewHolder holder;


    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view);
        //void onLongClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public SearchrAdapter(Context context, List<Searchbean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.serach_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(v);
            }
        });
//        holder = new MyViewHolder(view);
        //将创建的View注册点击事件
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(mContext).load(mDatas.get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(holder.image);
        holder.tv_fitting.setText(mDatas.get(position).getGoodsName());
        holder.people.setText(mDatas.get(position).getCount() + "人付款");
        holder.price.setText(mDatas.get(position).getPriceModeNew());//价格


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView price, people, tv_fitting;
        ImageView image;
        LinearLayout textVIew;

        public MyViewHolder(View view) {
            super(view);
            price = (TextView) view.findViewById(R.id.price);
            people = (TextView) view.findViewById(R.id.people);
            tv_fitting = (TextView) view.findViewById(R.id.Brief);
            image = (ImageView) view.findViewById(R.id.image);
            textVIew = (LinearLayout) view.findViewById(R.id.textView);

        }
    }


}