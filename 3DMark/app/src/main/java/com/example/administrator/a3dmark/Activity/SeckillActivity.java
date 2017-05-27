package com.example.administrator.a3dmark.Activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.fragment.SimpleFragment;
import com.example.administrator.a3dmark.util.BuyingPagerIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 限时秒杀
 */
public class SeckillActivity extends AppCompatActivity {

    private List<Fragment> mTabContents = new ArrayList<>();
    private ImageView image_title_white_back;
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private BuyingPagerIndicator mBuyingPagerIndicator;
    private ArrayList<String> mId;
    private ArrayList<String> mStartTime;
    private ArrayList<String> mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_seckill);

        mId = getIntent().getStringArrayListExtra("id");
        mStartTime = getIntent().getStringArrayListExtra("startTime");
        mStatus = getIntent().getStringArrayListExtra("status");

        initView();
        initDatas();

    }


    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mBuyingPagerIndicator = (BuyingPagerIndicator) findViewById(R.id.buying_indicator);
        image_title_white_back = (ImageView) findViewById(R.id.image_title_white_back);
        image_title_white_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mId.clear();
                mTabContents.clear();
                mStartTime.clear();
                mAdapter.notifyDataSetChanged();
                finish();
            }
        });
    }



    /**
     * 初始化Fragment
     */
    private void initDatas() {

        for (String data : mId) {
            SimpleFragment fragment = SimpleFragment.newInstance(data);
            mTabContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {

                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {

                return mTabContents.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mStartTime.get(position);
            }
        };

        setListner();
    }


    private void setListner() {
        mViewPager.setAdapter(mAdapter);
        //设置关联的ViewPager
        mBuyingPagerIndicator.setViewPager(mViewPager);
        mBuyingPagerIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(SeckillActivity.this, "设置监听器：onPageSelected: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //Test  如果直接设置viewPager去setCurrentItem，会导致显示错误，因为该控件内部还没来得及布局完毕
        //所以需要添加layout监听，layout完毕后再来设置
        mBuyingPagerIndicator.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT > 16) {
                    mBuyingPagerIndicator.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mBuyingPagerIndicator.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                mViewPager.setCurrentItem(1);//索引值 从1开始
            }
        });
    }

}