package com.example.administrator.a3dmark.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by baoyunlong on 16/6/8.
 */
public class MyScrollView extends ScrollView {
    private static String TAG = MyScrollView.class.getName();

    public void setScrollListener(ScrollListener scrollListener) {
        this.mScrollListener = scrollListener;
    }

    private ScrollListener mScrollListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float DownX = 0;
        float DownY = 0;
        float moveX = 0;
        float moveY = 0;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                DownX = ev.getX();//float DownX
                DownY = ev.getY();//float DownY
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getX() - DownX;//X轴距离
                moveY = ev.getY() - DownY;//y轴距离
                break;
            case MotionEvent.ACTION_UP:

                if (mScrollListener != null) {
                    int contentHeight = getChildAt(0).getHeight();
                    int scrollHeight = getHeight();
                    Log.d(TAG, "scrollY:" + getScrollY() + "contentHeight:" + contentHeight + " scrollHeight" + scrollHeight + "object:" + this);

                    int scrollY = getScrollY();
                    mScrollListener.onScroll(scrollY);

                    if (scrollY + scrollHeight >= contentHeight || contentHeight <= scrollHeight && moveY >= 80) {
                        mScrollListener.onScrollToBottom();
                    } else {
                        mScrollListener.notBottom();
                    }

                    if (scrollY == 0) {
                        if (moveY >= -80)
                        mScrollListener.onScrollToTop();
                    }

                }
                break;
        }

        boolean result = super.onTouchEvent(ev);
        requestDisallowInterceptTouchEvent(false);

        return result;
    }

    public interface ScrollListener {
        void onScrollToBottom();

        void onScrollToTop();

        void onScroll(int scrollY);

        void notBottom();
    }
}
