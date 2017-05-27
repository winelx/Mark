package com.example.administrator.a3dmark.child_pakage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.Activity.MainActivity;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.util.SharedUtil;
import com.lzy.okgo.cache.CacheManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/28.
 */
public class SetupActivity extends Activity {
    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.tv_setup_cache)
    TextView tvSetupCache;
    @BindView(R.id.tv_setup_Password)
    TextView tvSetupPassword;
    @BindView(R.id.tv_setup_agreement)
    TextView tvSetupAgreement;
    @BindView(R.id.tv_setup_Feedback)
    TextView tvSetupFeedback;
    @BindView(R.id.tv_setup_Signout)
    TextView tvSetupSignout;
    PopupWindow popupWindow;
    private String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup_activity);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvTitleText.setText("设置");
        tvTitleVice.setVisibility(View.GONE);
    }

    @OnClick({R.id.image_title_back, R.id.tv_setup_cache, R.id.tv_setup_Password,
            R.id.tv_setup_agreement, R.id.tv_setup_Feedback, R.id.tv_setup_Signout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_title_back:
                finish();
                break;
            //清理缓存
            case R.id.tv_setup_cache:
                showPopupWindow();
                break;
            //修改密码
            case R.id.tv_setup_Password:
                Intent password_intent = new Intent(SetupActivity.this, Modify_password.class);
                startActivity(password_intent);
                break;
//            //用户协议
//            case R.id.tv_setup_agreement:
//                Intent agreement_intent = new Intent(SetupActivity.this, WebViewActivity.class);
//                startActivity(agreement_intent);
//                break;
            //意见反馈
            case R.id.tv_setup_Feedback:
                Intent agreeintent = new Intent(SetupActivity.this, User_agreement.class);
                startActivity(agreeintent);
                break;
            //退出当前账号
            case R.id.tv_setup_Signout:
                signoutpop();
                break;
        }
    }

    private void signoutpop() {
        final View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);
//        @BindView(R.id.btn_setup_cache_cancel)
        TextView btnSetupCacheCancel = (TextView) contentView.findViewById(R.id.btn_setup_cache_cancel);
//        @BindView(R.id.btn_setup_cache_ensure)
        TextView btnSetupCacheEnsure = (TextView) contentView.findViewById(R.id.btn_setup_cache_ensure);
        TextView tv_pop_detail = (TextView) contentView.findViewById(R.id.tv_pop_detail);
        tv_pop_detail.setText("确认退出当前账号");
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
                    setBackgroundAlpha(SetupActivity.this, 1f);
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
                //退出登录logout, 清理所有缓存
                CacheManager.INSTANCE.clear();
                //同时退出环信
                SharedUtil.setParam(SetupActivity.this, "userid", "");
                startActivity(new Intent(SetupActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                popupWindow.dismiss();
            }
        });

    }

    private void showPopupWindow() {

        // 一个自定义的布局，作为显示的内容
        final View contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_window, null);
//        @BindView(R.id.btn_setup_cache_cancel)
        TextView btnSetupCacheCancel = (TextView) contentView.findViewById(R.id.btn_setup_cache_cancel);
//        @BindView(R.id.btn_setup_cache_ensure)
        TextView btnSetupCacheEnsure = (TextView) contentView.findViewById(R.id.btn_setup_cache_ensure);
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
                    setBackgroundAlpha(SetupActivity.this, 1f);
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
                popupWindow.dismiss();
                Toast.makeText(SetupActivity.this, "清理完成!", Toast.LENGTH_SHORT).show();
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
