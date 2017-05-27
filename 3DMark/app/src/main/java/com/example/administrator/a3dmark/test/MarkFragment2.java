package com.example.administrator.a3dmark.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.Activity.SearchActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Child_Adapter;
import com.example.administrator.a3dmark.adapter.Child_GridAdapter;
import com.example.administrator.a3dmark.adapter.Mark_GridAdapter;
import com.example.administrator.a3dmark.adapter.Mark_adapter;
import com.example.administrator.a3dmark.adapter.MyGridView;
import com.example.administrator.a3dmark.bean.Child;
import com.example.administrator.a3dmark.bean.Mark;
import com.example.administrator.a3dmark.bean.Mark_Recommend;
import com.example.administrator.a3dmark.bean.TypesGoods;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.util.Contants;
import com.loopj.android.image.SmartImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MarkFragment2 extends Fragment {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.top_bar)
    RelativeLayout topBar;
    @BindView(R.id.search)
    TextView editText;
    @BindView(R.id.tv_devide_recommend)
    TextView tvDevideRecommend;
    @BindView(R.id.lv_devide)
    ListView lvDevide;
    @BindView(R.id.lv_devide_child)
    ListView lvDevideChild;
    @BindView(R.id.recommend1)
    SmartImageView recommend1;
    @BindView(R.id.recommend2)
    SmartImageView recommend2;
    @BindView(R.id.gv_devide_recommend)
    MyGridView gvDevideRecommend;
    @BindView(R.id.ll_devide_child)
    LinearLayout llDevideChild;
    @BindView(R.id.gv_devide_child)
    GridView gvDevideChild;
    @BindView(R.id.scrollView)
    ScrollView markScrollView;
    @BindView(R.id.scollview_child)
    ScrollView childScrollView;

    private Context context;

    //一级item
    private Mark_adapter adapter;
    //二级item
    private Child_Adapter child_listAdapter;
    //推荐grid
    private Mark_GridAdapter mMark_gridAdapter;
    //其他商品grid
    private Child_GridAdapter mChild_gridAdapter;

    private List<Mark> markList = new ArrayList<>();
    private List<Mark_Recommend> recommed_grid = new ArrayList<>();
    private List<TypesGoods> typesGoods_grid = new ArrayList<>();
    private List<Child> child = null;

    // 匹配组元素的子元素数据列表
    private Map<String, List<Child>> children = new HashMap<String, List<Child>>();

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.devide_demo, container, false);
        ButterKnife.bind(this, view);
        tvTitleText.setText("商城");
        imageTitleBack.setVisibility(View.GONE);
        tvTitleVice.setVisibility(View.GONE);
        context = getContext();
        initData();
        initRecommentData();

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,SearchActivity.class));
            }
        });


        /**
         * 推荐商品item点击事件
         */
        gvDevideRecommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String goodsId = recommed_grid.get(position).getGoodsId();
                startActivity(new Intent(getActivity(), Boutique_Detail.class).putExtra("goods_id",goodsId));
            }
        });

        /**
         * 其他商品item点击事件
         */
        gvDevideChild.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String goodsId = typesGoods_grid.get(position).getGoodsId();
                startActivity(new Intent(getActivity(), Boutique_Detail.class).putExtra("goods_id",goodsId));
            }
        });

        return view;
    }


    /**
     * 一级分类item点击事件
     */
    private void initListener() {
        adapter.setListener(new Mark_adapter.MarkAdapterOnClickListener() {
            @Override
            public void ClickListener(int position) {

                Mark mark = markList.get(position);
                child = children.get(mark.getTypeName());

                lvDevideChild.setVisibility(View.VISIBLE);//显示二级分类界面
                child_listAdapter = new Child_Adapter(child, context);
                lvDevideChild.setAdapter(child_listAdapter);
                child_listAdapter.notifyDataSetChanged();
                initChildListenr();
            }
        });
    }

    /**
     * 二级分类item点击事件
     */
    private void initChildListenr() {
        child_listAdapter.setListener(new Child_Adapter.ChildAdapterOnClickListener() {
            @Override
            public void OnClick(int position) {
                markScrollView.setVisibility(View.GONE);//隐藏推荐商品界面ScrollView内容
                childScrollView.setVisibility(View.VISIBLE);//显示其他商品界面ScrollView内容
                String id = child.get(position).getChliderId();
                initShowTypeData(id);
            }
        });

    }

    /**
     *一级推荐点击事件
     */
    @OnClick(R.id.tv_devide_recommend)
    public void onClick() {
        lvDevideChild.setVisibility(View.GONE);//隐藏二级分类界面
        markScrollView.setVisibility(View.VISIBLE);//显示推荐商品界面ScrollView内容
        childScrollView.setVisibility(View.GONE);//隐藏其他商品界面ScrollView内容
        initRecommentData();
    }

    private void initRecommentData() {//推荐商品
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.MARK_RECOMMEND)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code----------", s);
                        mDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(s);
                            JSONObject js_obj = obj.getJSONObject("success");
                            JSONArray js_array = js_obj.getJSONArray("goodsRecord");
                            recommed_grid.clear();
                            for (int i = 0; i < js_array.length(); i++) {
                                JSONObject jsobj = js_array.getJSONObject(i);
                                Mark_Recommend mark_recommend = new Mark_Recommend();
                                mark_recommend.setGoodsId(jsobj.getString("goodsId"));
                                mark_recommend.setGoodsImg(jsobj.getString("goodsImg"));
                                mark_recommend.setGoodsName(jsobj.getString("goodsName"));
                                recommed_grid.add(mark_recommend);
                            }
                            mMark_gridAdapter = new Mark_GridAdapter(recommed_grid, getActivity());
                            gvDevideRecommend.setAdapter(mMark_gridAdapter);
                            JSONArray array = js_obj.getJSONArray("picRecord");
                            if(array.length() == 0){
                                recommend1.setVisibility(View.GONE);
                                recommend2.setVisibility(View.GONE);
                                return;
                            }
                            Glide.with(context).load(array.getJSONObject(0).getString("img")).fitCenter().placeholder(R.drawable.icon_empty).into(recommend1);
                            Glide.with(context).load(array.getJSONObject(1).getString("img")).fitCenter().placeholder(R.drawable.icon_empty).into(recommend2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    private void initData() {//其他商品与推荐商品同时获取
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.MARK_GOODS)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("initData----------", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            JSONArray array = jsobj.getJSONArray("success");
                            markList.clear();
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                Mark mark = new Mark();
                                mark.setTypeName(obj.getString("typeName"));
                                markList.add(mark);

                                ArrayList<Child> child = new ArrayList<>();
                                JSONArray jsarrray = obj.getJSONArray("children");
                                for (int j = 0; j < jsarrray.length(); j++) {
                                    JSONObject js_obj = jsarrray.getJSONObject(j);
                                    Child child_goods = new Child();
                                    child_goods.setChilderName(js_obj.getString("childerName"));
                                    child_goods.setChliderId(js_obj.getString("chliderId"));
                                    child.add(child_goods);
                                }
                                children.put(markList.get(i).getTypeName(),child);
                            }
                            adapter = new Mark_adapter(context, markList);
                            lvDevide.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            setListViewHeightBasedOnChildren(lvDevide);
                            initListener();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }



    /**
     * 商城其他商品
     */
    private void initShowTypeData(String id) {
        Log.d("id---------", id + "");
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.FIGUREINSTOREONE_SHOW)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("initData----------", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("success");
                            typesGoods_grid.clear();
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsObj = jsonArray.getJSONObject(j);
                                TypesGoods goods = new TypesGoods();
                                goods.setGoodsId(jsObj.getString("goodsId"));
                                goods.setGoodsType(jsObj.getString("goodsType"));
                                goods.setGoodsImg(jsObj.getString("img"));
                                goods.setGoodsName(jsObj.getString("goodsName"));
                                typesGoods_grid.add(goods);
                            }
                            mChild_gridAdapter = new Child_GridAdapter(typesGoods_grid, getActivity());
                            gvDevideChild.setAdapter(mChild_gridAdapter);
                            mChild_gridAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }


    /**
     *
     * @param listView
     * listView item 高度获取
     */
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
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


}
