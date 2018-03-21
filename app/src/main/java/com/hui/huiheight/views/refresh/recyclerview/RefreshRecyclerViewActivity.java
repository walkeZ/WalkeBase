package com.hui.huiheight.views.refresh.recyclerview;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import com.hui.huiheight.R;
import com.hui.huiheight.config.Datas;
import com.hui.huiheight.fragment.adapter.RecyclerViewAdapter;
import walke.base.activity.TitleActivity;
import walke.base.tool.SetUtil;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/18.
 */

public class RefreshRecyclerViewActivity extends TitleActivity {
    private CustomRecyclerView customRecyclerView;
    private ScrollRecyclerView mScrollLinearLayout;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_refresh_recyclerview;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("RefreshRecyclerViewActivity");
        customRecyclerView = ((CustomRecyclerView) findViewById(R.id.arr_customRecyclerView));
        mScrollLinearLayout = ((ScrollRecyclerView) findViewById(R.id.arr_scrollLinearLayout));
        customRecyclerView.setLayoutManager(new LinearLayoutManager(this));
       // ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Datas.FIRST_SKILLS);
        mAdapter = new RecyclerViewAdapter(this, SetUtil.arrToList(Datas.VIEW_SKILLS));
        customRecyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void initData() {
        mScrollLinearLayout.setRefreshListener(new ScrollRecyclerView.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mScrollLinearLayout.refreshComplete();
                    }
                }, 2000);
            }
        });
    }

}
