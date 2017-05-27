package com.example.administrator.a3dmark.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 完善信息
 * Created by Administrator on 2017/2/21.
 */
public class Perfect_information extends Activity {
    @BindView(R.id.image_finish)
    ImageView imageFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_submit)
    TextView btnSubmit;
    @BindView(R.id.progress_height)
    SeekBar progressHeight;
    @BindView(R.id.text_height)
    TextView textHeight;
    @BindView(R.id.progress_weight)
    SeekBar progressWeight;
    @BindView(R.id.text_weight)
    TextView textWeight;
    @BindView(R.id.progress_bust)
    SeekBar progressBust;
    @BindView(R.id.text_bust)
    TextView textBust;
    @BindView(R.id.progress_waist)
    SeekBar progressWaist;
    @BindView(R.id.text_waist)
    TextView textWaist;
    @BindView(R.id.progress_hipline)
    SeekBar progressHipline;
    @BindView(R.id.text_hipline)
    TextView textHipline;
    @BindView(R.id.man)
    TextView man;
    @BindView(R.id.girl)
    TextView girl;
    @BindView(R.id.nick)
    TextView nick;

    String userId;
    @BindView(R.id.image_height_reduce)
    ImageView imageHeightReduce;
    @BindView(R.id.image_height_add)
    ImageView imageHeightAdd;
    @BindView(R.id.image_weight_reduce)
    ImageView imageWeightReduce;
    @BindView(R.id.image_weight_add)
    ImageView imageWeightAdd;
    @BindView(R.id.image_bust_reduce)
    ImageView imageBustReduce;
    @BindView(R.id.image_bust_add)
    ImageView imageBustAdd;
    @BindView(R.id.image_waist_reduce)
    ImageView imageWaistReduce;
    @BindView(R.id.image_waist_add)
    ImageView imageWaistAdd;
    @BindView(R.id.image_hipline_reduce)
    ImageView imageHiplineReduce;
    @BindView(R.id.image_hipline_add)
    ImageView imageHiplineAdd;
    private String pwd;
    private String nickName;
    private int sex;
    private int height;
    private int weight;
    private int bust;
    private int waist;
    private int hipline;
    private String Username;
    private ImageView Perfect_Photo;
    private static final int PHOTO_REQUEST_CAREMA = 5;// 拍照
    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    private int progress_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfact_information);
        ButterKnife.bind(this);
        pwd = getIntent().getStringExtra("pwd");
        userId = (String) SharedUtil.getParam(Perfect_information.this, "userid", "");
        Username = getIntent().getStringExtra("username");
        girl.setSelected(true);
        sex = 2;
        initView();

    }

    private void initView() {
        tvTitle.setText("完善信息");
        Perfect_Photo = (ImageView) findViewById(R.id.Perfect_Photo);
//        Perfect_Photo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(Perfect_information.this);
//                builder.setIcon(R.drawable.logo);
//                // 指定下拉列表的显示数据
//                final String[] cities = {"相机", "相册"};
//                // 设置一个下拉的列表选择项
//                builder.setItems(cities, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if (which == 0) {
//                            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                            startActivityForResult(camera, PHOTO_REQUEST_CAREMA);
//                        } else {
//                            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
//                            pickIntent.setDataAndType(
//                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                                    "image/*");
//                            startActivityForResult(pickIntent, 1);
//                        }
//                    }
//                });
//                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        return;
//                    }
//                });
//                builder.show();
//            }
//        });
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girl.setSelected(false);
                man.setSelected(true);
                sex = 1;
            }
        });
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                man.setSelected(false);
                girl.setSelected(true);
                sex = 2;
            }
        });

        btnSubmit.setVisibility(View.VISIBLE);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//提交个人信息
                nickName = nick.getText().toString().trim();
                if (TextUtils.isEmpty(nickName)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Perfect_information.this);
                    builder.setIcon(R.drawable.logo)
                            .setMessage("确定不取一个好听的呢称吗？")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    requst();
                                }
                            })
                            .setNegativeButton("去设置", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return;
                }
                requst();
            }
        });
        progressHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                //progress_add = progress;
                //textHeight.setText("身高" + Integer.toString(progress_add) + "cm");
                textHeight.setText("身高" + Integer.toString(progress) + "cm");
                //身高
                height = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "停止滑动！");
            }
        });
        progressBust.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                textBust.setText("胸围" + Integer.toString(progress) + "cm");
                //胸围
                bust = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "停止滑动！");
            }
        });
        progressHipline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                textHipline.setText("臀围" + Integer.toString(progress) + "cm");
                //臀围
                hipline = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "停止滑动！");
            }
        });
        progressWaist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                textWaist.setText("腰围" + Integer.toString(progress) + "cm");
                //腰围
                waist = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "停止滑动！");
            }
        });
        progressWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // 当拖动条的滑块位置发生改变时触发该方法,在这里直接使用参数progress，即当前滑块代表的进度值
                textWeight.setText("体重" + Integer.toString(progress) + "kg");
                //体重
                weight = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "开始滑动！");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("------------", "停止滑动！");
            }
        });
    }


    /*
     * 提交信息
     */
    private void requst() {
        final ProgressDialog mDialog = ProgressDialog.show(Perfect_information.this, "提交信息", "提交中...");
        OkGo.post(Contants.MESSAGE_REGIS)
                .params("usersMain.pwd", pwd)
                .params("usersMain.username", Username)
                .params("usersPersonnel.nickname", nickName)
                .params("usersPersonnel.sex", sex)
                .params("usersPersonnel.weight", weight)
                .params("usersPersonnel.height", height)
                .params("usersPersonnel.bust", bust)
                .params("usersPersonnel.waistline", waist)
                .params("usersPersonnel.hipline", hipline)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        mDialog.dismiss();
                        try {
                            JSONObject json = new JSONObject(s);
                            //buyanjing//再次判断
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(Perfect_information.this, json.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Toast.makeText(Perfect_information.this, "提交成功", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Perfect_information.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        mDialog.dismiss();
                        Toast.makeText(Perfect_information.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @OnClick(R.id.image_finish)
    void image_finish(View v) {
        startActivity(new Intent(this, CodePasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
        this.finish();
    }

    //点击编辑框以外的位置隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }   // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }


    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};     //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode,
//                                    Intent datas) {
//        if (requestCode == 5 // 拍照
//                && resultCode == Activity.RESULT_OK) {
//            Bundle bundle = datas.getExtras();
//            // 获取相机返回的数据，并转换为图片格式s
//            Bitmap bitmap = (Bitmap) bundle.get("data");
//            byte[] by = Bitmap2Bytes(bitmap);
//            String portrait = biemap(by);
//            photo(portrait);
//            Perfect_Photo.setImageBitmap(bitmap);
//
//        } else {
//            if (requestCode == 1 && requestCode == REQUESTCODE_PICK) {// 相册
//
//                if (datas == null || datas.getData() == null) {
//                    return;
//                }
//                startPhotoZoom(datas.getData());
//
//            } else if (requestCode == REQUESTCODE_CUTTING) {
//                if (datas != null) {
//                    setPicToView(datas);
//                }
//            }
//            super.onActivityResult(requestCode, resultCode, datas);
//        }
//    }

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

    public String biemap(byte[] b) {
        StringBuffer sb = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1) {
                sb.append("0" + stmp);
            } else {
                sb.append(stmp);
            }

        }
        return sb.toString();
    }

    public void photo(String por) {
        OkGo.post(Contants.PORTRAIT)
                .params("userId", userId)
                .params("photo", por)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
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
            Perfect_Photo.setImageBitmap(photo);
            photo(portrait);
        }
    }

    @OnClick({R.id.image_height_reduce, R.id.image_height_add, R.id.image_weight_reduce,
            R.id.image_weight_add, R.id.image_bust_reduce, R.id.image_bust_add,
            R.id.image_waist_reduce, R.id.image_waist_add, R.id.image_hipline_reduce,
            R.id.image_hipline_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_height_reduce:
                break;
            case R.id.image_height_add:
//                progress_add++;
//                textHeight.setText("身高" + Integer.toString(progress_add) + "cm");
                break;
            case R.id.image_weight_reduce:
                break;
            case R.id.image_weight_add:
                break;
            case R.id.image_bust_reduce:
                break;
            case R.id.image_bust_add:
                break;
            case R.id.image_waist_reduce:
                break;
            case R.id.image_waist_add:
                break;
            case R.id.image_hipline_reduce:
                break;
            case R.id.image_hipline_add:
                break;
        }
    }
}
