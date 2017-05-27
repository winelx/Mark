package com.example.administrator.a3dmark.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.Comment_submit;
import com.example.administrator.a3dmark.Activity.OrderDetail;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Pending_Evaluation_adapter;
import com.example.administrator.a3dmark.adapter.Receiving_Goods_Adapter;
import com.example.administrator.a3dmark.bean.Goods_order;
import com.example.administrator.a3dmark.bean.Logistics;
import com.example.administrator.a3dmark.bean.MyOrderBean;
import com.example.administrator.a3dmark.bean.OrderBean;
import com.example.administrator.a3dmark.child_pakage.Logistic_message;
import com.example.administrator.a3dmark.util.Contants;
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
 * 待评价
 * Created by Administrator on 2017/2/27.
 */
public class Pending_evaluation extends Fragment {
    @BindView(R.id.lv_pending_evaluation)
    ListView lvPendingEvaluation;
    Pending_Evaluation_adapter pendingadapter;
    List<MyOrderBean> list = new ArrayList<MyOrderBean>();
    Context context;
    private String userid;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.pending_evaluation, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        userid = (String) SharedUtil.getParam(context, "userid", "");
        initgetData();
        return view;
    }

    private void initgetData() {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取信息", "获取中...");
        OkGo.post(Contants.NOEVALUATION)
                .params("userId", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            JSONObject obj = json.getJSONObject("success");
                            boolean is_null = obj.isNull("store");
                            if (!is_null) {
                                JSONArray jsarray = obj.getJSONArray("store");
                                MyOrderBean myorderbean;
                                Goods_order goods_order;
                                list.clear();
                                for (int i = 0; i < jsarray.length(); i++) {
                                    JSONObject jsobj = jsarray.getJSONObject(i);
                                    myorderbean = new MyOrderBean();
                                    myorderbean.setBuinessName(jsobj.getString("buinessName"));
                                    myorderbean.setBussinessId(jsobj.getString("bussinessId"));
                                    myorderbean.setLogo(jsobj.getString("logo"));
                                    myorderbean.setAllNum(jsobj.getString("allNum"));
                                    myorderbean.setAllTotalPrice(jsobj.getString("allTotalPrice"));
                                    myorderbean.setAllMail(jsobj.getString("allMail"));
                                    myorderbean.setOrderId(jsobj.getString("orderId"));
                                    JSONArray js_array = jsobj.getJSONArray("goods");
                                    List<Goods_order> list_goods = new ArrayList<Goods_order>();
                                    for (int j = 0; j < js_array.length(); j++) {
                                        JSONObject js_obj = js_array.getJSONObject(j);
                                        goods_order = new Goods_order();
                                        goods_order.setColor(js_obj.getString("color"));
                                        goods_order.setGoodsName(js_obj.getString("goodsName"));
                                        goods_order.setImg(js_obj.getString("img"));
                                        goods_order.setMail(js_obj.getString("mail"));
                                        goods_order.setNum(js_obj.getString("num"));
                                        goods_order.setPriceMode(js_obj.getString("priceMode"));
                                        goods_order.setPriceModeNew(js_obj.getString("priceModeNew"));
                                        goods_order.setSize(js_obj.getString("size"));
                                        goods_order.setState(js_obj.getString("state"));
                                        goods_order.setTotalPrice(js_obj.getString("totalPrice"));
                                        list_goods.add(goods_order);
                                        myorderbean.setGoodsOrders(list_goods);
                                    }
                                    list.add(myorderbean);
                                }
                                pendingadapter = new Pending_Evaluation_adapter(list, context);
                                lvPendingEvaluation.setAdapter(pendingadapter);
                                pendingadapter.notifyDataSetChanged();
//                                initListener();
//                                adapter = new Receiving_Goods_Adapter(list, context);
//                                lvReceivingGoods.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "当前没有订单", Toast.LENGTH_SHORT).show();
                            }
                            //buyanjing//再次判断
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(context, json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


//    private void initListener() {
//        pendingadapter.setlistener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent;
//                position = (int) v.getTag();
//                switch (v.getId()) {
//                    //提交评论
//                    case R.id.tv_order_payment:
//                        intent = new Intent(context, Comment_submit.class);
//                        intent.putExtra("goodsimg", list.get(position).getImg());
//                        intent.putExtra("shopimg", list.get(position).getLogo());
//                        intent.putExtra("orderId", list.get(position).getOrderID());
//                        intent.putExtra("goodsId", list.get(position).getGoodsId());
//                        intent.putExtra("bussinessId", list.get(position).getBussinessId());
//                        startActivity(intent);
//                        break;
//                    //订单详情
//                    case R.id.rl_order:
//                        intent = new Intent(context, OrderDetail.class);
//                        intent.putExtra("orderid", list.get(position).getOrderID());
//                        intent.putExtra("state", list.get(position).getState());
//                        startActivity(intent);
//                        break;
//                    //查看物流
//                    case R.id.tv_order_remove:
//                        intent = new Intent(context, Logistic_message.class);
//                        intent.putExtra("orderid", list.get(position).getOrderID());
//                        intent.putExtra("img", list.get(position).getImg());
//                        startActivity(intent);
//                        break;
//
//                }
//            }
//        });
//    }


}
