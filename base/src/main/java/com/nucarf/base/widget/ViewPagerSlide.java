package com.nucarf.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * 说明：ViewPager 可滑动控制 <br>
 * 作者：陈晓凯<br>
 * 时间：2017/7/11 23:11<br>
 * 修改记录： <br>
 */

public class ViewPagerSlide extends ViewPager {


    private boolean isSlidAble = true;

    public ViewPagerSlide(Context context) {
        super(context);
    }

    public ViewPagerSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.isSlidAble && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.isSlidAble && super.onInterceptTouchEvent(event);
    }

    /**
     * 是否可以滑动
     * @param b false 禁止滑动
     */
    public void setSlidAble(boolean b) {
        this.isSlidAble = b;
    }

}
