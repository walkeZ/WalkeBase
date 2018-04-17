package walke.widget.text;

import android.graphics.Typeface;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.widget.R;

/**
 * Created by walke.Z on 2018/4/17.
 * Android显示TextView文字的倒影效果   https://blog.csdn.net/slightfeverGK/article/details/51113069
 * Android字体Font相关知识  https://blog.csdn.net/songjinshi/article/details/46633341
 */

public class TextActivity extends TitleActivity {
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {

        TimeView tv = (TimeView) findViewById(R.id.timeView);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS-DIGII.TTF"));
//        Typeface fromAsset = Typeface.createFromAsset(getAssets(), "标准隶书体简.TTF");
//        tv.setTypeface(fromAsset);
    }

    @Override
    protected void initData() {

    }
}
