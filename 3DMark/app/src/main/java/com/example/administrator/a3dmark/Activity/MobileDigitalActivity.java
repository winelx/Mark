package com.example.administrator.a3dmark.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.easemob.ui.ConversationActivity;
import com.example.administrator.a3dmark.fragment.FruitFragment;
import com.example.administrator.a3dmark.fragment.SnacksFragment;
import com.example.administrator.a3dmark.fragment.SpecialtyFragment;
import com.example.administrator.a3dmark.fragment.Tea_DrinksFragment;
import com.example.administrator.a3dmark.fragment.WineFragment;

/**
 * Created by LGY on 2017/5/15.
 * 农副产品<-->手机数码
 */

public class MobileDigitalActivity extends BaseActivity implements View.OnClickListener{

    private FrameLayout mFrameLayout;
    private ImageView back,image_title_message;
    public ImageButton[] mTabs;
    private Fragment[] mFragments;
    private int mIndex;
    // 当前fragment的index
    private int mCurrentTabIndex = 0;
    private Fragment fruitFragment;
    private Fragment specialtyFragment;
    private Fragment wineFragment;
    private Fragment tea_DrinksFragment;
    private Fragment snacksFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_digital);

        back = (ImageView) findViewById(R.id.back);
        image_title_message = (ImageView) findViewById(R.id.image_title_message);
        mFrameLayout = (FrameLayout) findViewById(R.id.frame);
        fruitFragment = new FruitFragment();
        specialtyFragment = new SpecialtyFragment();
        wineFragment = new WineFragment();
        tea_DrinksFragment = new Tea_DrinksFragment();
        snacksFragment = new SnacksFragment();

        mFragments = new Fragment[]{fruitFragment, specialtyFragment, wineFragment, tea_DrinksFragment, snacksFragment};
        mTabs = new ImageButton[5];
        mTabs[0] = (ImageButton) findViewById(R.id.btn_fruit);
        mTabs[1] = (ImageButton) findViewById(R.id.btn_specialty);
        mTabs[2] = (ImageButton) findViewById(R.id.btn_wine);
        mTabs[3] = (ImageButton) findViewById(R.id.btn_tea_drinks);
        mTabs[4] = (ImageButton) findViewById(R.id.btn_snacks);
        mTabs[0].setOnClickListener(this);
        mTabs[1].setOnClickListener(this);
        mTabs[2].setOnClickListener(this);
        mTabs[3].setOnClickListener(this);
        mTabs[4].setOnClickListener(this);

        back.setOnClickListener(this);
        image_title_message.setOnClickListener(this);

        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frame, fruitFragment)
                .show(fruitFragment)
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
            case R.id.btn_fruit:
                mIndex = 0;
                break;
            case R.id.btn_specialty:
                mIndex = 1;
                break;
            case R.id.btn_wine:
                mIndex = 2;
                break;
            case R.id.btn_tea_drinks:
                mIndex = 3;
                break;
            case R.id.btn_snacks:
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
}
