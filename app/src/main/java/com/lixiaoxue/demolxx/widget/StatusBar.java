package com.lixiaoxue.demolxx.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lixiaoxue.demolxx.R;

public class StatusBar extends LinearLayout {

    private ImageView leftImage;
    private ImageView rightImage;
    private TextView titleView;
    private TextView rightTextView;
    private View rightView;

    public StatusBar(Context context) {
        this(context,null);
    }

    public StatusBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public StatusBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }
    public void init(Context context,AttributeSet attrs){

        View view  = View.inflate(context, R.layout.view_status_bar,null);
        leftImage = view.findViewById(R.id.left_image);
        rightImage = view.findViewById(R.id.right_image);
        rightTextView = view.findViewById(R.id.right_text);
        titleView = view.findViewById(R.id.title_view);
        rightView = view.findViewById(R.id.right_view);
        //不设置params,当隐藏右侧布局，中间布局android:layout_centerVertical="true"失效
        LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addView(view,params);
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.StatusBar, 0, 0);
        final Drawable leftDrawable = a.getDrawable(R.styleable.StatusBar_left_image);
        Drawable rightDrawable = a.getDrawable(R.styleable.StatusBar_right_image);
        String titleText = a.getString(R.styleable.StatusBar_title_text);
        int titleColor = a.getColor(R.styleable.StatusBar_title_color, Color.BLACK);
        //已经由sp转为px
        float titleSize = a.getDimension(R.styleable.StatusBar_title_size, 12);

        String rightText = a.getString(R.styleable.StatusBar_right_text);
        int rightColor = a.getColor(R.styleable.StatusBar_right_color, Color.BLACK);
        //已经由sp转为px
        float rightSize = a.getDimension(R.styleable.StatusBar_right_size, 12);

        Log.e("statusBar","titleSize = "+titleSize);

        int leftVisbility = a.getInteger(R.styleable.StatusBar_left_visbility,VISIBLE);
        int rightVisbility = a.getInteger(R.styleable.StatusBar_right_visbility,VISIBLE);


        a.recycle();
        leftImage.setVisibility(leftVisbility);
        rightView.setVisibility(rightVisbility);
        leftImage.setImageDrawable(leftDrawable);
        rightImage.setImageDrawable(rightDrawable);
        titleView.setText(titleText);
        titleView.setTextColor(titleColor);
        //默认TypedValue.COMPLEX_UNIT_SP，但是titleSize的值获取的是px
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_PX,titleSize);

        rightTextView.setText(rightText);
        rightTextView.setTextColor(rightColor);
        //默认TypedValue.COMPLEX_UNIT_SP，但是titleSize的值获取的是px
        rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,rightSize);
        leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageOnClickListener != null){
                    imageOnClickListener.leftOnClick(v);
                }
            }
        });
        rightView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageOnClickListener != null){
                    imageOnClickListener.rightOnClick(v);
                }
            }
        });


    }
    public void setImageOnClick(ImageOnClickListener listener){
        this.imageOnClickListener = listener;

    }

    public void setLeftVisbility(int visbility){
        leftImage.setVisibility(visbility);
    }
    public void setRightImageVisbility(int visbility){
        rightImage.setVisibility(visbility);

    }public void setRightTextVisbility(int visbility){
        rightTextView.setVisibility(visbility);

    }

    public ImageOnClickListener imageOnClickListener;

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public interface ImageOnClickListener{
        void leftOnClick(View v);
        void rightOnClick(View v);
    }
}
