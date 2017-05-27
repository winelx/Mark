package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.OrderBean;
import com.example.administrator.a3dmark.bean.OrderGoodsInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 退款
 * Created by Administrator on 2017/3/2.
 *
 */
public class Refund_Adapter extends RecyclerView.Adapter {
    Context context;
    RefundItemGoodsAdapter refundItemGoodsAdapter;
    List<OrderBean> stores;
    private Map<String, List<OrderBean>> goodss;

    public Refund_Adapter(Context context) {
        this.context = context;
    }

    public List<OrderBean> getStores() {
        return stores;
    }

    public void setStores(List<OrderBean> stores) {
        this.stores = stores;
    }

    public Map<String, List<OrderBean>> getGoodss() {
        return goodss;
    }

    public void setGoodss(Map<String, List<OrderBean>> goodss) {
        this.goodss = goodss;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refund, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Refund_Adapter.MyViewHolder){
            bind((Refund_Adapter.MyViewHolder) holder,position);
        }
    }

    private void bind(Refund_Adapter.MyViewHolder holder, final int position){

        List<OrderBean> GoodsInfos = getGoodss().get(getStores().get(position).getBussinessId());

        //店铺内容
        Glide.with(context).load(getStores().get(position).getLogo()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.image_order_shop);
        holder.tv_order_name.setText(getStores().get(position).getBuinessname());
        holder.orderId.setText(getStores().get(position).getOrderID());
        holder.tv_order_total.setText("交易金额  ￥"+getStores().get(position).getTotalprice());
        holder.tv_order_refund_total.setText("退款金额  ￥"+getStores().get(position).getRefund_money());

        if ("6".equals(getStores().get(position).getState())){
            holder.tv_order_item_pay.setText("退款中");
        }

        holder.tv_order_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("6".equals(getStores().get(position).getState())){
                    Toast.makeText(context, "退款进行中!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "已退入您的支付宝账号!", Toast.LENGTH_SHORT).show();
            }
        });

        //商品
        holder.recyclerView.setLayoutManager(new GridLayoutManager(holder.recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
        refundItemGoodsAdapter = new RefundItemGoodsAdapter(context);
        refundItemGoodsAdapter.setChildren(GoodsInfos);
        holder.recyclerView.setAdapter(refundItemGoodsAdapter);
    }


    @Override
    public int getItemCount() {
        return getStores().size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image_order_shop;//店铺头像
        TextView tv_order_name;//店铺名
        TextView tv_order_total;//交易金额
        TextView tv_order_refund_total;//退款金额
        TextView tv_order_payment;//钱款去向
        TextView tv_order_item_pay;//退款状态
        RecyclerView recyclerView;//商品
        EditText orderId;

        public MyViewHolder(final View itemView) {
            super(itemView);

            orderId = (EditText) itemView.findViewById(R.id.orderId);
            tv_order_name = (TextView) itemView.findViewById(R.id.tv_order_name);
            image_order_shop = (ImageView) itemView.findViewById(R.id.image_order_shop);
            tv_order_total = (TextView) itemView.findViewById(R.id.tv_order_total);
            tv_order_refund_total = (TextView) itemView.findViewById(R.id.tv_order_refund_total);
            tv_order_payment = (TextView) itemView.findViewById(R.id.tv_order_payment);
            tv_order_item_pay = (TextView) itemView.findViewById(R.id.tv_order_item_pay);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }

    }
}
