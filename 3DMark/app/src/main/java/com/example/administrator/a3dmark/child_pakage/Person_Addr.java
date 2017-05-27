package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.BaseActivity;
import com.example.administrator.a3dmark.Activity.LocalizationActivity;
import com.example.administrator.a3dmark.Activity.OrdersActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.PersonAddr_Adapter;
import com.example.administrator.a3dmark.detail_shop.Ensure_Order;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/27.
 */
public class Person_Addr extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.lv_new_addr)
    ListView lvNewAddr;
    @BindView(R.id.btn_new_addr)
    TextView btnNewAddr;
    ArrayList<Address> list = new ArrayList<Address>();
    PersonAddr_Adapter personadapter;
    @BindView(R.id.image_finish)
    ImageView imageFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    int position;
    String userid;
    String ordersActivityUserId;
    String goodid;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_addr);
        ButterKnife.bind(this);
        ordersActivityUserId = getIntent().getStringExtra("userid");
        userid = (String) SharedUtil.getParam(this, "userid", "");
        tvTitle.setText("管理收货地址");
        initView();
    }

    private void initView() {
        if (null != userid) {
            initData(userid);
        }
        personadapter = new PersonAddr_Adapter(list, Person_Addr.this);
        lvNewAddr.setAdapter(personadapter);
        personadapter.setlistener(new View.OnClickListener() {
            Intent intent = null;

            @Override
            public void onClick(View v) {
                position = (int) v.getTag();
//                Log.d("Yxcl", position + "");
                switch (v.getId()) {
                    case R.id.tv_default_addr:/*默认地址*/
                        Log.d("position", position + "");
                        if (null != userid) {
                            initDefaultData(userid);
                        }
                        break;
                    case R.id.tv_edit_addr:/*编辑地址*/
                        Log.d("position", position + "");
                        intent = new Intent(Person_Addr.this, AddNewAddr.class);
                        intent.putExtra("name", list.get(position).getName());
                        intent.putExtra("phone", list.get(position).getPhone());
                        intent.putExtra("area", list.get(position).getArea());
                        intent.putExtra("addr", list.get(position).getAddr());
                        intent.putExtra("street", list.get(position).getStreet());
                        intent.putExtra("city", list.get(position).getCity());
                        intent.putExtra("id", list.get(position).getId());
                        intent.putExtra("distract", list.get(position).getDistrict());
                        startActivity(intent);
                        break;
                    case R.id.tv_delete_addr:/*删除地址*/
                        Log.d("position", position + "");
                        showPopupWindow();
                        break;
                    case R.id.tv_detail_addr:/*详细地址跳转到页面*/
                        if (null != ordersActivityUserId){
                            Address address = new Address();
                            Bundle bundle = new Bundle();
                            address.setArea(list.get(position).getArea());
                            address.setDistrict(list.get(position).getDistrict());
                            address.setName(list.get(position).getName());
                            address.setPhone(list.get(position).getPhone());
                            address.setAddr(list.get(position).getAddr());
                            address.setStreet(list.get(position).getStreet());
                            address.setCity(list.get(position).getCity());
                            address.setId(list.get(position).getId());
                            bundle.putSerializable("address", address);
                            setResult(200, new Intent(Person_Addr.this,OrdersActivity.class).putExtras(bundle));
                            finish();
                            return;
                        }

                        Address address = new Address();
                        address.setArea(list.get(position).getArea());
                        address.setDistrict(list.get(position).getDistrict());
                        address.setName(list.get(position).getName());
                        address.setPhone(list.get(position).getPhone());
                        address.setAddr(list.get(position).getAddr());
                        address.setStreet(list.get(position).getStreet());
                        address.setCity(list.get(position).getCity());
                        address.setId(list.get(position).getId());
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("address", address);
                        startActivity(new Intent(Person_Addr.this, Ensure_Order.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtras(bundle));
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (null != userid) {
            initData(userid);
            Toast.makeText(Person_Addr.this, userid, Toast.LENGTH_SHORT).show();
        }
    }

    private void initDefaultData(final String userId) {
//        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        goodid = SharedUtil.getParam(Person_Addr.this, "goods_id", "").toString();
        OkGo.post(Contants.DEFAULT_ADDR)
                .params("goodsId",goodid)
                .params("userId", userId)
                .params("id", list.get(position).getId())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
//                        mDialog.dismiss()                        if (s.indexOf("error") != -1)
                            Toast.makeText(Person_Addr.this, "success", Toast.LENGTH_LONG).show();
                        if (null != userid) {
                            initData(userid);
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
//                        mDialog.dismiss();
                        Toast.makeText(Person_Addr.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initData(String userid) {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.post(Contants.PERSON_ADDR)
                .params("userId", userid)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray jsonArray = jsonObject.getJSONArray("success");
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Address address = new Address();
                                address.setName(object.getString("name"));
                                address.setAddr(object.getString("address"));
                                address.setPhone(object.getString("phone"));
                                address.setArea(object.getString("area"));
                                address.setDefaultaddress(object.getInt("defaultaddress"));
                                address.setStreet(object.getString("street"));
                                address.setCity(object.getString("city"));
                                address.setId(object.getString("id"));
                                address.setDistrict(object.getString("district"));
                                list.add(address);
                            }
                            personadapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Person_Addr.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    @OnClick({R.id.image_finish, R.id.btn_new_addr})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.image_finish:
                finish();
                break;
            case R.id.btn_new_addr:
                intent = new Intent(Person_Addr.this, EditAddr.class);
                startActivity(intent);
                break;
//            case R.id.tv_default_addr:
//                intent = new Intent(Person_Addr.this, AddNewAddr.class);
//                startActivity(intent);
//                break;
        }
    }

    private void showPopupWindow() {

        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);
//        @BindView(R.id.btn_setup_cache_cancel)
        TextView btnSetupCacheCancel = (TextView) contentView.findViewById(R.id.btn_setup_cache_cancel);
//        @BindView(R.id.btn_setup_cache_ensure)
        TextView btnSetupCacheEnsure = (TextView) contentView.findViewById(R.id.btn_setup_cache_ensure);
        TextView tv_pop_detail = (TextView) contentView.findViewById(R.id.tv_pop_detail);
        tv_pop_detail.setText("确认删除");
        tv_pop_detail.setTextColor(getResources().getColor(R.color.text_black));
        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
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
                    setBackgroundAlpha(Person_Addr.this, 1f);
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
                initDeleteData();
                popupWindow.dismiss();
                if (null != userid) {
                    initData(userid);
                }
            }
        });
    }

    /*删除地址接口*/
    private void initDeleteData() {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.post(Contants.DELETE_ADDR)
                .params("id", list.get(position).getId())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========", s);
                        mDialog.dismiss();
                        try {
                            JSONObject js = new JSONObject(s);
//                            JSONArray jsonArray = js.getJSONArray("success");
                            if (js.getString("success").equals("删除成功")) {
                                personadapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Person_Addr.this, response.message(), Toast.LENGTH_LONG).show();
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
