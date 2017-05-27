package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.OrderBean;

import java.util.List;

import butterknife.ButterKnife;

/**
 * 待付款
 * Created by Administrator on 2017/3/1.
 */
public class Pending_payment_adapter extends BaseAdapter {
    Context context;
    List<OrderBean> list;

    public Pending_payment_adapter(Context context, List<OrderBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder_pay viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.pendingadapter, null);
            viewHolder = new ViewHolder_pay();
            viewHolder.image_order_head = (ImageView) convertView.findViewById(R.id.image_order_head);
            viewHolder.image_order_shop = (ImageView) convertView.findViewById(R.id.image_order_shop);
            viewHolder.tv_order_name = (TextView) convertView.findViewById(R.id.tv_order_name);
            viewHolder.tv_order_item_pay = (TextView) convertView.findViewById(R.id.tv_order_item_pay);
            viewHolder.tv_product_description = (TextView) convertView.findViewById(R.id.tv_product_description);
            viewHolder.tv_order_present_price = (TextView) convertView.findViewById(R.id.tv_order_present_price);
            viewHolder.tv_order_Original_price = (TextView) convertView.findViewById(R.id.tv_order_Original_price);
            viewHolder.tv_order_commodity = (TextView) convertView.findViewById(R.id.tv_order_commodity);
            viewHolder.tv_order_total = (TextView) convertView.findViewById(R.id.tv_order_total);
            viewHolder.tv_order_seller = (TextView) convertView.findViewById(R.id.tv_order_seller);
            viewHolder.tv_order_remove = (TextView) convertView.findViewById(R.id.tv_order_remove);
            viewHolder.tv_order_payment = (TextView) convertView.findViewById(R.id.tv_order_payment);
            viewHolder.tv_order_num = (TextView) convertView.findViewById(R.id.tv_order_num);
            viewHolder.rl_order = (LinearLayout) convertView.findViewById(R.id.rl_order);
            viewHolder.tv_order_freight = (TextView) convertView.findViewById(R.id.tv_order_freight);
            convertView.setTag(viewHolder);
        }
        if (convertView != null) {
            viewHolder = (ViewHolder_pay) convertView.getTag();
        }
        //给原价中间加横线
        viewHolder.tv_order_item_pay.setText("待付款");
        viewHolder.tv_order_payment.setText("马上付款");
        viewHolder.tv_order_Original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        Glide.with(context).load(list.get(position).getImg()).centerCrop().placeholder(R.drawable.icon_empty).into(viewHolder.image_order_head);
        Glide.with(context).load(list.get(position).getLogo()).centerCrop().placeholder(R.drawable.icon_empty).into(viewHolder.image_order_shop);
        viewHolder.tv_order_name.setText(list.get(position).getBuinessname());
        viewHolder.tv_product_description.setText(list.get(position).getGoodsname());
        viewHolder.tv_order_present_price.setText("￥" + list.get(position).getPricenow());
        viewHolder.tv_order_Original_price.setText("￥" + list.get(position).getPricemode());
        viewHolder.tv_order_commodity.setText("共" + list.get(position).getNum() + "件商品");
        viewHolder.tv_order_total.setText("合计￥" + list.get(position).getTotalprice());
        viewHolder.tv_order_num.setText("X" + list.get(position).getNum());
        viewHolder.tv_order_freight.setText("含运费（￥" + list.get(position).getMail() + ")");

        viewHolder.tv_order_remove.setTag(position);
        viewHolder.tv_order_remove.setOnClickListener(myOrderlistener);
        viewHolder.rl_order.setTag(position);
        viewHolder.rl_order.setOnClickListener(myOrderlistener);
        return convertView;
    }

    private View.OnClickListener myOrderlistener;

    public void setlistener(View.OnClickListener myOrderlistener) {
        this.myOrderlistener = myOrderlistener;
    }

    static class ViewHolder_pay {
        ImageView image_order_shop;//店铺头像
        ImageView image_order_head;//商品头像
        TextView tv_order_name;//店铺名
        TextView tv_order_item_pay;//待付款
        TextView tv_product_description;//商品描述详情
        TextView tv_order_present_price;//item上面现价
        TextView tv_order_Original_price;//item上面原价
        TextView tv_order_commodity;//共几件商品
        TextView tv_order_total;//合计
        TextView tv_order_seller;//联系卖家
        TextView tv_order_remove;//取消订单
        TextView tv_order_payment;//付款
        TextView tv_order_num;//几件商品X1
        TextView tv_order_freight;
        LinearLayout rl_order;
    }
}
