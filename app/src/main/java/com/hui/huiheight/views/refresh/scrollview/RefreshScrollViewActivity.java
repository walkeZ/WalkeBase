package com.hui.huiheight.views.refresh.scrollview;

import android.os.Handler;
import android.view.View;

import com.hui.huiheight.R;
import com.hui.huiheight.fragment.adapter.RecyclerViewAdapter;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/18.
 */

public class RefreshScrollViewActivity extends TitleActivity {

    private CustomScrollView customRecyclerView;
    private RefreshScrollView mRefreshScrollView;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_refresh_scrollview;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("RefreshRecyclerViewActivity");
        customRecyclerView = ((CustomScrollView) findViewById(R.id.ars_CustomScrollView));
        mRefreshScrollView = ((RefreshScrollView) findViewById(R.id.ars_RefreshScrollView));


    }

    @Override
    protected void initData() {
        mRefreshScrollView.setRefreshListener(new RefreshScrollView.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshScrollView.refreshComplete();
                    }
                }, 2000);
            }
        });
    }

    public void click(View v){
        toast("click");
    }
    public void toasting(View v){
        toast("RefreshScrollViewActivity");
    }

}
