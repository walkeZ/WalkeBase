package walke.viewlibrary;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.viewlibrary.marquee.MarqueeActivity1;
import walke.viewlibrary.marquee.MarqueeActivity2;
import walke.viewlibrary.seekbar.SeekBarActivity;
import walke.viewlibrary.sunset.SunAnimationActivity;

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
