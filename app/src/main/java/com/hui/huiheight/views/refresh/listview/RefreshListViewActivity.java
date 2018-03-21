package com.hui.huiheight.views.refresh.listview;

import android.widget.ArrayAdapter;

import com.hui.huiheight.R;
import com.hui.huiheight.config.Datas;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/18.
 */

public class RefreshListViewActivity extends TitleActivity {
    private CustomListView mCustomListView;
    private ScrollLinearLayout mScrollLinearLayout;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_refresh_listview;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("RefreshListViewActivity");
        mCustomListView = ((CustomListView) findViewById(R.id.arl_customListView));
        mScrollLinearLayout = ((ScrollLinearLayout) findViewById(R.id.arl_scrollLinearLayout));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Datas.FIRST_SKILLS);
        mCustomListView.setAdapter(adapter);

    }

    @Override
    protected void initData() {

    }

}
