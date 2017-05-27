package com.example.administrator.a3dmark.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.LoginActivity;
import com.example.administrator.a3dmark.Activity.OrdersActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.ShopcartAdapter;
import com.example.administrator.a3dmark.bean.GoodsInfo;
import com.example.administrator.a3dmark.bean.StoreInfo;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.detail_shop.Shop_Activity;
import com.example.administrator.a3dmark.easemob.ui.ConversationActivity;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
 * Created by Administrator on 2017/2/22.
 */

public class ShoppingCardFragment extends BaseFragment implements ShopcartAdapter.CheckInterface,
        ShopcartAdapter.ModifyCountInterface, ShopcartAdapter.GroupEdtorListener,
        ShopcartAdapter.SourceNameInterface, ShopcartAdapter.SourceItemInterface {


    @BindView(R.id.image_title_back)
    ImageView title_back;
    @BindView(R.id.tv_title_text)
    TextView title;
    @BindView(R.id.tv_title_vice)
    TextView subtitle;
    @BindView(R.id.image_title_message)
    ImageView title_message;
    @BindView(R.id.top_bar)
    RelativeLayout topBar;
    @BindView(R.id.exListView)
    ExpandableListView exListView;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.all_chekbox)
    CheckBox allChekbox;
    @BindView(R.id.tv_delete)
    TextView tvDelete;
    @BindView(R.id.tv_go_to_pay)
    TextView tvGoToPay;

    @BindView(R.id.ll_shar)
    LinearLayout llShar;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.tv_save)
    TextView tvSave;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    @BindView(R.id.layout_cart_empty)
    LinearLayout cart_empty;

    private View view;
    private Context context;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private ShopcartAdapter selva;
    private List<StoreInfo> groups = new ArrayList<StoreInfo>();// 组元素数据列表
    private Map<String, List<GoodsInfo>> children = new HashMap<String, List<GoodsInfo>>();// 匹配组元素的子元素数据列表
    private int flag = 0;
    private boolean isLoginFlag = false;//登录操作记录
    private int currentCount = 1;//初始化,编辑数量


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_shopping_cart, null);
        ButterKnife.bind(this, view);
        title_back.setVisibility(View.GONE);
        title_message.setVisibility(View.VISIBLE);
        subtitle.setText("编辑");
        subtitle.setVisibility(View.GONE);
        initStart();
        return view;
    }


    public void initStart() {
        if (TextUtils.isEmpty(isLogin())) {//未登录
            startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            isLoginFlag = true;
            return;
        }
        if (Utils.isNetWorkAvailable(context) == false) {
            edit_totalLayoutGONE();
            Toast.makeText(getActivity(), "没有连接网络！", Toast.LENGTH_SHORT).show();
            return;
        }
        initDatas(isLogin());
    }


    @Override
    public void onStart() {
        super.onStart();
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction("status");			//添加动态广播的Action
        getActivity().registerReceiver(dynamicReceiver, dynamic_filter);	// 注册自定义动态广播消息

        if (!TextUtils.isEmpty(isLogin()) && isLoginFlag != false) {//是登录(成功)操作,再调一次请求
            initDatas(isLogin());
            isLoginFlag = false;
        }
    }


    private BroadcastReceiver dynamicReceiver = new BroadcastReceiver() {//动态广播的Receiver
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("status")){	//动作检测
                String msg = intent.getStringExtra("msg");
                //Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                if (!TextUtils.isEmpty(msg)) {
                    //初始化
                    initGoodsListDate();
                    initDatas(isLogin());//新加入购物车操作,更新购物车界面
                }
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑广播
        getActivity().unregisterReceiver(dynamicReceiver);
    }


    private void initEvents() {
        selva = new ShopcartAdapter(groups, children, getActivity());
        selva.setCheckInterface(this);// 关键步骤1,设置复选框接口
        selva.setModifyCountInterface(this);// 关键步骤2,设置数量增减接口
        selva.setSourceNameInterface(this);//店铺
        selva.setSourceItemInterface(this);//商品详情
        selva.setmListener(this);//编辑
        exListView.setAdapter(selva);
        for (int i = 0; i < selva.getGroupCount(); i++) {
            exListView.expandGroup(i);// 关键步骤3,初始化时，将ExpandableListView以展开的方式呈现
        }
    }


    /**
     * 设置购物车产品数量
     */
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(allChekbox.isChecked());
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            for (GoodsInfo goodsInfo : childs) {
                count += 1;
            }
        }

        //购物车已清空
        if (count == 0) {
            clearCart();
        } else {
            edit_totalLayoutVISIBLE();
            title.setText("购物车" + "(" + count + ")");
        }
    }

    private void clearCart() {
        title.setText("购物车" + "(" + 0 + ")");
        edit_totalLayoutGONE();
        llCart.setVisibility(View.GONE);
        cart_empty.setVisibility(View.VISIBLE);
    }

    /**
     * 遵循适配器的数据列表填充原则，组元素被放在一个List中，对应的组元素下辖的子元素被放在Map中，<br>
     * 其键是组元素的Id(通常是一个唯一指定组元素身份的值)
     */
    private void initDatas(String userId) {

        OkGo.post(Contants.CARD_GOODS)
                .params("userId", userId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        initGoodsListDate();
                        getDates(s);
                        Log.d("code", s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * 删除操作<br>
     * 1.不要边遍历边删除，容易出现数组越界的情况<br>
     * 2.现将要删除的对象放进相应的列表容器中，待遍历完后，以removeAll的方式进行删除
     */
    protected void doDelete() {

        List<String> scartIds = new ArrayList<>();

        List<StoreInfo> toBeDeleteGroups = new ArrayList<StoreInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsInfo> toBeDeleteProducts = new ArrayList<GoodsInfo>();// 待删除的子元素列表
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            for (GoodsInfo goods : toBeDeleteProducts) {
                scartIds.add(goods.getScartId());
            }
        }
        String scartId = scartIds.toString().substring(scartIds.toString().indexOf("[") + 1, scartIds.toString().indexOf("]"));
        Log.d("deleteDate///////id:", scartId.replaceAll("\\s*", "").trim());
        //删除多个  \\s* -->正则去空
        deleteDate(scartId.replaceAll("\\s*", "").trim(), -1, -1);
    }


    /**
     * 增加数量
     *
     * @param groupPosition 组元素位置
     * @param childPosition 子元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int groupPosition, int childPosition,
                           final View showCountView, boolean isChecked) {
        final GoodsInfo product = (GoodsInfo) selva.getChild(groupPosition,
                childPosition);
        currentCount = product.getCount();
        String ScardId = product.getScartId();
        currentCount++;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        //编辑请求
        editCard((TextView) showCountView, product, ScardId, true);
    }


    /**
     * 删减数量
     *
     * @param groupPosition 组元素位置
     * @param childPosition 子元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int groupPosition, int childPosition,
                           final View showCountView, boolean isChecked) {

        final GoodsInfo product = (GoodsInfo) selva.getChild(groupPosition,
                childPosition);
        currentCount = product.getCount();
        String ScardId = product.getScartId();
        if (currentCount == 1)
            return;
        currentCount--;
        product.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        selva.notifyDataSetChanged();
        //编辑请求
        editCard((TextView) showCountView, product, ScardId, false);
    }

    /**
     * 删除商品
     *
     * @param groupPosition
     * @param childPosition
     */
    @Override
    public void childDelete(int groupPosition, int childPosition) {
        deleteDate(children.get(groups.get(groupPosition).getId()).get(childPosition).getScartId(), groupPosition, childPosition);
    }


    /**
     * @param groupPosition 组元素位置
     * @param isChecked     组元素选中与否
     */
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            childs.get(i).setChoosed(isChecked);
        }
        if (isAllCheck())
            allChekbox.setChecked(true);
        else
            allChekbox.setChecked(false);
        selva.notifyDataSetChanged();
        calculate();
    }


    /**
     * @param groupPosition 组元素位置
     * @param childPosiTion
     * @param isChecked     子元素选中与否
     */
    @Override
    public void checkChild(int groupPosition, int childPosiTion,
                           boolean isChecked) {
        boolean allChildSameState = true;// 判断改组下面的所有子元素是否是同一种状态
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo> childs = children.get(group.getId());
        for (int i = 0; i < childs.size(); i++) {
            // 不全选中
            if (childs.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }
        //获取店铺选中商品的总金额
        if (allChildSameState) {
            group.setChoosed(isChecked);// 如果所有子元素状态相同，那么对应的组元素被设为这种统一状态
        } else {
            group.setChoosed(false);// 否则，组元素一律设置为未选中状态
        }

        if (isAllCheck()) {
            allChekbox.setChecked(true);// 全选
        } else {
            allChekbox.setChecked(false);// 反选
        }
        selva.notifyDataSetChanged();
        calculate();


    }

    private boolean isAllCheck() {

        for (StoreInfo group : groups) {
            if (!group.isChoosed())
                return false;

        }

        return true;
    }

    /**
     * 全选与反选
     */
    private void doCheckAll() {

        for (int i = 0; i < groups.size(); i++) {
            groups.get(i).setChoosed(allChekbox.isChecked());
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                childs.get(j).setChoosed(allChekbox.isChecked());
            }
        }
        selva.notifyDataSetChanged();
        calculate();
    }

    /**
     * 统计操作<br>
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作<br>
     * 3.给底部的textView进行数据填充
     */
    private void calculate() {
        totalCount = 0;
        totalPrice = 0.00;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                GoodsInfo product = childs.get(j);
                if (product.isChoosed()) {
                    totalCount++;
                    totalPrice += product.getPrice() * product.getCount();
                }
            }
        }

        tvTotalPrice.setText("￥" + totalPrice);
        tvGoToPay.setText("结算(" + totalCount + ")");
        //计算购物车的金额为0时候清空购物车的视图
        if (totalCount == 0) {
            setCartNum();
        } else {
            title.setText("购物车" + "(" + totalCount + ")");
        }
    }

    @OnClick({R.id.image_title_message,R.id.all_chekbox, R.id.tv_delete, R.id.tv_go_to_pay, R.id.tv_title_vice, R.id.tv_save, R.id.tv_share})
    public void onClick(View view) {
        AlertDialog alert;
        switch (view.getId()) {
            case R.id.image_title_message:
                startActivity(new Intent(getActivity(), ConversationActivity.class));
                break;
            case R.id.all_chekbox:
                try {
                    doCheckAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_delete:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要移除的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                alert = new AlertDialog.Builder(context).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doDelete();
                            }
                        });
                alert.show();
                break;
            case R.id.tv_go_to_pay:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要支付的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                toPay();//结算
                break;
            case R.id.tv_title_vice:
                if (flag == 0) {
                    llInfo.setVisibility(View.GONE);
                    tvGoToPay.setVisibility(View.GONE);
                    llShar.setVisibility(View.VISIBLE);
                    subtitle.setText("完成");
                } else if (flag == 1) {
                    llInfo.setVisibility(View.VISIBLE);
                    tvGoToPay.setVisibility(View.VISIBLE);
                    llShar.setVisibility(View.GONE);
                    subtitle.setText("编辑");
                }
                flag = (flag + 1) % 2;//其余得到循环执行上面2个不同的功能
                break;
            case R.id.tv_share:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要分享的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_save:
                if (totalCount == 0) {
                    Toast.makeText(context, "请选择要保存的商品", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getActivity(), "保存成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void groupEdit(int groupPosition) {
        groups.get(groupPosition).setIsEdtor(true);
        selva.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        initGoodsListDate();
    }


    /**
     * 初始化数据
     */
    private void initGoodsListDate() {
        selva = null;
        groups.clear();
        totalPrice = 0;
        totalCount = 0;
        children.clear();
    }


    //获取购物车数据
    private void getDates(String s) {
        try {
            JSONObject json = new JSONObject(s);
            //buyanjing//再次判断
            if (s.indexOf("error") != -1) {//有错误
                Toast.makeText(getActivity(), json.getString("error"), Toast.LENGTH_SHORT).show();
                return;
            }

            JSONObject jsonSuccess = json.getJSONObject("success");//成功标识
            JSONArray storeArray = jsonSuccess.getJSONArray("store");
            StoreInfo storeInfo = null;
            for (int i = 0; i < storeArray.length(); i++) {
                JSONObject storeObject = storeArray.getJSONObject(i);
                storeInfo = new StoreInfo();
                storeInfo.setId(storeObject.getString("bussiness_id"));
                storeInfo.setName(storeObject.getString("storeName"));
                storeInfo.setStoreLogoImg(storeObject.getString("logo"));
                groups.add(storeInfo);

                JSONArray goodsArray = storeObject.getJSONArray("goods");
                List<GoodsInfo> products = new ArrayList<GoodsInfo>();
                GoodsInfo goodsInfo = null;
                for (int j = 0; j < goodsArray.length(); j++) {
                    JSONObject goodsObject = goodsArray.getJSONObject(j);
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setBussinessId(storeObject.getString("bussiness_id"));
                    goodsInfo.setStoreName(storeObject.getString("storeName"));
                    goodsInfo.setStoreLogo(storeObject.getString("logo"));
                    goodsInfo.setGoodsId(goodsObject.getString("goods_id"));
                    goodsInfo.setScartId(goodsObject.getString("scartId"));
                    goodsInfo.setDesc(goodsObject.getString("goodsName"));
                    goodsInfo.setMail(goodsObject.getDouble("mail"));
                    goodsInfo.setDeliveryTime(goodsObject.getString("sendTime"));
//                    goodsInfo.setConnect(goodsObject.getString("connect"));
                    goodsInfo.setImageUrl(goodsObject.getString("img"));
                    goodsInfo.setPrice(goodsObject.getDouble("price"));
                    goodsInfo.setCount(goodsObject.getInt("num"));
                    goodsInfo.setColor(goodsObject.getString("color"));
                    goodsInfo.setSize(goodsObject.getString("size"));
                    products.add(goodsInfo);
                }
                edit_totalLayoutVISIBLE();
                children.put(groups.get(i).getId(), products);// 将组元素的一个唯一值，这里取Id，作为子元素List的Key
            }


            initEvents();
            setCartNum();

        } catch (Exception e) {
            e.printStackTrace();
            title.setText("购物车" + "(0)");
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }





    /**
     * 编辑请求
     * @param showCountView
     * @param product
     * @param scardId
     * @param flag
     */
    private void editCard(final TextView showCountView, final GoodsInfo product, String scardId, final boolean flag) {
        OkGo.post(Contants.EDIT_CARD_GOODS)
                .params("scartId", scardId)
                .params("num",currentCount)
                .params("size","null")
                .params("color","null")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject json = null;
                        try {
                            json = new JSONObject(s);
                            //buyanjing//再次判断
                            if (s.indexOf("error") != -1) {//有错误

                                if(flag != false){
                                    currentCount--;
                                    product.setCount(currentCount);
                                    showCountView.setText(currentCount + "");
                                    selva.notifyDataSetChanged();
                                    calculate();
                                    Toast.makeText(getActivity(), json.getString("error"), Toast.LENGTH_SHORT).show();
                                } else {
                                    currentCount++;
                                    product.setCount(currentCount);
                                    showCountView.setText(currentCount + "");
                                    selva.notifyDataSetChanged();
                                    calculate();
                                    Toast.makeText(getActivity(), json.getString("error"), Toast.LENGTH_SHORT).show();
                                }
                                return;
                            }
                            //成功
                            calculate();
                            Toast.makeText(getActivity(), json.getString("success"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        if(flag != false){
                            currentCount--;
                            product.setCount(currentCount);
                            showCountView.setText(currentCount + "");
                            selva.notifyDataSetChanged();
                            calculate();
                        } else {
                            currentCount++;
                            product.setCount(currentCount);
                            showCountView.setText(currentCount + "");
                            selva.notifyDataSetChanged();
                            calculate();
                        }
                    }
                });
    }




    /**
     * 删除某个/单个/多个
     *
     * @param scartId
     * @param groupPosition
     * @param childPosition
     */
    public void deleteDate(String scartId, final int groupPosition, final int childPosition) {

        final ProgressDialog mDialog = ProgressDialog.show(getActivity(), "删除商品", "正在删除...");
        OkGo.post(Contants.DELETE_CARD_GOODS)
                .params("scartId", scartId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            //buyanjing//再次判断
                            if (s.indexOf("error") != -1) {//有错误
                                edit_totalLayoutGONE();
                                Toast.makeText(getActivity(), json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Toast.makeText(getActivity(), json.getString("success"), Toast.LENGTH_SHORT).show();//成功标识

                            //单个删除
                            if (groupPosition != -1 && childPosition != -1) {
                                children.get(groups.get(groupPosition).getId()).remove(childPosition);
                                StoreInfo group = groups.get(groupPosition);
                                List<GoodsInfo> childs = children.get(group.getId());
                                if (childs.size() == 0) {
                                    groups.remove(groupPosition);
                                }
                                selva.notifyDataSetChanged();
                                calculate();
                                return;
                            }

                            //多个删除
                            deleteDate();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        Log.d("***code", s);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                        mDialog.dismiss();
                    }
                });

    }


    /**
     * 结算操作<br>
     */
    protected void toPay() {

        List<GoodsInfo> goodsInfos = new ArrayList<>();//给startActivity.putExtra使用,保证数据正确性

        List<StoreInfo> toBeDeleteGroups = new ArrayList<StoreInfo>();// 组元素列表

        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsInfo> toBeDeleteProducts = new ArrayList<GoodsInfo>();// 子元素列表
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }
            for (GoodsInfo goods : toBeDeleteProducts) {
                goodsInfos.add(goods);
            }
        }
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < goodsInfos.size(); i++) {
            String bussinessId = goodsInfos.get(i).getBussinessId();
            if (map.containsKey(bussinessId)) {

                JsonObject goods = (JsonObject) map.get(bussinessId);
                JsonArray jsonArray = goods.get("goods").getAsJsonArray();
                JsonObject JsonObject = new JsonObject();
                JsonObject.addProperty("color", goodsInfos.get(i).getColor());
//                JsonObject.addProperty("connect",goodsInfos.get(i).getConnect());
                JsonObject.addProperty("sendTime",goodsInfos.get(i).getDeliveryTime());
                JsonObject.addProperty("goodsName", goodsInfos.get(i).getDesc());
                JsonObject.addProperty("goods_id", goodsInfos.get(i).getGoodsId());
                JsonObject.addProperty("img", goodsInfos.get(i).getImageUrl());
                JsonObject.addProperty("num", goodsInfos.get(i).getCount());
                JsonObject.addProperty("price", goodsInfos.get(i).getPrice());
                JsonObject.addProperty("mail", goodsInfos.get(i).getMail());
                JsonObject.addProperty("scartId", goodsInfos.get(i).getScartId());
                JsonObject.addProperty("size", goodsInfos.get(i).getSize());
                jsonArray.add(JsonObject);

            } else {

                //店铺信息
                JsonObject store = new JsonObject();
                JsonArray goodsArray = new JsonArray();
                store.addProperty("bussiness_id", goodsInfos.get(i).getBussinessId());
                store.addProperty("logo", goodsInfos.get(i).getStoreLogo());
                store.addProperty("storeName", goodsInfos.get(i).getStoreName());
                //商品信息
                JsonObject JsonObject = new JsonObject();
                JsonObject.addProperty("color", goodsInfos.get(i).getColor());
//                JsonObject.addProperty("connect",goodsInfos.get(i).getConnect());
                JsonObject.addProperty("sendTime",goodsInfos.get(i).getDeliveryTime());
                JsonObject.addProperty("goodsName", goodsInfos.get(i).getDesc());
                JsonObject.addProperty("goods_id", goodsInfos.get(i).getGoodsId());
                JsonObject.addProperty("img", goodsInfos.get(i).getImageUrl());
                JsonObject.addProperty("num", goodsInfos.get(i).getCount());
                JsonObject.addProperty("mail", goodsInfos.get(i).getMail());
                JsonObject.addProperty("price", goodsInfos.get(i).getPrice());
                JsonObject.addProperty("scartId", goodsInfos.get(i).getScartId());
                JsonObject.addProperty("size", goodsInfos.get(i).getSize());
                goodsArray.add(JsonObject);
                store.add("goods", goodsArray);
                map.put(bussinessId, store);
            }
        }


        String results=map.values().toString();
        Log.d("购物车订单goodsInfos", results);
        startActivity(new Intent(getActivity(), OrdersActivity.class)
                .putExtra("goodsInfos", results).putExtra("userId", isLogin())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));

    }


    /**
     * 计算多个删除的商品
     */
    public void deleteDate() {
        totalPrice = 0.00;
        tvGoToPay.setText("结算(" + 0 + ")");
        List<StoreInfo> toBeDeleteGroups = new ArrayList<StoreInfo>();// 待删除的组元素列表
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsInfo> toBeDeleteProducts = new ArrayList<GoodsInfo>();// 待删除的子元素列表
            List<GoodsInfo> childs = children.get(group.getId());
            for (int j = 0; j < childs.size(); j++) {
                if (childs.get(j).isChoosed()) {
                    toBeDeleteProducts.add(childs.get(j));
                }
            }

            childs.removeAll(toBeDeleteProducts);
        }
        groups.removeAll(toBeDeleteGroups);
        //记得重新设置购物车
        setCartNum();
        selva.notifyDataSetChanged();
    }


    /**
     * 购物车为空，没有网络/不显示
     */
    public void edit_totalLayoutGONE() {
        cart_empty.setVisibility(View.VISIBLE);
        subtitle.setVisibility(View.GONE);
        llCart.setVisibility(View.GONE);
    }

    /**
     * 购物车不为空，有网络/显示
     */
    public void edit_totalLayoutVISIBLE() {
        cart_empty.setVisibility(View.GONE);
        subtitle.setVisibility(View.VISIBLE);
        subtitle.setVisibility(View.VISIBLE);
        llCart.setVisibility(View.VISIBLE);
    }


    /**
     * 进入店铺
     *
     * @param groupPosition
     */
    @Override
    public void SourceNameGroup(int groupPosition) {

//        if (flag != 1 && !isEdtor) {//不处于编辑情况下触发
//            startActivity(new Intent(context, Shop_Activity.class)
//                    .putExtra("bussiness_id", groups.get(groupPosition).getId())
//                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
//        }
        startActivity(new Intent(context, Shop_Activity.class)
                .putExtra("bussiness_id", groups.get(groupPosition).getId())
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }


    /**
     * 进入商品详情
     *
     * @param id
     */
    @Override
    public void SourceItemGroup(String id) {
//        if (flag != 1 && !isEdtor) {//不处于编辑情况下触发
//            startActivity(new Intent(context, Boutique_Detail.class).putExtra("goods_id", id)
//                    .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
//        }
        startActivity(new Intent(context, Boutique_Detail.class).putExtra("goods_id", id)
                .setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
    }

}
