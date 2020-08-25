package com.lixiaoxue.demolxx.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixiaoxue.demolxx.R;

import androidx.annotation.Nullable;

public class EmptyView extends LinearLayout {

    public ImageView imageView;
    public TextView descView;

    public EmptyView(Context context) {
        this(context,null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        View rootView = View.inflate(context, R.layout.layout_empty_view,this);
        imageView = rootView.findViewById(R.id.empty_image);
        descView = rootView.findViewById(R.id.desc_view);

    }
    public void setTopPadding(int top){
        setPadding(0,top,0,0);
    }
    public void setMiddlePadding(int middle){
        if(descView != null){
            descView.setPadding(0,middle,0,0);
        }
    }

    public void setText(String text){
        if(descView != null){
            descView.setText(text);
        }
    }

    public void setImageView(int drawable){
        if(imageView != null){
            imageView.setImageDrawable(getResources().getDrawable(drawable));
        }
    }



}
