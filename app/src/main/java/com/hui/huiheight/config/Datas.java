package com.hui.huiheight.config;

import walke.demolibrary.audio.activity.AudioActivity01;
import walke.demolibrary.audio.activity.AudioActivity02;
import walke.demolibrary.audio.recorder.RecorderActivity;
import walke.demolibrary.audio.volume.VolumeViewActivity;
import walke.demolibrary.audio.waveform.WaveformActivity;
import walke.demolibrary.layoutmanager.TTCardsActivity;
import walke.demolibrary.movedsp.activitys.FlashScreenActivity;
import walke.demolibrary.pinpu.PinPuActivity;
import walke.demolibrary.pinpu.PinPuActivity2;

/**
 * Created by Walke.Z on 2017/4/21.
 *
 * 据说在JVM里面有一个常量池，如果是这个值存在于这个常量池里，
 * 那么jvm会直接拿常量池里的对象进行替换。所以你值小的时候得到的结果是相等的
 *
 * JVM虚拟机的的优化，范围 -128 到 127之间有缓存
 *
 */
public class Datas {

    public static final String[] TAB_TITTLES = new String[]{
            "first",
            "views",
            "消息",
            "我的",
    };

    public static final String[] FIRST_SKILLS = new String[]{
            "本地获取经纬度",
            "选择省份市县三级联动",
            "选择省份市县三级联动pw",
            "CutPhonePictureActivity",
            "CutPhonePictureActivity2",
            "PhonePictureActivity没有剪切",
            "PhoneInfoActivity",
            "ScreenShotActivity",
            "retrofit2.0简单使用",
            "MainsActivity",
            "调用其他model(依赖)的activity(ButterKnife)",
            "sun set/rise",
            "Java多态接口动态加载实例",
            "ShopCarActivity(商品飞入购物车效果)",
            "AddressPickerActivity",
            "OSSActivity",
            "",
            "123",
            "123",
    };
    public static final String[] DEMO_SKILLS = new String[]{
            "demo1Java多态接口动态加载实例",
            "demo3里氏替换原则",
            "Android填空题实现",
            "Android编程之图片颜色处理方法",
            "探探卡片" + TTCardsActivity.class.getSimpleName(),
            "频谱" + PinPuActivity.class.getSimpleName() + " 优先级队列",
            "频谱" + PinPuActivity2.class.getSimpleName(),
            "音频(波、谱)" + AudioActivity01.class.getSimpleName(),
            "音频(波、谱)" + AudioActivity02.class.getSimpleName(),
            "音频(波)录制" + WaveformActivity.class.getSimpleName(),
            "音频-录制" + RecorderActivity.class.getSimpleName(),
            "音频-音量波纹视图" + VolumeViewActivity.class.getSimpleName(),
            "音频-fft" + FlashScreenActivity.class.getSimpleName(),
            "Glide获取保存网络图片和本地图片",
            "123",
            "456",

    };

    public static final String[] VIEW_SKILLS = new String[]{
            "事件分发",
            "RefreshWebView",
            "RefreshWebViewButterKnife",
            "RefreshLinearLayout1",
            "RefreshLinearLayout2",
            "RefreshLinearLayout3",
            "RefreshLinearLayout4",
            "滑动的相对布局-ScrollRelativeLayout1",
            "RefreshListView",
            "RefreshRecyclerView",
            "RefreshScrollView",
            "RefreshGridView",
            "封装MineRecyclerViewAdapter",
            "SunAnimationActivity",
            "SeekBarActivity",
            "MarqueeActivity1",
            "MarqueeActivity2",
            "FlowLayoutActivity",
            "PercentageJavaActivity",
            "LuckyPanActivity",
            "SnowFallActivity",
            "AnimActivity",
            "DragActivity1",
            "XiuActivity",
            "ImgActivity",
            "倒影效果的ImageView",
            "图片浏览的倒影效果",
            "TextView文字的倒影效果",
            "123",
            "456",
            "nothing",
    };
    public static final String[] NEWS_SKILLS = new String[]{
            "捕获全局异常",
            "抽屉1.SlidingPaneLayout",
            "抽屉2.DrawerLayout",
            "抽屉3.DrawerLayout+NavigationView",
            "抽屉4.SlidingMenuActivity",
            "抽屉4.SlidingMenuActivity2",
            "图文混排HTML、spannable",
            "三种动画",
            "动画活用",
            "协调者布局",
            "开关按钮",
            "WebView简易使用",
            "WebView与JS交互(Android调JS)",
            "WebView与JS交互(互调)",
            "CardView使用",
    };
    public static final String[] MINE_SKILLS = new String[]{
            "捕获全局异常",
            "抽屉1.SlidingPaneLayout",
            "抽屉2.DrawerLayout",
            "抽屉3.DrawerLayout+NavigationView",
            "抽屉4.SlidingMenuActivity",
            "抽屉4.SlidingMenuActivity2",
            "图文混排HTML、spannable",
            "三种动画",
            "动画活用",
            "协调者布局",
            "开关按钮",
            "WebView简易使用",
            "WebView与JS交互(Android调JS)",
            "WebView与JS交互(互调)",
            "CardView使用",
    };

    public static final String[] ADV_URL = new String[]{
            "http://img.taopic.com/uploads/allimg/130506/240498-1305060S12221.jpg",
            "http://img.sucai.redocn.com/attachments/images/201201/20120107/Redocn_2012010611551322.jpg",
            "http://pic.58pic.com/58pic/12/92/02/44G58PIC4.jpg",
            "http://i2.dpfile.com/2010-07-28/4849654_b.jpg(700x700)/thumb.jpg",
            "http://img3.3lian.com/2014/c2/70/picture4/41.jpg",
    };
}
