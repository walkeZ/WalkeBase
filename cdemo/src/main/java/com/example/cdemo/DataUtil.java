package com.example.cdemo;

import java.util.Formatter;

public class DataUtil {

    public static String bytesToHexString(byte[] bytes) {
        Formatter fmt = new Formatter(new StringBuilder(bytes.length * 2));
        byte[] var2 = bytes;
        int var3 = bytes.length;

        for (int var4 = 0; var4 < var3; ++var4) {
            byte b = var2[var4];
            fmt.format("%02x", b);
        }

        return fmt.toString().toUpperCase();
    }


    public static byte[] hexStringToBytes(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];

        for (int i = 0; i < l; ++i) {
            ret[i] = Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
        }

        return ret;
    }
}
