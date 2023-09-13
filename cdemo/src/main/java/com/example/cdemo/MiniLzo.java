package com.example.cdemo;

/**
 * 压缩算法工具
 */
public class MiniLzo {

    static {
        System.loadLibrary("mini-lzo");
    }

    public static native byte[] compress(byte[] originalBytes);

    public static native byte[] uncompress(byte[] originalBytes);
}
