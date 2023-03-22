package walke.demolibrary.layoutmanager;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * @author Walker - 2023/3/10 10:20 AM
 * @email 1126648815@qq.ocm
 * @desc : 探探切片效果1
 * https://blog.csdn.net/m0_65146205/article/details/122005140
 * https://www.jianshu.com/p/879988f9c78c
 */
public class TTCardsActivity extends TitleActivity {
    private int[] mImgs = new int[]{R.mipmap.movie1, R.mipmap.movie2, R.mipmap.movie3, R.mipmap.movie4,
            R.mipmap.movie5, R.mipmap.movie6, R.mipmap.movie7, R.mipmap.movie8};
    private RecyclerView mRecyclerView;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_tantan_cards;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("仿探探切片效果");
        mRecyclerView = findViewById(R.id.ttc_rv);
    }

    @Override
    protected void initData() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setLayoutManager(new SlideCardLayoutManager());
        TTCAdapter adapter = new TTCAdapter(mImgs);
        mRecyclerView.setAdapter(adapter);
        ArrayList<Integer> imgs = new ArrayList<>();
        for (int i = 0; i < mImgs.length; i++) {
            imgs.add(mImgs[i]);
        }
        // SlideCardLayoutManager
        new ItemTouchHelper(new SlideCardCallBack(adapter, imgs)).attachToRecyclerView(mRecyclerView);
    }
}