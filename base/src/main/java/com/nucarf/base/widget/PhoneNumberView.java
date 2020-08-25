package com.nucarf.base.widget;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.widget.AppCompatEditText;

import com.nucarf.base.R;
import com.nucarf.base.utils.LogUtils;


/**
 * @author lixiaoxue
 * @date On 2019/8/5
 */
public class PhoneNumberView extends AppCompatEditText implements
        View.OnFocusChangeListener, TextWatcher {

    private static final String HINT = "hint";
    private static final String TEXT = "text";
    private static final String TEXT_SIZE = "textSize";
    private static final String TEXT_COLOR = "textColor";
    private static final String HINT_TEXT_COLOR = "hintTextColor";
    private static final String INPUT_TYPE = "inputType";
    private static final String ONCLICK = "onClick";
    private static final String OBSERVE = "observe";
    private static final String PADDINGTOP = "paddingTop";
    private static final String PADDINGLEFT = "paddingLeft";
    private static final String PADDINGRIGHT = "paddingRight";
    private static final String PADDINGBOTTOM = "paddingBottom";
    private static final String BACKGROUNDCOLOR = "backgroundColor";
    private static final String BACKGROUND = "background";

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String MARGINLEFT = "marginLeft";
    private static final String MARGINTOP = "marginTop";
    private static final String MARGINRIGHT = "marginRight";
    private static final String MARGINBOTTOM = "marginBottom";
    private static final String WEIGHT = "weight";
    private static final String SECURETEXTENTRY = "secureTextEntry";
    private static boolean tag = false;

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    private TextWatcher watcher;

    private  boolean isPast = false;


    public PhoneNumberView(Context context) {
        this(context, null, 0);
    }

    public PhoneNumberView(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PhoneNumberView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }








    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
//          throw new NullPointerException("You can add drawableRight attribute in XML");
            mClearDrawable = getResources().getDrawable(R.mipmap.delete);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }


    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }

        if (s == null || s.length() == 0) return;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i != 3 && i != 8&& s.charAt(i) == ' ') {
                continue;
            } else {
                sb.append(s.charAt(i));
                if ((sb.length() == 4 || sb.length() == 9) && sb.charAt(sb.length() - 1) != ' ') {
                    sb.insert(sb.length() - 1, ' ');
                }
            }
        }
        if (!sb.toString().equals(s.toString())) {
            int index = start + 1;
            if (sb.charAt(start) == ' ') {
                if (start == 0) {
                    index++;
                } else {
                    index--;
                }
            } else {
                if (start == 1) {
                    index--;
                }
            }
            setText(sb.toString());
            setSelection(sb.length());
        }

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }


    @Override
    public void afterTextChanged(Editable s) {

        if (s.toString().length() == 13) {
            InputMethodManager manager = ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE));
            if (manager != null)
                manager.hideSoftInputFromWindow(getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if(watcher != null){
            watcher.afterTextChanged(s);
        }


    }

    public void setTextChangedListener(TextWatcher watcher){
        this.watcher = watcher;

    }


    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }
}
