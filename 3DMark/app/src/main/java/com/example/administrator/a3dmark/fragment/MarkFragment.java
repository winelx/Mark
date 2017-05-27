package com.example.administrator.a3dmark.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.Activity.SearchActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Mark_GridAdapter;
import com.example.administrator.a3dmark.adapter.MyGridView;
import com.example.administrator.a3dmark.bean.Main_Head;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.Utils;
import com.loopj.android.image.SmartImageView;
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

public class MarkFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.search)
    EditText editText;


    private TextView recommend, coat, skirt, trousers, shoes, bag, accessories;
    private SmartImageView recommend1, recommend2;
    private MyGridView mGridView;
    private ScrollView mScrollView;
    private Mark_GridAdapter mMark_gridAdapter;
    public TextView[] mTabs;
    //private Fragment[] mFragments;
    private int mIndex;
    // 当前fragment的index
    private int mCurrentTabIndex = 0;
    //数据资源路径
    List<Main_Head> mUrl_list = new ArrayList<Main_Head>();
    private Context context;
    private int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mark_fragment, container, false);
        ButterKnife.bind(this, view);
        context = getContext();
        initView(view);
        return view;
    }

    @Override
    public void onStart() {//推荐商品,在此调用避免没网络下,打开网络后能请求到数据
        super.onStart();
        if (Utils.isNetWorkAvailable(context) == false) {
            return;
        }
        initRecommenddata();
        initGradData();
    }

    public void initView(View view) {
        tvTitleText.setText("商城");
        imageTitleBack.setVisibility(View.GONE);
        tvTitleVice.setVisibility(View.GONE);

        recommend = (TextView) view.findViewById(R.id.recommend);
        coat = (TextView) view.findViewById(R.id.coat);
        skirt = (TextView) view.findViewById(R.id.skirt);
        trousers = (TextView) view.findViewById(R.id.trousers);
        shoes = (TextView) view.findViewById(R.id.shoes);
        bag = (TextView) view.findViewById(R.id.bag);
        accessories = (TextView) view.findViewById(R.id.accessories);
        mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        recommend1 = (SmartImageView) view.findViewById(R.id.recommend1);
        recommend2 = (SmartImageView) view.findViewById(R.id.recommend2);

        mGridView = (MyGridView) view.findViewById(R.id.mGridView);


        if (Utils.isNetWorkAvailable(context) != false) {
            initRecommenddata();
            initGradData();
        }


        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(view.getContext(), "你点击了~" + position + "~项", Toast.LENGTH_SHORT).show();
                String ids = mUrl_list.get(position).getZ_goods_type_id();
                Intent intent = new Intent(context, Boutique_Detail.class);
                intent.putExtra("goods_id", ids);
                startActivity(intent);
            }
        });


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Log.e("abc", "et1获取到焦点了。。。。。。");
                    context.startActivity(new Intent(context, SearchActivity.class));
                } else {
                }
            }
        });

//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
//                    if (!TextUtils.isEmpty(editText.getText())) {//非空
//                        //请求服务器
//                        Toast.makeText(getActivity(), "正在搜索。。。", Toast.LENGTH_SHORT).show();
//                        return true;
//                    }
//                    Toast.makeText(getActivity(), "请输入要搜索的商品", Toast.LENGTH_SHORT).show();
//                }
//                return false;
//            }
//        });


        mTabs = new TextView[7];
        mTabs[0] = (TextView) view.findViewById(R.id.recommend);
        mTabs[1] = (TextView) view.findViewById(R.id.coat);
        mTabs[2] = (TextView) view.findViewById(R.id.skirt);
        mTabs[3] = (TextView) view.findViewById(R.id.trousers);
        mTabs[4] = (TextView) view.findViewById(R.id.shoes);
        mTabs[5] = (TextView) view.findViewById(R.id.bag);
        mTabs[6] = (TextView) view.findViewById(R.id.accessories);
        mTabs[0].setOnClickListener(this);
        mTabs[1].setOnClickListener(this);
        mTabs[2].setOnClickListener(this);
        mTabs[3].setOnClickListener(this);
        mTabs[4].setOnClickListener(this);
        mTabs[5].setOnClickListener(this);
        mTabs[6].setOnClickListener(this);
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);

    }


    /**
     * 首次默认为推荐商品
     */
    private void initGradData() {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.SHOW_GOODS_INSTORE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code----------", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("success");
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsObj = jsonArray.getJSONObject(j);
                                Main_Head mainhead = new Main_Head();
                                mainhead.setImg(jsObj.getString("img"));
                                mainhead.setGoods_id(jsObj.getString("id"));
                                mainhead.setName(jsObj.optString("name"));
                                mUrl_list.add(mainhead);
                            }
