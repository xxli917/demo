package com.lixiaoxue.demolxx.android;

import androidx.appcompat.app.AppCompatActivity;

import io.reactivex.annotations.NonNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.lixiaoxue.demolxx.DemoApplication;
import com.lixiaoxue.demolxx.R;
import com.nucarf.base.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Pattern;

public class RxJavaActivity extends AppCompatActivity implements View.OnClickListener {
    private String TAG = RxJavaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        Button simple = findViewById(R.id.simple);
        simple.setOnClickListener(this);
        Button just = findViewById(R.id.just);
        just.setOnClickListener(this);
        Button from = findViewById(R.id.from);
        from.setOnClickListener(this);
        Button map = findViewById(R.id.map);
        map.setOnClickListener(this);
        Button flatmap = findViewById(R.id.flatmap);
        flatmap.setOnClickListener(this);
        Button concat = findViewById(R.id.concat);
        concat.setOnClickListener(this);
        ImageView imageView = findViewById(R.id.imageView);
        RequestOptions options = new RequestOptions()
                .circleCrop();
        Glide.with(DemoApplication.mINSTANCE).load("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1606187660&di=10d8083487af716d6a3a5c3b3e84eb8d&src=http://image.biaobaiju.com/uploads/20190807/13/1565154910-yBnwFIXLHc.jpeg").
                skipMemoryCache(true)
                // override(100,100)
                // .apply()
                // .placeholder()
                .diskCacheStrategy(DiskCacheStrategy.ALL).
                apply(options)
                .into(imageView);
        //new Glide()
        //返回当前可用线程数
        int cpus = Runtime.getRuntime().availableProcessors();
        Log.e(TAG, "cpus=" + cpus);
        Handler handler = new Handler(Looper.getMainLooper());
        //默认加载一个图片，会不会被压缩


    }


    private static final int MAXIMUM_AUTOMATIC_THREAD_COUNT = 4;

    static int bestThreadCount = 0;

    //最大4，最小依赖当前cpu可用数量
    public static int calculateBestThreadCount() {
        if (bestThreadCount == 0) {
            bestThreadCount =
                    Math.min(MAXIMUM_AUTOMATIC_THREAD_COUNT, Runtime.getRuntime().availableProcessors());
        }
        return bestThreadCount;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simple:
                simple();
                break;
            case R.id.just:
                just();
                break;
            case R.id.from:
                //和just类似，可以传的参数更多
                from();
                break;
            case R.id.map:
                map();
                break;
            case R.id.flatmap:
                flatMap();
                break;
            case R.id.concat:
                concat();
                break;
        }
    }

    private void concat() {
        Observable observer1 = Observable.just(1, 2);
        Observable observer2 = Observable.just(3, 4);

        //串行 merge是并行
        Observable.concat(observer1, observer2).subscribe(new Observer() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Object o) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void flatMap() {
        //这个方法可以将事件序列中的元素进行整合加工，返回一个新的被观察者。
    }

    private void map() {
        Observable.just(1, 2)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        return "转换过后的数据" + integer;
                    }
                }).map(new Function<String, String>() {
            @Override
            public String apply(@NonNull String o) throws Exception {
                return "双重转换=" + 0;

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                Log.e(TAG, "返回数据=" + s);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void from() {
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        Observable.fromArray(list).subscribe(new Consumer<ArrayList<String>>() {
            @Override
            public void accept(ArrayList<String> strings) throws Exception {
                Log.e(TAG, "call =" + strings);

            }
        });


    }

    private void just() {

        //将两个项转换为一个Observable发出。 也可以是两个observable
        Observable.just(1, 2)
                .subscribeOn(Schedulers.io())//被观察者执行线程
                .observeOn(AndroidSchedulers.mainThread()) //观察者执行线程
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        LogUtils.e(TAG, "onNext=" + integer);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        LogUtils.e(TAG, "onError");

                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e(TAG, "onCompleted");

                    }
                });

    }

    private void simple() {

        // Observable.concat()
        //被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
                LogUtils.e(TAG, "call");
                //处理逻辑，完成后调用onNext,onCompleted....
                emitter.onNext("1");
            }
        });
        //观察者 还可以是Consumer
        Observer<String> observer = new Observer<String>() {


            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onError");

            }

            @Override
            public void onComplete() {
                LogUtils.e(TAG, "onCompleted");

            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {


            }

            @Override
            public void onNext(String s) {
                LogUtils.e(TAG, "onNext=" + s);

            }
        };
        //订阅
        observable.subscribe(observer);
    }
}