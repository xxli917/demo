package com.nucarf.base.utils;

import android.content.Intent;


/**
 * Created by Amor on 2018/4/12.
 */

public class UserUtils {

    public static void logout(){
        new ActivityHelper().finishAllActivity();
        Intent intent = new Intent("member_login");
        intent.putExtra("isLogin",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        BaseAppCache.getContext().startActivity(intent);
    }

}
