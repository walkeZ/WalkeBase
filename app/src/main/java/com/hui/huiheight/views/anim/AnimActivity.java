package com.hui.huiheight.views.anim;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2018/2/28.
 */

public class AnimActivity extends TitleActivity {
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_anim;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("中奖散落、发散动画");
    }

    @Override
    protected void initData() {

    }
}
