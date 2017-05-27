package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.OrderGoodsInfo;
import com.example.administrator.a3dmark.bean.OrderStoreInfo;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 多个订单/商品数据适配器
 */
public class OrderItemGoodsAdapter extends RecyclerView.Adapter {

    private List<OrderGoodsInfo> children;
    private Context context;
    /**
     * 构造函数
     */
    public OrderItemGoodsAdapter(Context context) {
        this.context = context;
    }




    public List<OrderGoodsInfo> getChildren() {
        return children;
    }

    public void setChildren(List<OrderGoodsInfo> children) {
        this.children = children;
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders_goods, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            bind((MyViewHolder) holder,position);
        }
    }

    private void bind(MyViewHolder holder, int position){

        Glide.with(context).load(getChildren().get(position).getImageUrl()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.image_ensure_order_goods_image);
        holder.tv_ensure_order_goods_detail.setText(getChildren().get(position).getDesc());
        holder.tv_ensure_order_goods_price.setText("￥"+getChildren().get(position).getPrice());
        holder.tv_ensure_order_goods_num.setText("X"+getChildren().get(position).getCount());
        holder.tv_ensure_order_goods_size.setText("规格: "+getChildren().get(position).getSize());
        holder.tv_ensure_order_goods_color.setText("分类: "+getChildren().get(position).getColor());
        holder.tv_ensure_order_goods_time.setText("发货时间："+getChildren().get(position).getDeliveryTime()+"小时内发货");


    }


    @Override
    public int getItemCount() {
        return getChildren().size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_ensure_order_goods_image;//图片
        TextView tv_ensure_order_goods_detail;//描述
        TextView tv_ensure_order_goods_price;//价格
        TextView tv_ensure_order_goods_num;//数量
        TextView tv_ensure_order_goods_size;//商品尺寸
        TextView tv_ensure_order_goods_color;//商品颜色
        TextView tv_ensure_order_goods_time;//发货时间



        public MyViewHolder(final View itemView) {
            super(itemView);
            image_ensure_order_goods_image = (ImageView) itemView.findViewById(R.id.image_ensure_order_goods_image);
            tv_ensure_order_goods_detail = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_detail);
            tv_ensure_order_goods_price = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_price);
            tv_ensure_order_goods_num = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_num);
            tv_ensure_order_goods_size = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_size);
            tv_ensure_order_goods_color = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_color);
            tv_ensure_order_goods_time = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_time);
        }
    }


}
