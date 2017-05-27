package com.example.administrator.a3dmark.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.Activity.LoginActivity;
import com.example.administrator.a3dmark.Activity.MyOrder;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.bean.NickNamebean;
import com.example.administrator.a3dmark.child_pakage.CollectionActivity;
import com.example.administrator.a3dmark.child_pakage.RecodeActivity;
import com.example.administrator.a3dmark.child_pakage.SetupActivity;
import com.example.administrator.a3dmark.util.Contants;
import com.liji.circleimageview.CircleImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.a3dmark.R.id.image_mine_head;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MineFragment extends BaseFragment {
    View view;
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(image_mine_head)
    CircleImageView imageMineHead;
    Context context;
    @BindView(R.id.tv_mine_Record)
    TextView tvMineRecord;
    @BindView(R.id.tv_mine_collection)
    TextView tvMineCollection;
    @BindView(R.id.tv_mine_order)
    TextView tvMineOrder;
    @BindView(R.id.tv_mine_setup)
    TextView tvMineSetup;
    @BindView(R.id.ll_personmessage)
    LinearLayout llPersonmessage;
    TextView Username;
    @BindView(R.id.tv_person_signature)
    TextView tvPersonSignature;
    @BindView(R.id.top_bar)
    RelativeLayout topBar;
    String headimg;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragent, container, false);
        ButterKnife.bind(this, view);
        Username = (TextView) view.findViewById(R.id.Username);
        context = getContext();
        if (headImg() != null && nickName() != null && !signature().equals("暂无个性签名")) {
            Glide.with(context).load(headImg()).into(imageMineHead);
            Username.setText(nickName());
            tvPersonSignature.setText(signature());
        }
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if ("".equals(isLogin())) {//没有登录过
            startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
        String userid = isLogin();
        OkGo.post(Contants.MYSELF)
                .params("userId", userid)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        NickNamebean nickNamebean = new NickNamebean();
                        try {
                            JSONObject jsonObj = new JSONObject(s);
                            // 得到指定json key对象的value对象
                            JSONObject personObj = jsonObj.getJSONObject("success");
                            // 获取之对象的所有属性
                            Username.setText(personObj.getString("nickname"));
                            tvPersonSignature.setText(personObj.getString("signature"));
                            headimg = personObj.getString("photo");
                            Glide.with(context).load(headimg).fitCenter().placeholder(R.drawable.headimage).into(imageMineHead);

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

    private void initView() {
        imageTitleMessage.setVisibility(View.GONE);
        tvTitleVice.setVisibility(View.GONE);
        tvTitleText.setText("我的");
        imageTitleBack.setVisibility(View.GONE);
    }

    @OnClick(image_mine_head)
    void my_headimage() {
        //个人信息修改
        Intent intent = new Intent(context, PersonalInformation.class);
        intent.putExtra("image", headimg);
        startActivity(intent);
    }

    @OnClick(R.id.tv_mine_order)
    void mineorder() {
        Intent intent = new Intent(context, MyOrder.class);
        startActivity(intent);
    }

    @OnClick({R.id.tv_mine_Record, R.id.tv_mine_collection, R.id.tv_mine_setup})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_mine_Record:
                Intent intentrecord = new Intent(context, RecodeActivity.class);
                startActivity(intentrecord);
                break;
            case R.id.tv_mine_collection:
                Intent collectionintent = new Intent(context, CollectionActivity.class);
                startActivity(collectionintent);
                break;
            case R.id.tv_mine_setup:
                Intent intent = new Intent(context, SetupActivity.class);
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.ll_personmessage)
    public void onClick() {
        Intent intent = new Intent(context, PersonalInformation.class);
        intent.putExtra("image", headimg);
        startActivity(intent);
    }
}
