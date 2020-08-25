package com.lixiaoxue.demolxx.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.lixiaoxue.demolxx.R;

import androidx.annotation.Nullable;

public class DrawableView extends View {
    private Paint paint;
    private Path mArrow;
    private Paint mArrowPaint;
    private int mArrowWidth = 20;
    private int mArrowScale = 2;
    private int mStrokeWidth = 2;
    private int mArrowHeight = 100;
    private int mCurrentColor = R.color.red;

    public DrawableView(Context context) {
        super(context);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DrawableView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.red));
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);
        mArrow = new Path();
        Path path = new Path();

        // path.reset();
        path.moveTo(0, 0);
        path.lineTo(1000, 1000);

        Paint mPaint = new Paint();
        //参考下面6行
        mPaint.setStrokeWidth(5f);
        mPaint.setColor(Color.RED);
        // mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        /*mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);*/


        //canvas.drawPath(path,mPaint);


        mArrowPaint = new Paint();
        mArrowPaint.setAntiAlias(true);
        mArrowPaint.setColor(Color.YELLOW);
        mArrowPaint.setStrokeWidth(9f);
        mArrowPaint.setStyle(Paint.Style.STROKE);

        //canvas.drawPath(path,mArrowPaint);


        RectF rectF = new RectF();
        rectF.set(0, 0, 200, 200);
        drawTriangle(canvas, 0, 200, rectF);
    }

    void drawTriangle(Canvas c, float startAngle, float sweepAngle, RectF bounds) {
        if (mArrow == null) {
            mArrow = new android.graphics.Path();
            //  mArrow.setFillType(android.graphics.Path.FillType.EVEN_ODD);

            float centerRadius = Math.min(bounds.width(), bounds.height()) / 2f;
            float inset = mArrowWidth * mArrowScale / 2f;
            // Update the path each time. This works around an issue in SKIA
            // where concatenating a rotation matrix to a scale matrix
            // ignored a starting negative rotation. This appears to have
            // been fixed as of API 21.
            mArrow.moveTo(0, 0);
            mArrow.lineTo(300, 100);

             /*   mArrow.lineTo(mArrowWidth * mArrowScale, 100);
            mArrow.lineTo((mArrowWidth * mArrowScale / 2), (mArrowHeight
                    * mArrowScale));*/
           /* mArrow.offset(centerRadius + bounds.centerX() - inset,
                    bounds.centerY() + mStrokeWidth / 2f);
            mArrow.close();
            // draw a triangle
            mArrowPaint.setColor(getResources().getColor(R.color.red));
            mArrowPaint.setAlpha(255);
            c.save();
            c.rotate(startAngle + sweepAngle, bounds.centerX(),
                    bounds.centerY());*/
            c.drawPath(mArrow, mArrowPaint);
            // c.restore();
        }
    }

}
