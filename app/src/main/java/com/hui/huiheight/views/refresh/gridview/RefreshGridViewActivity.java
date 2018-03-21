package com.hui.huiheight.views.refresh.gridview;

import android.os.Handler;

import com.hui.huiheight.R;
import com.hui.huiheight.fragment.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/18.
 * GridView的使用---http://blog.csdn.net/leichelle/article/details/7951290
 */

public class RefreshGridViewActivity extends TitleActivity {
    private CustomGridView customGridView;
    private RefreshGridView mRefreshGridView;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_refresh_gridview;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("RefreshGridViewActivity");
        customGridView = ((CustomGridView) findViewById(R.id.arg_customGridView));
        mRefreshGridView = ((RefreshGridView) findViewById(R.id.arg_refreshGridView));

        mRefreshGridView.setRefreshListener(new RefreshGridView.OnRefreshListener() {
            @Override
            public void onRefreshing() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshGridView.refreshComplete();
                    }
                }, 2000);
            }
        });

    }
    //http://pic2.ooopic.com/10/51/63/13b1OOOPIC17.jpg
    @Override
    protected void initData() {
        List<Picture> list=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new Picture("picture "+i,"http://pic2.ooopic.com/10/51/63/13b1OOOPIC17.jpg"));
        }
        PictureAdapter adapter=new PictureAdapter(this,list);
        customGridView.setAdapter(adapter);

    }

}
