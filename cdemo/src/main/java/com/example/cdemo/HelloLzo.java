package com.example.cdemo;

/**
 * 压缩算法工具
 */
public class HelloLzo {

    static {
        System.loadLibrary("hello-lzo");
    }

    public static native byte[] testCompress(byte[] originalBytes);

    public static native byte[] compress(byte[] originalBytes);

    public static native byte[] uncompress(byte[] originalBytes);
}
