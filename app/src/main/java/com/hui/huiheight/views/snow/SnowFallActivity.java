package com.hui.huiheight.views.snow;

import android.view.View;

import com.hui.huiheight.R;

import walke.base.activity.AppActivity;
import walke.widget.snowfall.Snow2Utils;
import walke.widget.snowfall.Snow2View;
import walke.widget.snowfall.Snow3View;
import walke.widget.snowfall.SnowUtils;
import walke.widget.snowfall.SnowView;

/**
 * Created by walke.Z on 2018/2/23.
 */

public class SnowFallActivity extends AppActivity {
    private SnowView mSnowView;
    private Snow2View mSnow2View;
    private Snow3View mSnowView3;

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
        mSnow2View = ((Snow2View) findViewById(R.id.snowfall_snowView2));
        mSnowView3 = ((Snow3View) findViewById(R.id.snowfall_snowView3));

        mSnowView.startSnowAnim(SnowUtils.SNOW_LEVEL_MIDDLE);
        mSnowView3.startSnowAnim(SnowUtils.SNOW_LEVEL_MIDDLE);
//        mSnowView2.startSnowAnim(Snow2Utils.SNOW_LEVEL_SMALL);
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
                mSnow2View.changeSnowLevel(Snow2Utils.SNOW_LEVEL_SMALL);
                mSnowView3.changeSnowLevel(Snow2Utils.SNOW_LEVEL_SMALL);
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_SMALL);
                break;
            case R.id.snowfall_middle:
                mSnow2View.changeSnowLevel(Snow2Utils.SNOW_LEVEL_MIDDLE);
                mSnowView3.changeSnowLevel(Snow2Utils.SNOW_LEVEL_MIDDLE);
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_MIDDLE);
                break;
            case R.id.snowfall_big:
                mSnow2View.changeSnowLevel(Snow2Utils.SNOW_LEVEL_HEAVY);
                mSnowView3.changeSnowLevel(Snow2Utils.SNOW_LEVEL_HEAVY);
                mSnowView.changeSnowLevel(SnowUtils.SNOW_LEVEL_HEAVY);
                break;
        }
    }
}
