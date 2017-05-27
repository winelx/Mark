package com.example.administrator.a3dmark.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.easemob.ui.ConversationActivity;
import com.example.administrator.a3dmark.fragment.FacialCareFragment;
import com.example.administrator.a3dmark.fragment.HairCareFragment;
import com.example.administrator.a3dmark.fragment.PerfumeMakeupFragment;
import com.example.administrator.a3dmark.fragment.PhysicalCareFragment;

/**
 * Created by LGY on 2017/5/15.
 * 美妆护肤
 */

public class BeautyskinCareActivity extends BaseActivity implements View.OnClickListener{

    private FrameLayout mFrameLayout;
    private ImageView back,image_title_message;
    public TextView[] mTabs;
    private Fragment[] mFragments;
    private int mIndex;
    // 当前fragment的index
    private int mCurrentTabIndex = 0;
    private Fragment facialCareFragment;
    private Fragment perfumeMakeupFragment;
    private Fragment hairCareFragment;
    private Fragment physicalCareFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautyskin_care);


        back = (ImageView) findViewById(R.id.back);
        image_title_message = (ImageView) findViewById(R.id.image_title_message);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame);
        facialCareFragment = new FacialCareFragment();
        perfumeMakeupFragment = new PerfumeMakeupFragment();
        hairCareFragment = new HairCareFragment();
        physicalCareFragment = new PhysicalCareFragment();

        mFragments = new Fragment[]{facialCareFragment, perfumeMakeupFragment, hairCareFragment, physicalCareFragment};
        mTabs = new TextView[4];
        mTabs[0] = (TextView) findViewById(R.id.btn_facial_care);
        mTabs[1] = (TextView) findViewById(R.id.btn_perfume_makeup);
        mTabs[2] = (TextView) findViewById(R.id.btn_hair_care);
        mTabs[3] = (TextView) findViewById(R.id.btn_physical_care);
        mTabs[0].setOnClickListener(this);
        mTabs[1].setOnClickListener(this);
        mTabs[2].setOnClickListener(this);
        mTabs[3].setOnClickListener(this);

        back.setOnClickListener(this);
        image_title_message.setOnClickListener(this);

        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame, facialCareFragment)
                .show(facialCareFragment)
                .commit();

        // 把第一个tab设为选中状态
        mTabs[0].setSelected(true);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.image_title_message:
                startActivity(new Intent(this,ConversationActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP));
                break;
            case R.id.btn_facial_care:
                mIndex = 0;
                break;
            case R.id.btn_perfume_makeup:
                mIndex = 1;
                break;
            case R.id.btn_hair_care:
                mIndex = 2;
                break;
            case R.id.btn_physical_care:
                mIndex = 3;
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
}
