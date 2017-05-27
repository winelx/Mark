package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.administrator.a3dmark.R;

/**
 * Android自定义SearchView
 * Created by yuandl on 2016-11-17.
 */

public class SearchView extends LinearLayout {
    /**
     * 输入框
     */
    private EditText et_search;
    /**
     * 输入框后面的那个清除按钮
     */
    private Button bt_clear, back;


    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.pub_searchview, this, true);
        /***找出控件*/
        et_search = (EditText) findViewById(R.id.et_editext);
        bt_clear = (Button) findViewById(R.id.et_search);
        back = (Button) findViewById(R.id.et_back);
        et_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}