package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Goods_Main;
import com.example.administrator.a3dmark.bean.Today_Store;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/11/24.
 */
public class RecycleItemAdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Goods_Main> results;

    public List<Goods_Main> getResults() {
        return results;
    }

    public void setResults(List<Goods_Main> results) {
        this.results = results;
    }

    public RecycleItemAdapterMain(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view);
        //void onItemLongClick(View view);
    }
    //监听接口
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //监听实现
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_typemain_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view);
                }
            }
        });
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            bind((ItemViewHolder) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return getResults().size();
    }


    private void bind(ItemViewHolder holder, int position) {
        Glide.with(context).load(getResults().get(position).getImg()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.goods_image);
        holder.textpeople.setText(getResults().get(position).getCount() + "人付款");
        holder.textprice.setText(getResults().get(position).getPrice());
        holder.textBrief.setText(getResults().get(position).getName());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView goods_image;//商品图片
        public TextView textBrief;//商品简介
        public TextView textprice;//商品价格
        public TextView textpeople;//付款人数
        public TextView textfitting;//马上试衣

        public ItemViewHolder(View itemView) {
            super(itemView);
            goods_image = (ImageView) itemView.findViewById(R.id.goods_image);
            textBrief = (TextView) itemView.findViewById(R.id.Brief);
            textprice = (TextView) itemView.findViewById(R.id.price);
            textpeople = (TextView) itemView.findViewById(R.id.people);
            textfitting = (TextView) itemView.findViewById(R.id.tv_fitting);
        }
    }
}
