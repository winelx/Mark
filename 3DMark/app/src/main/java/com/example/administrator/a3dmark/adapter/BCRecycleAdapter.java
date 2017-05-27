package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.BCGoods;
import com.example.administrator.a3dmark.bean.BCNewGoods;
import com.example.administrator.a3dmark.bean.BCStore;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.detail_shop.Shop_Activity;
import com.example.administrator.a3dmark.util.ItemDivider;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2015/11/24.
 */
public class BCRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    //轮播图List<bean>对象
    private ArrayList<Banners> Flybanners;
    //商品List<bean>对象
    private List<BCStore> stores;
    private List<BCNewGoods> newGoods;
    private List<BCGoods> goods;


    //type
    public static final int TYPE_1 = 0xff011;
    public static final int TYPE_2 = 0xff022;
    public static final int TYPE_3 = 0xff033;
    public static final int TYPE_MAIN = 0xfffff;

    public BCRecycleAdapter(Context context) {
        this.context = context;
        Flybanners = new ArrayList<>();
        stores = new ArrayList<>();
        newGoods = new ArrayList<>();
        goods = new ArrayList<>();
    }


    public ArrayList<Banners> getFlybanners() {
        return Flybanners;
    }

    public void setFlybanners(ArrayList<Banners> flybanners) {
        Flybanners = flybanners;
    }

    public List<BCStore> getStores() {
        return stores;
    }

    public void setStores(List<BCStore> stores) {
        this.stores = stores;
    }

    public List<BCNewGoods> getNewGoods() {
        return newGoods;
    }

    public void setNewGoods(List<BCNewGoods> newGoods) {
        this.newGoods = newGoods;
    }

    public List<BCGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<BCGoods> goods) {
        this.goods = goods;
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
        switch (viewType){
            case TYPE_1:
                return new MyViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.md_recycle_item_type1, parent, false));
            case TYPE_2:
                return new MyViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.md_recycle_item_type2, parent, false));
            case TYPE_3:
                return new MyViewHolder3(LayoutInflater.from(parent.getContext()).inflate(R.layout.md_recycle_item_type3s, parent, false));
            case TYPE_MAIN:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.md_recycle_item_typemain, parent, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mOnItemClickListener != null) {
                            mOnItemClickListener.onItemClick(view);
                        }
                    }
                });
                return new MyViewHolderMain(view);
            default:
                Log.d("error","viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1){
            bindType1((MyViewHolder1) holder, position);
        }else if (holder instanceof MyViewHolder2){
            bindType2((MyViewHolder2) holder, position);
        }else if (holder instanceof MyViewHolder3){
            bindType3((MyViewHolder3) holder, position);
        }else if (holder instanceof MyViewHolderMain){
            bindTypeMain((MyViewHolderMain) holder, position);
        }
    }


    /**
     * Main RecyclerView getItemCount
     * @return
     */
    @Override
    public int getItemCount() {
        return getGoods().size()+3;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return TYPE_1;
        }else if (position == 1){
            return TYPE_2;
        }else if (position == 2){
            return TYPE_3;
        }else {
            return TYPE_MAIN;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type){
                        case TYPE_1:
                        case TYPE_2:
                        case TYPE_3:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }
    }


    private void bindType1(MyViewHolder1 holder, int position){
        //轮播图URL
        ArrayList<String> ImagesUrl = new ArrayList();

        if (getFlybanners().size() != 0) {
            for (Banners flyBanners : getFlybanners()) {
                ImagesUrl.add(flyBanners.getImg());
            }
            holder.flyBanner.setImagesUrl(ImagesUrl);
        }

        holder.flyBanner.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(context, Boutique_Detail.class);
                intent.putExtra("goods_id", Flybanners.get(position).getId());
                context.startActivity(intent);
            }
        });

    }

    private void bindType2(final MyViewHolder2 holder, int position){

        holder.recyclerView.setLayoutManager(new GridLayoutManager(holder.recyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        holder.recyclerView.addItemDecoration(new ItemDivider().setDividerWith(context,5));
        BCRecycleItemAdapterType2 adapter = new BCRecycleItemAdapterType2(context,getStores());
        holder.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BCRecycleItemAdapterType2.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = holder.recyclerView.getChildAdapterPosition(view);
                String id = getStores().get(position).getId();
                context.startActivity(new Intent(context, Shop_Activity.class).putExtra("bussiness_id", id).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });

    }

    private void bindType3(final MyViewHolder3 holder, int position){

        holder.recyclerView.setLayoutManager(new GridLayoutManager(holder.recyclerView.getContext(), 3, GridLayoutManager.VERTICAL, false));
        holder.recyclerView.addItemDecoration(new ItemDivider().setDividerWith(context,5));
        BCRecycleItemAdapterType3 adapter = new BCRecycleItemAdapterType3(context,getNewGoods());
        holder.recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BCRecycleItemAdapterType3.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = holder.recyclerView.getChildAdapterPosition(view);
                String id = getNewGoods().get(position).getId();
                context.startActivity(new Intent(context, Boutique_Detail.class).putExtra("goods_id", id).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });


    }



    private void bindTypeMain(MyViewHolderMain holder, int position){
        BCGoods bcGoods = null;
        if (position <= getGoods().size()+3) {
            bcGoods = getGoods().get(position-3);
        }
        if (null != bcGoods) {
            Glide.with(context).load(bcGoods.getImg()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.img);
            holder.textpeople.setText(bcGoods.getCount() + "人付款");
            holder.textprice.setText(bcGoods.getPrice());
            holder.name.setText(bcGoods.getName());
            final String id = bcGoods.getId();
            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, Boutique_Detail.class).putExtra("goods_id", id).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                }
            });
        }
    }


    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        public FlyBanner flyBanner;

        public MyViewHolder1(View itemView) {
            super(itemView);
            flyBanner = (FlyBanner) itemView.findViewById(R.id.banner);
        }
    }
    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;

        public MyViewHolder2(final View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        public RecyclerView recyclerView;

        public MyViewHolder3(final View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }

    public class MyViewHolderMain extends RecyclerView.ViewHolder {
        public ImageView img;//商品图片
        public TextView name;//商品简介
        public TextView textprice;//商品价格
        public TextView textpeople;//付款人数
        public TextView buy;//立即购买

        public MyViewHolderMain(final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            textprice = (TextView) itemView.findViewById(R.id.price);
            textpeople = (TextView) itemView.findViewById(R.id.people);
            buy = (Button) itemView.findViewById(R.id.buy);
        }
    }

}
