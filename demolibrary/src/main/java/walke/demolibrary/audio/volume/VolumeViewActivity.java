package walke.demolibrary.audio.volume;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.Random;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * @author walker
 * @date 2023/3/28
 * @desc 音量波纹视图 https://github.com/duchao/VolumeView
 */
public class VolumeViewActivity extends TitleActivity implements OnClickListener {
    private Button mMoveViewStartButton;

    private Button mMoveViewStopButton;

    private VolumeViewMoveWave mVolumeViewMoveWave;

    private Button mDoubleMoveViewStartButton;

    private Button mDoubleMoveViewStopButton;

    private VolumeViewDoubleMoveWave mVolumeViewDoubleMoveWave;

    private Button mDoubleMoveViewStartOptButton;

    private Button mDoubleMoveViewStopOptButton;

    private VolumeViewDoubleMoveWaveOpt mVolumeViewDoubleMoveWaveOpt;

    private VolumeView mVolumeView;

    private Button mVolumeViewRandomStartButton;

    private Button mVolumeViewRandomStopButton;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_volume_view;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        init();
    }

    @Override
    protected void initData() {

    }

    private void init() {
        mMoveViewStartButton = (Button) findViewById(R.id.move_wave_start);
        mMoveViewStartButton.setOnClickListener(this);
        mMoveViewStopButton = (Button) findViewById(R.id.move_wave_stop);
        mMoveViewStopButton.setOnClickListener(this);
        mVolumeViewMoveWave = (VolumeViewMoveWave) findViewById(R.id.move_wave);

        mDoubleMoveViewStartButton = (Button) findViewById(R.id.move_wave_double_start);
        mDoubleMoveViewStartButton.setOnClickListener(this);
        mDoubleMoveViewStopButton = (Button) findViewById(R.id.move_wave_double_stop);
        mDoubleMoveViewStopButton.setOnClickListener(this);
        mVolumeViewDoubleMoveWave = (VolumeViewDoubleMoveWave) findViewById(R.id.move_wave_double);

        mDoubleMoveViewStartOptButton = (Button) findViewById(R.id.move_wave_opt_double_start);
        mDoubleMoveViewStartOptButton.setOnClickListener(this);
        mDoubleMoveViewStopOptButton = (Button) findViewById(R.id.move_wave_opt_double_stop);
        mDoubleMoveViewStopOptButton.setOnClickListener(this);
        mVolumeViewDoubleMoveWaveOpt = (VolumeViewDoubleMoveWaveOpt) findViewById(R.id.move_wave_opt_double);

        mVolumeView = (VolumeView) findViewById(R.id.volume_view);
        mVolumeViewRandomStartButton = (Button) findViewById(R.id.volume_view_random_start);
        mVolumeViewRandomStartButton.setOnClickListener(this);
        mVolumeViewRandomStopButton = (Button) findViewById(R.id.volume_view_random_stop);
        mVolumeViewRandomStopButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.move_wave_start) {
            mVolumeViewMoveWave.start();
        } else if (id == R.id.move_wave_stop) {
            mVolumeViewMoveWave.stop();
        } else if (id == R.id.move_wave_double_start) {
            mVolumeViewDoubleMoveWave.start();
        } else if (id == R.id.move_wave_double_stop) {
            mVolumeViewDoubleMoveWave.stop();
        } else if (id == R.id.move_wave_opt_double_start) {
            mVolumeViewDoubleMoveWaveOpt.start();
        } else if (id == R.id.move_wave_opt_double_stop) {
            mVolumeViewDoubleMoveWaveOpt.stop();
        } else if (id == R.id.volume_view_random_start) {
            startRandomVolumeView();
        } else if (id == R.id.volume_view_random_stop) {
            stopRandmVolumeView();
        }
    }

    private void startRandomVolumeView() {
        mVolumeView.start();
        
        //随机设置音量大小.
        if (mRandomThreand != null) {
            mRandomThreand.stopRunning();
            mRandomThreand = null;
        }
        mRandomThreand = new RandomThreand();
        mRandomThreand.start();
    }

    private void stopRandmVolumeView() {
        if (mRandomThreand != null) {
            mRandomThreand.stopRunning();
            mRandomThreand = null;
        }
        mVolumeView.stop();
    }

    private RandomThreand mRandomThreand;

    private class RandomThreand extends Thread {
        private static final int MOVE_STOP = 1;

        private static final int MOVE_START = 0;

        private int state;

        @Override
        public void run() {
            state = MOVE_START;

            while (true) {
                if (state == MOVE_STOP) {
                    break;
                }
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mVolumeView.setVolume(getRandom(0, 100));
            }
        }

        public void stopRunning() {
            state = MOVE_STOP;
        }
    }

    private int getRandom(int min, int max) {
        Random random = new Random();
        int r = random.nextInt(max) % (max - min + 1) + min;
        return r;
    }

}
