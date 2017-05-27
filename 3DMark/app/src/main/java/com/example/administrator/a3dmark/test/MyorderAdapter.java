package com.example.administrator.a3dmark.test;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Goods_order;
import com.example.administrator.a3dmark.bean.MyOrderBean;
import com.example.administrator.a3dmark.easemob.ui.ChatActivity;
import com.example.administrator.a3dmark.util.Contants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/12.
 */
public class MyorderAdapter extends BaseAdapter {
    List<MyOrderBean> list;
    Context context;

    public OrderId orderId;
    public void setOrderId(OrderId orderId){
        this.orderId = orderId;
    }
    public interface OrderId{
        void setId(String id);
    }

    public MyorderAdapter(List<MyOrderBean> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final String id = list.get(position).getOrderId();
        ViewHolder_order holder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_myordertest, null);
            holder = new ViewHolder_order(convertView);
            convertView.setTag(holder);
        } else if (convertView != null) {
            holder = (ViewHolder_order) convertView.getTag();
        }
        Glide.with(context).load(list.get(position).getLogo()).centerCrop().placeholder(R.drawable.icon_empty).into(holder.imageMyorderNameShop);
        holder.orderId.setText(list.get(position).getOrderId());
        holder.tvMyorderNameShop.setText(list.get(position).getBuinessName());
        holder.tvMyorderState.setText("未付款");
        holder.tvOrderTotal.setText("合计：￥" + list.get(position).getAllTotalPrice());
        holder.tvOrderCommodity.setText("共" + list.get(position).getAllNum() + "件商品");
        holder.tvOrderFreight.setText("含运费（￥" + list.get(position).getAllMail() + "）");
        holder.tvOrderPayment.setText("马上付款");
        holder.tvOrderSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatActivity.class));
            }
        });


        holder.tvOrderPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderId.setId(id);
            }
        });


        OrderlistAdapter adapter = new OrderlistAdapter(context, list.get(position).getGoodsOrders());
        holder.lvMyorderTestgoods.setAdapter(adapter);
        //根据innerlistview的高度机损parentlistview item的高度
        setListViewHeightBasedOnChildren(holder.lvMyorderTestgoods);
         /*item里面的点击事件*/
        holder.tvOrderRemove.setTag(position);
        holder.tvOrderRemove.setOnClickListener(myOrderlistener);
        return convertView;
    }

    private View.OnClickListener myOrderlistener;

    public void setlistener(View.OnClickListener myOrderlistener) {
        this.myOrderlistener = myOrderlistener;
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
        ListAdapter listAdapter = listView.getAdapter();
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
