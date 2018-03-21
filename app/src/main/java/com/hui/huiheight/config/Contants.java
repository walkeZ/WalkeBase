package com.hui.huiheight.config;

import android.os.Environment;

/**
 * Created by walke.Z on 2017/6/16.
 */

public class Contants {

    public static final String FIRST_OPEN = "first_open";
    public static final String VERSION_NAME = "version_name";

    public static final String APP_LOCATION = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AHui/";

    public static final int PERMISSION_SDCARD_REQUEST_CODE = 74;//系统授权sd卡权限管理页面时的结果参数
    public static final int PERMISSION_CAMERA_REQUEST_CODE = 73;//系统授权摄像头管理页面时的结果参数

    public static String FILE_PROVIDER_PATHS="walke";//与xml一致

}
