package com.nucarf.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Administrator on 2018/4/13.
 */

public class MD5Utils {

    //获取MD5
    public static String getMd5(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] bytes = str.getBytes();
            messageDigest.update(bytes);
            byte[] result = messageDigest.digest();
            String urlMd5 = bufferToHex(result);
//            Log.v("Md5", urlMd5);
            return urlMd5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getSign(Map<String, String> map, String secret) {
        Map<String, String> map1 = new TreeMap<String,String>();
        map1.putAll(map);

        String sign = "";
        map1.put("api_time_stamp", "" + System.currentTimeMillis() / 1000);
        map1.put("api_random_number", "" + new Random().nextInt(100000));
        Map<String, String> sortMapByKey = NumberUtils.sortMapByKey(map1);
        sign = "secret=" + secret;
        for (Map.Entry<String, String> entry : sortMapByKey.entrySet()) {
            sign += "&" + entry.getKey() + "=" + entry.getValue();
        }
        LogUtils.e("tag",sign);
        sign = getMd5(encryptToSHA(sign));

        sign += "_" + sortMapByKey.get("api_time_stamp") + "_" + sortMapByKey.get("api_random_number");
        LogUtils.e("tag",sign);

        return sign;
    }

    public static void main1(String args[]) {
        Map<String, String> map = new TreeMap<String, String>();
        map.put("appid", "12345");
        map.put("code", "1111");
        map.put("deviceId", "12345");
        map.put("type", "3");
        map.put("username", "17600467122");
        String sign = "";
        map.put("api_time_stamp", "" + System.currentTimeMillis() / 1000);
        map.put("api_random_number", "" + new Random().nextInt(100000));
        System.out.print("" + map);

        Map<String, String> sortMapByKey = NumberUtils.sortMapByKey(map);
        System.out.print("" + sortMapByKey);

        sign = "secret=" + "wxw";
        for (Map.Entry<String, String> entry : sortMapByKey.entrySet()) {
            sign += "&" + entry.getKey() + "=" + entry.getValue();
        }
        System.out.print("" + sign);
        sign = getMd5(encryptToSHA(sign));
        sign += "_" + sortMapByKey.get("api_time_stamp") + "_" + sortMapByKey.get("api_random_number");

        System.out.print("" + sign);
    }
    public static void main(String args[]) {
//        ;113.680635,34.843955;
        String string = ",113.680635,34.843955,";
        String replaceFirst = string.replaceFirst(",", "");
        System.out.print("---" + replaceFirst);

    }

    /**
     * 获取sha1加密值
     *
     * @param info
     * @return
     */
    public static String encryptToSHA(String info) {
        byte[] digesta = null;
        try {
            MessageDigest alga = MessageDigest.getInstance("SHA-1");
            alga.update(info.getBytes());
            digesta = alga.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String rs = byte2hex(digesta);
        return rs;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs;
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {

        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }
}
