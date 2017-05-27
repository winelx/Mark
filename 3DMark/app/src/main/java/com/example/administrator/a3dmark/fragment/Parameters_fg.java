package com.example.administrator.a3dmark.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.a3dmark.R;
import com.example.administrator.a3dmark.adapter.Graphic_Adapter;
import com.example.administrator.a3dmark.adapter.Parameter_Adapter;
import com.example.administrator.a3dmark.bean.Detail_images;
import com.example.administrator.a3dmark.bean.RelevanceGoods;
import com.example.administrator.a3dmark.bean.SizeImages;
import com.example.administrator.a3dmark.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/3/7.
 */

public class Parameters_fg extends Fragment {
    Context context;
    @BindView(R.id.lv_fg_parameters)
    ListView lvFgParameters;
    List<String> list = new ArrayList<>();
    Parameter_Adapter adapter;
    View view;
    ArrayList<SizeImages> sizeimages = new ArrayList<>();
    private static final String SIZAIMAGE = "img";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = inflater.inflate(R.layout.parameters, container, false);
        ButterKnife.bind(this, view);
        context = getContext();

        Bundle arguments = getArguments();
        if (arguments != null) {
            String image = arguments.getString(SIZAIMAGE);
            if (null != image){
                String[] images = image.split(",");
                for (int i = 0; i < images.length; i++) {
                    SizeImages sizeImages = new SizeImages();
                    sizeImages.setDetail_image(images[i]);
                    sizeimages.add(sizeImages);
                }
                adapter = new Parameter_Adapter(sizeimages, context);
                lvFgParameters.setAdapter(adapter);
            }

        }

        return view;
    }


    public static Parameters_fg newInstance(String img) {
        Bundle bundle = new Bundle();
        bundle.putString(SIZAIMAGE, img);
        Parameters_fg fragment = new Parameters_fg();
        fragment.setArguments(bundle);
        return fragment;
    }

}
