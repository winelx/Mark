package com.example.administrator.a3dmark.Activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.MyAdapter;
import com.example.administrator.a3dmark.bean.Pro;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.MyListview;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

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

/*今日推荐*/

public class RecommActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.image_title_white_back)
    ImageView imageTitleWhiteBack;
    @BindView(R.id.tv_title_white)
    TextView tvTitleWhite;

    private Context context;
    private Pro pro;
    private JSONArray jsArray;
    private MyListview mListView;
    private MyAdapter mAdapter;
    private Context mContext;
    private List<Pro> mlist = new ArrayList<>();
    private int i = 1;
    private int s = 1;
    //    private SwipeRefresh refresh_recommend;
    private SwipeRefreshLayout swipe;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(mContext, "你已进入没有网络的次元", Toast.LENGTH_SHORT).show();
                    swipe.setRefreshing(false);
                    break;
                case 2:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mlist.clear();
                            initData(s);
                            i = 1;
                            mAdapter.notifyDataSetChanged();
                            swipe.setRefreshing(false);
                        }
                    });
                    break;
                case 3:
                    ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo info = connMgr.getActiveNetworkInfo();
                    if (info != null && info.isConnected()) {

                    } else {
                        Toast.makeText(RecommActivity.this, "你已进入没有网络的次元！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recomm_activity);
        ButterKnife.bind(this);
        mContext = RecommActivity.this;
        initView();
        initData(s);
    }

    @Override
    public void onStart() {
//        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo info = connMgr.getActiveNetworkInfo();
//        if (info != null && info.isConnected()) {
//        } else {
//            Toast.makeText(RecommActivity.this, "你已进入没有网络的次元！", Toast.LENGTH_SHORT).show();
//        }
        super.onStart();
    }

    private void initData(int i) {
        OkGo.post(Contants.RECOMMEND)
                .connTimeOut(5000)
                .params("page", i)
                .params("row", 3)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject jsobj = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(RecommActivity.this, "亲，没有数据咯！", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            jsArray = jsobj.getJSONArray("success");
                            for (int i = 0; i < jsArray.length(); i++) {
                                JSONObject object = jsArray.getJSONObject(i);
                                pro = new Pro();
                                Log.i("sss", "1");
                                ArrayList<String> goodsList = new ArrayList<String>();
                                String goodsCovers = object.getString("goodsCovers");
                                JsonArray jsonArray = new JsonParser().parse(goodsCovers).getAsJsonArray();
                                Log.i("sss", "2");
                                for (int e = 0; e < jsonArray.size(); e++) {
                                    JsonObject object1 = jsonArray.get(e).getAsJsonObject();
                                    String goodscover = object1.get("goodsCover").getAsString();
                                    String goodsId = object1.get("goodsId").getAsString();
                                    goodsList.add(goodsId);
                                    Log.i("sss", "3");
                                    if (e == 0) {
                                        pro.setImg1(goodscover);
                                    }
                                    if (e == 1) {
                                        pro.setImg2(goodscover);
                                    }
                                    if (e == 2) {
                                        pro.setImg3(goodscover);
                                    }
                                }
                                pro.setGoodsId(goodsList);
                                pro.setBussiness_id(object.getString("bussinessId"));
                                pro.setCount(object.getString("collectNum"));
                                pro.setStoreName(object.getString("bussinessName"));
                                pro.setLogo(object.getString("logo"));
                                pro.setIntroduce(object.getString("introduce"));
                                Log.i("sss", new Gson().toJson(pro));
                                mlist.add(pro);
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
//                        Toast.makeText(RecommActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    private void initView() {
        tvTitleWhite.setText("今日推荐");
        swipe = (SwipeRefreshLayout) findViewById(R.id.swip);
        mListView = (MyListview) findViewById(R.id.ListView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
//        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
//为SwipeRefreshLayout设置监听事件
        swipe.setOnRefreshListener(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        swipe.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mListView.setFocusable(false);
        mAdapter = new MyAdapter(mlist, mContext);
        mListView.setAdapter(mAdapter);
//上拉加载更多
        mListView.setOnLoadMoreListener(new MyListview.OnLoadMoreListener() {
            @Override
            public void onloadMore() {
                loadMore();
            }
        });
    }

    @OnClick(R.id.image_title_white_back)
    public void onClick() {
        finish();
    }

    //上拉加载更多
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
                initData(i);
            }
        }.start();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Recomm Page") // TODO: Define a title_trans for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public void onStop() {
        super.onStop();
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
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
                    mHandler.sendMessage(message);
                }
            }).start();
        } else {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }
}
