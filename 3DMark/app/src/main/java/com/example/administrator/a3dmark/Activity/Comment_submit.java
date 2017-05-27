package com.example.administrator.a3dmark.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.awen.photo.photopick.controller.PhotoPickConfig;
import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.CommGridAdapter;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.MyRatingBar;
import com.example.administrator.a3dmark.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/4/14.
 * 分表评价
 */
public class Comment_submit extends Activity {
    @BindView(R.id.tv_back)
    ImageView tvBack;
    @BindView(R.id.image_comment_submit_goods)
    ImageView imageCommentSubmitGoods;
    @BindView(R.id.RB_Description_match)
    MyRatingBar RBDescriptionMatch;
    @BindView(R.id.tv_comment_submit_goods)
    TextView tvCommentSubmitGoods;
    @BindView(R.id.btn_comment_submit)
    TextView btn_comment_submit;
    @BindView(R.id.edt_comment_submit_goods)
    EditText edtCommentSubmitGoods;
    @BindView(R.id.image_comment_submit_add)
    ImageView imageCommentSubmitAdd;
    @BindView(R.id.image_comment_submit_shop)
    ImageView imageCommentSubmitShop;
    @BindView(R.id.RB_Service_attitude)
    MyRatingBar RBServiceAttitude;
    @BindView(R.id.RB_Logistics_speed)
    MyRatingBar RBLogisticsSpeed;
    Intent intent;
    private float ServiceAttitude;
    private float LogisticsSpeed;
    private float DescriptionMatch;
    private GridView gridView;
    int REQUEST_CODE = 1;
    ArrayList<String> imagePaths = null;
    private CommGridAdapter gridAdapter;
    ArrayList<String> fileList = new ArrayList<>();
    ArrayList<String> photoLists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_submit);
        intent = getIntent();
        ButterKnife.bind(this);
        initView();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Glide.with(this).load(intent.getStringExtra("goodsimg")).fitCenter().placeholder(R.mipmap.default_image_goods).into(imageCommentSubmitGoods);
        Glide.with(this).load(intent.getStringExtra("shopimg")).fitCenter().placeholder(R.mipmap.touxiang_default).into(imageCommentSubmitShop);
    }

    private void initWebData() {
        OkGo.post(Contants.COMMENT_SUBMIT)
                .params("orderId", intent.getStringExtra("orderId"))
                .params("goodsId", intent.getStringExtra("goodsId"))
                .params("bussinessId", intent.getStringExtra("bussinessId"))
                .params("levelGeneral", DescriptionMatch)
                .params("levelService", ServiceAttitude)
                .params("levelLogistics", LogisticsSpeed)
                .params("content", edtCommentSubmitGoods.getText().toString())
                .params("picture", String.valueOf(fileList))
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            boolean is_null = jsobj.isNull("success");
                            if (!is_null) {
                                Toast.makeText(Comment_submit.this, jsobj.getString("success"), Toast.LENGTH_LONG).show();
                                finish();
                                return;
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

    private void initView() {
        gridView = (GridView) findViewById(R.id.CommentGridview);

    }

    private void initData() {
        //商品星级
        RBDescriptionMatch.setClickable(true);
        /*物流速度*/
        RBLogisticsSpeed.setClickable(true);
        /*服务态度*/
        RBServiceAttitude.setClickable(true);
        RBDescriptionMatch.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
//                Toast.makeText(Comment_submit.this, ratingCount + "", Toast.LENGTH_LONG).show();
                DescriptionMatch = ratingCount;
            }
        });
        RBLogisticsSpeed.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                LogisticsSpeed = ratingCount;
            }
        });
        RBServiceAttitude.setOnRatingChangeListener(new MyRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                ServiceAttitude = ratingCount;
            }
        });
    }

    @OnClick({R.id.tv_back, R.id.image_comment_submit_add, R.id.btn_comment_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.image_comment_submit_add:
                new PhotoPickConfig.Builder(this)
                        .pickMode(PhotoPickConfig.MODE_MULTIP_PICK) //多选，这里有单选和多选
                        .maxPickSize(3)                            //最多可选15张
                        .showCamera(false)                          //是否展示拍照icon,默认展示
                        .clipPhoto(false)                            //是否选完图片进行图片裁剪，默认是false,如果这里设置成true,就算设置了是多选也会被配置成单选
                        .spanCount(4)                               //图库的列数，默认3列，这个数建议不要太大
                        .build();
                break;
            case R.id.btn_comment_submit:
                String content = edtCommentSubmitGoods.getText().toString().trim();

                if (TextUtils.isEmpty(content)) {
                    Toast.makeText(Comment_submit.this, "请对该商品进行评价", Toast.LENGTH_SHORT).show();
                    return;
                }
                initWebData();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case PhotoPickConfig.PICK_REQUEST_CODE:
                //返回的图片地址集合
                photoLists = data.getStringArrayListExtra(PhotoPickConfig.EXTRA_STRING_ARRAYLIST);
                if (photoLists != null && !photoLists.isEmpty()) {
                    gridAdapter = new CommGridAdapter(this, photoLists);
                    gridView.setAdapter(gridAdapter);
                    for (int i = 0; i < photoLists.size(); i++) {
                        byte[] bt = Utils.File2byte(photoLists.get(i));
                        String portrait = Utils.biemap(bt);
                        fileList.add(portrait);
                    }

                }

                break;
        }
    }

}
