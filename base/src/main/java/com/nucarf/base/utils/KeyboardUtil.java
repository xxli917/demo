package com.nucarf.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by WANG on 2016/4/6.软键盘的控制
 */
public class KeyboardUtil {

    /**
     * 隐藏输入法键盘
     *
     * @param context
     * @param editText
     */
    public static void hideInput(EditText editText, Context context) {
        if (context != null && editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    public static void hideInput(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示输入法键盘
     *
     * @param context
     * @param editText
     */
    public static void showInput(Context context, EditText editText) {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
            if (imm != null)
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        }
    }

    public static void showInputAddTime(final EditText editText) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(editText, 0);
                           }
                       },
                998);
    }

    public static boolean isSoftInputShow(Activity context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();
    }


    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    public static void hideSoftInput(IBinder token, Activity activity) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null)
                im.hideSoftInputFromWindow(token,
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void pullKeywordTop(final Activity activity, final int lyRootID, final int vID, final int svID, final int needHideId) {

        ViewGroup ly = (ViewGroup) activity.findViewById(lyRootID);

//获取屏幕高度，根据经验，输入法弹出高度一般在屏幕1/3到1/2之间

        final int defaultHeight = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight();

        final int mKeyHeight = defaultHeight / 4;

        ly.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override

            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

//获取根布局前后高度差

                int height = oldBottom - bottom;

                ScrollView sv = (ScrollView) activity.findViewById(svID);

                if (height > mKeyHeight) {//当高度差大于屏幕1/4，认为是输入法弹出变动，可能会有特殊机型会失败

                    activity.findViewById(needHideId).setVisibility(View.GONE);

                    final int lybottom = bottom;

                    sv.post(new Runnable() {//用post防止有时输入法会自动滚动覆盖我们手动滚动

                        @Override

                        public void run() {

                            ScrollView runSv = (ScrollView) activity.findViewById(svID);

//获取要滚动至的控件到屏幕顶部高度

                            View v = (View) activity.findViewById(vID);

                            int[] loca = new int[2];

////获取在整个屏幕内的绝对坐标，注意这个值是要从屏幕顶端算起，也就是包括了通知栏的高度。loca[0] x,loca[1] y

                            v.getLocationOnScreen(loca);

//这种通知栏高度获取方法必须在布局构建完毕后才能生效，否则获取为0

                            Rect frame = new Rect();

                            //getWindowVisibleDisplayFrame方法可以获取到程序显示的区域，包括标题栏，但不包括状态栏。

                            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

//获取状态栏高度

                            int statusBarHeight = frame.top;

// 要滚动的距离=控件距屏幕顶部距离+控件高度-输入法弹出后的activity高度-通知栏高度

                            int scrollHeight = loca[1] + v.getHeight() - lybottom - statusBarHeight;

                            if (scrollHeight > 0) {

                                runSv.scrollBy(0, scrollHeight);

                            }

                        }

                    });

                } else if (-height > mKeyHeight) {//当输入法收起，回滚回顶部

                    sv.scrollTo(0, 0);

                    activity.findViewById(needHideId).setVisibility(View.VISIBLE);

                }

            }

        });

    }

}
