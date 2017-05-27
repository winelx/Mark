package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.List_Column;

import java.util.List;

/**
 * Created by Administrator on 2015/11/24.
 */
public class RecycleItemAdapterType2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<List_Column> results;

    //get & set
    public List<List_Column> getResults() {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type2_item, parent, false);
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

    public RecycleItemAdapterType2(Context context, List<List_Column> results) {
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
        holder.item_recyc_type2_item_img.setImageResource(getResults().get(position).getImgSrc());
        holder.item_recyc_type2_item_name.setText(getResults().get(position).getName());
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_recyc_type2_item_img;
        public TextView item_recyc_type2_item_name;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_recyc_type2_item_img = (ImageView) itemView.findViewById(R.id.item_recyc_type2_item_img);
            item_recyc_type2_item_name = (TextView) itemView.findViewById(R.id.item_recyc_type2_item_name);
        }
    }
}
