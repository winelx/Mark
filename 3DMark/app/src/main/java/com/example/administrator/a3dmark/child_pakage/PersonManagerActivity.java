package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.a3dmark.Activity.Add_Person;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.PersonManagerAdapter;
import com.example.administrator.a3dmark.bean.ManPhoto;
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

import static com.example.administrator.a3dmark.R.id.tv_title_vice;

/**
 * 人物管理
 * Created by Administrator on 2017/2/28.
 */
public class PersonManagerActivity extends Activity {
    private Context context;
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.lv_person_manager)
    ListView lvPersonManager;
    List<ManPhoto> list = new ArrayList<>();
    PersonManagerAdapter managerAdapter;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_manager);
        ButterKnife.bind(this);
        context = PersonManagerActivity.this;
        initView();
        InitOKgo();
    }

    private void InitOKgo() {
        String userID = (String) SharedUtil.getParam(PersonManagerActivity.this, "userid", "");
        OkGo.post(Contants.mangeManPhoto)
                .params("userId", userID)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject js = new JSONObject(s);
                            JSONArray jsArray = js.getJSONArray("success");
                            ManPhoto manPhoto = null;//javabean
                            list.clear();
                            for (int j = 0; j < jsArray.length(); j++) {
                                manPhoto = new ManPhoto();
                                JSONObject jsonbobject = jsArray.getJSONObject(j);
                                manPhoto.setBust(jsonbobject.getInt("bust"));//
                                manPhoto.setHeight(jsonbobject.getInt("height"));
                                manPhoto.setHipline(jsonbobject.getInt("hipline"));
                                manPhoto.setImg(jsonbobject.getString("img"));
                                manPhoto.setNickname(jsonbobject.getString("nickname"));
                                manPhoto.setPersonalId(jsonbobject.getString("personalId"));
                                manPhoto.setPhotoId(jsonbobject.getString("photoId"));
                                manPhoto.setWaistline(jsonbobject.getInt("waistline"));
                                manPhoto.setWeight(jsonbobject.getInt("weight"));
                                manPhoto.setIsFit(jsonbobject.getString("isFit"));
                                manPhoto.setIsSelf(jsonbobject.getString("isSelf"));
                                manPhoto.setSex(jsonbobject.getString("sex"));
                                manPhoto.setTime(jsonbobject.getString("time"));
                                list.add(manPhoto);
                            }
                            managerAdapter = new PersonManagerAdapter(context, list);
                            lvPersonManager.setAdapter(managerAdapter);
                            managerAdapter.notifyDataSetChanged();
                            inITLister();
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

    @Override
    protected void onRestart() {
        super.onRestart();
        InitOKgo();
    }

    private void inITLister() {
        managerAdapter.setlistener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = (int) v.getTag();
                switch (v.getId()) {
                    case R.id.tv_person_manager_delete:
                        initdelete();
                        break;
                }
            }
        });
    }


    private void initdelete() {
        OkGo.post(Contants.DELETE_PERSON)
                .params("persionId", list.get(position).getPersonalId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        InitOKgo();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });


    }

    private void initView() {
        tvTitleVice.setVisibility(View.GONE);
        tvTitleText.setText("人物管理");
        imageTitleMessage.setVisibility(View.VISIBLE);
        imageTitleMessage.setImageResource(R.drawable.add_manager);
    }


    @OnClick({R.id.image_title_back, R.id.image_title_message})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_title_back:
                finish();
                break;
            case R.id.image_title_message:
                Intent intent = new Intent(PersonManagerActivity.this, Add_Person.class);
                startActivity(intent);
                break;
        }
    }
}
