package com.lixiaoxue.demolxx.net;

import com.lixiaoxue.demolxx.DemoApplication;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.NetUtils;
import com.nucarf.base.utils.SharePreUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public enum OkHttpUtils {
    INSTANCE;

    OkHttpUtils() {

        class BasicAuthInterceptor implements Interceptor {

            private String credentials;

            public BasicAuthInterceptor(String user, String password) {
                this.credentials = Credentials.basic(user, password);
            }

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request authenticatedRequest = request.newBuilder()
                        .header("Authorization", credentials).build();
                return chain.proceed(authenticatedRequest);
            }
        }
        Interceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e("OkHttp", message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor rewriteCacheControlInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response originalResponse = chain.proceed(request);
                int maxAge = 1 * 60; // 在线缓存在1分钟内可读取 单位:秒
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            }
        };

        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();

                if (!NetUtils.isNetworkAvailable(DemoApplication.mINSTANCE)) {
/**
 * 离线缓存控制  总的缓存时间=在线缓存时间+设置离线缓存时间
 */
                    int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周,单位:秒
                    CacheControl tempCacheControl = new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(maxStale, TimeUnit.SECONDS)
                            .build();
                    request = request.newBuilder()
                            .cacheControl(tempCacheControl)
                            .build();
                    LogUtils.i("intercept:no network ");
                }
                return chain.proceed(request);
            }
        };


        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("Content-Type", "application/json;charset=UTF-8")
//                        .header("Origin", SettingsManager.getInstance().getOrigin())
                        // .header("x-host", SettingsManager.getInstance().getOrigin())
                        .header("source", "android_company")
                        // .header("source", "pc_company")
                        .header("n-token", SharePreUtils.getToken());
                Request request = requestBuilder.build();

                return chain.proceed(request);
            }
        };

        //外部存储
        File httpCacheDirectory = new File(DemoApplication.mINSTANCE.getExternalCacheDir(), "responses");
        //设置缓存 10M
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //设定10秒超时
        builder.connectTimeout(RetrofitConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(RetrofitConfig.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                //.cache(cache)
                .addInterceptor(headerInterceptor)
                .addInterceptor(logInterceptor);
       /* try {
            TrustManagerFactory trustManagerFactory = null;
            trustManagerFactory = TrustManagerFactory.getInstance(
                    TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:"
                        + Arrays.toString(trustManagers));
            }
            X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, trustManager);
        }catch (Exception e){

        }

        builder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });*/


        //.addInterceptor(cacheInterceptor)
        //.addNetworkInterceptor(rewriteCacheControlInterceptor);
       /* if (Const.DEV.equals(BuildConfig.FLAVOR)) {
            builder.addInterceptor(new BasicAuthInterceptor(SharePreUtils.getJiraName(), SharePreUtils.getJiraPassword()));
        }*/
        okHttpClient = builder.build();

    }


    private OkHttpClient okHttpClient;

    public OkHttpClient getClient() {
        return okHttpClient;
    }
}
