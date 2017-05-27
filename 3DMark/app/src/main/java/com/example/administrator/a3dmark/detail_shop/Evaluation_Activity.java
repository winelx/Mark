package com.example.administrator.a3dmark.detail_shop;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.EvaluationAdapter;
import com.example.administrator.a3dmark.bean.Evaluation_Bean;
import com.example.administrator.a3dmark.bean.Type;
import com.example.administrator.a3dmark.util.Contants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 查看全部评价
 * Created by Administrator on 2017/3/30.
 */
public class Evaluation_Activity extends Activity {
    @BindView(R.id.image_finish)
    ImageView imageFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title_share)
    TextView tvTitleShare;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.title_image_message)
    ImageView titleImageMessage;
    @BindView(R.id.fl_boutique_evaluate)
    TagFlowLayout flBoutiqueEvaluate;
    @BindView(R.id.lv_evaluation)
    ListView lvEvaluation;
    List<Evaluation_Bean> list = new ArrayList<Evaluation_Bean>();
    EvaluationAdapter adapter;
    //    private String[] mVals_evaluation = {"Hello", "Android", "Weclome Hi ", "Button", "TextView"};
    LayoutInflater mInflater;
    String goodsid;
    private List<String> bolckData = new ArrayList<>();
    private List<Type> bolcktype = new ArrayList<Type>();
    String name;
    String type;

    //String
    //String[] evaluationdata = new String[]{};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation_activity);
        ButterKnife.bind(this);
        goodsid = getIntent().getStringExtra("goods_id");
        lvEvaluation.setFocusable(false);
        initEvaluationData();
        initView();
    }

    private void initEvaluationData() {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.post(Contants.EVALUATION_GOODS_LIST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code===data", s);
                        mDialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(s);
                            JSONArray jsonArray = object.getJSONArray("success");
                            Type typeblock = null;
                            bolckData.clear();
                            bolcktype.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                typeblock = new Type();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                typeblock.setType(jsonObject.getString("type"));
                                typeblock.setName(jsonObject.getString("name"));
                                bolcktype.add(typeblock);
                                bolckData.add(jsonObject.getString("name"));
                            }
                            initflview();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Evaluation_Activity.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void initView() {
        tvTitleShare.setVisibility(View.VISIBLE);
        tvTitleShare.setText("分享");
        tvTitleShare.setBackgroundDrawable(null);
        tvTitle.setText("评价（1234）");
        type = "" + 1;
        initChildData();
    }

    TextView tv;

    private void initflview() {

        mInflater = LayoutInflater.from(Evaluation_Activity.this);
        flBoutiqueEvaluate.setAdapter(new TagAdapter<String>(bolckData) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                tv = (TextView) mInflater.inflate(R.layout.tv_evaluation, flBoutiqueEvaluate, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public boolean setSelected(int position, String s) {
                return s.equals("全部");
            }
        });

        flBoutiqueEvaluate.setOnTagClickListener(
                new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        //设置当前标签为选中状态
                        flBoutiqueEvaluate.setSelected(true);
                        /*设置type的值*/
                        type = bolcktype.get(position).getType();
                        /*根据每个type的值不同传入的参数来调接口*/
                        initChildData();
                        return true;
                    }
                });
    }

    /*商家评价的接口*/
    private void initChildData() {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        OkGo.post(Contants.EVALUATION_LIST)
                .params("goodsId", goodsid)
                .params("type", type)
                .params("page", 1)
                .params("row", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Log.d("code===child", s);
                        mDialog.dismiss();
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            /*判断是否有success字段*/
                            boolean ISerror = jsobj.isNull("success");
                            if (!ISerror) {
                                JSONArray arry = jsobj.getJSONArray("success");
                                list.clear();
                                for (int i = 0; i < arry.length(); i++) {
                                    Evaluation_Bean beans = new Evaluation_Bean();
                                    JSONObject obj = arry.getJSONObject(i);
                                    beans.setColor_evaluation(obj.getString("goods_color"));
                                    beans.setContent_evaluation(obj.getString("content"));
                                    beans.setEvaluation_name(obj.getString("nickname"));
                                    beans.setTime_evaluation(obj.getString("gmt_create"));
                                    boolean head = obj.isNull("headImgs");
                                    if (!head) {
                                        beans.setHeadimg(obj.getString("headImgs"));
                                    }
                                    beans.setSize_evaluation(obj.getString("goods_size"));
                                    boolean bool = obj.isNull("evaImgs");
                                    if (!bool) {
                                        beans.setEvaImgs(obj.getString("evaImgs"));
                                        String images = beans.getEvaImgs();
                                        String images_eva[] = images.split(",");
                                        if (images_eva.length == 1) {
                                            beans.setImage_evaluation_1(images_eva[0]);
                                        } else if (images_eva.length == 2) {
                                            beans.setImage_evaluation_1(images_eva[0]);
                                            beans.setImage_evaluation_2(images_eva[1]);
                                        } else if (images_eva.length == 3) {
                                            beans.setImage_evaluation_1(images_eva[0]);
                                            beans.setImage_evaluation_2(images_eva[1]);
                                            beans.setImage_evaluation_3(images_eva[2]);
                                        }
                                    }
                                    /*判断是否有追加评论*/
                                    boolean add_evaluation = obj.isNull("more");
                                    if (!add_evaluation) {
                                        beans.setContent_evaluation_add(obj.getJSONObject("more").getString("content"));
                                        beans.setTime_add(obj.getJSONObject("more").getString("gmt_create"));
                                        boolean evaimg = obj.getJSONObject("more").isNull("evaImgs");
                                        if (!evaimg) {
                                            beans.setEvaimages_add(obj.getJSONObject("more").getString("evaImgs"));
                                            String images = beans.getEvaimages_add();
                                            String images_eva[] = images.split(",");
                                            if (images_eva.length == 1) {
                                                beans.setImage_evaluation_add_1(images_eva[0]);
                                            } else if (images_eva.length == 2) {
                                                beans.setImage_evaluation_add_1(images_eva[0]);
                                                beans.setImage_evaluation_add_2(images_eva[1]);
                                            } else if (images_eva.length == 3) {
                                                beans.setImage_evaluation_add_1(images_eva[0]);
                                                beans.setImage_evaluation_add_2(images_eva[1]);
                                                beans.setImage_evaluation_add_3(images_eva[2]);
                                            }
                                        }

                                    }
                                    //判断是否存在reply（商家回复）这个字段
                                    boolean reply = obj.isNull("reply");
                                    if (!reply) {
                                        beans.setContent_evaluation_reply(obj.getJSONObject("reply").getString("content"));
                                        beans.setTime_reply(obj.getJSONObject("reply").getString("gmt_create"));
                                    }
                                    list.add(beans);
                                }
                                adapter = new EvaluationAdapter(list, Evaluation_Activity.this);
                                lvEvaluation.setAdapter(adapter);
                                /*测量listview的高度方法，解決scollview嵌套listview的显示不全问题*/
                                setListViewHeightBasedOnChildren(lvEvaluation);
                            } else {
                                Toast.makeText(Evaluation_Activity.this, jsobj.getString("error"), Toast.LENGTH_SHORT).show();
                            }
//                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Evaluation_Activity.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });
    }

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

    @OnClick({R.id.image_finish, R.id.tv_title_share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_finish:
                finish();
                break;
            case R.id.tv_title_share:
                break;
        }
    }
}

