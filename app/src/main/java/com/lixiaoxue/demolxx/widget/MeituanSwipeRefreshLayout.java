package com.lixiaoxue.demolxx.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.base.config.Const;
import com.nucarf.base.utils.LogUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import static com.nucarf.base.utils.CardUtils.init;

public class MeituanSwipeRefreshLayout extends LinearLayout implements NestedScrollingParent {
    private String TAG = MeituanActivity.class.getSimpleName();

    private HeaderView headerView;
    private int headerHeight = 300;
    private View targetView;
    private int headerWidth;
    private int mTouchSlop;

    private final int pulldown = 0;
    private final int relase = 1;//松开刷新
    private final int refreshing = 2;//刷新中
    private final int finishing = 3;//刷新停止
    private boolean mIsBeingDragged;
    /**
     * 何时开始旋转动画 手抬起up
     */


    // Target is returning to its start offset because it was cancelled or a
    // refresh was triggered.
    //目标正在返回其起始偏移量，因为它被取消或
    //已触发刷新。
    private boolean mReturningToStart;
    boolean mRefreshing = false;

    private final NestedScrollingParentHelper mNestedScrollingParentHelper;
    // If nested scrolling is enabled, the total amount that needed to be
    // consumed by this as the nested scrolling parent is used in place of the
    // overscroll determined by MOVE events in the onTouch handler
    private float mTotalUnconsumed;

    private boolean mNestedScrollInProgress;

    // Whether the client has set a custom starting position;
    boolean mUsingCustomStart;
    private int mState;
    private int totalUnConsumed;
    private int absTotal;
    private int absStart;


    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
        // LogUtils.e(TAG,"onStartNestedScroll");
        //可以点击 && 没有正在滑动 && 是垂直
        return isEnabled() && !mReturningToStart && !mRefreshing
                && (axes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {
        LogUtils.e(TAG, "onNestedScrollAccepted=" + axes);
      /*  // Reset the counter of how much leftover scroll needs to be consumed.
        ////重置需要消耗多少剩余卷轴的计数器。
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, axes);
        // Dispatch up to the nested parent
        startNestedScroll(axes & ViewCompat.SCROLL_AXIS_VERTICAL);
        mTotalUnconsumed = 0;
        mNestedScrollInProgress = true;*/


    }

    @Override
    public void onStopNestedScroll(@NonNull View target) {
        //如果滑动不够，则回弹
        int height = headerView.getMeasuredHeight();
        LogUtils.e(TAG, "onStopNestedScroll=" + height);
        if (height < headerHeight / 2) {
            hideHeader(height, 0);
        }
    }


    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        LogUtils.e(TAG, "onNestedScroll");

        LogUtils.e(TAG, "dxConsumed = " + dxConsumed + "dyConsumed=" + dyConsumed +
                "dxUnconsumed = " + dxUnconsumed + "dyunConsumed = " + dyUnconsumed);
       /* totalUnConsumed = totalUnConsumed+Math.abs(dyUnconsumed);
        if(totalUnConsumed>mTouchSlop){
            startHeaderScroll(totalUnConsumed);
        }*/
        /**
         * 知道滑动距离，如果滑动距离大于touchShop 则滑动，滑动放手时如果距离小于高度一半则收回
         * 如果大于一半，显示全部高度，并显示正在刷新
         */
        // Dispatch up to the nested parent first
        /*dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed,
                mParentOffsetInWindow);
*/
        // This is a bit of a hack. Nested scrolling works from the bottom up, and as we are
        // sometimes between two nested scrolling views, we need a way to be able to know when any
        // nested scrolling parent has stopped handling events. We do that by using the
        // 'offset in window 'functionality to see if we have been moved from the event.
        // This is a decent indication of whether we should take over the event stream or not.
       /* final int dy = dyUnconsumed + mParentOffsetInWindow[1];
        if (dy < 0 && !canChildScrollUp()) {
            mTotalUnconsumed += Math.abs(dy);
            moveSpinner(mTotalUnconsumed);
        }*/


    }

    private void moveSpinner(float mTotalUnconsumed) {
        //startHeaderScroll((int)mTotalUnconsumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (mState != refreshing) {
            LogUtils.e(TAG, "onNestedPreScroll");

            absStart = Math.abs(totalUnConsumed);
            totalUnConsumed = totalUnConsumed + dy;
            absTotal = Math.abs(totalUnConsumed);
            LogUtils.e(TAG, "dx = " + dx + "dy=" + dy + "totalUnConsumed = " + totalUnConsumed);
            if (dy < 0) {
                if (totalUnConsumed < 0) {
                    if (absTotal > mTouchSlop) {
                        startHeaderScroll(absStart, absTotal);
                        //如果你需要消耗一定的dx,dy，就通过最后一个参数consumed进行指定，例如我要消耗一半的dy，就可以写consumed[1]=dy/2


                        consumed[1] = dy;
                    }
                }
            }


        }


