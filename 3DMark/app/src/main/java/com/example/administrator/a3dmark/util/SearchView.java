package com.example.administrator.a3dmark.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.administrator.a3dmark.R;


/**
 * @name Shopping-master
 * @class name：com.winelx.a10942.shop.bean
 * @class describe
 * @anthor 10942 QQ:1032006226
 * @time 2017/5/17 0017 15:57
 * @change
 * @chang time
 * @class describe
 */
public class SearchView extends LinearLayout {
    /**
     * 输入框
     */
    private EditText et_editext;
    /**
     * 输入框后面的那个清除按钮
     */
    private LinearLayout liner;
    private Button et_search, et_back;

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.pub_searchview, this, true);
        /***找出控件*/
        et_editext = (EditText) findViewById(R.id.et_editext);
        et_back = (Button) findViewById(R.id.et_back);
        et_search = (Button) findViewById(R.id.et_search);
    }


}
