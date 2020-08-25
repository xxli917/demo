package com.nucarf.base.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nucarf.base.R;
import com.nucarf.base.utils.toast.BadTokenListener;
import com.nucarf.base.utils.toast.ToastCompat;

/**
 * Created by Administrator on 2018/4/25.
 */

public class ToastUtils {
    public static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    public static final int LENGTH_LONG = Toast.LENGTH_LONG;
    public static Toast toast;

    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Runnable run = new Runnable() {
        public void run() {
            toast.cancel();
            toast = null;
        }
    };

    private static ToastCompat toastCompat;
    private static TextView textView;
    private static TextView discriView;
    private static ImageView pic;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 安全地显示短时吐司
     *
     * @param text 文本
     */
    public static void showShortSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShortSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShortSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShortSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_SHORT, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param text 文本
     */
    public static void showLongSafe(final CharSequence text) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(text, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLongSafe(final @StringRes int resId) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLongSafe(final @StringRes int resId, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(resId, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 安全地显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLongSafe(final String format, final Object... args) {
        sHandler.post(new Runnable() {
            @Override
            public void run() {
                show(format, Toast.LENGTH_LONG, args);
            }
        });
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void showShort(CharSequence text) {
        show(text, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void showShort(@StringRes int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showShort(@StringRes int resId, Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showShort(String format, Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLong(@StringRes int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLong(@StringRes int resId, Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(String format, Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void show(@StringRes int resId, int duration) {
        show(BaseAppCache.getContext().getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(@StringRes int resId, int duration, Object... args) {
        show(String.format(BaseAppCache.getContext().getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(String format, int duration, Object... args) {
        show(String.format(format, args), duration);
    }

    /**
     * 取消吐司
     */
    public static void cancle() {
        if (toastCompat != null) {
            toastCompat.cancel();
        }
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void show(CharSequence text, int duration) {
        try {
            toastCompat = ToastCompat.makeText(BaseAppCache.getContext(), text, duration)
                    .setBadTokenListener(new BadTokenListener() {
                        @Override
                        public void onBadTokenCaught(@NonNull Toast toast) {
                            LogUtils.e("failed toast", "hello");
                        }
                    });
            toastCompat.show();
        } catch (Exception e) {
        }

    }

    /**
     * @param ctx
     * @param msg
     * @param duration
     * @throws NullPointerException
     * @Description 在中间显示的tost
     * @author wlj
     * @date 2015-8-28 下午12:05:26
     */
    public static void show_middle(Context ctx, CharSequence msg, int duration) {
        try {
            if (null != ctx) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast2(ctx, msg, duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void show_middle_pic(Context ctx, int resId, CharSequence msg, int duration) {
        try {
            if (null != ctx && !"".equals(ctx)) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast_pic(ctx, resId, msg, duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
    public static void show_middle_pic( int resId, CharSequence msg, int duration) {
        try {
            if (null != BaseAppCache.getContext() && !"".equals(BaseAppCache.getContext())) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast_pic(BaseAppCache.getContext(), resId, msg, duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public static void show_middle(Context ctx, int resId, int duration) {
        try {
            if (null != ctx && !"".equals(ctx)) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast2(ctx, ctx.getResources().getString(resId), duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }


    public static void show_top_pic( int resId, CharSequence msg,CharSequence dis, int duration) {
        try {
            if (null != BaseAppCache.getContext() && !"".equals(BaseAppCache.getContext())) {
                if (0 > duration) {
                    duration = LENGTH_SHORT;
                }
                toast_top_pic(BaseAppCache.getContext(), resId, msg, dis,duration);
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private static void toast_top_pic(Context ctx, int resId, CharSequence msg, CharSequence dis,int duration) {
        sHandler.removeCallbacks(run);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.toast_content_pic, null);
        textView = (TextView) view.findViewById(R.id.toast_text);
        discriView = (TextView)view.findViewById(R.id.disc_text);
        pic = (ImageView) view.findViewById(R.id.icon_iv);
        textView.setText(msg);
        discriView.setText(dis);
        pic.setImageResource(resId);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 3000;
                break;
            default:
                break;
        }
            toast = new Toast(ctx);
            toast.setView(view);
            if (duration == 1000) {
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast.setDuration(Toast.LENGTH_LONG);
            }

            toast.setGravity(Gravity.TOP, 0, ScreenUtil.dip2px(183));

        sHandler.postDelayed(run, duration);
        toast.show();
    }

    private static void toast2(Context ctx, CharSequence msg, int duration) {
        sHandler.removeCallbacks(run);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.toast_default, null);
        textView = (TextView) view.findViewById(R.id.toast_text);
        textView.setText(msg);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 2000;
                break;
            default:
                break;
        }
        if (null != toast) {
            textView.setText(msg);
        } else {
            // toast = android.widget.Toast.makeText(ctx, msg, duration);
            toast = new Toast(ctx);
            toast.setView(view);
            if (duration == 1000) {
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast.setDuration(Toast.LENGTH_LONG);
            }

            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        sHandler.postDelayed(run, duration);
        toast.show();
    }

    private static void toast_pic(Context ctx, int resId, CharSequence msg, int duration) {
        sHandler.removeCallbacks(run);
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.toast_pic, null);
        textView = (TextView) view.findViewById(R.id.toast_text);
        pic = (ImageView) view.findViewById(R.id.icon_iv);
        textView.setText(msg);
        pic.setImageResource(resId);
        // handler的duration不能直接对应Toast的常量时长，在此针对Toast的常量相应定义时长
        switch (duration) {
            case LENGTH_SHORT:// Toast.LENGTH_SHORT值为0，对应的持续时间大概为1s
                duration = 1000;
                break;
            case LENGTH_LONG:// Toast.LENGTH_LONG值为1，对应的持续时间大概为3s
                duration = 3000;
                break;
            default:
                break;
        }
        if (null != toast) {
            textView.setText(msg);
            pic.setImageResource(resId);
            toast.setView(view);
        } else {
            toast = new Toast(ctx);
            toast.setView(view);
            if (duration == 1000) {
                toast.setDuration(Toast.LENGTH_SHORT);
            } else {
                toast.setDuration(Toast.LENGTH_LONG);
            }

            toast.setGravity(Gravity.CENTER, 0, 0);
        }
        sHandler.postDelayed(run, duration);
        toast.show();
    }


}
