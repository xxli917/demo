package com.lixiaoxue.demolxx.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.lixiaoxue.demolxx.R;

import androidx.annotation.Nullable;

public class BeerView extends View {
    private Paint paint;
    private int measureHeight;
    private int measureWidth;
    private float mCurrentProgress = 0;

    public BeerView(Context context) {
        super(context);
    }

    public BeerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BeerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BeerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();

        Bitmap bitmap = Bitmap.createBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.pull_image));
        //这个方法是对画布进行缩放，从而达到椭圆形图片的缩放，第一个参数为宽度缩放比例，第二个参数为高度缩放比例，

        canvas.scale(mCurrentProgress,mCurrentProgress,measureWidth/2, measureHeight/2);
        canvas.drawBitmap(bitmap,0,measureHeight/4,null);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureHeight = getMeasuredHeight();
        measureWidth = getMeasuredWidth();
    }


    public void setCurrentProgress(float currentProgress){
        mCurrentProgress = currentProgress;
        invalidate();
    }
}
