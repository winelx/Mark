package com.example.administrator.a3dmark.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Graphic_Adapter;
import com.example.administrator.a3dmark.adapter.MyGridView;
import com.example.administrator.a3dmark.bean.Detail_images;
import com.example.administrator.a3dmark.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/7.
 */

public class Graphic_details extends Fragment {
    private static final String BUNDLE_IMG = "img";
    @BindView(R.id.lv_graphic_detail)
    ListView lvGraphicDetail;
    private String goods_id;
    Graphic_Adapter adapter;
    ArrayList<Detail_images> detailImg = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = View.inflate(getActivity(), R.layout.graphic_detail, null);
        ButterKnife.bind(this, view);
        goods_id = (String) SharedUtil.getParam(getActivity(), "goods_id", "");
        lvGraphicDetail.setFocusable(false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String image = arguments.getString(BUNDLE_IMG);
            if (null != image){
                String[] images = image.split(",");
                for (int i = 0; i < images.length; i++) {
                    Detail_images detail_images = new Detail_images();
                    detail_images.setDetail_image(images[i]);
                    detailImg.add(detail_images);
                }
                adapter = new Graphic_Adapter(getActivity(), detailImg);
                lvGraphicDetail.setAdapter(adapter);
            }

        }


        return view;
    }

    public static Graphic_details newInstance(String img) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_IMG, img);
        Graphic_details fragment = new Graphic_details();
        fragment.setArguments(bundle);
        return fragment;
    }
}
