package com.example.administrator.a3dmark.child_pakage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.fragment.BabyFragment;
import com.example.administrator.a3dmark.fragment.ShopFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/28.
 * //收藏界面
 */
public class CollectionActivity extends AppCompatActivity {
    @BindView(R.id.tv_collection_baby)
    TextView tvCollectionBaby;
    @BindView(R.id.view_collection_baby)
    View viewCollectionBaby;
    @BindView(R.id.tv_collection_shop)
    TextView tvCollectionShop;
    @BindView(R.id.view_collection_shop)
    View viewCollectionShop;
    @BindView(R.id.fg_collection)
    FrameLayout fgCollection;
    @BindView(R.id.image_collection_back)
    ImageView imageCollectionBack;
    FragmentManager fragmentManager;
    @BindView(R.id.ll_collection_baby)
    LinearLayout llCollectionBaby;
    @BindView(R.id.ll_collection_shop)
    LinearLayout llCollectionShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collection_activity);
        ButterKnife.bind(this);
        Fragment babyfg = new BabyFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fg_collection, babyfg);
        ft.commit();
    }

    @OnClick({R.id.tv_collection_baby, R.id.tv_collection_shop, R.id.ll_collection_baby, R.id.ll_collection_shop})
    public void onClick(View view) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.tv_collection_baby:
                tvCollectionBaby.setTextColor(getResources().getColor(R.color.Pink));
                viewCollectionBaby.setVisibility(View.VISIBLE);
                tvCollectionShop.setTextColor(getResources().getColor(R.color.text_black));
                viewCollectionShop.setVisibility(View.GONE);
                Fragment babyfg = new BabyFragment();
                ft.replace(R.id.fg_collection, babyfg);
                break;
            case R.id.tv_collection_shop:
                tvCollectionShop.setTextColor(getResources().getColor(R.color.Pink));
                viewCollectionShop.setVisibility(View.VISIBLE);
                tvCollectionBaby.setTextColor(getResources().getColor(R.color.text_black));
                viewCollectionBaby.setVisibility(View.GONE);
                Fragment shop = new ShopFragment();
                ft.replace(R.id.fg_collection, shop);
                break;
            case R.id.ll_collection_baby:
                Fragment babyfg2 = new BabyFragment();
                ft.replace(R.id.fg_collection, babyfg2);
                break;
            case R.id.ll_collection_shop:
                Fragment shop2 = new ShopFragment();
                ft.replace(R.id.fg_collection, shop2);
                break;
        }
        ft.commit();
    }

    @OnClick(R.id.image_collection_back)
    public void onClick() {
        finish();
    }
}
