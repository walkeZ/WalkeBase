package com.hui.huiheight.views.drag;

import com.hui.huiheight.R;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * 拖拽移动,选中View按着移动
 * author Walke - 2022/5/8 9:43 下午
 * email 1126648815@qq.ocm
 * dec :
 */
public class DragActivity1 extends TitleActivity {

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_drag1;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("拖拽移动1");
    }

    @Override
    protected void initData() {

    }
}
