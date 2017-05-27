package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Today_Store;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/11/24.
 */
public class RecycleItemAdapterType4 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Today_Store> results;

    public List<Today_Store> getResults() {
        return results;
    }

    public void setResults(List<Today_Store> results) {
        this.results = results;
    }

    public RecycleItemAdapterType4(Context context) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type4_item, parent, false);
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


    private void bind(ItemViewHolder holder, int position){
        Glide.with(context).load(getResults().get(position).getImgUrl()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.item_recyc_type4_item_img);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_recyc_type4_item_img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_recyc_type4_item_img = (ImageView) itemView.findViewById(R.id.item_recyc_type4_item_img);
        }
    }
}
