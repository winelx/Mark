package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
 * 多个订单数据适配器
 */
public class OrderAdapter extends RecyclerView.Adapter {

    private List<OrderStoreInfo> groups;
    private Map<String, List<OrderGoodsInfo>> children;
    private OrderItemGoodsAdapter itemAdapter;
    private Context context;

    private double cont = 0.0;//邮费
    private double total = 0.0;//小计
    private String mode ;//免邮费

    /**
     * 构造函数
     */
    public OrderAdapter(Context context) {
        this.context = context;
    }




    public List<OrderStoreInfo> getGroups() {
        return groups;
    }

    public void setGroups(List<OrderStoreInfo> groups) {
        this.groups = groups;
    }

    public Map<String, List<OrderGoodsInfo>> getChildren() {
        return children;
    }

    public void setChildren(Map<String, List<OrderGoodsInfo>> children) {
        this.children = children;
    }






    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orders_recycle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            bind((MyViewHolder) holder,position);
        }
    }

    private void bind(MyViewHolder holder, int position){

        List<OrderGoodsInfo> orderGoodsInfos = getChildren().get(getGroups().get(position).getId());

        Glide.with(context).load(getGroups().get(position).getStoreLogoImg()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.storeLogoimg);
        holder.tvSourceName.setText(getGroups().get(position).getName());

        //邮费---->小计  计算
        for (OrderGoodsInfo info : orderGoodsInfos){
            cont += info.getMail()*info.getCount();
            total += (info.getPrice()+info.getMail())*info.getCount();
        }

        if (cont != 0.0){
            holder.tv_ensure_order_goods_mode.setText("快递: ￥"+cont);//邮费
            holder.tv_ensure_order_goods_Subtotal.setText("小计：￥"+total);//小计
            cont = 0.0;//初始化邮费
            total = 0.0;//初始化小计
        }else{
            holder.tv_ensure_order_goods_mode.setText("快递 免邮");//免邮费
            holder.tv_ensure_order_goods_Subtotal.setText("小计：￥"+total);//小计
            total = 0.0;//初始化小计
        }
        holder.tv_ensure_order_goods_num.setText("共"+orderGoodsInfos.size()+"件商品");

        //添加editText的监听事件
        holder.tv_ensure_order_goods_message.addTextChangedListener(new TextSwitcher(holder,position));
        //通过设置tag，防止position紊乱
        //holder.tv_ensure_order_goods_message.setTag(position);
        //editTextGetTextListener.editText(position,holder.tv_ensure_order_goods_message.getText().toString());
        //商品
        holder.goods_recyc.setLayoutManager(new GridLayoutManager(holder.goods_recyc.getContext(), 1, GridLayoutManager.VERTICAL, false));
        itemAdapter = new OrderItemGoodsAdapter(context);
        itemAdapter.setChildren(orderGoodsInfos);
        holder.goods_recyc.setAdapter(itemAdapter);
    }


    @Override
    public int getItemCount() {
        return getGroups().size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSourceName;//店名
        ImageView storeLogoimg;//logo
        RecyclerView goods_recyc;//商品
        EditText tv_ensure_order_goods_message;//留言
        TextView tv_ensure_order_goods_mode;//邮费
        TextView tv_ensure_order_goods_num;//商品数
        TextView tv_ensure_order_goods_Subtotal;//小计



        public MyViewHolder(final View itemView) {
            super(itemView);
            storeLogoimg = (ImageView) itemView.findViewById(R.id.store_logoimg);
            tvSourceName = (TextView) itemView.findViewById(R.id.tv_source_name);
            goods_recyc = (RecyclerView) itemView.findViewById(R.id.goods_recyc);
            tv_ensure_order_goods_mode = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_mode);
            tv_ensure_order_goods_message = (EditText) itemView.findViewById(R.id.tv_ensure_order_goods_message);
            tv_ensure_order_goods_num = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_num);
            tv_ensure_order_goods_Subtotal = (TextView) itemView.findViewById(R.id.tv_ensure_order_goods_Subtotal);
        }
    }



    public interface EditTextGetTextListener{
        void editText(int position,String editText);
    }
    public EditTextGetTextListener editTextGetTextListener;
    public void setEditTextGetTextListener(EditTextGetTextListener editTextGetTextListener){
        this.editTextGetTextListener = editTextGetTextListener;
    }


    //自定义EditText的监听类
    class TextSwitcher implements TextWatcher {

        private MyViewHolder mHolder;
        private int position;

        public TextSwitcher(MyViewHolder mHolder, int position) {
            this.mHolder = mHolder;
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //用户输入完毕后，处理输入数据，回调给主界面处理
            EditTextGetTextListener listener= (EditTextGetTextListener) context;
            if(s!=null){
                listener.editText(position,s.toString());
            }
        }
    }



}
