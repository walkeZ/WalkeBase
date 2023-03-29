package walke.demolibrary.movedsp.utils;

import android.app.Application;

import walke.demolibrary.movedsp.model.DataModel;

/**
 * 全局类
 *
 * @author sman
 */
public class CustomApplication extends Application {

    private static CustomApplication instance;
    private DataModel dataModel = null;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initConfig();
    }

    public static CustomApplication getInstance() {
        return instance;
    }

    /**
     * 初始化系统参数
     */
    private void initConfig() {
        //设置为debug模式
//        LogUtil.isDebug = true;
//        LogUtil.setContext(this);
    }

    public DataModel getDataModel() {
        return dataModel;
    }

    public void setDataModel(DataModel dataModel) {
        this.dataModel = dataModel;
    }

}
