package com.example.administrator.a3dmark.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.child_pakage.PersonManagerActivity;
import com.example.administrator.a3dmark.child_pakage.Person_Addr;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.example.administrator.a3dmark.util.Utils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.liji.circleimageview.CircleImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.example.administrator.a3dmark.util.Utils.biemap;

/**
 * Created by Administrator on 2017/2/24.
 */
public class PersonalInformation extends Activity {

    private static final int PHOTO_REQUEST_CAREMA = 5;// 拍照
    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.image_mine_head)
    CircleImageView imageMineHead;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    private Context mContext;
    public TextView tv_person_nickname, tv_person_signature, tv_person_addr, tv_person_manager, tv_person_qrcode;
    String userId;
    LinearLayout headphoto;
    String headimageView;
    private EditText editText;
    Context context;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_information);
        ButterKnife.bind(this);
        mContext = PersonalInformation.this;
        Intent intent = this.getIntent();
        headimageView = (String) intent.getSerializableExtra("image");
        userId = (String) SharedUtil.getParam(mContext, "userid", "");
        initView();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initView() {
        tvTitleVice.setVisibility(View.GONE);
        tvTitleText.setText("个人信息");
        Glide.with(this).load(headimageView).into(imageMineHead);
        tv_person_nickname = (TextView) findViewById(R.id.tv_person_nickname);//昵称
        tv_person_signature = (TextView) findViewById(R.id.tv_person_signature);//个性签名
        tv_person_qrcode = (TextView) findViewById(R.id.tv_person_qrcode);//二维码
        tv_person_manager = (TextView) findViewById(R.id.tv_person_manager);//人物管理
        tv_person_addr = (TextView) findViewById(R.id.tv_person_addr);//收货地址
        tv_person_nickname.setOnClickListener(new MyClickListener());
        tv_person_signature.setOnClickListener(new MyClickListener());
        tv_person_addr.setOnClickListener(new MyClickListener());
        tv_person_manager.setOnClickListener(new MyClickListener());
        tv_person_qrcode.setOnClickListener(new MyClickListener());

    }

    @OnClick(R.id.image_title_back)
    void back() {
        finish();
    }


    @OnClick(R.id.image_mine_head)
    void setImageMineHead() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalInformation.this);
        builder.setIcon(R.drawable.logo);
        // 指定下拉列表的显示数据
        final String[] cities = {"相机", "相册"};
        // 设置一个下拉的列表选择项
        builder.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera, PHOTO_REQUEST_CAREMA);
                } else {
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    pickIntent.setDataAndType(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            "image/*");
                    startActivityForResult(pickIntent, 1);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {
        if (requestCode == 5 // 拍照
                && resultCode == Activity.RESULT_OK) {
            Bundle bundle = datas.getExtras();
            // 获取相机返回的数据，并转换为图片格式s
            Bitmap bitmap = (Bitmap) bundle.get("data");
            byte[] by = Bitmap2Bytes(bitmap);
            String portrait = Utils.biemap(by);
            photo(portrait);
            imageMineHead.setImageBitmap(bitmap);

        } else {
            if (requestCode == 1 && requestCode == REQUESTCODE_PICK) {// 相册

                if (datas == null || datas.getData() == null) {
                    return;
                }
                startPhotoZoom(datas.getData());

            } else if (requestCode == REQUESTCODE_CUTTING) {
                if (datas != null) {
                    setPicToView(datas);
                }
            }
            super.onActivityResult(requestCode, resultCode, datas);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("PersonalInformation Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    class MyClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.tv_person_nickname://昵称修改
                    reviseName();
                    break;
                case R.id.tv_person_signature://个性签名
                    signature();
                    break;
                case R.id.tv_person_addr://地址管理
                    Intent intent = new Intent(PersonalInformation.this, Person_Addr.class);
                    startActivity(intent);
                    break;
                case R.id.tv_person_manager://人物管理
                    Intent personmanager = new Intent(PersonalInformation.this, PersonManagerActivity.class);
                    startActivity(personmanager);
                    break;
                case R.id.tv_person_qrcode://二维码
                    break;

            }
        }
    }


    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 200);
        intent.putExtra("aspectY", 250);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            byte[] by = Bitmap2Bytes(photo);
            String portrait = biemap(by);
            imageMineHead.setImageBitmap(photo);
            photo(portrait);
        }
    }


    /**
     * 修改昵称
     */
    public void reviseName() {
        final EditText editText = new EditText(mContext);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(mContext);
        inputDialog.setTitle("请输入").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        if (name.length() != 0) {
                            nickname(name);
                        }
                    }
                }).show();

    }

    /**
     * bitmap 转 byte
     *
     * @param bm
     * @return
     */
    public byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    public void photo(String por) {
        OkGo.post(Contants.PORTRAIT)
                .params("userId", userId)
                .params("photo", por)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void nickname(String str) {
        OkGo.post(Contants.MODIFY_NICKNAME)
                .params("userId", userId)
                .params("nickname", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    //个性签名接口
    public void signatureOKgo(String str) {
        OkGo.post(Contants.Signature)
                .params("userId", userId)
                .params("signature", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void signature() {
        final EditText editText = new EditText(mContext);
        AlertDialog.Builder inputDialog =
                new AlertDialog.Builder(mContext);
        inputDialog.setTitle("请输入").setView(editText);
        inputDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = editText.getText().toString();
                        if (name.length() != 0) {
                            signatureOKgo(name);
                        }
                    }
                }).show();

    }

}




