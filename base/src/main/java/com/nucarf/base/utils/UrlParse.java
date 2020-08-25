package com.nucarf.base.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlParse {
    /**
     * 获得解析后的URL参数
     * @param url url对象
     * @return URL参数map集合
     */
    public static Map<String, String> getUrlParams(String url){
        final Map<String, String> query_pairs = new LinkedHashMap();
        URL mUrl  = stringToURL(url);

        if(mUrl == null)
        {
            return query_pairs;
        }
        try {
            String query = mUrl.getQuery();
            if(query==null){
                return query_pairs;
            }
            //判断是否包含url=,如果是url=后面的内容不用解析
            if(query.contains("url=")){
                int index = query.indexOf("url=");
                String urlValue = query.substring(index + 4);
                query_pairs.put("url", URLDecoder.decode(urlValue, "UTF-8"));
                query = query.substring(0, index);
            }
            //除url之外的参数进行解析
            if(query.length()>0) {
                final String[] pairs = query.split("&");
                for (String pair : pairs) {
                    final int idx = pair.indexOf("=");
                    //如果等号存在且不在字符串两端，取出key、value
                    if (idx > 0 && idx < pair.length() - 1) {
                        final String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                        final String value = URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                        query_pairs.put(key, value);
                    }
                }
            }
        }catch (Exception ex){
            LogUtils.e(ex.getMessage());
        }
        return query_pairs;
    }
    /**
     * 字符串转为URL对象
     * @param url url字符串
     * @return url对象
     */
    private static URL stringToURL(String url){
        if(url==null || url.length() == 0 || !url.contains("://")){
            return null;
        }
        try {
           url =  url.replace("nucarf","http");
            //StringBuilder sbUrl = new StringBuilder("http");
            //sbUrl.append(url.substring(url.indexOf("://")));
            URL mUrl = new URL(url.toString());
            return mUrl;
        }catch (Exception ex){
            LogUtils.e(ex.getMessage());
            return null;
        }
    }
}
