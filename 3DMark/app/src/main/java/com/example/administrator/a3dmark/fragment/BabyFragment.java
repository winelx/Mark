package com.example.administrator.a3dmark.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Collection_babyAdapter;
import com.example.administrator.a3dmark.bean.Collection;
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
 * Created by Administrator on 2017/3/1.
 * 商品/宝贝
 */
public class BabyFragment extends Fragment {
    @BindView(R.id.lv_collection_baby)
    ListView lvCollectionBaby;
    Collection_babyAdapter babyadapter;
    List<Collection> list = new ArrayList<Collection>();
    Context context;
    private String userid;
    private String goods_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.babyfragment, container, false);
        ButterKnife.bind(this, view);
        context = getActivity();

        userid = (String) SharedUtil.getParam(context, "userid", "");
        if (null != userid) {
            initData(userid);
        }
        return view;
    }

    private void initView() {
        babyadapter.setlistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int poistion = (int) v.getTag();
                switch (v.getId()) {
                    case R.id.tv_collection_shop_delete:
                        goods_id = list.get(poistion).getGoods_id();
                        initdelete_collect_goods();
                        break;
                }
            }
        });
    }

    private void initdelete_collect_goods() {
        OkGo.post(Contants.DELETE_COLLECTION_GOODS)
                .params("id", goods_id)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code========goods", s);
                        try {
                            JSONObject object = new JSONObject(s);
                            /*判断是否存在success*/
                            boolean Is_success = object.isNull("success");
                            if (!Is_success) {
                                Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                                initData(userid);
                                return;
                            }
                            boolean is_error = object.isNull("error");
                            if (!is_error) {
                                Toast.makeText(context, object.getString("error"), Toast.LENGTH_LONG).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        Toast.makeText(context, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initData(String userId) {
        final ProgressDialog mDialog = ProgressDialog.show(context, "获取数据", "获取数据中");
        OkGo.post(Contants.COLLECTION_GOODS)
                .params("userId", userId)
                .params("page", 1)
                .params("row", 15)
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
                                Collection collection = new Collection();
                                collection.setGoods_id(object.getString("goodsId"));
                                collection.setImg(object.getString("img"));
                                collection.setMoney(object.getString("priceModeNow"));
                                collection.setName(object.getString("goodsname"));
                                list.add(collection);
                            }
                            babyadapter = new Collection_babyAdapter(context, list);
                            lvCollectionBaby.setAdapter(babyadapter);
                            initView();
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


}
