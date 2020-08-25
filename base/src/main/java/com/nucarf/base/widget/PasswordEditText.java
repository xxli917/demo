package com.nucarf.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.nucarf.base.R;


/**
 * @author lixiaoxue
 * @date On 2019/8/5
 */
public class PasswordEditText extends LinearLayout implements TextWatcher, View.OnClickListener {
    private static Context context;

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
    private static final String LIMITLENGTH = "limitLength";//: 8,
    private static final String IMAGE = "image";//;:"hide",
    private static final String IMAGESELECTED = "imageSelected";//"display"
    private static final String CLEARIMAGE = "clearImage";//"delete"
    private static final String MIPMAP = "mipmap";


    private EditText editText;
    private ImageView showView;
    private ImageView deletView;
    private boolean isHide = true;//默认隐藏密码
    private Drawable imageDrawable ;
    private Drawable selectDrawable;
    private TextWatcher watcher;
    private boolean mHasFoucs;
    private int textSize = 15;
    private int textColor = Color.parseColor("#051C3E");


    public PasswordEditText(Context context) {
        super(context);
    }

    public PasswordEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs, defStyleAttr);
    }


    private void init(Context context, AttributeSet attrs, final int defStyleAttr) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PasswordEditText, defStyleAttr, 0);
        String hint = array.getString(R.styleable.PasswordEditText_password_hint);
        textSize = array.getDimensionPixelSize(R.styleable.PasswordEditText_password_textSize, textSize);
        textColor = array.getColor(R.styleable.PasswordEditText_password_textColor,textColor);
        imageDrawable = array.getDrawable(R.styleable.PasswordEditText_password_image);
        selectDrawable = array.getDrawable(R.styleable.PasswordEditText_password_seleted_image);
                array.recycle();
        View.inflate(context, R.layout.view_password, this);
        editText = findViewById(R.id.edit);
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
        editText.setTextColor(textColor);
        showView = findViewById(R.id.show);
        deletView = findViewById(R.id.delete);
        editText.addTextChangedListener(this);
        showView.setVisibility(INVISIBLE);
        deletView.setVisibility(INVISIBLE);
        showView.setOnClickListener(this);
        deletView.setOnClickListener(this);
        editText.setHint(hint);
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                mHasFoucs = hasFocus;
                if(mHasFoucs){
                    showView.setVisibility(VISIBLE);
                    deletView.setVisibility(VISIBLE);
                }else{
                    showView.setVisibility(GONE);
                    deletView.setVisibility(GONE);
                }
            }
        });

        if (isHide) {
            showView.setImageDrawable(imageDrawable);
        } else {
            showView.setImageDrawable(selectDrawable);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(mHasFoucs){
            if (TextUtils.isEmpty(s.toString())) {
                deletView.setVisibility(INVISIBLE);
                showView.setVisibility(INVISIBLE);
                isHide = true;
                showView.setImageDrawable(imageDrawable);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            } else {
                deletView.setVisibility(VISIBLE);
                showView.setVisibility(VISIBLE);
            }
        }

        if(watcher != null){
            watcher.afterTextChanged(s);
        }

    }

    @Override
    public void onClick(View v) {
        if (R.id.show == v.getId()) {
            isHide = !isHide;
            String content = editText.getText().toString();
            if (isHide) {
                showView.setImageDrawable(imageDrawable);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码不可见

            } else {
                showView.setImageDrawable(selectDrawable);
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance()); //密码可见
            }

            if (!TextUtils.isEmpty(content)) {
                editText.setSelection(content.length());
            }

        } else if (R.id.delete == v.getId()) {
            editText.setText("");
        }

    }

    public String getText() {
        return editText.getText().toString();
    }
    public void setTextChangedListener(TextWatcher watcher){
        this.watcher = watcher;

    }

    public void setText(String password) {
        editText.setText(password);
    }


}
