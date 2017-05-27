package com.example.administrator.a3dmark.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.RecycleAdapter;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.bean.Goods_Main;
import com.example.administrator.a3dmark.bean.Today_Goods;
import com.example.administrator.a3dmark.bean.Today_Store;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.ItemDivider;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/2/22.
 */

public class HomePagerFragment extends Fragment {


    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;


    RecyclerView recyclerView;
    RecycleAdapter adapter;

    //轮播图List<bean>对象
    private ArrayList<Banners> Flybanners = new ArrayList();
    //商品List<bean>对象
    private ArrayList<Goods_Main> Goods_Mains = new ArrayList<>();
    private List<Today_Goods> today_goodses = new ArrayList<>();
    private List<Today_Store> today_stores = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_pager, container, false);

        ButterKnife.bind(this, view);
        imageTitleBack.setVisibility(View.GONE);
        tvTitleVice.setVisibility(View.GONE);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2,
          GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ItemDivider().setDividerWith(getActivity(), 20));
        adapter = new RecycleAdapter(getActivity());
        initCarouseldata();
        initIsGoodsAndBussiness();
        initGoodsData(1);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new RecycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                Goods_Main goods_main = null;
                if (position <= Goods_Mains.size() + 4) {
                    goods_main = Goods_Mains.get(position - 4);
                }
                if (null != goods_main) {
                    String ids = goods_main.getId();
                    String goodsId = (String) SharedUtil.getParam(getActivity(), "userid", "");
                    Intent intent = new Intent(getActivity(), Boutique_Detail.class);
                    intent.putExtra("goods_id", ids);
                    intent.putExtra("userid", goodsId);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

    /*轮播图的网络请求*/
    private void initCarouseldata() {
        OkGo.post(Contants.MAIN_HEAD)
                .params("row", 10)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(getActivity(), obj.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            JSONArray jsArray = obj.getJSONArray("success");
                            for (int j = 0; j < jsArray.length(); j++) {
                                Banners flyBanners = new Banners();
                                JSONObject jsObj = jsArray.getJSONObject(j);
                                flyBanners.setImg(jsObj.getString("goodsCover"));
                                if (jsObj.has("goodsId"))
                                    flyBanners.setId(jsObj.getString("goodsId"));
                                Flybanners.add(flyBanners);
                            }
                            adapter.setFlybanners(Flybanners);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();

                    }

                });
    }

    /*好货,好店*/
    private void initIsGoodsAndBussiness() {
        OkGo.post(Contants.IS_GOODS_BUSSINESS)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(getActivity(), obj.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            JSONObject success = obj.getJSONObject("success");
                            //好店
                            JSONArray goodBussiness = success.getJSONArray("goodBussiness");
                            for (int j = 0; j < goodBussiness.length(); j++) {
                                Today_Store today_store = new Today_Store();
                                JSONObject jsObj = goodBussiness.getJSONObject(j);
                                today_store.setImgUrl(jsObj.getString("img"));
                                today_store.setId(jsObj.getString("bussinessId"));
                                today_stores.add(today_store);
                            }
                            adapter.setToday_stores(today_stores);
                            //好货
                            JSONArray goodGoods = success.getJSONArray("goodGoods");
                            for (int j = 0; j < goodGoods.length(); j++) {
                                Today_Goods today_goods = new Today_Goods();
                                JSONObject jsObj = goodGoods.getJSONObject(j);
                                today_goods.setImgUrl(jsObj.getString("img"));
                                today_goods.setId(jsObj.getString("goodsId"));
                                today_goodses.add(today_goods);
                            }
                            adapter.setToday_goodses(today_goodses);
                            adapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();

                    }

                });
    }


    /*商品网络请求*/
    private void initGoodsData(int i) {
        final ProgressDialog mDialog = ProgressDialog.show(getActivity(), "获取数据", "获取数据中");
        OkGo.post(Contants.HEAD_GOODS)
                .tag(this)
                .params("page", i)
                .params("row", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();

                        try {
                            JSONObject object = new JSONObject(s);

                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(getActivity(), object.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray jsonArray = object.getJSONArray("success");//成功标识
                            Goods_Main goods_main = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                goods_main = new Goods_Main();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                goods_main.setImg(jsonObject.getString("img"));
                                goods_main.setName(jsonObject.getString("name"));
                                goods_main.setId(jsonObject.getString("id"));
                                goods_main.setCount(jsonObject.getString("count"));
                                goods_main.setPrice(jsonObject.getString("price"));
                                Goods_Mains.add(goods_main);
                            }

                            adapter.setGoods_mains(Goods_Mains);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
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

