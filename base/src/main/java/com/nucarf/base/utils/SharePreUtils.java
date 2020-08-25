package com.nucarf.base.utils;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by WANG on 2016/4/27.
 */
public class SharePreUtils {
    public static SharePreUtils instance;

    public static synchronized SharePreUtils getInstance() {
        if (null == instance) {
            instance = new SharePreUtils();
        }
        return instance;
    }

    // 请求时url 拼接添加验证
    public static String getSign(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.SIGN, "");

    }

    public static void setSign(Context context, String sign) {
        SharedPreferencesUtil.setValue(context, SharedKeys.SIGN, sign);

    }

    // 是否是第一次进入app
    public static boolean getIsNewUser() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getBoolean(SharedKeys.IsNewUser, true);
    }

    public static void setIsNewUser(boolean isFirst) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.IsNewUser, isFirst);

    }

//    public static boolean getisFirstInstall(Context context) {
//        return SharedPreferencesUtil.getSharedPreferences(context).getBoolean(SharedKeys.ISFIRST, true);
//    }
//
//    public static void setIsFirstInstall(Context context, boolean isfIRST) {
//        SharedPreferencesUtil.setValue(context, SharedKeys.ISFIRST, isfIRST);
//
//    }

    //是否登录 true ,false
    public static boolean getIsLogin(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getBoolean(SharedKeys.IS_LOGIN, false);
    }

    public static void setLoginStatus(Context context, boolean isLogin) {
        SharedPreferencesUtil.setValue(context, SharedKeys.IS_LOGIN, isLogin);

    }


    public static String getToken() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.TOKEN, "");
    }

    public static void setToken(String jwt_token) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.TOKEN, jwt_token);

    }

    public static void setName(String name) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.NAME, name);
    }

    public static String getName() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.NAME, "");
    }

    public static void setUserName(Context context, String username) {
        SharedPreferencesUtil.setValue(context, SharedKeys.USER_NAME, username);
    }

    public static String getUserName() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.USER_NAME, "");
    }

    public static void setUserPhone(String phone) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.USER_PHONE, phone);
    }

    public static String getUserPhone() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.USER_PHONE, "");
    }

    public static void setUserCode(Context context, String usercode) {
        SharedPreferencesUtil.setValue(context, SharedKeys.USER_CODE, usercode);
    }

    public static String getUserCode(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.USER_CODE, "");
    }

    public static void setMemberId(Context context, String memberId) {
        SharedPreferencesUtil.setValue(context, SharedKeys.MEMBER_ID, memberId);
    }

    public static String getMemberId(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.MEMBER_ID, "");
    }

    public static String getBalance(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.BALANCE, "");
    }

    public static void setBalance(Context context, String balance) {
        SharedPreferencesUtil.setValue(context, SharedKeys.BALANCE, balance);

    }

    public static void setRegion(Context context, String region) {
        SharedPreferencesUtil.setValue(context, SharedKeys.REGION, region);

    }

    public static String getRegion(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.REGION, "");
    }

    public static void setCar_no(Context context, String car_no) {
        SharedPreferencesUtil.setValue(context, SharedKeys.CAR_NO, car_no);

    }

    public static String getCar_no(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.CAR_NO, "");
    }

    public static void setLatLong(Context context, String latlong) {
        SharedPreferencesUtil.setValue(context, SharedKeys.LAT_LONG, latlong);
    }

    public static String getLatLong(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.LAT_LONG, "");
    }

    public static void setProvince(Context context, String province) {
        SharedPreferencesUtil.setValue(context, SharedKeys.PROVINCE, province);
    }

    public static void setProvinceAddress(Context context, String address) {
        SharedPreferencesUtil.setValue(context, SharedKeys.ADDRESS, address);
    }

    public static String getProvinceAddress(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.ADDRESS, "");
    }

    public static String getProvince(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.PROVINCE, "");
    }


    /**
     * not_pass "0"非免密 “1”免密
     *
     * @param context
     * @param not_pass
     */
    public static void setNoPass(Context context, String not_pass) {
        SharedPreferencesUtil.setValue(context, SharedKeys.NOT_PASS, not_pass);

    }

    public static String getNoPass(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.NOT_PASS, "");

    }

    public static void setSetPayPass(Context context, String pay_pass) {
        SharedPreferencesUtil.setValue(context, SharedKeys.PAY_PASS, pay_pass);
    }

    public static String getSetPayPass(Context context) {
        return SharedPreferencesUtil.getSharedPreferences(context).getString(SharedKeys.PAY_PASS, "");
    }

    public static void setDownLoadApkPath(String path) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.SP_DOWNLOAD_PATH, path);
    }

    public static String getDownLoadApkPath() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.SP_DOWNLOAD_PATH, "");

    }

    public static void setHeaderUrl(Context context, String headimg) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.HEADER_URL, headimg);

    }

    public static String getHeaderUrl() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.HEADER_URL, "");
    }

    public static void setIsSetAlias(boolean isSetAlias) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.ISSETALIAS, isSetAlias);
    }

    public static boolean getIsSetAlias() {
        return SharedPreferencesUtil.getBooleanValue(BaseAppCache.getContext(), SharedKeys.ISSETALIAS, false);
    }

    public static void setNoCommentOrderCount(String count) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.NOCOMMENTCOUNT, count);
    }

    public static String getNoCommentOrderCount() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.NOCOMMENTCOUNT, "");

    }

    public static String getDefaultFuel() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.DEFAULTFUEL, "");

    }

    public static void setDefaultFuel(String type_fuelno) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.DEFAULTFUEL, type_fuelno);
    }

    public static void removeKey(Context context) {
        String sarr[] = {SharedKeys.JWT_TOKEN, SharedKeys.IS_LOGIN, SharedKeys.DEFAULTFUEL, SharedKeys.NOT_PASS, SharedKeys.JWT_TOKEN, SharedKeys.SIGN};
        SharedPreferencesUtil.removeKeyArray(context, sarr);
    }


    public static void setCarrierType(String carrierTypeStr) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.CARRIERTYPESTR, carrierTypeStr);

    }

    public static String getCarrierType() {
        return SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), SharedKeys.CARRIERTYPESTR);
    }

    public static void setPassword(String password) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.PASSWORD, password);

    }

    public static String getPassword() {
        return SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), SharedKeys.PASSWORD);
    }

    //设置登录密码，加密
    public static void setLoginPassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return;
        }
        try {
            byte[] passBytes = AESUtil.encrypt(password.getBytes("ISO8859-1"), AESUtil.KEY, AESUtil.IV);
            String p = new String(passBytes, "ISO8859-1");
            SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.PASSWORD, p);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //设置登录密码，加密
    public static void setLoginPassword(String key,String password) {
        if (TextUtils.isEmpty(password)) {
            return;
        }
        try {
            byte[] passBytes = AESUtil.encrypt(password.getBytes("ISO8859-1"), AESUtil.KEY, AESUtil.IV);
            String p = new String(passBytes, "ISO8859-1");
            SharedPreferencesUtil.setValue(BaseAppCache.getContext(), key, p);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getLoginPassword(String key) {
        String passWord =  SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), key);
        if(!TextUtils.isEmpty(passWord)) {
            try {
                byte[] passBytes = AESUtil.decrypt(passWord.getBytes("ISO8859-1"), AESUtil.KEY, AESUtil.IV);
                return new String (passBytes,"ISO8859-1");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "";
    }
    public static String getLoginPassword() {
        String passWord =  SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), SharedKeys.PASSWORD);
        if(!TextUtils.isEmpty(passWord)) {
            try {
                byte[] passBytes = AESUtil.decrypt(passWord.getBytes("ISO8859-1"), AESUtil.KEY, AESUtil.IV);
                return new String (passBytes,"ISO8859-1");

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return "";
    }

    public static String getJiraName() {
        return SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), SharedKeys.JIRE_NAME);
    }

    public static String getJiraPassword() {
        return SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), SharedKeys.JIRE_PASSWORD);
    }
    public static void setJireName(String jireName){
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.JIRE_NAME, jireName);
    }
    public static void setJriePassword(String jirePassword){
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.JIRE_PASSWORD, jirePassword);
    }

    public static void setVersionCode(Context context, int versionCode) {
        SharedPreferencesUtil.setValue(context, SharedKeys.NUCARF_VERSION_CODE, versionCode);
    }
    public static int getVersionCode(){
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getInt(SharedKeys.NUCARF_VERSION_CODE,0);
    }

    public static String getAdMessage() {
        return SharedPreferencesUtil.getSharedPreferences(BaseAppCache.getContext()).getString(SharedKeys.AD,"");
    }
    public static void setAdMessage(String ad){
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.AD, ad);
    }
}
