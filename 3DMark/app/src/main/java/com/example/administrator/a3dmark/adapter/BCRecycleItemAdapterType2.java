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
import com.example.administrator.a3dmark.bean.BCStore;
import com.example.administrator.a3dmark.bean.List_Column;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class BCRecycleItemAdapterType2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<BCStore>  results;

    //get & set
    public List<BCStore> getResults() {
        return results;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.md_and_bc_recycleitemadapter_type, parent, false);
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

    public BCRecycleItemAdapterType2(Context context, List<BCStore> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder){
            bind((ItemViewHolder) holder,position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    /////////////////////////////

    private void bind(ItemViewHolder holder, int position){
        Glide.with(context).load(getResults().get(position).getImg()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.img);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
