package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.Activity.Comment_submit;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.MyOrderBean;
import com.example.administrator.a3dmark.easemob.ui.ChatActivity;
import com.example.administrator.a3dmark.test.OrderlistAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 待评价
 * Created by Administrator on 2017/2/27.
 */

public class Pending_Evaluation_adapter extends BaseAdapter {

    List<MyOrderBean> list;
    Context context;

    public Pending_Evaluation_adapter(List<MyOrderBean> list, Context context) {
        this.list = list;
        this.context = context;
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
        ViewHolder_order holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_myordertest, null);
            holder = new ViewHolder_order(convertView);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (ViewHolder_order) convertView.getTag();
        }
        Glide.with(context).load(list.get(position).getLogo()).centerCrop().placeholder(R.drawable.icon_empty).into(holder.imageMyorderNameShop);
        holder.tvMyorderNameShop.setText(list.get(position).getBuinessName());
//        String state = list.get(position)
        holder.orderId.setText(list.get(position).getOrderId());
        holder.tvMyorderState.setText("已签收");
        holder.tvOrderTotal.setText("合计：￥" + list.get(position).getAllTotalPrice());
        holder.tvOrderCommodity.setText("共" + list.get(position).getAllNum() + "件商品");
        holder.tvOrderFreight.setText("含运费（￥" + list.get(position).getAllMail() + "）");
        holder.tvOrderPayment.setText("  评价  ");
        holder.tvOrderRemove.setVisibility(View.GONE);
        OrderlistAdapter adapter = new OrderlistAdapter(context, list.get(position).getGoodsOrders());
        holder.lvMyorderTestgoods.setAdapter(adapter);
        //根据innerlistview的高度机损parentlistview item的高度
        setListViewHeightBasedOnChildren(holder.lvMyorderTestgoods);
         /*item里面的点击事件*/

        holder.tvOrderPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String orderId = list.get(position).getOrderId();
                String goodsId = list.get(position).getBussinessId();
                Intent intent = new Intent(context, Comment_submit.class);
                intent.putExtra("orderId", orderId);
                intent.putExtra("goodsId",goodsId);
                context.startActivity(intent);
            }
        });
        holder.tvOrderSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatActivity.class));
            }
        });
        return convertView;
    }


    static class ViewHolder_order {
        @BindView(R.id.image_myorder_name_shop)
        ImageView imageMyorderNameShop;
        @BindView(R.id.tv_myorder_name_shop)
        TextView tvMyorderNameShop;
        @BindView(R.id.tv_myorder_state)
        TextView tvMyorderState;
        @BindView(R.id.lv_myorder_testgoods)
        ListView lvMyorderTestgoods;
        @BindView(R.id.tv_order_commodity)
        TextView tvOrderCommodity;
        @BindView(R.id.tv_order_total)
        TextView tvOrderTotal;
        @BindView(R.id.tv_order_freight)
        TextView tvOrderFreight;
        @BindView(R.id.tv_order_seller)
        TextView tvOrderSeller;
        @BindView(R.id.tv_order_remove)
        TextView tvOrderRemove;
        @BindView(R.id.tv_order_payment)
        TextView tvOrderPayment;
        @BindView(R.id.orderId)
        EditText orderId;

        ViewHolder_order(View view) {
            ButterKnife.bind(this, view);
        }
    }

    /**
     * @param ：计算parentlistview item的高度。
     *                          如果不使用此方法，无论innerlistview有多少个item，则只会显示一个item。
     **/
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        android.widget.ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
