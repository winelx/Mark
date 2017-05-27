package com.example.administrator.a3dmark.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.OrderDetail;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Wait_Deliver_Adapter;
import com.example.administrator.a3dmark.bean.Goods_order;
import com.example.administrator.a3dmark.bean.MyOrderBean;
import com.example.administrator.a3dmark.bean.OrderBean;
import com.example.administrator.a3dmark.test.MyorderAdapter;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.recker.flybanner.FlyBanner;

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
 * 待发货
 * Created by Administrator on 2017/3/2.
 */
public class Wait_Deliver_fragment extends Fragment {
    Context context;
    Wait_Deliver_Adapter adapter;
    @BindView(R.id.lv_receiving_goods)
    ListView lvDeliver_fragment;
    private String userid;
    private int position;
    private PopupWindow popupWindow;
    List<MyOrderBean> list = new ArrayList<MyOrderBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.receiving_goods, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        userid = (String) SharedUtil.getParam(context, "userid", "");
        adapter = new Wait_Deliver_Adapter(list, context);
        initgetData();
        lvDeliver_fragment.setAdapter(adapter);
        adapter.setRefund(new Wait_Deliver_Adapter.Refund() {
            @Override
            public void setInfo(int position) {
                final String orderId = list.get(position).getOrderId();
                final EditText editText = new EditText(context);
                editText.setBackgroundResource(R.drawable.btn_return2);

                new AlertDialog.Builder(context).setTitle("请输入退款原因")
                        .setIcon(android.R.drawable.ic_dialog_info).setView(editText)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String message = editText.getText().toString().trim();
                                goRefund(orderId,message);
                            }
                        })
                        .setNegativeButton("取消", null).show();
            }
        });
        return view;
    }

    private void initgetData() {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取信息", "获取中...");
        OkGo.post(Contants.ORDER_STATE)
                .params("userId", SharedUtil.getParam(context, "userid", "").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            JSONObject obj = json.getJSONObject("success");
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
                            adapter.notifyDataSetChanged();
//                            adapter = new MyorderAdapter(list, context);
//                            lvPendingEvaluation.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                            initListener();
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


    private void goRefund(String orderId,String message) {

        Log.i("message: ",message);

        OkGo.post(Contants.REFUNDAPPLICATION)
                .params("orderId",orderId)
                .params("message",message)
                .params("picture","")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(context, json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //成功
                            initgetData();//回调接口更新界面
                            Toast.makeText(context, json.getString("success"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });



    }


}