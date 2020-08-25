package com.nucarf.base.retrofit.api;

import android.content.Context;

import com.nucarf.base.utils.AndroidUtil;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.MD5Utils;
import com.nucarf.base.utils.SharePreUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Http基本工具类
 * Creator: mrni-mac on 16-menu_code_no_pressed-5.
 * Email  : nishengwen_android@163.com
 */
public class BaseHttp {

    public static Map<String, String> getBaseParams() {
        Map<String, String> baseParams = new HashMap<>();
//        PhoneDetail mDetail = Utils.getDeviceInfo(context);
        baseParams.put("android_version", BaseAppCache.getSystemVersion());
        baseParams.put("sign", SharePreUtils.getSign(BaseAppCache.getContext()));
        baseParams.put("appid", "wxw");
        baseParams.put("source", "app");
        return baseParams;
    }
    public static Map<String, String> getTokenParams() {
        Context mContext = BaseAppCache.getContext();
        Map<String, String> map = new TreeMap<String, String>();
        map.put("deviceId", "" + AndroidUtil.getDeviceId(mContext));
        map.put("member_id", SharePreUtils.getMemberId(BaseAppCache.getContext()));
        map.put("token", SharePreUtils.getToken());
        String sign = MD5Utils.getSign(map, "wxw");
        SharePreUtils.setSign(mContext, sign);
        return map;
    }

    /**
     * @return
     */
    public static Map getDefaultHeaders() {
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//                Request.Builder newBuilder = chain.request().newBuilder();
//                Map<String, String> defaultHeaders = getDefaultHeaders();
//                newBuilder.addHeader("header", new Gson().toJson(defaultHeaders));
//                return chain.proceed(newBuilder.build());
//            }
//        });
        Map<String, String> params = new HashMap();
//        params.put("version_code", String.valueOf(BaseAppCache.getVersion_code()));
        params.put("sign", SharePreUtils.getSign(BaseAppCache.getContext()));
        params.put("appid", "wxw");
        params.put("user_type", "member");
        params.put("source", "app");
        return params;
    }
    public static Map<String, String> getBaseParamsModAct(String mod, String act) {
        Map<String, String> baseParams = getBaseParams();
        baseParams.put("mod", mod);
        baseParams.put("act", act);
        return baseParams;
    }

    public static Map<String, String> getBaseParamsModActUid(String mod, String act, String uid) {
        Map<String, String> baseParams = getBaseParams();
        baseParams.put("mod", mod);
        baseParams.put("act", act);
        baseParams.put("uid", uid);
        return baseParams;
    }

}
