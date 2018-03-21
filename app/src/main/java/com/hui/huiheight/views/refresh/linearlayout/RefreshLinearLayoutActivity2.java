package com.hui.huiheight.views.refresh.linearlayout;

import android.view.View;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/18.
 */

public class RefreshLinearLayoutActivity2 extends TitleActivity {
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_refresh_linearlayout2;
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
        toast("RefreshLinearLayoutActivity2");
    }
}
