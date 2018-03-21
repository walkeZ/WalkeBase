package walke.viewlibrary.seekbar;


import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.viewlibrary.R;

/**
 * Created by walke.Z on 2017/9/11.
 *
 * http://blog.sina.com.cn/s/blog_6efce07e01013lfl.html
 * http://www.cnblogs.com/xiaomo8086/p/4193662.html
 *  http://blog.csdn.net/jacklog/article/details/50067227
 * android 5.0及以上，seekbar thumb 透明效果出现父布局背景颜色的解决方法:
 *      在seekbar中添加  android:splitTrack="false" 即可
 *
 */

public class SeekBarActivity extends TitleActivity {


    @Override
    protected int rootLayoutId() {
        return R.layout.activity_seekbar;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("SeekBar");
    }

    @Override
    protected void initData() {

    }
}
