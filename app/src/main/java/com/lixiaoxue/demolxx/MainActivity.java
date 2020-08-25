package com.lixiaoxue.demolxx;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.internal.FlowLayout;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.lixiaoxue.demolxx.base.BaseActivity;
import com.lixiaoxue.demolxx.fragment.AndroidFragment;
import com.lixiaoxue.demolxx.fragment.GitFragment;
import com.lixiaoxue.demolxx.fragment.GradleFragment;
import com.lixiaoxue.demolxx.fragment.JavaFragment;
import com.lixiaoxue.demolxx.fragment.MateralDesignFragment;
import com.lixiaoxue.demolxx.fragment.WidgetFragment;
import com.lixiaoxue.demolxx.widget.StatusBar;
import com.nucarf.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseActivity {
    private String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e(TAG,"onCreate");
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();




    }

    @Override
    protected void initData() {

    }

    private void initView() {

        String [] titles = {"java基础","android基础","自定义控件","material design",
               "git","gradle"};
        // "设计模式","app架构","常见ui","动画",
        for(int i = 0;i<titles.length;i++){
            tabLayout.addTab(tabLayout.newTab().setText(titles[i]));
        }
        List<Fragment> fragmentList = new ArrayList<>();
        JavaFragment javaFragment = new JavaFragment();
        AndroidFragment androidFragment = new AndroidFragment();
        WidgetFragment widgetFragment = new WidgetFragment();
        MateralDesignFragment materalDesignFragment = new MateralDesignFragment();
        GitFragment gitFragment = new GitFragment();
        GradleFragment gradleFragment = new GradleFragment();
        fragmentList.add(javaFragment);
        fragmentList.add(androidFragment);
        fragmentList.add(widgetFragment);
        fragmentList.add(materalDesignFragment);
        fragmentList.add(gitFragment);
        fragmentList.add(gradleFragment);

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager(),fragmentList,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }
        });



    }


    public class MyFragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;
        private String[] mTitles;

    public MyFragmentAdapter(FragmentManager fm, List<Fragment> mFragments, String[] mTitles) {
        super(fm);
        this.mFragments = mFragments;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.e(TAG,"onAttachedToWindow");

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.e(TAG,"onDetachedFromWindow");

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.e(TAG,"onDestroy");
        if(viewPager != null){
            LogUtils.e("不等于null");
        }else{
            LogUtils.e("等于null");

        }
        super.onDestroy();

    }
}
