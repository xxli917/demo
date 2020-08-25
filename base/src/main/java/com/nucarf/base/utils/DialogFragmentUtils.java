package com.nucarf.base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.nucarf.base.R;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


/**
 * 使用DialogFragment实现弹窗，解决Dialog生命周期同步问题
 */
public class DialogFragmentUtils {
    public static DialogFragmentUtils instance;

    public static DialogFragmentUtils getInstance() {
        if (null == instance) {
            instance = new DialogFragmentUtils();
        }

        return instance;
    }

    public static void showDoubleDialog(FragmentActivity context){
        MyDialogFragment myDialogFragment = new MyDialogFragment();
        myDialogFragment.show(context.getSupportFragmentManager(),"dialog");
    }



    //center dialog
    public static Dialog showCenterDialog(Context context, View view,double width ,double height,boolean isoutside){
        final Dialog dialog = new Dialog(context, R.style.customMainDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
        // 设置dialog距屏幕的边距都为0
        // 适配一些手机，会自带背景高度
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        //params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = (int)(ScreenUtil.getScreenWidth(context)*width); //设置宽度
        params.height = (int)(ScreenUtil.getScreenHeight(context)*height);
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }
    public static Dialog showCenterDialog(Context context, View view, boolean isoutside){
        final Dialog dialog = new Dialog(context, R.style.customMainDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
        // 设置dialog距屏幕的边距都为0
        // 适配一些手机，会自带背景高度
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = (int)(ScreenUtil.getScreenWidth(context)*0.7); //设置宽度
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }
    public static Dialog showCenterDialog(Context context, String title, String content, final OnClickListener ensureListener , boolean isoutside){
        final Dialog dialog = new Dialog(context, R.style.customMainDialog);
        View view = View.inflate(context, R.layout.layout_center_dialog,null);
        TextView titleView = view.findViewById(R.id.title_view);
        TextView contentView = view.findViewById(R.id.content_view);
        TextView cancelView = view.findViewById(R.id.cancel_view);
        TextView ensureView = view.findViewById(R.id.ensure_view);
        titleView.setText(title);
        contentView.setText(content);
        cancelView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ensureView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ensureListener != null){
                    ensureListener.onClick(v);
                    dialog.dismiss();
                }

            }
        });

        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
        // 设置dialog距屏幕的边距都为0
        // 适配一些手机，会自带背景高度
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = (int)(ScreenUtil.getScreenWidth(context)*0.7); //设置宽度
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }


    //bottom dialog
    public static BottomSheetDialog showBottomSheetDialog(Context context, View view, boolean isoutside) {
        BottomSheetDialog dialog=new BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme);
        dialog.setCanceledOnTouchOutside(isoutside);
        dialog.setContentView(view);
        dialog.show();
        return dialog;
    }
    public static BottomSheetDialog showBottomSheetDialog(Context context, View view, int height, boolean isoutside) {
        BottomSheetDialog dialog=new BottomSheetDialog(context, R.style.CustomBottomSheetDialogTheme);
        dialog.setCanceledOnTouchOutside(isoutside);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
//         设置dialog距屏幕的边距都为0
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height =height;
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        dialog.show();
        return dialog;
    }
    public static Dialog showBottomDialog(Context context, View view, boolean isoutside) {
        Dialog dialog = new Dialog(context, R.style.customMainDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
//         设置dialog距屏幕的边距都为0
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }

    /**
     * 自定义Dialog内容
     *
     * @param context
     * @param view
     * 可以指定高度，宽度全屏
     * @return
     */
    public static Dialog showBottomDialog(Context context, View view, int height, boolean isoutside) {
        Dialog dialog = new Dialog(context, R.style.CustomBottomSheetDialogTheme);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
//         设置dialog距屏幕的边距都为0
        dialog.getWindow().getDecorView().setPadding(0, 0, 0, 0);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = height;
        params.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }



    /**
     * 自定义Dialog内容 背景颜色 60 黑
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog showCustomPushDialog(Context context, View view, boolean isoutside) {
        Dialog dialog = new Dialog(context, R.style.customMainDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
//         设置dialog距屏幕的边距都为0
        dialog.getWindow().getDecorView().setPadding(0, ScreenUtil.getStatusBarHeight(context), 0, 0);
        //背景透明
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }
    /**
     * 自定义Dialog内容 背景颜色 60 黑
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog showUploadDialog(Context context, View view, boolean isoutside) {
        Dialog dialog = new Dialog(context, R.style.customMainDialog);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
//         设置dialog距屏幕的边距都为0
        dialog.getWindow().getDecorView().setPadding(0, ScreenUtil.getStatusBarHeight(context), 0, 0);
        //背景透明
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
//        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }

    /**
     * 自定义Dialog内容 背景颜色 60 黑
     *
     * @param context
     * @param view
     * @return
     */
    public static Dialog showHighWayDialog(Context context, View view, boolean isoutside) {
        Dialog dialog = new Dialog(context, R.style.customMainDialog);
//        ViewGroup.LayoutParams layoutParams =new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        // 设置宽高为match_parent，不要去算出来屏幕宽高再赋值哦，因为有些 // 有虚拟按键的手机上计算出来的高度不一定准确，所以dialog不会全屏
//         设置dialog距屏幕的边距都为0
        dialog.getWindow().getDecorView().setPadding(0, ScreenUtil.getStatusBarHeight(context), 0, 0);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.setCancelable(isoutside);
        dialog.setCanceledOnTouchOutside(isoutside);
        return dialog;
    }

    //内容多选对话框
    public static Dialog showListDialog(Context context, String title, ArrayList<String> items, final DialogItemClickListener dialogItemClickListener) {
        return ShowListItemDialog(context, title, items, dialogItemClickListener);
    }

    //内容多选对话框(样式不同)
    public static Dialog showListDialogDefind(Context context, String title, ArrayList<String> items, int pMaxHeight, final DialogItemClickListener2 dialogItemClickListener) {
        return ShowListItemDialogDefind(context, title, items, pMaxHeight, dialogItemClickListener);
    }

    public static void showPhoneDialog(final Context context,String name, final String phoneNumber) {
            View view = View.inflate(context, R.layout.layout_dialog_call, null);
            TextView contextView = view.findViewById(R.id.content_view);
            contextView.setText("确认给" + name + "拨打电话吗？");
            View cancleView = view.findViewById(R.id.cancel_view);
            View ensureView = view.findViewById(R.id.ensure_view);
            final Dialog callDialog = showCenterDialog(context, view, true);
            callDialog.show();
            cancleView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callDialog != null) {
                        callDialog.dismiss();
                    }

                }
            });
            ensureView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    AndroidUtil.callPhone(context,phoneNumber);
                    if (callDialog != null) {
                        callDialog.dismiss();
                    }
                }
            });
        }

    public static Dialog showLoadingWithIm(Context context) {
        Dialog dialog = new Dialog(context, R.style.customDialog);
        dialog.setContentView(R.layout.layout_loading);
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        //弹窗和键盘共存
        dialogWindow.setFlags(
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM,
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        dialogWindow.setAttributes(lp);
        return dialog;
    }




    //例如：举报，删除等
    private static Dialog ShowDialogMenu(Context context, String okbtn, final DialogClickListener dialogClickListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle2);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_menu, null);
        dialog.setContentView(view);

        TextView mOk = (TextView) view.findViewById(R.id.ok);
        mOk.setText(okbtn);

        view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = ScreenUtil.getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = ScreenUtil.getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();

        return dialog;
    }


    //动态创建的lsitItem
    private static Dialog ShowListItemDialog(Context context, String title, ArrayList<String> items, final DialogItemClickListener dialogClickListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle2);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list, null);
        dialog.setContentView(view);
        TextView mtittle = (TextView) view.findViewById(R.id.title);
        if ("".equalsIgnoreCase(title)) {
            mtittle.setVisibility(View.GONE);
        } else {
            mtittle.setVisibility(View.VISIBLE);
            mtittle.setText(title);
        }
        // 根据items动态创建
        LinearLayout parent = (LinearLayout) view.findViewById(R.id.dialogLayout);
        parent.removeAllViews();
        int length = items.size();
        for (int i = 0; i < items.size(); i++) {
            LayoutParams params1 = new LayoutParams(-1, -2);
            params1.rightMargin = 1;
            final TextView tv = new TextView(context);
            tv.setLayoutParams(params1);
            tv.setTextSize(18);
            tv.setText(items.get(i));
            tv.setTextColor(context.getResources().getColor(R.color.black));
            int pad = context.getResources().getDimensionPixelSize(R.dimen.padding10);
            tv.setPadding(pad, pad, pad, pad);
            tv.setSingleLine(true);
            tv.setGravity(Gravity.CENTER);
            if (i == 0) {
                //todo 第一个
                if ("".equals(title)) {
                    if (items.size() == 1) {
                        //圆形
                        tv.setBackgroundResource(R.drawable.menudialog_bottom_selector);
                    } else {
                        tv.setBackgroundResource(R.drawable.menudialog_top_selector2);
                    }

                } else {
                    if (items.size() == 1) {
                        tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);
                    } else {
                        tv.setBackgroundResource(R.drawable.menudialog_center_selector);
                    }
                }

            } else if (i == length - 1) {
                //todo 最底部的
                tv.setBackgroundResource(R.drawable.menudialog_bottom2_selector);
            } else {
                //todo 中间
                tv.setBackgroundResource(R.drawable.menudialog_center_selector);
            }

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    dialog.dismiss();
                    dialogClickListener.confirm(tv.getText().toString());
                }
            });
            parent.addView(tv);
            if (i != length - 1) {
                if (items.size() != 1) {
                    TextView divider = new TextView(context);
                    LayoutParams params = new LayoutParams(-1, (int) 1);
                    divider.setLayoutParams(params);
                    divider.setBackgroundResource(android.R.color.darker_gray);
                    parent.addView(divider);
                }
            }
        }
        view.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = ScreenUtil.getScreenWidth(context);
        mWindow.setGravity(Gravity.BOTTOM);
        // 添加动画
        mWindow.setWindowAnimations(R.style.dialogAnim);
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    static int mClickPosition;

    private static Dialog ShowListItemDialogDefind(final Context context, String title, final ArrayList<String> items, int pMaxHeight, final DialogItemClickListener2 dialogClickListener) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle2);
