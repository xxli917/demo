package com.nucarf.base.utils;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 限制输入两位小数
 */
public class MoneyTextWatcher implements TextWatcher {
    private EditText editText;
    private OnAfterTextChangeListener onAfterTextChangeListener;
    private int digits = 2;//小数点位数
    private int maxInt = -1;//整数位数
    //抹零规则（1关 2抹百位 3抹拾位 4抹个位 5抹小数位 6小数点后一位 7小数点后2位（不进位） 8小数点后2位（进位））
    private String balanceExpenses = "";

    public MoneyTextWatcher(EditText et) {
        editText = et;
    }

    public MoneyTextWatcher(EditText et, OnAfterTextChangeListener listener) {
        editText = et;
        onAfterTextChangeListener = listener;
    }

    public MoneyTextWatcher setDigits(int d) {
        digits = d;
        return this;
    }

    public MoneyTextWatcher setMaxInt(int d) {
        maxInt = d;
        return this;
    }

    public void setNumberRule(String balanceExpenses) {
        this.balanceExpenses = balanceExpenses;
        if (TextUtils.isEmpty(balanceExpenses)) {
            return;
        }
        switch (Integer.parseInt(balanceExpenses)) {
            case 1:
            case 7:
            case 8:
                digits = 2;
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                break;
            case 2:
            case 3:
            case 4:
            case 5:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 6:
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                digits = 1;
                break;

        }
    }

    public interface OnAfterTextChangeListener {
        void afterTextChange(CharSequence s);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //删除“.”后面超过2位后的数据
        if (s.toString().contains(".")) {
            if (s.length() - 1 - s.toString().indexOf(".") > digits) {
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + digits + 1);
                editText.setText(s);
                editText.setSelection(s.length()); //光标移到最后
            }
            if (maxInt > 0 && s.toString().indexOf(".") > maxInt) {
                String a;
                String left = s.toString().substring(0, maxInt);
                String right = s.toString().substring(maxInt + 1, s.length());
                a = left + right;
                editText.setText(a);
                editText.setSelection(a.length()); //光标移到最后
            }
        } else {
            if (maxInt > 0 && s.length() > maxInt) {
                s = s.toString().substring(0, maxInt);
                editText.setText(s);
                editText.setSelection(s.length()); //光标移到最后
            }
        }
        //如果"."在起始位置,则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")) {
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0,且第二位跟的不是".",则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1) {
            if (!s.toString().substring(1, 2).equals(".")) {
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
//                return;
            }
        }
        if (!TextUtils.isEmpty(s)) {
            s = NumberUtils.getFormatNumber(balanceExpenses, s.toString());
        }
        if (onAfterTextChangeListener != null) {
            onAfterTextChangeListener.afterTextChange(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
//        String formatNumber = NumberUtils.getFormatNumber(balanceExpenses, editText.getText().toString());
//        editText.removeTextChangedListener(this);
//        editText.setText(formatNumber);
//        editText.setSelection(formatNumber.length());
//        if (onAfterTextChangeListener != null) {
//            onAfterTextChangeListener.afterTextChange(formatNumber);
//        }
//        editText.addTextChangedListener(this);
    }
}
