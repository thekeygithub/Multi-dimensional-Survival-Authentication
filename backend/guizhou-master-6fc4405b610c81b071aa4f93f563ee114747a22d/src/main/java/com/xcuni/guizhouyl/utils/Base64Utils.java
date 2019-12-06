package com.xcuni.guizhouyl.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Utils {

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    public static String decryptBASE64ToString(String key) throws Exception {
        byte[] deBytes = decryptBASE64(key);
        return new String(deBytes);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    public static String encryptBASE64(String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        return encryptBASE64(keyBytes);
    }

    public static void main(String[] args) throws Exception {
        String para = "{\"IdList1\": 1,\"IdList2\": [1,2,3,4,5,6,18],\"IdList3\": [1,2]}";
        String data = Base64Utils.encryptBASE64(para.getBytes());
        System.out.println("加密后：" + data);

        byte[] byteArray = Base64Utils.decryptBASE64(data);
        System.out.println("解密后：" + new String(byteArray));
    }
}
