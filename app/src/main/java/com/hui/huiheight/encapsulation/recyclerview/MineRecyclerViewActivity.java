package com.hui.huiheight.encapsulation.recyclerview;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hui.huiheight.R;

import java.util.ArrayList;
import java.util.List;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/22.
 */

public class MineRecyclerViewActivity extends TitleActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_mine_recyclerview;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("封装MineRecyclerViewActivity");
        mRecyclerView = ((RecyclerView) findViewById(R.id.amr_recyclerView));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void initData() {
        List<MinePicture> list=new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            list.add(new MinePicture("picture "+i,"http://pic2.ooopic.com/10/51/63/13b1OOOPIC17.jpg","使用RecycleView时候的adapter的简单封装"+(i+1)));
        }
        MineRecyclerViewAdapter adapter=new MineRecyclerViewAdapter(list);
        mRecyclerView.setAdapter(adapter);

    }
}
