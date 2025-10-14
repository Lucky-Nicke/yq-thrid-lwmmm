package com.lanxige.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Helper {
    public static String md5(String str) {
        char hexChars[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        byte[] bytes = str.getBytes();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            int j = bytes.length;
            char[] chars = new char[j * 2];
            int k = 0;
            for (int i = 0; i < bytes.length; i++) {
                byte byte0 = bytes[i];
                chars[k++] = hexChars[byte0 >>> 4 & 0xf];
                chars[k++] = hexChars[byte0 & 0xf];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException("MD5加密出错！！+" + e);
        }
    }

    //测试MD5
//    public static void main(String[] args) {
//        String original = "Nicke";
//        String encrypted = MD5Helper.md5(original);
//        System.out.println("原始字符串：" + original);
//        System.out.println("MD5加密结果（自定义字符集）：" + encrypted);
//    }
}
