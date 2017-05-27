package com.example.administrator.a3dmark.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.alipay.PayResult;
import com.example.administrator.a3dmark.bean.Goods_order;
import com.example.administrator.a3dmark.bean.MyOrderBean;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheManager;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 我的订单/未付款
 * Created by Administrator on 2017/5/12.
 */

public class MyOrderChange extends Fragment {
    @BindView(R.id.lv_pending_evaluation)
    ListView lvPendingEvaluation;
    List<MyOrderBean> list = new ArrayList<MyOrderBean>();

    MyorderAdapter adapter;
    private Context context;
    private int position;
    private PopupWindow popupWindow;
    private ProgressDialog mDialog;


    private static final int SDK_PAY_FLAG = 1;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {

                        try {
                            String status = (String) CacheManager.INSTANCE.get("outTradeNo").getData();
                            JSONObject json = new JSONObject(status);
                            JSONObject successObj =  json.getJSONObject("success");
                            String id = successObj.getString("outTradeNo");
                            sendService(id);

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.pending_evaluation, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        adapter = new MyorderAdapter(list, context);
        initgetData();
        lvPendingEvaluation.setAdapter(adapter);
        adapter.setOrderId(new MyorderAdapter.OrderId() {
            @Override
            public void setId(String id) {
                conmitOrder(id);
            }
        });
        return view;
    }

    private void initgetData() {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取信息", "获取中...");
        OkGo.post(Contants.NOT_PAYING)
                .params("userId", SharedUtil.getParam(context, "userid", "").toString())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(context, json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
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
                            initListener();
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

    private void initListener() {
        adapter.setlistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = (int) v.getTag();
                switch (v.getId()) {
                    case R.id.tv_order_remove:
                        initpop();
                        break;
                }
            }
        });
    }

    private void initpop() {

        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(context).inflate(
                R.layout.pop_window, null);
//        @BindView(R.id.btn_setup_cache_cancel)
        TextView btnSetupCacheCancel = (TextView) contentView.findViewById(R.id.btn_setup_cache_cancel);
//        @BindView(R.id.btn_setup_cache_ensure)
        TextView btnSetupCacheEnsure = (TextView) contentView.findViewById(R.id.btn_setup_cache_ensure);
        TextView tv_pop_detail = (TextView) contentView.findViewById(R.id.tv_pop_detail);
        tv_pop_detail.setText("是否取消订单");
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.7f;
        getActivity().getWindow().setAttributes(params);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
//设置点击popupwindow的点击外部消失
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        LinearLayout layout = (LinearLayout) contentView.findViewById(R.id.ll_pop);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (popupWindow != null) {
                    setBackgroundAlpha(getActivity(), 1f);
                }
            }
        });
        // 设置好参数之后再show

        int height = layout.getHeight();
        int width = layout.getWidth();
//        popupWindow.showAsDropDown(contentView);
        popupWindow.showAtLocation(contentView, Gravity.CENTER, contentView.getWidth() / 2 - width / 2, contentView.getHeight() / 2 - height / 2);
        btnSetupCacheCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        btnSetupCacheEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initremove();
            }
        });
    }

    private void initremove() {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.REMOVE_ORDER)
                .params("orderId", list.get(position).getOrderId())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("remove========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            if (!jsobj.isNull("success")) {
                                Toast.makeText(context, jsobj.getString("success"), Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
                                initgetData();
                                return;
                            }
                            if (!jsobj.isNull("error")) {
                                Toast.makeText(context, jsobj.getString("error"), Toast.LENGTH_SHORT).show();
                                popupWindow.dismiss();
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
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    //设置popupwindow的背景半透明；
    public void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }


    private void conmitOrder(String id){
        mDialog = ProgressDialog.show(getActivity(),"","");
        OkGo.post(Contants.ORDERPAY)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .cacheKey("outTradeNo")
                .params("orderId",id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                CacheManager.INSTANCE.remove("outTradeNo");//去掉错误缓存
                                Toast.makeText(getActivity(), json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //body:{"success":"购物车待支付成功"}
                            //调后台支付接口 -->未支付生成未付款订单,支付生成未发货订单
                            JSONObject successObj =  json.getJSONObject("success");
                            final String orderInfo = successObj.getString("orderInfo");

                            Log.i("orderInfo",orderInfo);

                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    PayTask alipay = new PayTask(getActivity());
                                    Map<String, String> result = alipay.payV2(orderInfo, true);
                                    Log.i("msp", result.toString());

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };

                            Thread payThread = new Thread(payRunnable);
                            payThread.start();

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


    /*成功回调服务器接口*/
    private void sendService(final String id){
        mDialog = ProgressDialog.show(getActivity(),"","");
        OkGo.post(Contants.PAYSUCCESSS)
                .params("outTradeNo", id)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("OrdersActivity支付", s);
                        mDialog.dismiss();
                        JSONObject json = null;
                        try {
                            json = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(getActivity(), json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //成功
                            CacheManager.INSTANCE.remove("outTradeNo");//去掉缓存
                            initgetData();//回调接口更新界面
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        CacheManager.INSTANCE.remove("outTradeNo");//去掉缓存
    }
}
