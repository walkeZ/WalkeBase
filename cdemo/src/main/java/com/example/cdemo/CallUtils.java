package com.example.cdemo;

/**
 * https://blog.csdn.net/qq_32175491/article/details/83588167
 */
public class CallUtils {
    static {
        System.loadLibrary("use_ndk_build");
    }
    public static native String callSimpleInfo();
}
