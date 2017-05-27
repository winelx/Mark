package com.example.administrator.a3dmark.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.SeckillAdapter;
import com.example.administrator.a3dmark.bean.Banners;
import com.example.administrator.a3dmark.bean.Goods_Seckill;
import com.example.administrator.a3dmark.detail_shop.Boutique_Detail;
import com.example.administrator.a3dmark.util.Contants;
import com.example.administrator.a3dmark.util.SnapUpCountDownTimerView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.recker.flybanner.FlyBanner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class SimpleFragment extends BaseFragment {
    public static final String BUNDLE_TITLE = "title_trans";
    private String mTitleId = "DefaultValue";

    private RelativeLayout reLayout;
    private FlyBanner loopPicture;
    private TextView text_msg, before$, text_left;
    private SnapUpCountDownTimerView text_time;
    private ListView mLridView;
    private SeckillAdapter mSeckillAdapter;
    private List<Goods_Seckill> mainlist = new ArrayList<Goods_Seckill>();
    private List<String> imageUrls = new ArrayList<String>();
    private List<Banners> Flybanners = new ArrayList();
    private View view;
    private int i = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            //去填充一个布局
            view = inflater.inflate(R.layout.fragment_seckill, container, false);
            //text_msg = (TextView) view.findViewById(R.id.text_msg);
            mLridView = (ListView) view.findViewById(R.id.mLridView);
//            mLridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//
//                    startActivity(new Intent(getActivity(), Boutique_Detail.class).putExtra("goods_id", mainlist.get(position).getId()));
//                }
//            });
            View headerView = View.inflate(getContext(), R.layout.flybanner, null);
            reLayout = (RelativeLayout) headerView.findViewById(R.id.reLayout);
            reLayout.setVisibility(View.VISIBLE);
            loopPicture = (FlyBanner) headerView.findViewById(R.id.loopPicture);
            loopPicture.setOnItemClickListener(new FlyBanner.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent intent = new Intent(getActivity(), Boutique_Detail.class);
                    intent.putExtra("goods_id", Flybanners.get(position).getId());
                    startActivity(intent);
                }
            });

            text_time = (SnapUpCountDownTimerView) headerView.findViewById(R.id.text_time);
            text_left = (TextView) headerView.findViewById(R.id.text_left);
            text_time.setEndListener(new SnapUpCountDownTimerView.EndListener() {
                @Override
                public void over(boolean flag) {
                    if (flag != false) {
                        //去调接口方法更新状态
                    }
                }
            });
            mLridView.addHeaderView(headerView);
            //fragment获取
            Bundle arguments = getArguments();
            if (arguments != null) {
                //获取当前tab fragment
                mTitleId = arguments.getString(BUNDLE_TITLE);
                initGoodsData(mTitleId, 1);
            }
        }
        // 缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);

        }
        return view;
    }


    public static SimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
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
                initGoodsData(mTitleId, i);
            }
        }.start();
    }

    /*商品网络请求*/
    private void initGoodsData(String id, int i) {
        mainlist.clear();//清理List
        imageUrls.clear();
        OkGo.post(Contants.LIMIT_TIME_GOODS)
                .tag(this)
                .params("id", id)
                .params("page", 1)
                .params("row", 10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        try {
                            //{"success":"1","result":{"carousel":[{"img":"http://192.168.1.136:8080/3DMark/img?id=1","goods_id":"1"}],"goods":[],"end_time":"03/24 12:30"}}
                            JSONObject object = new JSONObject(s);
                            if (s.indexOf("error") != -1) {//有错误
                                Toast.makeText(getActivity(), object.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String status = object.getString("success");
                            JSONObject result = object.getJSONObject("result");
                            //服务器当前时间
                            //String resume_time = result.getString("resume_time");
                            //String end_time = result.getString("end_time");
                            String time = result.getString("countdown");
                            if ("1".equals(status)) {
                                text_left.setVisibility(View.INVISIBLE);//("活动未开始");
                                text_time.setVisibility(View.INVISIBLE);//("活动未开始");
                            } else if ("2".equals(status)) {
                                text_left.setVisibility(View.INVISIBLE);//("活动已结束");
                                text_time.setVisibility(View.INVISIBLE);//("活动已结束");
                            } else if ("3".equals(status)) {
                                //00:00:00
                                String[] times = time.split("\\:");
                                int HH = Integer.parseInt(times[0]);
                                int mm = Integer.parseInt(times[1]);
                                int ss = Integer.parseInt(times[2]);

                                text_time.setTime(HH, mm, ss);//倒计时时间
                                text_time.start();//开始倒计时
                            }
                            JSONArray carousel = result.getJSONArray("carousel");//成功标识
                            for (int i = 0; i < carousel.length(); i++) {
                                Banners flyBanners = new Banners();
                                JSONObject jsObj = carousel.getJSONObject(i);
                                flyBanners.setImg(jsObj.getString("goodsCover"));
                                flyBanners.setId(jsObj.getString("goodsId"));
                                Flybanners.add(flyBanners);
                                //imageUrls.add(img.getString("goodsCover"));
                            }
                            if (Flybanners.size() != 0) {
                                for (Banners flyBanners : Flybanners) {
                                    imageUrls.add(flyBanners.getImg());
                                }
                                loopPicture.setImagesUrl(imageUrls);
                            }

                            JSONArray jsonArray = result.getJSONArray("goods");//成功标识
                            Goods_Seckill goods_Seckill = null;
                            for (int i = 0; i < jsonArray.length(); i++) {
                                goods_Seckill = new Goods_Seckill();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                goods_Seckill.setImg(jsonObject.getString("goodsCover"));
                                goods_Seckill.setName(jsonObject.getString("goodsName"));
                                goods_Seckill.setId(jsonObject.getString("goodsId"));
                                goods_Seckill.setBuynum(jsonObject.getString("paymentNum"));
                                goods_Seckill.setPrice_now(jsonObject.getString("priceModeNew"));
                                goods_Seckill.setPrice_old(jsonObject.getString("priceMode"));
                                goods_Seckill.setProportion(jsonObject.getDouble("proportion"));
                                mainlist.add(goods_Seckill);
                            }
                            mSeckillAdapter = new SeckillAdapter(mainlist, getActivity());
                            mLridView.setAdapter(mSeckillAdapter);
                            mSeckillAdapter.setBtnClick(new SeckillAdapter.BtnClick() {
                                @Override
                                public void click(int position) {
                                    startActivity(new Intent(getActivity(), Boutique_Detail.class)
                                            .putExtra("goods_id", mainlist.get(position).getId())
                                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        e.printStackTrace();
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    /**
     * 时间转化
     * @param mss
     * @return
     */
    public static String formatDuring(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return days + " days " + hours + " hours " + minutes + " minutes "
                + seconds + " seconds ";
    }




}
