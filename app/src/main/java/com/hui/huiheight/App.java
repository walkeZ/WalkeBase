package com.hui.huiheight;

import walke.demolibrary.movedsp.utils.CustomApplication;

/**
 * Created by walke.Z on 2017/6/16.
 * 这是app启动入口 生命周期与app一致
 * 要在 manifests文件中使用 在application节点添加name属性
 *
 * 主要用于
 *  1.保存基本用户信息
 *  2.部分第三方框架的注册
 *  3.添加启动标示用于开启闪屏页
 *  ...
 
 * git使用测试01
 */

public class App extends CustomApplication {


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
