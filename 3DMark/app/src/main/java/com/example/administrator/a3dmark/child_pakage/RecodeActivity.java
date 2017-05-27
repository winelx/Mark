package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.RecodeAdapter;
import com.example.administrator.a3dmark.bean.Fitting;
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
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/3/2.
 * 试衣记录
 */
public class RecodeActivity extends Activity {
    @BindView(R.id.lv_recode)
    ListView lvRecode;
    RecodeAdapter adapter;
    List<Fitting> list = new ArrayList<>();
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.top_bar)
    RelativeLayout topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recode_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleText.setText("试衣记录");
        tvTitleVice.setVisibility(View.GONE);
        initData();
    }

    private void initData() {
        final ProgressDialog mDialog = ProgressDialog.show(this, "获取数据", "获取数据中");
        String userID = (String) SharedUtil.getParam(RecodeActivity.this, "userid", "");
        OkGo.post(Contants.FITTING_RECORD)
                .params("userId", userID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            mDialog.dismiss();
                            JSONObject js = new JSONObject(s);
                            boolean is_null = js.isNull("success");
                            if (!is_null) {
                                JSONArray jsobj = js.getJSONArray("success");
                                list.clear();
                                for (int i = 0; i < jsobj.length(); i++) {
                                    JSONObject obj = jsobj.getJSONObject(i);
                                    Fitting fitting = new Fitting();
                                    fitting.setImg(obj.getString("img"));
                                    fitting.setTime(obj.getString("time"));
                                    fitting.setDetail(obj.getString("goodsName"));
                                    fitting.setName(obj.getString("nickname"));
                                    fitting.setSex(obj.getString("sex"));
                                    fitting.setFittingId(obj.getString("fitId"));
                                    list.add(fitting);
                                }
                                adapter = new RecodeAdapter(list, RecodeActivity.this);
                                lvRecode.setAdapter(adapter);
                                initListener();
                            }
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

    private void initListener() {
        adapter.setlistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                switch (v.getId()) {
                    case R.id.image_recode_delete:
                        initDelete(position);
                        break;
                }
            }
        });
    }

    private void initDelete(int position) {
//        String userID = (String) SharedUtil.getParam(RecodeActivity.this, "userid", "");
        OkGo.post(Contants.DELETE_FITTING)
                .params("fitId", list.get(position).getFittingId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject obj = new JSONObject(s);
                            boolean is_null = obj.isNull("success");
                            if (!is_null) {
                                String js = obj.getString("success");
                                Toast.makeText(RecodeActivity.this, js, Toast.LENGTH_SHORT).show();
                                initData();
                            }
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

    @OnClick(R.id.image_title_back)
    public void onClick() {
        finish();
    }
}