//                            mMark_gridAdapter = new Mark_GridAdapter(mUrl_list, getActivity());
                            mGridView.setAdapter(mMark_gridAdapter);
//                            mMark_gridAdapter.notifyDataSetChanged();
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
    private void initData() {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.FIGUREINSTOREONE_SHOW)
                .params("id", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("initData----------", s);
                        Log.d("id---------", id + "");
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("success");
                            for (int j = 0; j < jsonArray.length(); j++) {
                                JSONObject jsObj = jsonArray.getJSONObject(j);
                                Main_Head mainhead = new Main_Head();
                                mainhead.setImg(jsObj.getString("img"));
                                mainhead.setZ_goods_type_id("z_goods_type_id");
                                mainhead.setName(jsObj.optString("name"));
                                mUrl_list.add(mainhead);
                            }
//                            mMark_gridAdapter = new Mark_GridAdapter(mUrl_list, getActivity());
                            mGridView.setAdapter(mMark_gridAdapter);
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
     * 推荐商品第一行
     */
    private void initRecommenddata() {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.FIGUREINSTOREONE)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code----------", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(context, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            JSONArray jsonArray = jsonObject.getJSONArray("success");
                            mUrl_list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObj = jsonArray.getJSONObject(i);
                                Glide.with(context).load(jsonArray.getJSONObject(1).getString("img")).centerCrop()
                                        .placeholder(R.drawable.icon_empty).into(recommend1);
                                Glide.with(context).load(jsonArray.getJSONObject(0).getString("img")).centerCrop()
                                        .placeholder(R.drawable.icon_empty).into(recommend2);
                            }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recommend://推荐
                mIndex = 0;
                mUrl_list.clear();//先清理
                initGradData();
                refreshFocusUp(mMark_gridAdapter, mScrollView);
                break;
            case R.id.coat://上衣
                mIndex = 1;
                mUrl_list.clear();//先清理
                id = 0;
                initData();
                refreshFocusUp(mMark_gridAdapter, mScrollView);
                break;
            case R.id.skirt://裙子
                mIndex = 2;
                mUrl_list.clear();//先清理
                id = 3;
                initData();
                refreshFocusUp(mMark_gridAdapter, mScrollView);
                break;
            case R.id.trousers://裤子
                mIndex = 3;
                mUrl_list.clear();//先清理
                id = 1;
                initData();
                refreshFocusUp(mMark_gridAdapter, mScrollView);
                break;
            case R.id.shoes://鞋子
                mIndex = 4;
                mUrl_list.clear();//先清理
                id = 2;
                initData();
                refreshFocusUp(mMark_gridAdapter, mScrollView);
                break;
            case R.id.bag://包包
                mIndex = 5;
                mUrl_list.clear();//先清理
                id = 4;
                initData();
                refreshFocusUp(mMark_gridAdapter, mScrollView);
                break;
            case R.id.accessories://配饰
                mIndex = 6;
                mUrl_list.clear();//先清理
                id = 5;
                initData();
                refreshFocusUp(mMark_gridAdapter, mScrollView);
                break;
        }


        if (mIndex != 0) {//不是推荐列隐藏
            recommend1.setVisibility(View.GONE);
            recommend2.setVisibility(View.GONE);
        } else {
            recommend1.setVisibility(View.VISIBLE);
            recommend2.setVisibility(View.VISIBLE);
        }

        mTabs[mCurrentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[mIndex].setSelected(true);
        mCurrentTabIndex = mIndex;
    }

}
