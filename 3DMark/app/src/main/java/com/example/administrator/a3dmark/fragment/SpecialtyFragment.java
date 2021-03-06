package com.example.administrator.a3dmark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.MDRecycleAdapter;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.bean.MDGoods;
import com.example.administrator.a3dmark.bean.MDNewGoods;
import com.example.administrator.a3dmark.bean.MDStore;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.ItemDivider;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by LGY on 2017/5/15.
 * 特产
 */

public class SpecialtyFragment extends BaseFragment {

    private ArrayList<Banners> Flybanners = new ArrayList<>();
    private List<MDStore> stores = new ArrayList<>();
    private List<MDNewGoods> newGoods = new ArrayList<>();
    private List<MDGoods> goods = new ArrayList<>();

    private RecyclerView recyclerView;
    private MDRecycleAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mobile_digital, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.mdRecyclerView);

        //调接口
        intiData();

        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 1, GridLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new ItemDivider().setDividerWith(getActivity(),20));
        adapter = new MDRecycleAdapter(getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MDRecycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                MDGoods mdGoods = null;
                if (position <= goods.size()+3) {
                    mdGoods = goods.get(position-3);
                }
                Intent intent = new Intent(getActivity(), Boutique_Detail.class);
                intent.putExtra("goods_id", mdGoods.getId());
                getActivity().startActivity(intent);
            }
        });

        return view;
    }


    private void intiData() {

        OkGo.post(Contants.AGRICULTURAL)
                .params("typeId",18)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {

                            JSONObject obj = new JSONObject(s);

                            //buyanjing//再次判断
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(getActivity(), obj.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            JSONObject success = obj.getJSONObject("success");
                            //轮播图
                            JSONArray slideShow = success.getJSONArray("slideShow");
                            for (int j = 0; j < slideShow.length(); j++) {
                                Banners banners = new Banners();
                                JSONObject jsObj = slideShow.getJSONObject(j);
                                banners.setImg(jsObj.getString("img"));
                                banners.setId(jsObj.getString("goodsId"));
                                Flybanners.add(banners);
                            }
                            adapter.setFlybanners(Flybanners);
                            //好店
                            JSONArray goodBussiness = success.getJSONArray("goodsStotreToday");
                            for (int j = 0; j < goodBussiness.length(); j++) {
                                MDStore store = new MDStore();
                                JSONObject jsObj = goodBussiness.getJSONObject(j);
                                store.setImg(jsObj.getString("img"));
                                store.setId(jsObj.getString("bussinessId"));
                                stores.add(store);
                            }
                            adapter.setStores(stores);
                            //上新好货
                            JSONArray newFarmGoods = success.getJSONArray("newFarmGoods");
                            for (int j = 0; j < newFarmGoods.length(); j++) {
                                MDNewGoods newGood = new MDNewGoods();
                                JSONObject jsObj = newFarmGoods.getJSONObject(j);
                                newGood.setImg(jsObj.getString("img"));
                                newGood.setId(jsObj.getString("goodsId"));
                                newGoods.add(newGood);
                            }
                            adapter.setNewGoods(newGoods);
                            //热门好货
                            JSONArray hotFruit = success.getJSONArray("hotFruit");
                            for (int j = 0; j < hotFruit.length(); j++) {
                                MDGoods good = new MDGoods();
                                JSONObject jsObj = hotFruit.getJSONObject(j);
                                good.setImg(jsObj.getString("img"));
                                good.setCount(jsObj.getString("count"));
                                good.setId(jsObj.getString("goodsId"));
                                good.setName(jsObj.getString("goodsName"));
                                good.setPrice(jsObj.getString("priceModeNew"));
                                goods.add(good);
                            }
                            adapter.setGoods(goods);
                            adapter.notifyDataSetChanged();

                        } catch (Exception e) {
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

}