//        dialog.setCancelable(true);
//        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list_defind, null);
//        dialog.setContentView(view);
//        TextView mtittle = (TextView) view.findViewById(R.id.title);
//        if ("".equalsIgnoreCase(title)) {
//            mtittle.setVisibility(View.GONE);
//        } else {
//            mtittle.setVisibility(View.VISIBLE);
//            mtittle.setText(title);
//        }
//        RecyclerView mRecycleView = (RecyclerView) view.findViewById(R.id.list_recyclerview);
//        if (pMaxHeight > 0) {
//            ScreenUtil.setLinearLayoutParams(mRecycleView, LayoutParams.MATCH_PARENT, pMaxHeight);
//        }
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        mRecycleView.setLayoutManager(layoutManager);
//        DialogListAdapter mAdapter = new DialogListAdapter(context);
//        mRecycleView.setAdapter(mAdapter);
//        mAdapter.setList(items);
//        mClickPosition = -1;
//        mAdapter.setOnItemClickListener(new DialogListAdapter.OnItemClick() {
//            @Override
//            public void ClickItem(int position) {
//                Clog.e("点击了", "点击了" + items.get(position));
//                mClickPosition = position;
//                dialogClickListener.confirm(items.get(mClickPosition), mClickPosition);
//                dialog.dismiss();
//
//            }
//        });
//
//        view.findViewById(R.id.close_iv).setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                dialog.dismiss();
//            }
//        });
//        Window mWindow = dialog.getWindow();
//        WindowManager.LayoutParams lp = mWindow.getAttributes();
//        lp.width = ScreenUtil.getScreenWidth(context);
//        mWindow.setGravity(Gravity.BOTTOM);
//        // 添加动画
//        mWindow.setWindowAnimations(R.style.dialogAnim);
//        mWindow.setAttributes(lp);
//        dialog.show();
        return dialog;
    }

    public interface DialogClickListener {
        public abstract void confirm();

        public abstract void cancel();
    }

    public interface DialogItemClickListener {
        public abstract void confirm(String result);
    }

    public interface DialogItemClickListener2 {
        public abstract void confirm(String result, int position);
    }

    public interface InputView_Interface {
        void cancel();

        void onClickSend(String content, String tag);
    }

    public static AlertDialog dialogPro(Context pContext, String msg, boolean pCanAancelable) {
        LayoutInflater inflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.layout_nobtn_dialog, null);
        AlertDialog myDialog = new AlertDialog.Builder(pContext, R.style.DialogStyle3).create();
        myDialog.setView(((Activity) pContext).getLayoutInflater().inflate(R.layout.layout_nobtn_dialog, null));
        myDialog.show();
        myDialog.getWindow().setContentView(layout);
        TextView mTextView = (TextView) layout.findViewById(R.id.dialog_msg);
        mTextView.setText(msg);
        RelativeLayout mRootLayout = (RelativeLayout) layout.findViewById(R.id.dialog_root_layout);
        mRootLayout.setVisibility(View.VISIBLE);
        myDialog.setCancelable(pCanAancelable);
        Window dialogWindow = myDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        lp.width = ScreenUtil.dip2px(200);
        lp.height = ScreenUtil.dip2px(200);
        dialogWindow.setAttributes(lp);
        return myDialog;
    }

    public static Dialog showLoading(Context context){
        Dialog dialog = new Dialog(context, R.style.customDialog);
        dialog.setContentView(R.layout.layout_loading);
        dialog.show();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        dialogWindow.setAttributes(lp);
        return dialog;

    }

}