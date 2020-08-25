package com.lixiaoxue.demolxx.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public enum RetrofitUtils {
    INSTANCE;

    private Retrofit retrofitsingleton;
    private final Gson gson;

    RetrofitUtils() {
        //java.lang.NumberFormatException: empty String
        gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapter(long.class, LongTypeAdapter)
                .registerTypeAdapter(Long.class, LongTypeAdapter)
                //.registerTypeAdapter(int.class, IntTypeAdapter)
               // .registerTypeAdapter(Integer.class, IntTypeAdapter)

                .create();

    }

    public <T> T getClient(Class<T> clazz) {
        Retrofit.Builder builder = new Retrofit.Builder();
        //配置服务器路径(baseUrl和注解中url连接的”/”最好写在baseUrl的后面，而不是注解中url的前面，否则可能会出现不可预知的错误。)
       // ToastUtils.show_middle(BaseAppCache.getContext(),"="+BuildConfig.FLAVOR,1);
//        if(Const.DEV.equals(BuildConfig.FLAVOR)){
//            LogUtils.e("get_test_url="+RetrofitConfig.getBaseUrl());
//            builder.baseUrl(RetrofitConfig.getBaseUrl());
//        }else{
//            builder.baseUrl(RetrofitConfig.ONLINE_HOST_URL);
//        }

        builder.baseUrl(RetrofitConfig.getBaseUrl());
        //设置OKHttpClient为网络客户端
        builder.client(OkHttpUtils.INSTANCE.getClient());
        builder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofitsingleton = builder.build();
        return retrofitsingleton.create(clazz);
    }

    //基于特定的url生成的 RestAdapter
    public <T> T getSpecialClient(String url, Class<T> clazz) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);//配置服务器路径
        //设置OKHttpClient为网络客户端
        builder.client(OkHttpUtils.INSTANCE.getClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        Retrofit specialSingleton = builder.build();
        return specialSingleton.create(clazz);
    }

    //基于特定的url生成的 RestAdapter
    public <T> T getRxjavaClient(Class<T> clazz) {
        Retrofit.Builder builder = new Retrofit.Builder();
        //builder.baseUrl(RetrofitConfig.TEST_HOST_URL);//配置服务器路径
//        if(Const.DEV.equals(BuildConfig.FLAVOR)){
//            builder.baseUrl(RetrofitConfig.getBaseUrl());
//        }else{
//            builder.baseUrl(RetrofitConfig.ONLINE_HOST_URL);
//        }

        builder.baseUrl(RetrofitConfig.getBaseUrl());

        //设置OKHttpClient为网络客户端
        builder.client(OkHttpUtils.INSTANCE.getClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        Retrofit specialSingleton = builder.build();
        return specialSingleton.create(clazz);
    }


    //处理解析错误问题
    private  TypeAdapter<Number> IntTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                return Integer.parseInt(result);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    };

    //处理解析错误问题
    private  TypeAdapter<Number> LongTypeAdapter = new TypeAdapter<Number>() {
        @Override
        public void write(JsonWriter out, Number value) throws IOException {
            out.value(value);
        }

        @Override
        public Number read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            try {
                String result = in.nextString();
                if ("".equals(result)) {
                    return null;
                }
                return Long.parseLong(result);
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }
    };

}

