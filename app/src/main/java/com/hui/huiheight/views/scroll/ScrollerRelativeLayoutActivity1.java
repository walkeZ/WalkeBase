package com.hui.huiheight.views.scroll;

import android.view.View;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/18.
 */

public class ScrollerRelativeLayoutActivity1 extends TitleActivity {
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_scroll_relativelayout1;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {

    }

    @Override
    protected void initData() {

    }

    public void click(View v){
        toast("click");
    }
    public void toasting(View v){
        toast("ScrollRelativeLayout1");
    }
}
