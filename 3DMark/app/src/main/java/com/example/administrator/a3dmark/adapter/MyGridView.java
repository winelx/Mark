package com.example.administrator.a3dmark.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

import com.example.administrator.a3dmark.util.MyScrollView;

/**
 * Created by 10942 on 2017/2/28 0028.
 */

/**
 * 自定义gridview，解决ListView中嵌套gridview显示不正常的问题（1行半）
 *
 * @author wangyx
 * @version 1.0.0 2012-9-14
 */
public class MyGridView extends GridView {
    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }




//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//
//        float DownX = 0;
//        float DownY = 0;
//        float moveX = 0;
//        float moveY = 0;
//
//            switch (ev.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    DownX = ev.getX();//float DownX
//                    DownY = ev.getY();//float DownY
//                    //ACTION_DOWN的时候，赶紧把事件hold住
//                    requestDisallowInterceptTouchEvent(true);
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    moveX = ev.getX() - DownX;//X轴距离
//                    moveY = ev.getY() - DownY;//y轴距离
//                    if (moveY <= 10 || moveY <= -10){
//                        //自己处理
//                        requestDisallowInterceptTouchEvent(true);
//                    }else {
//                        //发现不是自己处理，还给父类
//                        requestDisallowInterceptTouchEvent(false);
//                    }
//                    break;
//                case MotionEvent.ACTION_CANCEL:
//                    break;
//                case MotionEvent.ACTION_UP:
//                    break;
//
//            }
//
//
//        boolean result = super.onInterceptTouchEvent(ev);
//        requestDisallowInterceptTouchEvent(false);
//
//        return result;
//    }



}
