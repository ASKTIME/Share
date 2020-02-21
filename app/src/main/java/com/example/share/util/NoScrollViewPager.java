package com.example.share.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

    private boolean NoScrollViewPager = true;

    public void setNoScrollViewPager(boolean noScrollViewPager) {
        NoScrollViewPager = noScrollViewPager;
    }

    public NoScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);

    }


    //触摸事件


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (NoScrollViewPager) {
            return false;
        }else {
            return super.onTouchEvent(ev);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (NoScrollViewPager) {
            return false;
        }else {
            return super.onInterceptTouchEvent(ev);
        }
    }


    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }


    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);

    }
}
