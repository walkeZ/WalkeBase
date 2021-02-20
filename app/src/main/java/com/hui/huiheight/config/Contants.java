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
    public static String TEST_CONTENT="GitHub是一个面向开源及私有软件项目的托管平台，因为只支持Git作为唯一的版本库格式进行托管，故名GitHub。\n" +
            "GitHub于2008年4月10日正式上线，除了Git代码仓库托管及基本的Web管理界面以外，还提供了订阅、讨论组、文本渲染、在线文件编辑器、协作图谱（报表）、代码片段分享（Gist）等功能。目前，其注册用户已经超过350万，托管版本数量也是非常之多，其中不乏知名开源项目Ruby on Rails、jQuery、python等。\n" +
            "2018年6月4日，微软宣布，通过75亿美元的股票交易收购代码托管平台GitHub。\n" +
            "2008年4月10日，GitHub正式上线。\n" +
            "2014年1月23日，联合创始人汤姆·普雷斯顿-维尔纳（Tom Preston-Werner）从另一位联合创始人克里斯·万斯特拉斯（Chris Wanstrath）手中接过总裁职位，后者也将接过普雷斯顿-维尔纳留下的CEO位置。\n" +
            "2018年6月4日晚，微软宣布，通过75亿美元的股票交易收购GitHub。 [2]  10月26日，微软以75亿美元收购GitHub交易已完成。10月29日，微软开发者服务副总裁奈特·弗里德曼（Nat Friedman）将成为GitHub的新一任CEO。 [3] \n" +
            "2020年3月17日，Github宣布收购npm，GitHub现在已经保证npm将永远免费。";//与xml一致

}
