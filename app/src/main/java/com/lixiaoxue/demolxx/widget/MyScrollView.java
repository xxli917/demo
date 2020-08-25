package com.lixiaoxue.demolxx.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingParent;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 就是NestedScrollingParent内部的View，在滑动到时候，会首先将dx、dy传入给NestedScrollingParent，
 * NestedScrollingParent可以决定是否对其进行消耗，一般会根据需求消耗部分或者全部(不过这里并没有实际的约束，你可以随便写消耗多少，可能会对内部View造成一定的影响）。
 */
public class MyScrollView implements NestedScrollingParent {
    /**
     * 对启动可嵌套滚动操作的子视图作出反应
     * ViewCompat.startNestedScroll(View,int)将调用此方法，以响应子视图
     * 提供了一个响应和声明嵌套滚动操作的机会，允许返回true
     * 此方法可能被ViewParent实现重写，以指示视图何时支持嵌套滚动操作
     * 嵌套滚动完成后，此副视图将接收 onStopNestedScroll()的调用
     * @param child
     * @param target
     * @param axes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(@NonNull View child, @NonNull View target, int axes) {
      //  ViewCompat.startNestedScroll()
        return false;
    }

    /**
     * 对嵌套滚动操作对成功声明做出反应
     * 此方法将在onStartNestedScroll()返回true后执行，它提供视图及其超类执行初始配置对机会
     * 对于嵌套滚动，此方法的实现应始终调用其超类的方法实现
     * @param child
     * @param target
     * @param axes
     */
    @Override
    public void onNestedScrollAccepted(@NonNull View child, @NonNull View target, int axes) {

    }

    /**
     * 对嵌套滚动操作做出反应
     * 执行清理
     * 例如 嵌套触摸时，ACTION_UP ACTION_CANCEL 将调用此方法
     * @param target
     */
    @Override
    public void onStopNestedScroll(@NonNull View target) {

    }

    /**
     * 对正在进行嵌套滚动做出反应（前提 onStartNestedScroll(View,View,int)返回true）
     *
     * 滚动距离的消耗和未消耗部分均报告给
     * *查看父对象。实现可以选择使用消耗部分来匹配或跟踪滚动
     * *例如，多个子元素的位置。未使用部分可用于
     * *允许连续拖动多个滚动或可拖动的元素，例如滚动
     * *垂直抽屉中的一种列表，在该列表中，抽屉开始拖动内部的边缘
     * *达到滚动内容</p>
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     */
    @Override
    public void onNestedScroll(@NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    /**
     * 在目标（当前）视图消耗部分滚动之前，对正在进行对嵌套滚动做出反应
     * 在使用嵌套滚动时，父视图需要一个机会在子视图之前使用滚动
     * 例子：用户希望滚动整体，在滚动列表之前
     * 这个方法被调用，当View.dispatchNestedPreScroll()
     * @param target
     * @param dx
     * @param dy
     * @param consumed 被消耗的 此参数永远不会为空。消耗[0]和消耗[1]的初始值
     * *将始终为0
     */
    @Override
    public void onNestedPreScroll(@NonNull View target, int dx, int dy, @NonNull int[] consumed) {

    }

    @Override
    public boolean onNestedFling(@NonNull View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(@NonNull View target, float velocityX, float velocityY) {
        return false;
    }

    /**
     * 返回此嵌套滚动父级的当前嵌套滚动轴。
     *        ViewCompat.SCROLL_AXIS_NONE SCROLL_AXIS_HORIZONTAL
     * @return
     */
    @Override
    public int getNestedScrollAxes() {
        //ViewCompat.SCROLL_AXIS_HORIZONTAL
       // SwipeRefreshLayout

        return 0;
    }
}
