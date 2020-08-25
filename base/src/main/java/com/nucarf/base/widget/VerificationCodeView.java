package com.nucarf.base.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.core.graphics.drawable.DrawableCompat;


import com.nucarf.base.R;
import com.nucarf.base.utils.LogUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;



/**
 * @author lixiaoxue
 * @date On 2019/8/6
 */
public class VerificationCodeView extends LinearLayout {
    private String TAG = VerificationCodeView.class.getSimpleName();
    private Timer timer;
    private int time = 59;
    private TextView noteView;
    private EditText codeView;
    private TextWatcher watcher;

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private boolean running = false;


    public VerificationCodeView(Context context) {
       super(context);
    }

    public VerificationCodeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerificationCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public void timer() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                running = true;
               // LogUtils.a(TAG, "执行了timer");
                Message message = Message.obtain();
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task, 0, 1000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dealMessage(msg);
        }
    };

    private void dealMessage(Message msg) {
        VerificationCodeView.this.setEnabled(false);
        if (time == 0) {
            VerificationCodeView.this.setEnabled(true);
            noteView.setText("重新获取");
            time = 59;
            cancelTime();
        }else{
            noteView.setText(time + "s");
            time--;
        }
    }
    public void cancelTime() {
        if (timer != null) {
            running = false;
            timer.cancel();
            timer = null;
        }
    }


    public void addOnClickListener(OnClickListener l) {
        noteView.setOnClickListener(l);

    }


    public void setTextChangedListener(TextWatcher watcher){
        this.watcher = watcher;

    }

    public void setEnabled(boolean enabled){
        noteView.setEnabled(enabled);
        if(enabled){
            noteView.setTextColor(getResources().getColor(R.color.blue));
        }else{
            noteView.setTextColor(getResources().getColor(R.color.color_c3c7ce));
        }
}
    /**
     * 初始化View
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context,R.layout.view_verification_code,this);
        codeView = findViewById(R.id.code);
        codeView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.a(TAG,"afterTextChanged");
                if(watcher != null){
                    watcher.afterTextChanged(s);
                }

            }
        });
        noteView = findViewById(R.id.note);
        noteView.setEnabled(false);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.VerificationCodeView, defStyleAttr, 0);

        array.recycle();
    }


    public String getText() {
        return codeView.getText().toString();
    }
    public void setText(String text){
        codeView.setText(text);

    }

    public void resume() {
        if(time >0 && timer == null){
            timer();
        }
    }
}
