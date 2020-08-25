package com.nucarf.base.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.nucarf.base.R;


/**
 * 说明：底部导航菜单 <br>
 * 时间：2017/7/12 0:02<br>
 * 修改记录： <br>
 */

public class BottomBar extends RelativeLayout implements View.OnClickListener {

    /**
     * 首页
     */
    public final int HOME = 0;

    /**
     * 卡包
     */
    private static final int CARD = 1;

    /**
     * 加油码
     */
    public final int OILCODE = 2;

    /**
     * 订单
     */

    public final int ORDER = 3;
    /**
    我的
     */
    public final int MY = 4;

    LinearLayout LL_bottom_item_home;
    LinearLayout LL_bottom_item_card;

    LinearLayout LL_bottom_item_oil_code;
    LinearLayout LL_bottom_item_order;
    LinearLayout LL_bottom_item_my;

    private LinearLayout bottom_layout;
    private View view_line;

    public BottomBar(Context context) {
        super(context);
        initView(context);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    /**
     * 初始化布局
     */
    private void initView(Context context) {
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(R.layout.view_bottom_bar, this, true);
        LL_bottom_item_home =  findViewById(R.id.ll_bottom_item_home);
        LL_bottom_item_card =  findViewById(R.id.ll_bottom_item_card);
        LL_bottom_item_oil_code = findViewById(R.id.ll_bottom_item_oil_code);
        LL_bottom_item_order =  findViewById(R.id.ll_bottom_item_order);
        LL_bottom_item_my = findViewById(R.id.ll_bottom_item_my);
        bottom_layout =  findViewById(R.id.bottom_layout);

        view_line =  findViewById(R.id.view_line);
        LL_bottom_item_home.setOnClickListener(this);
        LL_bottom_item_card.setOnClickListener(this);
        LL_bottom_item_oil_code.setOnClickListener(this);
        LL_bottom_item_order.setOnClickListener(this);
        LL_bottom_item_my.setOnClickListener(this);

        setChoice(HOME);
        this.setClipChildren(false);
    }


    BottomClickListener bottomClickListener;

    /**
     *
     * @param position 下标
     */
    public void setChoice(int position) {
        LL_bottom_item_home.setSelected(position == HOME);
        LL_bottom_item_card.setSelected(position == CARD);
        LL_bottom_item_oil_code.setSelected(position == OILCODE);
        LL_bottom_item_order.setSelected(position == ORDER);
        LL_bottom_item_my.setSelected(position == MY);
    }

    public void setBottomClickListener(BottomClickListener bottomClickListener) {
        this.bottomClickListener = bottomClickListener;
    }
//    //获取发布的按钮
//    public LinearLayout getPostLayout() {
//        return LLBottomItemPost;
//    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.ll_bottom_item_home) {
            if (bottomClickListener != null) {
                bottomClickListener.onItemClick(HOME);
                setChoice(HOME);
//                view_line.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }

        } else if (i == R.id.ll_bottom_item_card) {
            if (bottomClickListener != null) {
                bottomClickListener.onItemClick(CARD);
                setChoice(CARD);
//                view_line.setBackgroundColor(Color.parseColor("#FF31353E"));
            }

        }

        else if (i == R.id.ll_bottom_item_oil_code) {
            if (bottomClickListener != null) {
                bottomClickListener.onItemClick(OILCODE);
                //setChoice(OILCODE);
//                view_line.setBackgroundColor(Color.parseColor("#FF31353E"));

            }

        } else if (i == R.id.ll_bottom_item_order) {
            if (bottomClickListener != null) {
                bottomClickListener.onItemClick(ORDER);
                setChoice(ORDER);
//                view_line.setBackgroundColor(Color.parseColor("#FF31353E"));
            }

        }

        else if (i == R.id.ll_bottom_item_my) {
            if (bottomClickListener != null) {
                bottomClickListener.onItemClick(MY);
                setChoice(MY);
//                view_line.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            }

        }
    }

    public interface BottomClickListener {
        void onItemClick(int position);
    }

}
