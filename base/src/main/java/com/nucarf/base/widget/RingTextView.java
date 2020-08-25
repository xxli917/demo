package com.nucarf.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.nucarf.base.R;

public class RingTextView extends TextView {
    private float percent;
    public RingTextView(Context context) {
        super(context);
    }

    public RingTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RingTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        RectF rectF = new RectF(getLeft(),getTop(),getRight(),getBottom());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.blue));
        paint.setStrokeWidth(1);

        Paint defaultPaint = new Paint();
        defaultPaint.setAntiAlias(true);
        defaultPaint.setColor(getResources().getColor(R.color.color_1A1554));
        defaultPaint.setStrokeWidth(1);

        Paint insertPaint = new Paint();
        insertPaint.setAntiAlias(true);
        insertPaint.setColor(getResources().getColor(R.color.white));
        insertPaint.setStrokeWidth(1);
        if(percent != 0){
            //0 右侧水平点开始顺势针
            float startAngle = -90;
            float colorSweepAngle = 360*percent;
            float defaultStartAngle = -90+360*percent;
            float defaultSweepAngle = 360*(1-percent);
            canvas.drawArc(rectF,startAngle,colorSweepAngle,true,paint);
            canvas.drawArc(rectF,defaultStartAngle,defaultSweepAngle,true,defaultPaint);

        }

        RectF insertF = new RectF(getLeft()+5,getTop()+5,getRight()-5,getBottom()-5);
        canvas.drawArc(insertF,0,360,true,insertPaint);
        super.onDraw(canvas);

    }

    public void setPercent(float percent){
        this.percent = percent;
    }
}
