package com.lixiaoxue.demolxx.net;

import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.SharedKeys;
import com.nucarf.base.utils.SharedPreferencesUtil;

/**
 * Creator: mrni-mac on 16-menu_code_no_pressed-4.
 * Email  : nishengwen_android@163.com
 */
public class RetrofitConfig {

    public static final String ERROR_NETWORK = "net_error";
    public static final String ERROR_PARSE = "parse_error";

    public final static int HTTP_CONNECT_TIMEOUT = 1000 * 15;
    public final static int HTTP_READ_TIMEOUT = 1000 * 15;

    public static final String TEST_HOST_URL = "http://gegas.nucarf.tech/";//测试base_urlhttp://gegas.nucarf.tech/rest/gegas/ http://open.nucarf.tech/rest/pegas/login
    public static final String QA_HOST_URL = "https://gegas.nucarf.cn/";//预发base_url
    public static final String ONLINE_HOST_URL = "https://gegas.nucarf.com/";//线上环境

    public static final String WEB_TEST = "http://id.nucarf.tech/";
    public static final String WEB_QA = "https://id.nucarf.cn/";
    public static final String WEB_ONLINE = "https://id.nucarf.com/";



    public static String getBaseUrl() {
       /* if(Const.DEV.equals(BuildConfig.FLAVOR)){
            String url = TEST_HOST_URL;
            return SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), SharedKeys.BASE_URL,url);
        }else{
           return ONLINE_HOST_URL;
        }*/
        String url = TEST_HOST_URL;
        return url;

    }


    public static void setBaseUrl(String url) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.BASE_URL,url);
    }

    public static String getWebBaseUrl() {
        String url = WEB_TEST;
        return SharedPreferencesUtil.getStringValue(BaseAppCache.getContext(), SharedKeys.WEB_BASE_URL,url);

    }
    public static void setWebBaseUrl(String url) {
        SharedPreferencesUtil.setValue(BaseAppCache.getContext(), SharedKeys.WEB_BASE_URL,url);
    }
}
