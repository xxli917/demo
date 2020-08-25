package com.nucarf.base.widget.recycleview;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.lang.reflect.Field;

/**
 * Created by lizhixian on 16/1/4.
 */
public class LRecyclerView extends RecyclerView {
    private boolean pullRefreshEnabled = true;
    private LScrollListener mLScrollListener;
    private ArrowRefreshHeader mRefreshHeader;
    private View mEmptyView;
    private final RecyclerView.AdapterDataObserver mDataObserver = new DataObserver();
    private float mLastY = -1;
    private static final float DRAG_RATE = 3;

    private  LRecyclerViewAdapter mWrapAdapter;
    private boolean isNoMore = false;

    //scroll variables begin
    /**
     * 当前RecyclerView类型
     */
    protected LayoutManagerType layoutManagerType;

    /**
     * 最后一个的位置
     */
    private int[] lastPositions;

    /**
     * 最后一个可见的item的位置
     */
    private int lastVisibleItemPosition;

    /**
     * 当前滑动的状态
     */
    private int currentScrollState = 0;

    /**
     * 触发在上下滑动监听器的容差距离
     */
    private static final int HIDE_THRESHOLD = 20;

    public void setmScrolledYDistance(int mScrolledYDistance) {
        this.mScrolledYDistance = mScrolledYDistance;
    }

    /**
     * 滑动的距离
     */
    private int mDistance = 0;

    /**
     * 是否需要监听控制
     */
    private boolean mIsScrollDown = true;

    /**
     * Y轴移动的实际距离（最顶部为0）
     */
    private int mScrolledYDistance = 0;

    /**
     * X轴移动的实际距离（最左侧为0）
     */
    private int mScrolledXDistance = 0;

    public LRecyclerView(Context context) {
        this(context, null);
    }

    public LRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        mViewConfig = ViewConfiguration.get(getContext());

//        initAttrs(context, attrs, defStyle);
//        setNestedScrollingEnabled(false);
    }