        /**
         * 知道滑动距离，如果滑动距离大于touchShop 则滑动，滑动放手时如果距离小于高度一半则收回
         * 如果大于一半，显示全部高度，并显示正在刷新
         */

    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
       /* LogUtils.e(TAG,"onNestedFling"+"--velocityY = "+velocityY+"--consumed = "+consumed);
        if(velocityY<0 && Math.abs(velocityY)>headerHeight/2){
            LogUtils.e(TAG,"onNestedFling全部展示");
            headerScroll((int)Math.abs(velocityY),headerHeight);
            setState(relase);

        }else{
            headerScroll((int)Math.abs(velocityY),0);
            LogUtils.e(TAG,"onNestedFling隐藏");

        }*/

        return dispatchNestedFling(velocityX, velocityY, consumed);
    }

    private void startHeaderScroll(int start, int dy) {
        if (start > headerHeight) {
            return;
        }
        if (dy > headerHeight) {
            dy = headerHeight;
        }
        LogUtils.e("动画开始高度=" + start, "动画结束高度 =" + dy);


        ValueAnimator animator = ValueAnimator.ofInt(start, dy);
        int finalDy = dy;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private float pro;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LayoutParams) headerView.getLayoutParams();
                params.height = height;
                headerView.setLayoutParams(params);

                if (headerHeight != 0) {
                    pro = (float) Math.abs(height) / (float) 300;

                }


                //高度全部显示则设置状态为松开刷新
                if (height == headerHeight) {
                    setState(relase);
                    headerView.startSencendAnimator();
                }
                //  LogUtils.e("height = "+height +"headerHeight="+headerHeight +"pro = "+pro);
                if (pro > 1) {
                    pro = 1;
                }
                if (height > 20 && height < 30) {
                    setState(pulldown);
                    headerView.start(pro);
                }
                if (height > 100 && height < 200) {
                    headerView.startFirstAnimator();
                }
                // invalidate();

            }
        });
        animator.setDuration(500);
        animator.start();

    }


    private void hideHeader(int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            private float pro;

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LayoutParams) headerView.getLayoutParams();
                params.height = height;
                headerView.setLayoutParams(params);
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        // LogUtils.e(TAG,"onNestedPreFling"+"--velocityY = "+velocityY);

        return dispatchNestedPreFling(velocityX, velocityY);
    }

    @Override
    public int getNestedScrollAxes() {
        LogUtils.e(TAG, "getNestedScrollAxes");

        return 0;
    }


    public MeituanSwipeRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public MeituanSwipeRefreshLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeituanSwipeRefreshLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);

        init(context);

    }

    public MeituanSwipeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }

    public void init(Context context) {

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        LogUtils.e("执行了init");
        headerView = (HeaderView) View.inflate(context, R.layout.meittuan_swipe_header_view, null);
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        setVerticalGravity(VERTICAL);


        addView(headerView, params);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.e("执行了onFinishInflate");
        int count = getChildCount();
        if (count == 0) {
            return;
        }
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (!(view instanceof HeaderView)) {
                targetView = view;
                return;
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //LogUtils.e("执行了onMeasure");
        //  headerHeight = headerView.getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // LogUtils.e("执行了onLayout="+headerView.getMeasuredHeight());
        if (targetView != null) {
            // LogUtils.e("targetView高度 = "+targetView.getMeasuredHeight());
        }


        /*if(headerView != null){
            headerView.layout(0,0,headerWidth,headerHeight);
        }
        if(targetView != null){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getDisplay().getMetrics(displayMetrics);
            targetView.layout(0,headerHeight,headerWidth,displayMetrics.heightPixels);
        }*/


    }

    public void completed() {
        ValueAnimator animator = ValueAnimator.ofInt(headerHeight, 0);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                LinearLayout.LayoutParams params = (LayoutParams) headerView.getLayoutParams();
                LogUtils.e("complete height = " + height);
                params.height = height;
                headerView.setLayoutParams(params);

                if (height == 0) {
                    headerView.stopAnimator();

                }
            }
        });
        animator.setDuration(200);
        animator.start();


    }

    public void setState(int state) {
        mState = state;
        switch (state) {
            case refreshing:
                headerView.setText("正在刷新");
                break;

            case pulldown:
                headerView.setText("下拉刷新");
                break;
            case relase:
                headerView.setText("松开刷新");
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + state);
        }


    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //当正在加载中当时候
        // LogUtils.e(TAG,"onInterceptTouchEvent");
        // LogUtils.e(TAG,"mState = "+(mState==relase));

        /*if(mState == relase){
            return true;
        }else{
            return false;
        }*/
        return super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                // 抬起时候刷新 完全显示之后，设置其状态为relas
                setState(refreshing);
                headerView.startSencendAnimator();
                break;
        }

        return super.onTouchEvent(event);
    }
}
