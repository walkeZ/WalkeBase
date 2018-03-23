package walke.widget.sunset;


import android.view.View;
import android.widget.Button;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.widget.R;

/**
 * Created by walke.Z on 2017/9/11.
 */

public class SunAnimationActivity extends TitleActivity {

    Button button;
    SunAnimationView sumView;

    private String mCurrentTime;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_sun_anim;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("日出日落太阳动效");
    }

    @Override
    protected void initData() {
        mCurrentTime = "18:40";//15:40
        sumView = (SunAnimationView) findViewById(R.id.sun_view);
        button = (Button) findViewById(R.id.btn_set_time);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                sumView.setTimes("05:10", "18:40", mCurrentTime);
                button.setText("当前时间：" + mCurrentTime);
            }
        });
    }
}
