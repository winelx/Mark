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
import com.example.administrator.a3dmark.bean.OrderBean;
import com.example.administrator.a3dmark.bean.OrderGoodsInfo;

import java.util.List;

/**
 * 多个订单/商品数据适配器
 */
public class RefundItemGoodsAdapter extends RecyclerView.Adapter {

    private List<OrderBean> children;
    private Context context;
    /**
     * 构造函数
     */
    public RefundItemGoodsAdapter(Context context) {
        this.context = context;
    }




    public List<OrderBean> getChildren() {
        return children;
    }

    public void setChildren(List<OrderBean> children) {
        this.children = children;
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refund_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            bind((MyViewHolder) holder,position);
        }
    }

    private void bind(MyViewHolder holder, int position){

        Glide.with(context).load(getChildren().get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(holder.image_order_head);
        holder.tv_product_description.setText(getChildren().get(position).getGoodsname());
        holder.tv_order_num.setText("X" + getChildren().get(position).getNum());
    }


    @Override
    public int getItemCount() {
        return getChildren().size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_order_head;//商品头像
        TextView tv_order_num;//几件商品X1
        TextView tv_product_description;//商品描述详情



        public MyViewHolder(final View itemView) {
            super(itemView);
            image_order_head = (ImageView) itemView.findViewById(R.id.image_order_head);
            tv_product_description = (TextView) itemView.findViewById(R.id.tv_product_description);
            tv_order_num = (TextView) itemView.findViewById(R.id.tv_order_num);

        }
    }


}
