package com.lixiaoxue.demolxx;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lixiaoxue.demolxx.ad.AdMessageBean;
import com.lixiaoxue.demolxx.ad.AdPresenter;
import com.lixiaoxue.demolxx.ad.AdView;
import com.lixiaoxue.demolxx.base.BaseActivity;
import com.lixiaoxue.demolxx.base.BaseWebActivity;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SplashActivity extends BaseActivity implements AdView {
    private final String TAG = SplashActivity.class.getSimpleName();

    Disposable mDisposable;
    Disposable mAdDisposable;
    @BindView(R.id.ad_image)
    ImageView adImage;
    @BindView(R.id.ad_text)
    TextView adText;
    private int adShowTime = 3;
    private AdMessageBean bean;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        start();

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ad_text,R.id.ad_image})
    public void onViewClick(View v){
        if (mAdDisposable != null && !mAdDisposable.isDisposed()) {
            mAdDisposable.dispose();
        }
        switch (v.getId()){
            case R.id.ad_text:
                gotoNextActivity();
                break;
            case R.id.ad_image:
                if(bean != null){
                    gotoWebActivity();
                }
                break;
        }
    }

    private void gotoWebActivity() {
        //返回进入首页
        BaseWebActivity.lauch(this,"广告",bean.getAdUrl(),"ad");
    }

    public void start() {
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e(TAG, "aLong=" + aLong);
                        if (aLong == 2) {//0,1,2
                            if (mDisposable != null && !mDisposable.isDisposed()) {
                                mDisposable.dispose();
                            }
                            AdPresenter presenter = new AdPresenter(SplashActivity.this);
                            presenter.getAd();

                        }
                    }
                });

    }

    @Override
    public void gotoNextActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setAdInfo(final String ad) {
        Gson gson = new Gson();
        bean = gson.fromJson(ad, AdMessageBean.class);
        if (bean != null) {
            if (bean.isShow()) {
                adText.setVisibility(View.VISIBLE);
                //Glide.with(adImage).load(bean.getImgUrl());
                adText.setText("跳过广告（"+adShowTime+"）");
                adImage.setImageDrawable(getResources().getDrawable(R.mipmap.ic_ad));
                adShowTime = adShowTime -1;
                mAdDisposable = Observable.interval(1, TimeUnit.SECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(@NonNull Long aLong) throws Exception {
                                Log.e(TAG, "aLong=" + aLong);
                                adText.setText("跳过广告（"+(adShowTime-aLong)+"）");
                                if (aLong == adShowTime) {//0,1,2
                                    if (mAdDisposable != null && !mAdDisposable.isDisposed()) {
                                        mAdDisposable.dispose();
                                    }
                                    gotoNextActivity();

                                }
                            }
                        });
            }else{
                adText.setVisibility(View.GONE);
                gotoNextActivity();
            }

        }

    }
}
