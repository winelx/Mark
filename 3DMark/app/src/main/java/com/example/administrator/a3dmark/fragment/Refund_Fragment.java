package com.example.administrator.a3dmark.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.OrderDetail;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Pending_payment_adapter;
import com.example.administrator.a3dmark.adapter.Refund_Adapter;
import com.example.administrator.a3dmark.bean.OrderBean;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
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
import okhttp3.Call;
import okhttp3.Response;

/**
 * 退款
 * Created by Administrator on 2017/3/2.
 */
public class Refund_Fragment extends BaseFragment {
    @BindView(R.id.lv_refund)
    RecyclerView lvRefund;
    Refund_Adapter adapter;
    private List<OrderBean> stores = new ArrayList<OrderBean>();// 组元素数据列表
    private Map<String, List<OrderBean>> goodss = new HashMap<String, List<OrderBean>>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.refund_fragment, container, false);
        ButterKnife.bind(this, view);
        lvRefund.setLayoutManager(new GridLayoutManager(lvRefund.getContext(), 1, GridLayoutManager.VERTICAL, false));
        adapter = new Refund_Adapter(getActivity());
        if (!TextUtils.isEmpty(isLogin())){
            initData(isLogin());
        }
        return view;
    }

    private void initData(String userId) {
        final ProgressDialog mDialog = ProgressDialog.show(getActivity(), "获取信息", "获取中...");
        OkGo.post(Contants.REFUND)
                .params("userId", userId)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);

                            if (!jsonObject.isNull("success")) {
                                JSONObject success = jsonObject.getJSONObject("success");
                                JSONArray jsonArray = success.getJSONArray("store");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    OrderBean orderBean = new OrderBean();
                                    orderBean.setBuinessname(object.getString("buinessName"));
                                    orderBean.setBussinessId(object.getString("bussinessId"));
                                    orderBean.setLogo(object.getString("logo"));
                                    orderBean.setOrderID(object.getString("orderId"));
                                    orderBean.setState(object.getString("state"));
                                    orderBean.setTotalprice(object.getString("allTotalPrice"));
                                    orderBean.setRefund_money(object.getString("allTotalPrice"));
                                    orderBean.setAllMail(object.getString("allMail"));
                                    orderBean.setAllNum(object.getString("allNum"));
                                    stores.add(orderBean);

                                    List<OrderBean> goods = new ArrayList<OrderBean>();
                                    JSONArray goodsArray = object.getJSONArray("goods");
                                    for(int j = 0; j < goodsArray.length(); j++){
                                        JSONObject goodsinfo = goodsArray.getJSONObject(j);
                                        OrderBean orderBean2 = new OrderBean();
                                        orderBean2.setImg(goodsinfo.getString("img"));
                                        orderBean2.setNum(goodsinfo.getString("num"));
                                        orderBean2.setGoodsname(goodsinfo.getString("goodsName"));
                                        goods.add(orderBean2);
                                    }
                                    goodss.put(stores.get(i).getBussinessId(), goods);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
                                    adapter.setGoodss(goodss);
                                }
                                adapter.setStores(stores);
                                lvRefund.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        goodss.clear();
        stores.clear();
    }
}