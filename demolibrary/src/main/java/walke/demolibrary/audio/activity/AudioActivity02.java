package walke.demolibrary.audio.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.IOException;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;
import walke.demolibrary.audio.widget.SimpleWaveformRenderer;
import walke.demolibrary.audio.widget.WaveformView;
import walke.demolibrary.pinpu.VisualizerView;


/**
 * Demo to show how to use VisualizerView
 * <p>
 * audio：音频， form https://github.com/walkeZ/AudioWaveShow
 * 波形页面
 */
public class AudioActivity02 extends TitleActivity implements Visualizer.OnDataCaptureListener {
    private MediaPlayer mPlayer;
    private WaveformView mWaveformView;
    /**
     * 实物投影机;插画师;查看器;画师。即音频频谱
     */
    private Visualizer mVisualizer;
    private boolean mPlayPause;
    private WaveformView mWaveformView2;
    private VisualizerView mVisualizerView;
    private LinearLayout mLayout;

//    /**
//     * Called when the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView();
//    }

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_audio02;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("波形 AudioActivity02");
        init();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        cleanUp();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        cleanUp();
        super.onDestroy();
    }

    private void init() {
//        mPlayer = MediaPlayer.create(this, R.raw.ski);
        mPlayer = MediaPlayer.create(this, R.raw.aaaass);
        mPlayer.setLooping(true);
        mPlayer.start();

        // We need to link the visualizer view to the media player so that
        // it displays something
        mWaveformView = (WaveformView) findViewById(R.id.audio02_waveformView);
        mWaveformView2 = (WaveformView) findViewById(R.id.audio02_waveformView2);
        mLayout = (LinearLayout) findViewById(R.id.audio02_root);
//        mVisualizerView = (VisualizerView) findViewById(R.id.audio02_visualizerView);
        mWaveformView.setRenderer(new SimpleWaveformRenderer(Color.GREEN, new Paint(), new Path()));
        mWaveformView2.setRenderer(new SimpleWaveformRenderer(Color.GREEN, new Paint(), new Path()));
        // 实例化mVisualizer
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
        // 设置内容长度为1024
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        // Visualizer.getMaxCaptureRate()-获取最大采样率,采样速率为512MHz，(3、4 -> true, true)设置同时获取时域、频域波形数据
        mVisualizer.setDataCaptureListener(this, Visualizer.getMaxCaptureRate() / 2, true, true);

        // Enabled Visualizer and disable when we're done with the stream
        mVisualizer.setEnabled(true);
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            Log.i("ArHui", "init: --> 播放完");
            mVisualizer.setEnabled(false);
        });

        mVisualizerView = new VisualizerView(this);
        mVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,//宽度
                (int) (150f * getResources().getDisplayMetrics().density)//高度
        ));
        //将频谱View添加到布局
        mLayout.addView(mVisualizerView,0);
        //设置允许波形表示，并且捕获它
        mVisualizerView.setVisualizer(mVisualizer);
        mVisualizerView.setDataCaptureListener(this);
    }

    private void cleanUp() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    // Actions for buttons defined in xml
    public void start(View view) throws IllegalStateException, IOException {
        if (mPlayer.isPlaying()) {
            return;
        }
        if (mPlayPause) {
            mPlayer.start();
        } else {
            mPlayer.prepare();
            mPlayer.start();
            mVisualizer.setEnabled(true);
        }
        mPlayPause = false;
    }

    public void pause(View view) {
        mPlayPause = true;
        mPlayer.pause();
    }

    public void stop(View view) {
        mPlayPause = false;
        mPlayer.stop();
        mVisualizer.setEnabled(false);
    }

    //捕获波形数,时域波形数据
    @Override
    public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int i) {
        mWaveformView.setWaveform(bytes);
    }

    //捕获傅里叶数据,频域波形数据
    @Override
    public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int i) {
        mWaveformView2.setWaveform(bytes);
    }
}