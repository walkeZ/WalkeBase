package walke.activity.xiu;


import android.media.MediaPlayer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import java.io.IOException;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.widget.R;
import walke.widget.xiu.WaveView;

/**
 * Created by walke.Z on 2018/3/23.
 */

public class XiuActivity extends TitleActivity {

    private ImageView head;
    private WaveView wave;
    private ScaleAnimation scaleAnimation;
    private MediaPlayer mPlayer;
    @Override
    protected int rootLayoutId() {
        return R.layout.activity_xiu;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {

        scaleAnimation = new ScaleAnimation(1.2f, 1f, 1.2f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);
        wave = (WaveView) findViewById(R.id.xiu_WaveView);
        head = (ImageView) findViewById(R.id.xiu_head);
        mPlayer = MediaPlayer.create(this, R.raw.water_wave);
        head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wave.addWave();
                head.startAnimation(scaleAnimation);
                if(mPlayer.isPlaying()){
                    mPlayer.stop();
                    try {
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                mPlayer.start();
            }
        });
        wave.start();

    }

    @Override
    protected void initData() {

    }
}
