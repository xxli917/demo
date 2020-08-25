package com.nucarf.base.widget.recycleview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nucarf.base.R;


public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {

    private LinearLayout mContainer;
    //刷新的imageview
    private ImageView mRefreshImageView;
    private TextView mStatusTextView;
    private ProgressBar mProBar;
    private int mState = STATE_NORMAL;
    private TextView mHeaderTimeView;
    private static final int ROTATE_ANIM_DURATION = 180;

    public int mMeasuredHeight;
    private Context mContext;

    public ArrowRefreshHeader(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    /**
     * @param context
     * @param attrs
     */
    public ArrowRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        mContext = getContext();
        // 初始情况，设置下拉刷新view高度为0
        mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.jrecycleview_header, null);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 0);
        this.setLayoutParams(lp);
        this.setPadding(0, 0, 0, 0);

        addView(mContainer, new LayoutParams(LayoutParams.MATCH_PARENT, 0));
        setGravity(Gravity.BOTTOM);
        mStatusTextView = (TextView) findViewById(R.id.refresh_status_textview);
        mRefreshImageView = (ImageView) findViewById(R.id.refresh_iv);
        mProBar = (ProgressBar) findViewById(R.id.pro_bar);
        mHeaderTimeView = (TextView) findViewById(R.id.last_refresh_time);
        measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mMeasuredHeight = getMeasuredHeight();
//        Log.e("menasureheight",mMeasuredHeight+"");
    }


    public void setState(int state) {
        if (state == mState) return;
        switch (state) {
            case STATE_NORMAL:
                mStatusTextView.setText(R.string.listview_header_hint_normal);
                mProBar.setVisibility(GONE);
                mRefreshImageView.setVisibility(VISIBLE);
                break;
            case STATE_RELEASE_TO_REFRESH:
                if (mState != STATE_RELEASE_TO_REFRESH) {
                    mStatusTextView.setText(R.string.listview_header_hint_release);
                }
                break;
            case STATE_REFRESHING:
                mProBar.setVisibility(VISIBLE);
                mRefreshImageView.setVisibility(GONE);
//                Animation circle_anim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_loading_3601);
//                LinearInterpolator interpolator = new LinearInterpolator();  //设置匀速旋转，在xml文件中设置会出现卡顿
//                circle_anim.setInterpolator(interpolator);
//                circle_anim.setRepeatCount(10000);
//                if (circle_anim != null) {
//                    mProBar.startAnimation(circle_anim);  //开始动画
//                }
                mStatusTextView.setText(R.string.refreshing);
                break;
            case STATE_DONE:
                mStatusTextView.setText(R.string.refresh_done);
//                mProBar.clearAnimation();
                break;
            default:
        }

        mState = state;
    }

    public int getState() {
        return mState;
    }

    @Override
    public void refreshComplete() {
//        mHeaderTimeView.setText(friendlyTime(new Date()));
        setState(STATE_DONE);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                reset();
            }
        }, 200);
    }

    public void setVisibleHeight(int height) {
        if (height < 0) height = 0;
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        lp.height = height;
        mContainer.setLayoutParams(lp);

        if (ivCover != null) {
            if (ivCoverW == 0) {
                ivCoverH = ivCover.getHeight();
                ivCoverW = ivCover.getWidth();
            }

            ViewGroup.LayoutParams lp_iv =  ivCover.getLayoutParams();
            lp_iv.height = ivCoverH + height ;
            lp_iv.width = ivCoverW + height * ivCoverW / ivCoverH ;
            ivCover.setLayoutParams(lp_iv);
        }
    }

    ImageView ivCover;
    int ivCoverW = 0;
    int ivCoverH = 0;

    public void setBindImageView(ImageView iv) {
        ivCover = iv;
    }

    public int getVisibleHeight() {
        LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
        return lp.height;
    }

    @Override
    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0) {
//            Log.e("滑动的距离",delta+"  "+getVisibleHeight()+"   "+mMeasuredHeight);
            setVisibleHeight((int) delta + getVisibleHeight());
            if (mState <= STATE_RELEASE_TO_REFRESH) {
                // 未处于刷新状态，更新箭头
                if (getVisibleHeight() > mMeasuredHeight) {
                    setState(STATE_RELEASE_TO_REFRESH);
                } else {
                    setState(STATE_NORMAL);
                }
            }
        }
    }

    @Override
    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) // not visible.
            isOnRefresh = false;

        if (getVisibleHeight() > mMeasuredHeight && mState < STATE_REFRESHING) {
            setState(STATE_REFRESHING);
            isOnRefresh = true;
        }
        // refreshing and header isn't shown fully. do nothing.
        if (mState == STATE_REFRESHING && height <= mMeasuredHeight) {
            //return;
        }
        int destHeight = 0; // default: scroll back to dismiss header.
        // is refreshing, just scroll back to show all the header.
        if (mState == STATE_REFRESHING) {
            destHeight = mMeasuredHeight;
        }
        smoothScrollTo(destHeight);

        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                setState(STATE_NORMAL);
            }
        }, 500);
    }

    private void smoothScrollTo(int destHeight) {
//        Log.e("smoothscrollto",destHeight+"");
        ValueAnimator animator = ValueAnimator.ofInt(getVisibleHeight(), destHeight);
        animator.setDuration(300).start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setVisibleHeight((int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

//    public String friendlyTime(Date time) {
//        int ct = (int) ((System.currentTimeMillis() - time.getTime()) / 1000);
//
//        if (ct == 0) {
//            return mContext.getResources().getString(R.string.text_just);
//        }
//
//        if (ct > 0 && ct < 60) {
//            return ct + mContext.getResources().getString(R.string.text_seconds_ago);
//        }
//
//        if (ct >= 60 && ct < 3600) {
//            return Math.max(ct / 60, 1) + mContext.getResources().getString(R.string.text_minute_ago);
//        }
//        if (ct >= 3600 && ct < 86400)
//            return ct / 3600 + mContext.getResources().getString(R.string.text_hour_ago);
//        if (ct >= 86400 && ct < 2592000) { //86400 * 30
//            int day = ct / 86400;
//            return day + getContext().getResources().getString(R.string.text_day_ago);
//        }
//        if (ct >= 2592000 && ct < 31104000) { //86400 * 30
//            return ct / 2592000 + mContext.getResources().getString(R.string.text_month_ago);
//        }
//        return ct / 31104000 + mContext.getResources().getString(R.string.text_year_ago);
//    }

}