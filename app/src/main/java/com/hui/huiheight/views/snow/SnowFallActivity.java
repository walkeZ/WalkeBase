package com.hui.huiheight.views.snow;

import android.view.View;

import com.hui.huiheight.R;

import walke.base.activity.AppActivity;
import walke.viewlibrary.snowfall.Snow2View;
import walke.viewlibrary.snowfall.SnowUtils;
import walke.viewlibrary.snowfall.SnowView;

/**
 * Created by walke.Z on 2018/2/23.
 */

public class SnowFallActivity extends AppActivity {
    private SnowView mSnowView;
    private Snow2View mSnow2View;

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
        mSnow2View = ((Snow2View) findViewById(R.id.snowfall_snow2View));
        mSnowView.startSnowAnim(SnowUtils.SNOW_LEVEL_SMALL);
//        mSnow2View.startSnowAnim(SnowUtils.SNOW_LEVEL_MIDDLE);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.snowfall_small:
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_SMALL);
                break;
            case R.id.snowfall_middle:
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_MIDDLE);
                break;
            case R.id.snowfall_big:
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_HEAVY);
                break;
        }
    }
}
