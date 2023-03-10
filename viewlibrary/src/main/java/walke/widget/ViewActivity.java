package walke.widget;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import walke.activity.MarqueeActivity1;
import walke.activity.MarqueeActivity2;
import walke.activity.seekbar.SeekBarActivity;
import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.widget.sunset.SunAnimationActivity;

/**
 * Created by walke.Z on 2017/9/25.
 */

public class ViewActivity extends TitleActivity {
    private RecyclerView mRecyclerView;
    private List<AppCompatActivity> mActivities;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_view;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        mRecyclerView = ((RecyclerView) findViewById(R.id.view_recyclerView));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        initActivities();
        mRecyclerView.setAdapter(new ViewAdapter(ViewActivity.this,mActivities));
    }

    private void initActivities() {
        mActivities=new ArrayList<>();
        mActivities.add(new SunAnimationActivity());
        mActivities.add(new SeekBarActivity());
        mActivities.add(new MarqueeActivity1());
        mActivities.add(new MarqueeActivity2());
    }

    @Override
    protected void initData() {

    }
}
