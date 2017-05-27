package com.example.administrator.a3dmark.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.fragment.FittingRoomFragment;
import com.example.administrator.a3dmark.fragment.HomePagerFragment;
import com.example.administrator.a3dmark.fragment.MineFragment;
import com.example.administrator.a3dmark.fragment.ShoppingCardFragment;
import com.example.administrator.a3dmark.test.MarkFragment2;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.UpdateService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 主界面
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private FrameLayout mFrameLayout;
    public RadioButton[] mTabs;
    private Fragment[] mFragments;
    private int mIndex;
    // 当前fragment的index
    private int mCurrentTabIndex = 0;
    private Fragment homepager;
    private Fragment mark;
    private Fragment fitting_room;
    private Fragment shoppingcard;
    private Fragment mine;
    private CacheEntity cache;
    //传过来的启动fragment
    private int mLoginIndex;
    private Toolbar mToolbar;
    private Context context;
    String version;
    String URL;
    boolean lean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        context = MainActivity.this;
        mFrameLayout = (FrameLayout) findViewById(R.id.fl_show);
        homepager = new HomePagerFragment();
        mark = new MarkFragment2();
//        mark = new MarkFragment();
        fitting_room = new FittingRoomFragment();
        shoppingcard = new ShoppingCardFragment();
        mine = new MineFragment();

        //版本号
        version = getVersionName(context);
        OkGo.post(Contants.VERSION)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(s);
                            URL = jsonObject.getString("version");
                            if (version.equals(URL)) {
                                return;
                            } else {
                                //启动服务
                                Intent service = new Intent(MainActivity.this, UpdateService.class);
                                startService(service);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        mFragments = new Fragment[]{homepager, mark, fitting_room, shoppingcard, mine};
        mTabs = new RadioButton[5];
        mTabs[0] = (RadioButton) findViewById(R.id.rb_homepager);
        mTabs[1] = (RadioButton) findViewById(R.id.rb_mark);
        mTabs[2] = (RadioButton) findViewById(R.id.rb_fitting_room);
        mTabs[3] = (RadioButton) findViewById(R.id.rb_shopping_card);
        mTabs[4] = (RadioButton) findViewById(R.id.rb_mine);
        mTabs[0].setOnClickListener(this);
        mTabs[1].setOnClickListener(this);
        mTabs[2].setOnClickListener(this);
        mTabs[3].setOnClickListener(this);
        mTabs[4].setOnClickListener(this);

        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_show, homepager)
                //.add(R.id.fl_show, mark)
                //.hide(mark)//
                .show(homepager)
                .commit();
        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_homepager:
                mIndex = 0;
                break;
            case R.id.rb_mark:
                mIndex = 1;
                break;
            case R.id.rb_fitting_room:
                mIndex = 2;
                break;
            case R.id.rb_shopping_card:
                mIndex = 3;
                break;
            case R.id.rb_mine:
                mIndex = 4;
                break;
        }

        if (mCurrentTabIndex != mIndex) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[mCurrentTabIndex]);
            if (!mFragments[mIndex].isAdded()) {
                trx.add(mFrameLayout.getId(), mFragments[mIndex]);
            }
            trx.show(mFragments[mIndex]).commit();
        }
        mTabs[mCurrentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[mIndex].setSelected(true);
        mCurrentTabIndex = mIndex;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {

            if (keyCode == KeyEvent.KEYCODE_BACK) {
                this.finish();
                return true;
            }
        }
        return false;
    }

    //版本名
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }


}
