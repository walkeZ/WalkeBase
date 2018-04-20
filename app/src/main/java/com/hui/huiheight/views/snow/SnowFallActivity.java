package com.hui.huiheight.views.snow;

import android.view.View;

import com.hui.huiheight.R;

import walke.base.activity.AppActivity;
import walke.widget.snowfall.SnowUtils;
import walke.widget.snowfall.SnowUtils2;
import walke.widget.snowfall.SnowView;
import walke.widget.snowfall.SnowView2;

/**
 * Created by walke.Z on 2018/2/23.
 */

public class SnowFallActivity extends AppActivity {
    private SnowView mSnowView;
    private SnowView2 mSnowView2,mSnowView3;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_snow_fall;
    }

    @Override
    protected void initView() {
        findViewById(R.id.snowfall_small).setOnClickListener(this);
        findViewById(R.id.snowfall_middle).setOnClickListener(this);
        findViewById(R.id.snowfall_big).setOnClickListener(this);
        mSnowView = ((SnowView) findViewById(R.id.snowfall_snowView));
        mSnowView2 = ((SnowView2) findViewById(R.id.snowfall_snowView2));
        mSnowView3 = ((SnowView2) findViewById(R.id.snowfall_snowView3));

        mSnowView.startSnowAnim(SnowUtils.SNOW_LEVEL_MIDDLE);
//        mSnowView2.startSnowAnim(SnowUtils2.SNOW_LEVEL_SMALL);
//        mSnowView3.startSnowAnim(SnowUtils3.SNOW_LEVEL_SMALL);

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.snowfall_small:
                mSnowView2.changeSnowLevel(SnowUtils2.SNOW_LEVEL_SMALL);
                mSnowView3.changeSnowLevel(SnowUtils2.SNOW_LEVEL_SMALL);
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_SMALL);
                break;
            case R.id.snowfall_middle:
                mSnowView2.changeSnowLevel(SnowUtils2.SNOW_LEVEL_MIDDLE);
                mSnowView3.changeSnowLevel(SnowUtils2.SNOW_LEVEL_MIDDLE);
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_MIDDLE);
                break;
            case R.id.snowfall_big:
                mSnowView2.changeSnowLevel(SnowUtils2.SNOW_LEVEL_HEAVY);
                mSnowView3.changeSnowLevel(SnowUtils2.SNOW_LEVEL_HEAVY);
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_HEAVY);
                break;
        }
    }
}
