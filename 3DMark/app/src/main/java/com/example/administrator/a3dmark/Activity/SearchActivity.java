package com.example.administrator.a3dmark.Activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.SearchrAdapter;
import com.example.administrator.a3dmark.bean.Searchbean;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.util.Contants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static android.view.View.GONE;


/*
  *搜索页面
 */
public class SearchActivity extends Activity {
    private RecyclerView recyclerView;
    private Context context;
    private static final String TAG = "SearchActivity";
    private List<Searchbean> mDatas = new ArrayList<>();
    private SearchrAdapter mAdapter;
    private String Switch = "1";
    ImageView back, searchbar, search_back;
    Button pub_back;
    Button pub_search;

    EditText editext;
    LinearLayout activity_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = SearchActivity.this;
        search_back = (ImageView) findViewById(R.id.search_back);
        activity_search = (LinearLayout) findViewById(R.id.activity_search);
        activity_search.setVisibility(GONE);
        searchbar = (ImageView) findViewById(R.id.searchbar);

        //求下次写的时候记得初始化
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        mAdapter = new SearchrAdapter(context, mDatas);
        infase();
        recyclerView.setAdapter(mAdapter);
        back = (ImageView) findViewById(R.id.search_back);
        pub_back = (Button) findViewById(R.id.et_back);
        editext = (EditText) findViewById(R.id.et_editext);
        pub_search = (Button) findViewById(R.id.et_search);
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter.setOnItemClickListener(new SearchrAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view) {
                int position = recyclerView.getChildAdapterPosition(view);
                startActivity(new Intent(SearchActivity.this, Boutique_Detail.class).putExtra("goods_id", mDatas.get(position).getGoodsId()));
            }
        });
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_search.setVisibility(View.VISIBLE);
            }
        });
    }

    //加载的另外的布局
    private void infase() {
        pub_back = (Button) findViewById(R.id.et_back);
        editext = (EditText) findViewById(R.id.et_editext);
        pub_search = (Button) findViewById(R.id.et_search);
        pub_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity_search.setVisibility(GONE);
            }
        });
        pub_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editext.getText().toString();
                //搜索
                initData(str);
            }
        });
    }

    private void initData(String str) {
        OkGo.post(Contants.SEARCH)
                .tag(this)
                .params("name", str)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        try {
                            JSONObject object = new JSONObject(s);
                            mDatas.clear();
                            if (s.indexOf("error") != -1) {//有错误

                                return;
                            }
                            JSONArray jsonArray = object.getJSONArray("success");//成功标识
                            if (jsonArray.length()==0){
                                Toast.makeText(getApplicationContext(), "没有数据", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.d(TAG, jsonArray + "");
                            Searchbean goods_main = null;
                            Log.d(TAG, jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                goods_main = new Searchbean();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                goods_main.setImg(jsonObject.getString("img"));
                                goods_main.setGoodsName(jsonObject.getString("goodsName"));
                                goods_main.setGoodsId(jsonObject.getString("goodsId"));
                                goods_main.setCount(jsonObject.getInt("count"));
                                goods_main.setPriceModeNew(jsonObject.getString("priceModeNew"));
                                mDatas.add(goods_main);
                            }
                            mAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                    }
                });
    }

    public void anim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(searchbar, "alpha", 1f, 0f);
        animator.setDuration(500);
        animator.start();
    }

}
