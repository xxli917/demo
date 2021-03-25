package com.nucarf.javalib;


import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtil {

    static final String KEY_ALGORITHM = "AES";
    static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";
    static final String KEY = "fdbab8561f7138914179b773a732e1aa";


    //加密
    public static String encrypt(String content, String key) throws Exception {
        byte[] contentBytes = content.getBytes("utf-8");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
        byte[] raw = key.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(contentBytes);
        Base64.Encoder encoder = Base64.getEncoder();
        String encodedText = encoder.encodeToString(encrypted);
        return encodedText;
    }

    // 解密
    public static String decrypt(String content, String key) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] enCode = decoder.decode(content);
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);
            byte[] raw = key.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] original = cipher.doFinal(enCode);
            return new String(original, "utf-8");
        } catch (Exception ex) {
            System.out.println("错误信息" + ex.getMessage());
            return null;
        }
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String content = "18222116409";
        System.out.println("加密前：" + content);
        try {
            // 加密
            String encodedText = encrypt(content, KEY);
            System.out.println("加密后：" + encodedText);
            //解密
            String decode = decrypt(encodedText, KEY);
            System.out.println("解密后" + decode);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}