package com.example.administrator.a3dmark.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Administrator on 2017/3/27.
 */

public class ListView_Demo extends ListView {

    public ListView_Demo(Context context) {
        super(context);
    }

    public ListView_Demo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListView_Demo(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
