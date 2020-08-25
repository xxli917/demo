package com.lixiaoxue.demolxx.widget;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.lixiaoxue.demolxx.R;
import com.lixiaoxue.demolxx.base.BaseActivity;
import com.nucarf.base.utils.LogUtils;

import java.io.File;

public class AnimateActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate);
    }

    @Override
    protected void initData() {
        //内部目录 不需要权限
        File file = new File(getFilesDir(), "test");
        LogUtils.e("filepath = "+file.getAbsolutePath()+"---"+file.getPath());
        //内部缓存目录，大小1M,可能被系统删除
        file = new File(getCacheDir(),"test");
        LogUtils.e("filepath = "+file.getAbsolutePath()+"---"+file.getPath());
        //外部储存 需要权限
        //私有目录 当用户卸载应用时，系统会清除这些文件。
        file = getExternalFilesDir(null) ;
        LogUtils.e("filepath = "+file.getAbsolutePath()+"---"+file.getPath());
        //公开目录
        //如果要保存照片、音频文件或视频剪辑，请使用 MediaStore API。
        //如果要保存任何其他文件（如 PDF 文档），请使用 ACTION_CREATE_DOCUMENT intent，这是存储访问框架的一部分。
       // getSharedPreferences();
        getPreferences(Context.MODE_PRIVATE);




    }
}