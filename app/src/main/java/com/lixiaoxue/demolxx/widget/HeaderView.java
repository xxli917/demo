package com.lixiaoxue.demolxx.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lixiaoxue.demolxx.R;

import androidx.annotation.Nullable;

/**
 * 下拉刷新需要有几种状态
 * 下拉
 * 松开刷新
 * 刷新
 */
public class HeaderView extends LinearLayout {

    private ImageView imageView;
    private BeerView beerView;
    private TextView descView;
    private AnimationDrawable animationDrawable;

    public HeaderView(Context context) {
        this(context,null,0);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }
    public void init(Context context){
        View view = View.inflate(context, R.layout.meittuan_header_view,this);
        imageView = view.findViewById(R.id.imageView);
        beerView = view.findViewById(R.id.beer_view);
        descView = view.findViewById(R.id.desc_view);
    }

    public void setText(String message) {
        descView.setText(message);

    }

    public void start(float currentProgress){
        beerView.setVisibility(VISIBLE);
        imageView.setVisibility(GONE);
        beerView.setCurrentProgress(currentProgress);

    }
    public void startFirstAnimator() {
        beerView.setVisibility(GONE);
        imageView.setVisibility(VISIBLE);
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        imageView.setImageResource(R.drawable.pull_to_refresh_second_anim);

        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.pull_to_refresh_third_anim));
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();
    }
    public void startSencendAnimator(){
        beerView.setVisibility(GONE);
        imageView.setVisibility(VISIBLE);
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        imageView.setImageResource(R.drawable.pull_to_refresh_third_anim);

        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.pull_to_refresh_third_anim));
        animationDrawable = (AnimationDrawable) imageView.getDrawable();
        animationDrawable.start();

    }
    public void stopAnimator(){
        if(animationDrawable != null){
            animationDrawable.stop();
        }
        beerView.setCurrentProgress(0);
        beerView.setVisibility(VISIBLE);
        imageView.setVisibility(GONE);
    }
}
