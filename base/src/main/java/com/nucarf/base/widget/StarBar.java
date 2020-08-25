package com.nucarf.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.nucarf.base.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${yuwenming} on 2018/11/10.
 */
public class StarBar extends View {
    /**
     * 选择的星星 的bitmap
     */
    private Bitmap drawable_chose;
    /**
     * 非选择的星星 的bitmap
     */
    private Bitmap drawable_normal;
    /**
     * 星星总颗数 的bitmap
     */
    private int mStarNumber;
    /**
     * 选择的星星颗数 的bitmap
     */
    private int mChoseNumber;
    /**
     * 邻近星星之间的padding值
     */
    private float mPaddingLeft;
    /**
     * 整个View的高度
     */
    private int mHeight;
    /**
     * 整个View的宽度
     */
    private int mWidth;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 最左侧星星距离左边界的距离
     */
    private float mLeft0;
    /**
     * 每个星星定位点 点集合
     */
    private List<RectF> mPointList;
    /**
     * 星星点击事件回调
     */
    private StarChoseListener mStarChoseListener;
    private boolean mIsClickEnable = true;

    public void setStarChoseListener(StarChoseListener starChoseListener) {
        mStarChoseListener = starChoseListener;
    }

    public interface StarChoseListener {
        void choseNumber(int num);
    }

    public StarBar(Context context) {
        this(context, null);
    }

    public StarBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StarIndicator, defStyleAttr, 0);
        //获取选中图片
        BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(R.styleable.StarIndicator_indicator_icon_chose);
        drawable_chose = drawable.getBitmap(); //获取选中图片
        BitmapDrawable drawable1 = (BitmapDrawable) a.getDrawable(R.styleable.StarIndicator_indicator_icon_normal);
        drawable_normal = drawable1.getBitmap(); //获取star个数
        mStarNumber = a.getInteger(R.styleable.StarIndicator_indicator_star_size, 5); //选中个数
        mChoseNumber = a.getInteger(R.styleable.StarIndicator_indicator_star_chose, 5); //获取 间隙
        mPaddingLeft = a.getDimension(R.styleable.StarIndicator_indicator_margin_left, 5); //回收
        mIsClickEnable = a.getBoolean(R.styleable.StarIndicator_is_chose_enable, true); //回收
        a.recycle();
        initPaint();
    }

    private void initPaint() { //获取xml中的属性 //初始化paint，初始化一些其他内容
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heithtSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        float width = 4 * (mPaddingLeft + mHeight) + mHeight;
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) width, mHeight);
        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension((int) width, heithtSpecSize);
        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize, mHeight);
        }
        mWidth = (int) width;
        Log.d("tag", "mHeight" + mHeight + "mWidth" + mWidth);
//        mLeft0 = (mWidth - mStarNumber * mHeight - mPaddingLeft * (mStarNumber - 1)) * 0.5f;
        mLeft0 = 0;
        mPointList = new ArrayList<>();
        for (int i = 0; i < mStarNumber; i++) {
            float left = mLeft0 + i * (mPaddingLeft + mHeight);
            mPointList.add(new RectF(left, 0, left + mHeight, mHeight));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mPointList.size(); i++) {
            if (i < mChoseNumber) {
                canvas.drawBitmap(drawable_chose, null, mPointList.get(i), mPaint);
            } else {
                canvas.drawBitmap(drawable_normal, null, mPointList.get(i), mPaint);
            }
        }
    }

    public void setChoseNumber(int choseNumber) {
        mChoseNumber = choseNumber;
        postInvalidate();
    }

    public void setClickEnable(boolean isClickEnable) {
        mIsClickEnable = isClickEnable;
    }

    public int getStarNumber() {
        return mChoseNumber;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!mIsClickEnable) {
            return true;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                if (x > mWidth) {
                    x = mWidth;
                }
                if (x < 0) {
                    x = 0;
                }
                int a;
                if (x <= mLeft0) {
                    setChoseNumber(a = 0);
                } else if (x >= (mWidth - mLeft0)) {
                    a = mStarNumber;
                    setChoseNumber(mStarNumber);
                } else {
                    a = (int) ((x - mLeft0) / (mHeight + mPaddingLeft) + 1);
                    setChoseNumber(a);
                }
                if (mStarChoseListener != null) {
                    mStarChoseListener.choseNumber(a);
                }
                break;
        }
        return true;
    }


}