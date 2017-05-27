package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.bean.ShopGoods;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/8.
 */
public class ShopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    //轮播图List<bean>对象
    private ArrayList<Banners> Flybanners;
    //商品List<bean>对象
    private ArrayList<ShopGoods> goods;


    //type
    public static final int TYPE_1 = 0xff0111;
    public static final int TYPE_MAIN = 0xffffff;

    public ShopAdapter(Context context) {
        this.context = context;
        Flybanners = new ArrayList<>();
        goods = new ArrayList<>();
    }


    public ArrayList<Banners> getFlybanners() {
        return Flybanners;
    }

    public void setFlybanners(ArrayList<Banners> flybanners) {
        Flybanners = flybanners;
    }

    public List<ShopGoods> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<ShopGoods> goods) {
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
                return new MyViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_recycle_item_type, parent, false));
            case TYPE_MAIN:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop_typemain, parent, false);
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
        return getGoods().size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0){
            return TYPE_1;
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
//                Intent intent = new Intent(context, Boutique_Detail.class);
//                intent.putExtra("goods_id", Flybanners.get(position).getId());
//                context.startActivity(intent);
            }
        });

    }


    private void bindTypeMain(MyViewHolderMain holder, int position){
        ShopGoods shopGoods = null;

        if (position <= getGoods().size()) {
            shopGoods = getGoods().get(position-1);
        }

        if (null != shopGoods) {
            Glide.with(context).load(shopGoods.getImages()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.img);
            holder.textprice.setText(shopGoods.getPricenow());
            holder.name.setText(shopGoods.getGoodsname());
            holder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //去试衣间
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

    public class MyViewHolderMain extends RecyclerView.ViewHolder {
        public ImageView img;//商品图片
        public TextView name;//商品简介
        public TextView textprice;//商品价格
        public TextView buy;//立即购买

        public MyViewHolderMain(final View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            textprice = (TextView) itemView.findViewById(R.id.price);
            buy = (Button) itemView.findViewById(R.id.buy);
        }
    }
}
