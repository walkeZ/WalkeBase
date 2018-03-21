package walke.viewlibrary.luckypan;

import android.view.View;
import android.widget.Toast;

import walke.base.activity.AppActivity;
import walke.viewlibrary.R;
import walke.viewlibrary.count_down.CountdownView;

/**
 * Created by Walke.Z
 * on 2017/9/15. 43.
 * email：1126648815@qq.com
 */
public class LuckyPanActivity extends AppActivity {
    private TurntableView mTurntableView;
    private CountdownView mCountdownView;


    @Override
    protected int rootLayoutId() {
        return R.layout.activity_lucky_pan;
    }

    @Override
    protected void initView() {
        mTurntableView = ((TurntableView) findViewById(R.id.alp_TurntableView));
        findViewById(R.id.alp_btChange).setOnClickListener(this);


        mCountdownView = ((CountdownView) findViewById(R.id.alp_CountdownView));
        mCountdownView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCountdownView.startTimeDown(30);
            }
        });
    }

    @Override
    protected void initData() {
        mTurntableView.setRotatingListener(new TurntableView.RotatingListener() {
            @Override
            public void rotateStop(int index) {
                Toast.makeText(LuckyPanActivity.this, "您抽中：" +index, Toast.LENGTH_LONG).show();
            }
        });
        mTurntableView.setOdds(new int[]{500,600,600,700,800,1000});//0.5,0.1,0,0.1,0.1,0.2
    }


    @Override
    public void onClick(View v) {
        mTurntableView.setOdds(new int[]{0,400,800,850,900,1000});//0,0.4,0.4,0.05,0.05,0.1
        mTurntableView.getIvTurntable().setImageResource(R.mipmap.turntable_bg2);
    }

    public void reset(View v) {
        mTurntableView.setOdds(new int[]{600,600,600,600,600,1000});//0,0.4,0.4,0.05,0.05,0.1
        mTurntableView.getIvTurntable().setImageResource(R.mipmap.turntable_bg1);
    }


}
