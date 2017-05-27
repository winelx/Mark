package com.example.administrator.a3dmark.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.HotAdapter;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.bean.Goods_Main;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.MyListview;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.recker.flybanner.FlyBanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;


/**
 * 人气商品
 */
public class HotcomActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.image_title_white_back)
    ImageView imageTitleWhiteBack;
    @BindView(R.id.tv_title_white)
    TextView tvTitleWhite;
    @BindView(R.id.tv_collection_title)
    TextView tvCollectionTitle;
    @BindView(R.id.tv_title_num)
    TextView tvTitleNum;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.tv_title_white_vice)
    TextView tvTitleWhiteVice;
    private RelativeLayout reLayout;
    private MyListview mListView;
    private FlyBanner loopPicture;
    private HotAdapter mAdapter;
    private Context mContext;
    private SwipeRefreshLayout swiper;
    private List<Goods_Main> mainlist = new ArrayList<>();
    private List<Banners> Flybanners = new ArrayList();
    private List<String> ImagesUrl = new ArrayList();
    int height = 0;
    private int i = 1;
    private int s = 1;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "你已进入没有网络的次元", Toast.LENGTH_SHORT).show();
                    swiper.setRefreshing(false);
                    break;
                case 2:
                    Flybanners.clear();
                    ImagesUrl.clear();
                    mainlist.clear();
                    mAdapter.notifyDataSetChanged();
                    initBanner();
                    Loider(s);
                    i = 1;
                    swiper.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot);
        mContext = getApplicationContext();
        ButterKnife.bind(this);
        swiper = (SwipeRefreshLayout) findViewById(R.id.swip);
        mListView = (MyListview) findViewById(R.id.ListView);


        View headerView = View.inflate(this, R.layout.flybanner, null);
        reLayout = (RelativeLayout) headerView.findViewById(R.id.reLayout);
        reLayout.setVisibility(View.GONE);
        loopPicture = (FlyBanner) headerView.findViewById(R.id.loopPicture);
        //轮播图点击事件
        loopPicture.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                toast("点击了第" + position + "张图片");
                Intent intent = new Intent(HotcomActivity.this, Boutique_Detail.class);
                intent.putExtra("goods_id", Flybanners.get(position).getId());
                startActivity(intent);
            }
        });
        mListView.addHeaderView(headerView);

        //为SwipeRefreshLayout设置监听事件
        swiper.setOnRefreshListener(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        swiper.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mListView.setFocusable(false);
        initView();
        initBanner();
        Loider(s);
        mAdapter = new HotAdapter(mainlist, mContext);
        mListView.setAdapter(mAdapter);
        /*
        *上拉加载更多
         */
        mListView.setOnLoadMoreListener(new MyListview.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                loadMore();
            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                Goods_Main goods_main = null;
                if (position <= mainlist.size() + 1) {
                    goods_main = mainlist.get(position - 1);
                }
                if (null != goods_main) {
                    Intent intent = new Intent(HotcomActivity.this, Boutique_Detail.class);
                    intent.putExtra("goods_id", goods_main.getId());
                    startActivity(intent);
                }
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    /**
     * 上拉加载更多
     */
    private void loadMore() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i += 1;
                Loider(i);
            }
        }.start();
    }

    /**
     * 轮播图网络请求
     */
    private void initBanner() {
        OkGo.get(Contants.RECOMM)
                .connTimeOut(500)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject jsonobject = new JSONObject(s);
                            JSONArray jsArray = jsonobject.getJSONArray("success");
                            for (int i = 0; i < jsArray.length(); i++) {
                                Banners flyBanners = new Banners();
                                JSONObject job = jsArray.getJSONObject(i);
                                flyBanners.setImg(job.getString("goodsCover"));
                                flyBanners.setId(job.getString("goodsId"));
                                Flybanners.add(flyBanners);
                            }
                            if (Flybanners.size() != 0) {
                                for (Banners flyBanners : Flybanners) {
                                    ImagesUrl.add(flyBanners.getImg());
                                }
                                loopPicture.setImagesUrl(ImagesUrl);
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

    private void Loider(int i) {
        OkGo.post(Contants.HOT)
                .connTimeOut(5000)
                .params("page", i)
                .params("row", 20)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            JSONObject js = new JSONObject(s);
                            JSONArray jsArray = js.getJSONArray("success");
                            Goods_Main goods_main = null;//javabean
                            for (int j = 0; j < jsArray.length(); j++) {
                                goods_main = new Goods_Main();
                                JSONObject jsonbobject = jsArray.getJSONObject(j);
                                goods_main.setCount(jsonbobject.getString("collectNum"));
                                goods_main.setId(jsonbobject.getString("goodsId"));
                                goods_main.setImg(jsonbobject.getString("goodsCover"));
                                goods_main.setName(jsonbobject.getString("goodsName"));
                                goods_main.setPrice(jsonbobject.getString("priceModeNew"));
                                goods_main.setSortnum(jsonbobject.getString("sortnum"));
                                mainlist.add(goods_main);
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mAdapter.notifyDataSetChanged();
                                    mListView.setLoadCompleted();
                                }
                            });
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
        tvTitleWhite.setText("人气商品");
    }

    @OnClick(R.id.image_title_white_back)
    public void onClick() {
        finish();
    }

    @Override
    public void onRefresh() {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Message message = new Message();
                    message.what = 2;
                    handler.sendMessage(message);
                }
            }).start();
        } else {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Hotcom Page") // TODO: Define a title_trans for the content shown.
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

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void toast(String str1) {
        Toast.makeText(HotcomActivity.this, str1, Toast.LENGTH_SHORT).show();
    }
}
