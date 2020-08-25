package com.nucarf.base.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by quantan.liu on 2017/menu_mycenter_nopressed/23.
 */

public class GlideUtils {

    /**
     * 加载图片回掉
     *
     * @param url
     */
    public static void loadFilmBackground(Context context, String url, SimpleTarget<Drawable> requestListener) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) context).isDestroyed()) {
                    return;
                }
            } else {
                if (((Activity) context).isFinishing()) {
                    return;
                }
            }
        }
        url = url.replace("https", "http").replace("{size}", "middle");
        Glide.with(context).asDrawable()
                .load(url)
                .into(requestListener);
    }

    public static void loadFilmBackground(Fragment fragment, String url, SimpleTarget<Drawable> requestListener) {
        if (fragment == null || fragment.isDetached() || TextUtils.isEmpty(url) || !fragment.isAdded()) {
            return;
        }
        url = url.replace("https", "http").replace("{size}", "middle");
        Glide.with(fragment).asDrawable()
                .load(url)
                .into(requestListener);
    }

    public static void load(Context mContext, String url, ImageView iv) {
        if (mContext == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) mContext).isDestroyed()) {
                    return;
                }
            } else {
                if (((Activity) mContext).isFinishing()) {
                    return;
                }
            }
        }
        url = url.replace("https", "http");
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop();
        Glide.with(mContext)
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }

    public static void load(Context mContext, int drawId, SimpleTarget<Drawable> requestListener) {
        if (mContext == null) {
            return;
        }
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) mContext).isDestroyed()) {
                    return;
                }
            } else {
                if (((Activity) mContext).isFinishing()) {
                    return;
                }
            }
        }
        Glide.with(mContext).asDrawable()
                .load(drawId)
                .into(requestListener);
    }

    public static void load(Fragment fragment, String url, ImageView iv) {
        if (fragment == null || fragment.isDetached() || TextUtils.isEmpty(url)) {
            return;
        }
        url = url.replace("https", "http");
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop();

        Glide.with(fragment)
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }

    public static void load(Fragment fragment, String url, int def, ImageView iv) {
        if (fragment == null || fragment.isDetached() || !fragment.isAdded()) {
            return;
        }
        url = url.replace("https", "http");
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop().placeholder(def).error(def);

        Glide.with(fragment)
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }

    public static void load(Context mContext, String url, int def, ImageView iv) {
        if (mContext == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) mContext).isDestroyed()) {
                    return;
                }
            } else {
                if (((Activity) mContext).isFinishing()) {
                    return;
                }
            }
        }
        url = url.replace("https", "http");
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop().placeholder(def).error(def);

        Glide.with(mContext)
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }
    public static void load(Context mContext, String url, int def, int error,ImageView iv) {
        if (mContext == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) mContext).isDestroyed()) {
                    return;
                }
            } else {
                if (((Activity) mContext).isFinishing()) {
                    return;
                }
            }
        }
        url = url.replace("https", "http");
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop().placeholder(def).error(error);

        Glide.with(mContext)
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }

    public static void loadCrossfade(Context mContext, String url, ImageView iv) {
        if (mContext == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) mContext).isDestroyed()) {
                    return;
                }
            } else {
                if (((Activity) mContext).isFinishing()) {
                    return;
                }
            }
        }
        url = url.replace("https", "http");
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop();
//                .centerCrop().placeholder(def);

        Glide.with(mContext)
                .load(url)
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(requestOptions)
                .into(iv);
    }

    public static void load(Context mContext, String url, ImageView iv, RequestOptions requestOptions) {
        if (mContext == null || TextUtils.isEmpty(url)) {
            return;
        }
        if (mContext instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (((Activity) mContext).isDestroyed()) {
                    return;
                }
            } else {
                if (((Activity) mContext).isFinishing()) {
                    return;
                }
            }
        }
        Glide.with(mContext)
                .load(url)
                .apply(requestOptions)
                .into(iv);
    }

}