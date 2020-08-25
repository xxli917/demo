package com.nucarf.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.nucarf.base.R;
import com.nucarf.base.utils.ScreenUtil;


/**
 * Created by Administrator on 2018/3/22.
 */

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {

    private float width, height;
    private boolean top_left;
    private boolean top_right;
    private boolean bottom_left;
    private boolean bottom_right;
    private int radius;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        getAttrs(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        getAttrs(context, attrs);
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.round_image);
            top_left = mTypedArray.getBoolean(R.styleable.round_image_top_left, false);
            top_right = mTypedArray.getBoolean(R.styleable.round_image_top_right, false);
            bottom_left = mTypedArray.getBoolean(R.styleable.round_image_bottom_left, false);
            bottom_right = mTypedArray.getBoolean(R.styleable.round_image_bottom_right, false);
            radius = mTypedArray.getDimensionPixelSize(R.styleable.round_image_round_radius, 0);
            radius = ScreenUtil.getRealWidth(context, radius);
        }
    }

    public void setTop_left(boolean top_left) {
        this.top_left = top_left;
        this.invalidate();
    }

    public void setTop_right(boolean top_right) {
        this.top_right = top_right;
        this.invalidate();
    }

    public void setBottom_left(boolean bottom_left) {
        this.bottom_left = bottom_left;
        this.invalidate();
    }

    public void setBottom_right(boolean bottom_right) {
        this.bottom_right = bottom_right;
        this.invalidate();
    }

    public void setRadius(int radius) {
        this.radius = radius;
        this.invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > radius && height > radius) {
            Path path = new Path();
            //上右
            if (top_right) {
                path.moveTo(radius, 0);
                path.lineTo(width - radius, 0);
                path.quadTo(width, 0, width, radius);
            } else {
                path.moveTo(0, 0);
                path.lineTo(width, 0);
            }
            //下右
            if (bottom_right) {
                path.lineTo(width, height);
                path.lineTo(width, height - radius);
                path.quadTo(width, height, width - radius, height);
            } else {
                path.lineTo(width, height);
                path.lineTo(width, height);
            }
//            //下左
            if (bottom_left) {
                path.lineTo(0, height);
                path.lineTo(radius, height);
                path.quadTo(0, height, 0, height - radius);
            } else {
                path.lineTo(0, height);
                path.lineTo(0, height);
            }
            //上左
            if (top_left) {
                path.lineTo(0, radius);
                path.quadTo(0, 0, radius, 0);
            } else {
                path.lineTo(0, 0);
            }
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }
}
