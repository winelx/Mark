package com.example.administrator.a3dmark.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.example.administrator.a3dmark.Activity.OrderDetail;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Pending_Evaluation_adapter;
import com.example.administrator.a3dmark.adapter.Pending_payment_adapter;
import com.example.administrator.a3dmark.adapter.Wait_Deliver_Adapter;
import com.example.administrator.a3dmark.bean.OrderBean;
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
 * 待付款
 * Created by Administrator on 2017/3/1.
 */
public class Pending_payment extends Fragment {
    @BindView(R.id.lv_pending_evaluation)
    ListView lvPendingEvaluation;
    List<OrderBean> list = new ArrayList<>();
    Context context;
    Pending_payment_adapter adapter;
    private String userid;
    private int position;
    private PopupWindow popupWindow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.pending_evaluation, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();
        userid = (String) SharedUtil.getParam(context, "userid", "");
        Toast.makeText(context, userid, Toast.LENGTH_SHORT).show();
        initData(userid);
        return view;
    }

    private void initData(String userId) {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.NOT_PAYING)
                .params("userId", userId)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========11", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("success");
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                OrderBean orderBean = new OrderBean();
                                orderBean.setPricenow(object.getString("priceModeNew"));
                                orderBean.setGoodsname(object.getString("goodsName"));
                                orderBean.setBuinessname(object.getString("buinessName"));
                                orderBean.setImg(object.getString("img"));
                                orderBean.setLogo(object.getString("logo"));
                                orderBean.setNum(object.getString("num"));
                                orderBean.setOrderID(object.getString("orderId"));
                                orderBean.setPricemode(object.getString("priceModeNew"));
                                orderBean.setState(object.getString("state"));
                                orderBean.setTotalprice(object.getString("totalPrice"));
                                orderBean.setMail(object.getString("mail"));
                                list.add(orderBean);
                            }
                            adapter = new Pending_payment_adapter(context, list);
                            lvPendingEvaluation.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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

    private void initListener() {
        adapter.setlistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = (int) v.getTag();
                Intent intent;
                switch (v.getId()) {
                    case R.id.tv_order_remove:
                        initpop();
                        break;
                    case R.id.rl_order:
                        intent = new Intent(context, OrderDetail.class);
                        intent.putExtra("orderid", list.get(position).getOrderID());
                        intent.putExtra("state", list.get(position).getState());
                        startActivity(intent);
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
                .params("orderId", list.get(position).getOrderID())
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
                                initData(userid);
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
}
