package com.nucarf.base.utils;

import android.util.Log;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    static final String KEY_ALGORITHM = "AES";
    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    static final String KEY = "mQbJILokBccRHUkS";
    static final String IV = "mQbJILokBccRHUkS";


    //加密
    public static byte[] encrypt(byte[] content, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        byte[] raw = key.getBytes("ASCII");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        IvParameterSpec ivps = new IvParameterSpec(iv.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivps);
        byte[] encrypted = cipher.doFinal(content);
        return encrypted;
    }

    // 解密
    public static byte[] decrypt(byte[] content, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            IvParameterSpec ivps = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivps);
            byte[] original = cipher.doFinal(content);
            return original;
        } catch (Exception ex) {
            Log.e("--",ex.getMessage());
            return null;
        }
    }
}