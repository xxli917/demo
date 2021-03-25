package com.lixiaoxue.demolxx.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * author : lixiaoxue
 * date   : 2020/11/18/1:51 PM
 */
public class MyView extends View {
    private int cY;

    private Paint paint ;
    public MyView(Context context) {
        this(context,null,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        cY = 200;
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path =new Path();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

        paint.setAntiAlias(true);
        path.moveTo(100, 100);

        int startX = 0;
        int startY = 40;
        path.moveTo(startX, startY);
        int cX = getWidth() / 2 ;
        int endX = getWidth();
        int endY = 40;
        if(cY <= 42){
            canvas.drawLine(0,40,getWidth(),40,paint);
        }else{
            path.quadTo(cX,cY , endX, endY);
            canvas.drawPath(path,paint);

        }

       // paint.setColor(Color.GREEN);

       // path.lineTo(300, 100);

    }
    public void start(){
        ValueAnimator animator =  ValueAnimator.ofInt(cY,40);
        animator.setDuration(10000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                cY = (Integer) animation.getAnimatedValue();
                invalidate();
                if(cY == 0){

                }
            }
        });
        animator.start();
    }
}