//    private void initAttrs(Context context, AttributeSet attrs, int defStyle) {
//        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewPager, defStyle,
//                0);
//        mFlingFactor = a.getFloat(R.styleable.RecyclerViewPager_rvp_flingFactor, 0.15f);
//        mTriggerOffset = a.getFloat(R.styleable.RecyclerViewPager_rvp_triggerOffset, 0.25f);
//        mSinglePageFling = a.getBoolean(R.styleable.RecyclerViewPager_rvp_singlePageFling, mSinglePageFling);
//        a.recycle();
//    }

    @Override
    public void setAdapter(Adapter adapter) {
        Adapter oldAdapter = getAdapter();
        if (oldAdapter != null && mDataObserver != null) {
            oldAdapter.unregisterAdapterDataObserver(mDataObserver);
        }
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(mDataObserver);
        mDataObserver.onChanged();

        mWrapAdapter = (LRecyclerViewAdapter) getAdapter();
        mRefreshHeader = mWrapAdapter.getRefreshHeader();


    }


    private class DataObserver extends RecyclerView.AdapterDataObserver {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();

            if (adapter instanceof LRecyclerViewAdapter) {
                LRecyclerViewAdapter headerAndFooterAdapter = (LRecyclerViewAdapter) adapter;
                if (headerAndFooterAdapter.getInnerAdapter() != null && mEmptyView != null) {
                    int count = headerAndFooterAdapter.getInnerAdapter().getItemCount();
                    if (count == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        LRecyclerView.this.setVisibility(View.GONE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                        LRecyclerView.this.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (adapter != null && mEmptyView != null) {
                    if (adapter.getItemCount() == 0) {
                        mEmptyView.setVisibility(View.VISIBLE);
                        LRecyclerView.this.setVisibility(View.GONE);
                    } else {
                        mEmptyView.setVisibility(View.GONE);
                        LRecyclerView.this.setVisibility(View.VISIBLE);
                    }
                }
            }

        }

    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mLastY == -1) {
            mLastY = ev.getRawY();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = ev.getRawY();

//                if (mCurView != null) {
//                    mMaxLeftWhenDragging = Math.max(mCurView.getLeft(), mMaxLeftWhenDragging);
//                    mMaxTopWhenDragging = Math.max(mCurView.getTop(), mMaxTopWhenDragging);
//                    mMinLeftWhenDragging = Math.min(mCurView.getLeft(), mMinLeftWhenDragging);
//                    mMinTopWhenDragging = Math.min(mCurView.getTop(), mMinTopWhenDragging);
//                }

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                if (isOnTop() && pullRefreshEnabled) {
                    mRefreshHeader.onMove(deltaY / DRAG_RATE);
                    if (mRefreshHeader.getVisibleHeight() > 0 && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING) {
                        return false;
                    }
                }


                break;
            default:
                mLastY = -1; // reset
                if (isOnTop() && pullRefreshEnabled) {
                    if (mRefreshHeader.releaseAction()) {
                        if (mLScrollListener != null) {
                            mLScrollListener.onRefresh();
                        }
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
//        return true;
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private boolean isOnTop() {
        if (pullRefreshEnabled && mRefreshHeader.getParent() != null) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * set view when no content item
     *
     * @param emptyView visiable view when items is empty
     */
    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
    }

    public void refreshComplete() {
        mRefreshHeader.refreshComplete();
        setNoMore(false);
    }

    public void setNoMore(boolean noMore) {
        isNoMore = noMore;
    }

    public void setRefreshHeader(ArrowRefreshHeader refreshHeader) {
        mRefreshHeader = refreshHeader;
    }

    public void setPullRefreshEnabled(boolean enabled) {
        pullRefreshEnabled = enabled;
        if (mWrapAdapter != null) {
            mWrapAdapter.setPullRefreshEnabled(enabled);
        }
    }

    public void setLScrollListener(LScrollListener listener) {
        mLScrollListener = listener;
    }

    public interface LScrollListener {

        void onRefresh();//pull down to refresh

        void onScrollUp();//scroll down to up

        void onScrollDown();//scroll from up to down

        void onBottom();//load next page

        void onScrolled(int distanceX, int distanceY);// moving state,you can get the move distance
    }

    private int mRefreshHeaderHeight;

    public void setRefreshing(boolean refreshing) {
        if (refreshing && pullRefreshEnabled && mLScrollListener != null) {
            mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
            mRefreshHeaderHeight = mRefreshHeader.getMeasuredHeight();
            mRefreshHeader.onMove(mRefreshHeaderHeight);
            mLScrollListener.onRefresh();
        }
    }

    public void setBindImageView(ImageView iv){
        if(mRefreshHeader!=null){
            mRefreshHeader.setBindImageView(iv);
        }
    }

    public void forceToRefresh() {
        if (pullRefreshEnabled && mLScrollListener != null) {
            mRefreshHeader.setState(ArrowRefreshHeader.STATE_REFRESHING);
            mRefreshHeader.onMove(mRefreshHeaderHeight);
            mLScrollListener.onRefresh();
        }
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (null != mLScrollListener) {
            int firstVisibleItemPosition = 0;
            RecyclerView.LayoutManager layoutManager = getLayoutManager();

            if (layoutManagerType == null) {
                if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = LayoutManagerType.LinearLayout;
                } else if (layoutManager instanceof GridLayoutManager) {
                    layoutManagerType = LayoutManagerType.GridLayout;
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    layoutManagerType = LayoutManagerType.StaggeredGridLayout;
                } else {
                    throw new RuntimeException(
                            "Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
                }
            }

            switch (layoutManagerType) {
                case LinearLayout:
                    firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    break;
                case GridLayout:
                    firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    break;
                case StaggeredGridLayout:
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    if (lastPositions == null) {
                        lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
                    }
                    staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
                    lastVisibleItemPosition = findMax(lastPositions);
                    staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(lastPositions);
                    firstVisibleItemPosition = findMax(lastPositions);
                    break;
            }
            if (null != mScrollStatusChnageListener) {
                mScrollStatusChnageListener.firstVisibleItemPositionListener(firstVisibleItemPosition);
            }

            // 根据类型来计算出第一个可见的item的位置，由此判断是否触发到底部的监听器
            // 计算并判断当前是向上滑动还是向下滑动
            calculateScrollUpOrDown(firstVisibleItemPosition, dy);
            // 移动距离超过一定的范围，我们监听就没有啥实际的意义了
            mScrolledXDistance += dx;
            mScrolledYDistance += dy;
            mScrolledXDistance = (mScrolledXDistance < 0) ? 0 : mScrolledXDistance;
            mScrolledYDistance = (mScrolledYDistance < 0) ? 0 : mScrolledYDistance;
            if (mIsScrollDown && (dy == 0)) {
                mScrolledYDistance = 0;
            }
            //Be careful in here
            mLScrollListener.onScrolled(mScrolledXDistance, mScrolledYDistance);
        }

    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        currentScrollState = state;

        if (currentScrollState == RecyclerView.SCROLL_STATE_IDLE && mLScrollListener != null) {
            RecyclerView.LayoutManager layoutManager = getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            if (visibleItemCount > 0
                    && lastVisibleItemPosition >= totalItemCount - 1
                    && totalItemCount > visibleItemCount
                    && !isNoMore
                    && !mIsScrollDown
                    && mRefreshHeader.getState() < ArrowRefreshHeader.STATE_REFRESHING) {
                mLScrollListener.onBottom();
            }

        }


    }

    /**
     * 计算当前是向上滑动还是向下滑动
     */
    private void calculateScrollUpOrDown(int firstVisibleItemPosition, int dy) {
        if (firstVisibleItemPosition == 0) {
            if (!mIsScrollDown) {
                mIsScrollDown = true;
                mLScrollListener.onScrollDown();
            }
        } else {
            if (mDistance > HIDE_THRESHOLD && mIsScrollDown) {
                mIsScrollDown = false;
                mLScrollListener.onScrollUp();
                mDistance = 0;
            } else if (mDistance < -HIDE_THRESHOLD && !mIsScrollDown) {
                mIsScrollDown = true;
                mLScrollListener.onScrollDown();
                mDistance = 0;
            }
        }
        if ((mIsScrollDown && dy > 0) || (!mIsScrollDown && dy < 0)) {
            mDistance += dy;
        }
    }

    public enum LayoutManagerType {
        LinearLayout,
        StaggeredGridLayout,
        GridLayout
    }


    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        try {
            Field fLayoutState = state.getClass().getDeclaredField("mLayoutState");
            fLayoutState.setAccessible(true);
            Object layoutState = fLayoutState.get(state);
            Field fAnchorOffset = layoutState.getClass().getDeclaredField("mAnchorOffset");
            Field fAnchorPosition = layoutState.getClass().getDeclaredField("mAnchorPosition");
            fAnchorPosition.setAccessible(true);
            fAnchorOffset.setAccessible(true);
            if (fAnchorOffset.getInt(layoutState) > 0) {
                fAnchorPosition.set(layoutState, fAnchorPosition.getInt(layoutState) - 1);
            } else if (fAnchorOffset.getInt(layoutState) < 0) {
                fAnchorPosition.set(layoutState, fAnchorPosition.getInt(layoutState) + 1);
            }
            fAnchorOffset.setInt(layoutState, 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        super.onRestoreInstanceState(state);
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);

        if (layout instanceof LinearLayoutManager) {
//            reverseLayout = ((LinearLayoutManager) layout).getReverseLayout();
        }
    }

    private int getItemCount() {
        return mWrapAdapter.getItemCount();
    }


    public interface firstVisibleItemPositionListener {
        void firstVisibleItemPositionListener(int position);
    }

    private firstVisibleItemPositionListener mScrollStatusChnageListener;

    public void setGetFirstVisibleItemPositonListener(firstVisibleItemPositionListener pScrollStatusChnageListener) {
        this.mScrollStatusChnageListener = pScrollStatusChnageListener;
    }


}
