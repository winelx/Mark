package com.example.administrator.a3dmark.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.child_pakage.MyMexssageActivity;
import com.example.administrator.a3dmark.easemob.ui.ConversationActivity;
import com.example.administrator.a3dmark.fragment.Pending_evaluation;
import com.example.administrator.a3dmark.fragment.Pending_payment;
import com.example.administrator.a3dmark.fragment.Receiving_Goods;
import com.example.administrator.a3dmark.fragment.Refund_Fragment;
import com.example.administrator.a3dmark.fragment.Wait_Deliver_fragment;
import com.example.administrator.a3dmark.test.MyOrderChange;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 待付款，待付款，待收货，待评价
 * Created by Administrator on 2017/2/27.
 */
public class MyOrder extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.image_title_back)
    ImageView imageTitleBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.tv_title_vice)
    TextView tvTitleVice;
    @BindView(R.id.image_title_message)
    ImageView imageTitleMessage;
    @BindView(R.id.fl_myorder)
    FrameLayout flMyorder;
    private Fragment pending_payment;//待付款
    private Fragment wait_deliver;//待付款
    private Fragment receiving_goods;//待收货
    private Fragment pending_evaluation;//待评价
    private Fragment refund;/*退款*/
    private Fragment[] mFragments;
    private TextView[] Tags;
    private int mIndex;
    // 当前fragment的index
    private int mCurrentTabIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myorder);
        ButterKnife.bind(this);
        initview();
        initFragment();
    }

    private void initFragment() {
        pending_payment = new MyOrderChange();
        wait_deliver = new Wait_Deliver_fragment();
        receiving_goods = new Receiving_Goods();
        pending_evaluation = new Pending_evaluation();
        refund = new Refund_Fragment();
        mFragments = new Fragment[]{pending_payment, wait_deliver, receiving_goods, pending_evaluation, refund};
        Tags = new TextView[5];
        Tags[0] = (TextView) findViewById(R.id.rb_pending_payment);
        Tags[1] = (TextView) findViewById(R.id.rb_wait_deliver);
        Tags[2] = (TextView) findViewById(R.id.rb_receiving_goods);
        Tags[3] = (TextView) findViewById(R.id.rb_pending_evaluation);
        Tags[4] = (TextView) findViewById(R.id.rb_refund);

        // 把第一个tab设为选中状态
        Tags[0].setOnClickListener(this);
        Tags[1].setOnClickListener(this);
        Tags[2].setOnClickListener(this);
        Tags[3].setOnClickListener(this);
        Tags[4].setOnClickListener(this);



        if (!TextUtils.isEmpty(getIntent().getStringExtra("framnt"))){
            // 添加显示已付款未发货
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_myorder, wait_deliver)
                    .show(wait_deliver)
                    .commit();
            // 把第一个tab设为选中状态
            Tags[1].setSelected(true);
            mCurrentTabIndex = 1;
            return;
        }

        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_myorder, pending_payment)
                //.add(R.id.fl_myorder, pending_evaluation)
                //.hide(pending_evaluation)//程序停止运行
                .show(pending_payment)
                .commit();

        // 把第一个tab设为选中状态
        Tags[0].setSelected(true);
    }

    private void initview() {
        tvTitleText.setText("我的订单");
        tvTitleVice.setVisibility(View.GONE);
        imageTitleMessage.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.image_title_back, R.id.image_title_message,
            R.id.rb_pending_payment, R.id.rb_wait_deliver,
            R.id.rb_receiving_goods, R.id.rb_pending_evaluation, R.id.rb_refund})

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_title_back:
                finish();
                break;
            case R.id.image_title_message:
                Intent intent = new Intent(this, ConversationActivity.class);
                startActivity(intent);
                break;
            //待付款
            case R.id.rb_pending_payment:
                mIndex = 0;
                break;
            //待发货
            case R.id.rb_wait_deliver:
                mIndex = 1;
                break;
//            待收货
            case R.id.rb_receiving_goods:
                mIndex = 2;
                break;
            //待评价
            case R.id.rb_pending_evaluation:
                mIndex = 3;
                break;
            //退款
            case R.id.rb_refund:
                mIndex = 4;
                break;
        }

        if (mCurrentTabIndex != mIndex) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(mFragments[mCurrentTabIndex]);
            if (!mFragments[mIndex].isAdded()) {
//                trx.add(flMyorder.getGoodsId(), mFragments[mIndex]);

                trx.replace(flMyorder.getId(), mFragments[mIndex]);
            }
            trx.show(mFragments[mIndex]).commit();
        }
        Tags[mCurrentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        Tags[mIndex].setSelected(true);
        mCurrentTabIndex = mIndex;
    }

}
