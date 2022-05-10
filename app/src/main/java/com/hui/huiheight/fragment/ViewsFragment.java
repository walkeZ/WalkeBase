package com.hui.huiheight.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hui.huiheight.R;
import com.hui.huiheight.config.Datas;
import com.hui.huiheight.encapsulation.recyclerview.MineRecyclerViewActivity;
import com.hui.huiheight.fragment.adapter.RecyclerViewAdapter;
import com.hui.huiheight.views.anim.AnimActivity;
import com.hui.huiheight.views.drag.DragActivity1;
import com.hui.huiheight.views.refresh.gridview.RefreshGridViewActivity;
import com.hui.huiheight.views.refresh.linearlayout.RefreshLinearLayoutActivity1;
import com.hui.huiheight.views.refresh.linearlayout.RefreshLinearLayoutActivity2;
import com.hui.huiheight.views.refresh.linearlayout.RefreshLinearLayoutActivity3;
import com.hui.huiheight.views.refresh.linearlayout.RefreshLinearLayoutActivity4;
import com.hui.huiheight.views.refresh.listview.RefreshListViewActivity;
import com.hui.huiheight.views.refresh.recyclerview.RefreshRecyclerViewActivity;
import com.hui.huiheight.views.refresh.scrollview.RefreshScrollViewActivity;
import com.hui.huiheight.views.refresh.webview.RefreshWebViewActivity;
import com.hui.huiheight.views.refresh.webview.WebViewButterKnifeActivity;
import com.hui.huiheight.views.scroll.ScrollerRelativeLayoutActivity1;
import com.hui.huiheight.views.snow.SnowFallActivity;
import com.hui.huiheight.views.touch.TouchEventActivity;

import java.util.ArrayList;
import java.util.List;

import walke.activity.MarqueeActivity1;
import walke.activity.MarqueeActivity2;
import walke.activity.seekbar.SeekBarActivity;
import walke.activity.xiu.XiuActivity;
import walke.base.AppFragment;
import walke.base.tool.SetUtil;
import walke.widget.flowlayout.FlowLayoutActivity;
import walke.widget.gallery.GalleryActivity;
import walke.widget.img.ImgActivity;
import walke.widget.img.ImgActivity2;
import walke.widget.luckypan.LuckyPanActivity;
import walke.widget.percentage.PercentageJavaActivity;
import walke.widget.sunset.SunAnimationActivity;
import walke.widget.text.TextActivity;

/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2016/11/24.
 */

public class ViewsFragment extends AppFragment {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;

    @Override
    protected int rootLayoutId() {
        return R.layout.fragment_views;
    }

    @Override
    protected void initView(View rootView, Bundle savedInstanceState) {
        mRecyclerView = ((RecyclerView) rootView.findViewById(R.id.fv_recyclerView));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        mAdapter = new RecyclerViewAdapter(getContext(), SetUtil.arrToList(Datas.VIEW_SKILLS));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        final List<AppCompatActivity> activities=new ArrayList<>();
        activities.add(new TouchEventActivity());
        activities.add(new RefreshWebViewActivity());
        activities.add(new WebViewButterKnifeActivity());
        activities.add(new RefreshLinearLayoutActivity1());
        activities.add(new RefreshLinearLayoutActivity2());
        activities.add(new RefreshLinearLayoutActivity3());
        activities.add(new RefreshLinearLayoutActivity4());
        activities.add(new ScrollerRelativeLayoutActivity1());
        activities.add(new RefreshListViewActivity());
        activities.add(new RefreshRecyclerViewActivity());
        activities.add(new RefreshScrollViewActivity());
        activities.add(new RefreshGridViewActivity());
        activities.add(new MineRecyclerViewActivity());
        activities.add(new SunAnimationActivity());
        activities.add(new SeekBarActivity());
        activities.add(new MarqueeActivity1());
        activities.add(new MarqueeActivity2());
        activities.add(new FlowLayoutActivity());
        activities.add(new PercentageJavaActivity());
        activities.add(new LuckyPanActivity());
        activities.add(new SnowFallActivity());
        activities.add(new AnimActivity());
        activities.add(new DragActivity1());
        activities.add(new XiuActivity());
        activities.add(new ImgActivity());
        activities.add(new ImgActivity2());
        activities.add(new GalleryActivity());
        activities.add(new TextActivity());

        mAdapter.setItemClickListener(new RecyclerViewAdapter.onItemClickListener() {
            @Override
            public void onClick(int position) {
                startActivity(new Intent(getContext(),activities.get(position).getClass()));
            }
        });


    }
}
