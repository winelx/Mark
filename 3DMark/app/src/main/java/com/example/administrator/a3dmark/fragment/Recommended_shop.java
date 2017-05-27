package com.example.administrator.a3dmark.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.MyGridView;
import com.example.administrator.a3dmark.adapter.Recommended_shop_Adapter;
import com.example.administrator.a3dmark.bean.RelevanceGoods;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 推荐店铺
 * Created by Administrator on 2017/3/7.
 */

public class Recommended_shop extends Fragment {
    Context context;
    View view;
    @BindView(R.id.gv_recomment_shop)
    MyGridView gvRecommentShop;
    Recommended_shop_Adapter adapter;
    ArrayList<RelevanceGoods> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.recomment_shop, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        Bundle arguments = getArguments();
        if (arguments != null) {
            list = (ArrayList<RelevanceGoods>) arguments.getSerializable("data");
        }
        initView();
        return view;
    }

    private void initView() {

        adapter = new Recommended_shop_Adapter(list, context);
        gvRecommentShop.setAdapter(adapter);
        gvRecommentShop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                startActivity(new Intent(getActivity(), Boutique_Detail.class).putExtra("goods_id",list.get(position).getGoodsId()).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
            }
        });

    }

    public static Recommended_shop newInstance(ArrayList<RelevanceGoods> goods) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", goods);
        Recommended_shop fragment = new Recommended_shop();
        fragment.setArguments(bundle);
        return fragment;
    }
}
