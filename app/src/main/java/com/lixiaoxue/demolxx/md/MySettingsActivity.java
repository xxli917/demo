package com.lixiaoxue.demolxx.md;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.lixiaoxue.demolxx.R;

//https://developer.android.google.cn/guide/topics/ui/settings
public class MySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_container, new MySettingsFragment())
                .commit();
    }
}