package com.example.administrator.a3dmark.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.Activity.BeautyskinCareActivity;
import com.example.administrator.a3dmark.Activity.HotcomActivity;
import com.example.administrator.a3dmark.Activity.MobileDigitalActivity;
import com.example.administrator.a3dmark.Activity.RecommActivity;
import com.example.administrator.a3dmark.Activity.SearchActivity;
import com.example.administrator.a3dmark.Activity.SeckillActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.bean.Goods_Main;
import com.example.administrator.a3dmark.bean.List_Column;
import com.example.administrator.a3dmark.bean.Today_Goods;
import com.example.administrator.a3dmark.bean.Today_Store;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.detail_shop.Shop_Activity;
import com.example.administrator.a3dmark.easemob.ui.ConversationActivity;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.ItemDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.recker.flybanner.FlyBanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2015/11/24.
 */
public class RecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;

    //轮播图List<bean>对象
    private ArrayList<Banners> Flybanners;
    //商品List<bean>对象
    private List<Goods_Main> goods_mains;
    private List<Today_Goods> today_goodses;
    private List<Today_Store> today_stores;


    //type
    public static final int TYPE_1 = 0xff01;
    public static final int TYPE_2 = 0xff02;
    public static final int TYPE_3 = 0xff03;
    public static final int TYPE_4 = 0xff04;
    public static final int TYPE_MAIN = 0xffff;

    public RecycleAdapter(Context context) {
        this.context = context;
        goods_mains = new ArrayList<>();
        Flybanners = new ArrayList<>();
        today_goodses = new ArrayList<>();
        today_stores = new ArrayList<>();
    }


    public ArrayList<Banners> getFlybanners() {
        return Flybanners;
    }

    public void setFlybanners(ArrayList<Banners> flybanners) {
        Flybanners = flybanners;
    }

    public List<Goods_Main> getGoods_mains() {
        return goods_mains;
    }

    public void setGoods_mains(List<Goods_Main> goods_mains) {
        this.goods_mains = goods_mains;
    }

    public List<Today_Goods> getToday_goodses() {
        return today_goodses;
    }

    public void setToday_goodses(List<Today_Goods> today_goodses) {
        this.today_goodses = today_goodses;
    }

    public List<Today_Store> getToday_stores() {
        return today_stores;
    }

    public void setToday_stores(List<Today_Store> today_stores) {
        this.today_stores = today_stores;
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
        switch (viewType) {
            case TYPE_1:
                return new MyViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type1, parent, false));
            case TYPE_2:
                return new MyViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type2, parent, false));
            case TYPE_3:
                return new MyViewHolder3(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type3, parent, false));
            case TYPE_4:
                return new MyViewHolder4(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type4, parent, false));
            case TYPE_MAIN:
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_typemain_item, parent, false);
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
                Log.d("error", "viewholder is null");
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder1) {
            bindType1((MyViewHolder1) holder, position);
        } else if (holder instanceof MyViewHolder2) {
            bindType2((MyViewHolder2) holder, position);
        } else if (holder instanceof MyViewHolder3) {
            bindType3((MyViewHolder3) holder, position);
        } else if (holder instanceof MyViewHolder4) {
            bindType4((MyViewHolder4) holder, position);
        } else if (holder instanceof MyViewHolderMain) {
            bindTypeMain((MyViewHolderMain) holder, position);
        }
    }


    /**
     * Main RecyclerView getItemCount
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return getGoods_mains().size() + 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else if (position == 1) {
            return TYPE_2;
        } else if (position == 2) {
            return TYPE_3;
        } else if (position == 3) {
            return TYPE_4;
        } else {
            return TYPE_MAIN;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_1:
                        case TYPE_2:
                        case TYPE_3:
                        case TYPE_4:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }
    }


    private void bindType1(MyViewHolder1 holder, int position) {

        //搜索
        holder.editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SearchActivity.class));
            }
        });

        //消息
        holder.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ConversationActivity.class));
            }
        });


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
                if (Flybanners.get(position).getId() != null) {
                    Intent intent = new Intent(context, Boutique_Detail.class);
                    intent.putExtra("goods_id", Flybanners.get(position).getId());
                    context.startActivity(intent);
                }
            }
        });

    }

    private void bindType2(final MyViewHolder2 holder, int position) {
        holder.item_recyc_type2.setLayoutManager(new GridLayoutManager(holder.item_recyc_type2.getContext(), 2, GridLayoutManager.HORIZONTAL, false));

        final List<List_Column> results = new ArrayList<List_Column>();
        results.add(new List_Column("1", R.mipmap.skill_time, "限时秒杀"));
        results.add(new List_Column("2", R.mipmap.mobile_digital, "农副产品"));
        results.add(new List_Column("3", R.mipmap.popular_goods, "人气宝贝"));
        results.add(new List_Column("4", R.mipmap.beautyskin_care, "美妆护肤"));
        results.add(new List_Column("5", R.mipmap.today_recom, "店铺推荐"));
        results.add(new List_Column("6", R.mipmap.brand_life, "敬请期待!"));//品牌生活
//        holder.item_recyc_type2.addItemDecoration(new ItemDivider().setDividerWith(context,15));//setDividerWith(上下文,间距);
        RecycleItemAdapterType2 adapterType2 = new RecycleItemAdapterType2(context, results);
        holder.item_recyc_type2.setAdapter(adapterType2);
        holder.item_recyc_type2.setNestedScrollingEnabled(false);
        adapterType2.setOnItemClickListener(new RecycleItemAdapterType2.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = holder.item_recyc_type2.getChildAdapterPosition(view);
                String id = results.get(position).getId();
                switch (id) {
                    case "1"://现实秒杀
                        initTimeData();
                        break;

                    case "2"://手机数码-->农副产品
                        context.startActivity(new Intent(context, MobileDigitalActivity.class));
                        break;

                    case "3"://人气宝贝
                        context.startActivity(new Intent(context, HotcomActivity.class));
                        break;

                    case "4"://美妆护肤
                        context.startActivity(new Intent(context, BeautyskinCareActivity.class));
                        break;

                    case "5"://店铺推荐
                        context.startActivity(new Intent(context, RecommActivity.class));
                        break;

                    case "6"://品牌生活//未上
                        //context.startActivity(new Intent(context, BrandLifeActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void bindType3(final MyViewHolder3 holder, int position) {

        holder.item_recyc_type3.setLayoutManager(new GridLayoutManager(holder.item_recyc_type3.getContext(), 3, GridLayoutManager.VERTICAL, false));
        holder.item_recyc_type3.addItemDecoration(new ItemDivider().setDividerWith(context, 2));

        RecycleItemAdapterType3 adapterType3 = new RecycleItemAdapterType3(context);
        adapterType3.setResults(getToday_goodses());
        holder.item_recyc_type3.setAdapter(adapterType3);
        holder.item_recyc_type3.setNestedScrollingEnabled(false);

        adapterType3.setOnItemClickListener(new RecycleItemAdapterType3.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = holder.item_recyc_type3.getChildAdapterPosition(view);
                String id = today_goodses.get(position).getId();
                context.startActivity(new Intent(context, Boutique_Detail.class).putExtra("goods_id", id).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
    }

    private void bindType4(final MyViewHolder4 holder, int position) {
        holder.item_recyc_type4.setLayoutManager(new GridLayoutManager(holder.item_recyc_type4.getContext(), 2, GridLayoutManager.VERTICAL, false));
        holder.item_recyc_type4.addItemDecoration(new ItemDivider().setDividerWith(context, 2));

        RecycleItemAdapterType4 adapterType4 = new RecycleItemAdapterType4(context);
        adapterType4.setResults(getToday_stores());
        holder.item_recyc_type4.setAdapter(adapterType4);
        holder.item_recyc_type4.setNestedScrollingEnabled(false);

        adapterType4.setOnItemClickListener(new RecycleItemAdapterType4.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = holder.item_recyc_type4.getChildAdapterPosition(view);
                String id = today_stores.get(position).getId();
                context.startActivity(new Intent(context, Shop_Activity.class).putExtra("bussiness_id", id).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });
    }

    private void bindTypeMain(MyViewHolderMain holder, int position) {
        Goods_Main goods_main = null;
        if (position <= getGoods_mains().size() + 4) {
            goods_main = getGoods_mains().get(position - 4);
            if (null != goods_main) {
                Glide.with(context).load(goods_main.getImg()).fitCenter().placeholder(R.drawable.icon_empty).into(holder.goods_image);
                holder.textpeople.setText(goods_main.getCount() + "人付款");
                holder.textprice.setText("￥"+goods_main.getPrice());
                holder.textBrief.setText(goods_main.getName());
            }
        }
    }


    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        public FlyBanner flyBanner;
        public EditText editText;
        public ImageView message;

        public MyViewHolder1(View itemView) {
            super(itemView);
            flyBanner = (FlyBanner) itemView.findViewById(R.id.banner);
            editText = (EditText) itemView.findViewById(R.id.editText);
            message = (ImageView) itemView.findViewById(R.id.message);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public RecyclerView item_recyc_type2;

        public MyViewHolder2(final View itemView) {
            super(itemView);
            item_recyc_type2 = (RecyclerView) itemView.findViewById(R.id.item_recyc_type2);
        }
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        public RecyclerView item_recyc_type3;

        public MyViewHolder3(final View itemView) {
            super(itemView);
            item_recyc_type3 = (RecyclerView) itemView.findViewById(R.id.item_recyc_type3);
        }
    }

    public class MyViewHolder4 extends RecyclerView.ViewHolder {
        public RecyclerView item_recyc_type4;

        public MyViewHolder4(final View itemView) {
            super(itemView);
            item_recyc_type4 = (RecyclerView) itemView.findViewById(R.id.item_recyc_type4);
        }
    }

    public class MyViewHolderMain extends RecyclerView.ViewHolder {
        public ImageView goods_image;//商品图片
        public TextView textBrief;//商品简介
        public TextView textprice;//商品价格
        public TextView textpeople;//付款人数
        public TextView textfitting;//马上试衣

        public MyViewHolderMain(final View itemView) {
            super(itemView);
            goods_image = (ImageView) itemView.findViewById(R.id.goods_image);
            textBrief = (TextView) itemView.findViewById(R.id.Brief);
            textprice = (TextView) itemView.findViewById(R.id.price);
            textpeople = (TextView) itemView.findViewById(R.id.people);
            textfitting = (TextView) itemView.findViewById(R.id.tv_fitting);
        }
    }


    /**
     * 限时秒杀相关属性值
     */
    //显示的Tab个数
    private ArrayList<String> mId = new ArrayList<>();
    //显示的Tab名称/与Tab个数相同
    private ArrayList<String> mStartTime = new ArrayList<>();
    //tab状态
    private ArrayList<String> mStatus = new ArrayList<>();

    /**
     * 限时秒杀/接口
     */
    private void initTimeData() {
        mId.clear();
        mStartTime.clear();
        final ProgressDialog mDialog = ProgressDialog.show(context, null, null);
        OkGo.post(Contants.LIMIT_TIME)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("限时秒杀", s);
                        mDialog.dismiss();
                        try {
                            //{"success":[{"startTime":"03/20 11:00","id":"1","status":2}
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                return;
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("success");

                            if (0 == jsonArray.length()) {
                                Toast.makeText(context, "没有活动!", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String startTime = object.getString("startTime");
                                String id = object.getString("id");
                                String status = object.getString("status");
                                mId.add(id);
                                mStatus.add(status);
                                if ("1".equals(status)) {
                                    mStartTime.add(startTime + "#" + "未抢购");
                                } else if ("2".equals(status)) {
                                    mStartTime.add(startTime + "#" + "已抢购");
                                } else if ("3".equals(status)) {
                                    mStartTime.add(startTime + "#" + "抢购中");
                                }
                            }
                            //跳转界面/去秒杀界面
                            context.startActivity(new Intent(context, SeckillActivity.class)
                                    .putStringArrayListExtra("id", mId)
                                    .putStringArrayListExtra("status", mStatus)
                                    .putStringArrayListExtra("startTime", mStartTime));

                        } catch (Exception e) {
                            e.printStackTrace();
                            mDialog.dismiss();

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                        mDialog.dismiss();

                    }

                });
    }

}
