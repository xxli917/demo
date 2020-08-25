package com.lixiaoxue.demolxx.net;


import com.nucarf.base.utils.AndroidUtil;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public enum OkHttpUtilsCache {
    INSTANCE;

    OkHttpUtilsCache() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定10秒超时
        builder.connectTimeout(RetrofitConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.readTimeout(RetrofitConfig.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
        builder.addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("OkHttp", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY));//网络和日志拦截

        //addHeader
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("x-host","http://nucarf_fabu.vip.saas.nucarf.cn")
                        .header("source","pc_company");
                       // .header("token", SettingsManager.getInstance().getToken());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        //addCache
        File cacheFile = new File(BaseAppCache.getContext().getExternalCacheDir(), "demoCache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!AndroidUtil.networkStatusOK(BaseAppCache.getContext())) {
                    CacheControl.Builder cacheBuilder = new CacheControl.Builder();
                    cacheBuilder.maxAge(0, TimeUnit.SECONDS);
                    cacheBuilder.maxStale(365, TimeUnit.DAYS);
                    CacheControl cacheControl = cacheBuilder.build();
                    request = request.newBuilder()
                            .cacheControl(cacheControl)
                            .build();
                }
                Response response = chain.proceed(request);
                if (AndroidUtil.networkStatusOK(BaseAppCache.getContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()

                            .removeHeader("Cache-Control")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .removeHeader("Cache-Control")
                            .removeHeader("Pragma")
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
                return response;
            }
        };
        builder.cache(cache);
        builder.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR);
        builder.addInterceptor(cacheInterceptor);
        okHttpClient = builder.build();
    }

    private OkHttpClient okHttpClient;

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    Interceptor REWRITE_RESPONSE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            String cacheControl = originalResponse.header("Cache-Control");
            if (cacheControl == null || cacheControl.contains("no-store") || cacheControl.contains("no-cache") ||
                    cacheControl.contains("must-revalidate") || cacheControl.contains("max-age=0")) {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + 10)
                        .build();
            } else {
                return originalResponse;
            }
        }
    };
    //cache
//    Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR =  new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
//            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
//            cacheBuilder.maxStale(365,TimeUnit.DAYS);
//            CacheControl cacheControl = cacheBuilder.build();
//
//            Request request = chain.request();
//            if(!AndroidUtil.networkStatusOK(StarshowApplication.getContext())){
//                request = request.newBuilder()
//                        .cacheControl(cacheControl)
//                        .build();
//            }
//            Response originalResponse = chain.proceed(request);
//            if(AndroidUtil.networkStatusOK(StarshowApplication.getContext())) {
//                int maxAge =0;// read from cache
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control","public ,max-age="+ maxAge)
//                        .build();
//            } else{
//                int maxStale =60*60*24*28;// tolerate 4-weeks stale
//                return originalResponse.newBuilder()
//                        .removeHeader("Pragma")
//                        .removeHeader("Cache-Control")
//                        .header("Cache-Control","public, only-if-cached, max-stale="+ maxStale)
//                        .build();
//            }
//        }
//    };


}
