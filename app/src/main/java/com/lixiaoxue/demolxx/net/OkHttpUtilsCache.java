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
 * 思路1. 需要添加两个Interceptor,
 *     //没有网络时不会走NetworkInterceptor拦截器
 *
 *     思路1：没网络的时候使用缓存，有网络的时候获取网落数据
 *
 *     https://www.imooc.com/article/271657
 *     1. 没网络的时候强制使用缓存，那么就设置一个Interceptor就好，但是前面要指定缓存地址，大小
 *     请求参数应该还是需要设置以下的 提示可以跟具请求的时间和数据里面的时间对比
 *
 *     思路2： 无论是否有网，一定时间段内都是先读缓存，不同的接口根据自己的实时性可以自定义这个时间段长短
 *
 *     请求可以设置请求头，响应也可以设置
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
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10);
        builder.cache(cache);
        //没有网络时不会走NetworkInterceptor拦截器
        builder.addNetworkInterceptor(REWRITE_RESPONSE_INTERCEPTOR);
        builder.addInterceptor(cacheInterceptor);
        okHttpClient = builder.build();
    }

    private OkHttpClient okHttpClient;

    public OkHttpClient getClient() {
        return okHttpClient;
    }

    /**
     * 有网络的时候，如果返台返回的策略没有缓存，则手动添加
     */

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

    /**
     * 有网络没有网络，都会走这个
     */
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
                        //expires和cache-control
                        //Expires要求客户端和服务端的时钟严格同步。HTTP1.1引入Cache-Control来克服Expires头的限制。如果max-age和Expires同时出现，则max-age有更高的优先级。
                        .removeHeader("Cache-Control")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        //  .header("",cacheControl.toString())
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

    /**
     * 无网络的时候，强制使用缓存
     */
    public class RequestHeadersInterceptor implements Interceptor
    {
        private static final String TAG = "RequestHeadersInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException
        {
           // Logger.debug(TAG, "RequestHeadersInterceptor.");
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            // builder.header("Content-Type", "application/json;charset=UTF-8")
            //       .header("Accept-Charset", "UTF-8");
            if (!AndroidUtil.networkStatusOK(BaseAppCache.getContext())) {
                // 无网络时，强制使用缓存
               // Logger.debug(TAG, "network unavailable, force cache.");
                builder.cacheControl(CacheControl.FORCE_CACHE);
            }
            return chain.proceed(builder.build());
        }
    }



}
