package walke.widget.text;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.widget.TextView;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.widget.R;

/**
 * Created by walke.Z on 2018/4/17.
 * Android显示TextView文字的倒影效果   https://blog.csdn.net/slightfeverGK/article/details/51113069
 * Android字体Font相关知识  https://blog.csdn.net/songjinshi/article/details/46633341
 * TextView(文本框)详解 文字跑马灯效果 * https://www.jianshu.com/p/4b832a8526f7
 */
public class TextActivity extends TitleActivity {
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_text;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {

        TimeView tv = findViewById(R.id.text_timeView);
        tv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/DS-DIGII.TTF"));
//        Typeface fromAsset = Typeface.createFromAsset(getAssets(), "标准隶书体简.TTF");
//        tv.setTypeface(fromAsset);

        // 字体渐变
        TextView textGradient = findViewById(R.id.text_tvGradient);//找到控件
        LinearGradient mLinearGradient = new LinearGradient(0, 0,
                textGradient.getPaint().getTextSize() * textGradient.getText().length(), 0,
                Color.parseColor("#FFFF68FF"),
                Color.parseColor("#FFFED732"),
                Shader.TileMode.CLAMP);//创建线性渐变
        textGradient.getPaint().setShader(mLinearGradient);//设置渐变色
        textGradient.invalidate();

        TextView textView_marquee = findViewById(R.id.text_tvMarquee);
//        textView_marquee.setFocusable(true); // 或在xml中设置
//        textView_marquee.setFocusableInTouchMode(true);
        textView_marquee.setSelected(true); // 关键

        TextView tvMarquee2 = findViewById(R.id.text_tvMarquee2);
        tvMarquee2.setSelected(true); // 关键

        LinearGradient mLinearGradient2 = new LinearGradient(0, 0,
                tvMarquee2.getPaint().getTextSize() * tvMarquee2.getText().length(), 0,
                new int[]{Color.parseColor("#FFFED732"),  Color.BLUE, Color.GREEN,
                        Color.parseColor("#FFFED732"), Color.GREEN, Color.BLUE},
                new float[]{0, 0.1f, 0.4f, 0.6f, 0.8f, 1},
                Shader.TileMode.CLAMP);//创建线性渐变
        tvMarquee2.getPaint().setShader(mLinearGradient2);//设置渐变色
        tvMarquee2.invalidate();
    }

    @Override
    protected void initData() {

    }
}
