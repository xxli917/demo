package com.nucarf.base.utils;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;


/**
 * Description: 文字监听
 * Author: wang
 * Time: 2017/7/10 menu_code_pressed:08
 */
public class TextWatcherUtils implements TextWatcher {
    private EditText mEditText;
    private int editStart;
    private int editEnd;
    //中文汉字的长度 ，英文为中文的2倍
    private int maxLength;
    private Context mContext;
    //是否区分中英文
    private boolean mpdistinguishChineseOrEnglish;


    public TextWatcherUtils(Context pContext, EditText pEditText, int pMaxLength, boolean pdistinguishChineseOrEnglish) {
        this.mEditText = pEditText;
        this.maxLength = pMaxLength;
        this.mContext = pContext;
        mpdistinguishChineseOrEnglish = pdistinguishChineseOrEnglish;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mChangeListener != null) {
            mChangeListener.onTextChange(mEditText.getId(), s, start, before, count);
        }

    }

    @Override
    public void afterTextChanged(Editable s) {
        editStart = mEditText.getSelectionStart();
        editEnd = mEditText.getSelectionEnd();

        // 先去掉监听器，否则会出现栈溢出
        mEditText.removeTextChangedListener(this);

        // 注意这里只能每次都对整个EditText的内容求长度，不能对删除的单个字符求长度
        // 因为是中英文混合，单个字符而言，calculateLength函数都会返回1
        if (mListener != null) {
            mListener.current_text_length(mEditText.getId(),calculateLength(s.toString()) + "");
        }
        LogUtils.d("字符数", calculateLength(s.toString()) + "");
        while (calculateLength(s.toString()) > maxLength) { // 当输入字符个数超过限制的大小时，进行截断操作
            ToastUtils.showShort("最多输入" + maxLength + "个字");
            s.delete(editStart - 1, editEnd);
            editStart--;
            editEnd--;
        }
        mEditText.setText(s);
        mEditText.setSelection(editStart);

        // 恢复监听器
        mEditText.addTextChangedListener(this);
    }

    private long calculateLength(CharSequence c) {
        double len = 0;
        if (mpdistinguishChineseOrEnglish) {
            for (int i = 0; i < c.length(); i++) {
                int tmp = (int) c.charAt(i);
                if (tmp > 0 && tmp < 127) {
                    len += 0.5;
                } else {
                    len++;
                }
            }
        } else {
            for (int i = 0; i < c.length(); i++) {
                len++;

            }
        }
        return Math.round(len);
    }

    public interface textlengthListener {
        void current_text_length(int id,String length);

    }

    private textlengthListener mListener;

    public void setOnTextLengthListener(textlengthListener pListener) {
        this.mListener = pListener;
    }

    public interface textChangeListener {
        void onTextChange(int id, CharSequence s, int start, int before, int count);

    }

    private textChangeListener mChangeListener;

    public void setOnTextChangeListener(textChangeListener pListener) {
        this.mChangeListener = pListener;
    }
}
