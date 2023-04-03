package walke.demolibrary.audio.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import java.io.IOException;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;
import walke.demolibrary.audio.widget.SimpleWaveformRenderer;
import walke.demolibrary.audio.widget.WaveformView;
import walke.demolibrary.movedsp.views.VisualizerFFTView;
import walke.demolibrary.movedsp.views.VisualizerWaveView;
import walke.demolibrary.pinpu.VisualizerView;
import walke.demolibrary.pinpu.VisualizerView2;


/**
 * Demo to show how to use VisualizerView
 * <p>
 * audio：音频， form https://github.com/walkeZ/AudioWaveShow
 * 波形页面
 */
public class AudioActivity02 extends TitleActivity implements Visualizer.OnDataCaptureListener {
    private static final int UPDATE_PROGRESS = 10;
    private MediaPlayer mPlayer;
    private WaveformView mWaveformView;
    /**
     * 实物投影机;插画师;查看器;画师。即音频频谱
     */
    private Visualizer mVisualizer;
    private boolean mPlayPause;
    private WaveformView mWaveformView2;
    private VisualizerView mVisualizerView;
    private VisualizerView2 mVisualizerView2;
    private LinearLayout mLayout;
    private VisualizerWaveView mVisualizerWaveView;
    private VisualizerFFTView mVisualizerFFTView;
    private int duration;
    private ProgressBar progress;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (mPlayer == null) return;
            progress.setProgress(mPlayer.getCurrentPosition());
            if (mHandler.hasMessages(UPDATE_PROGRESS)) return;
            mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 200);
        }
    };

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

        mVisualizerWaveView = findViewById(R.id.audio02_VisualizerWaveView);
        mVisualizerFFTView = findViewById(R.id.audio02_VisualizerFFTView);
        progress = findViewById(R.id.audio02_progressBar);

        // We need to link the visualizer view to the media player so that
        // it displays something
        mWaveformView = findViewById(R.id.audio02_waveformView);
        mWaveformView2 = findViewById(R.id.audio02_waveformView2);
        mLayout = findViewById(R.id.audio02_root);
//        mVisualizerView = (VisualizerView) findViewById(R.id.audio02_visualizerView);
        mWaveformView.setRenderer(new SimpleWaveformRenderer(Color.GREEN, new Paint(), new Path()));
        mWaveformView2.setRenderer(new SimpleWaveformRenderer(Color.GREEN, new Paint(), new Path()));
        // 实例化mVisualizer
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
        // 设置内容长度为1024
//        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        int i = Visualizer.getCaptureSizeRange()[1]; // Visualizer.getCaptureSizeRange() [128, 1024]
        mVisualizer.setCaptureSize(i);
        // Visualizer.getMaxCaptureRate()-获取最大采样率,采样速率为512MHz，(3、4 -> true, true)设置同时获取时域、频域波形数据
        // 第一个参数是回调, 使用waveformdata或fftdata; 第二个是更新率; 第三个是判断使用waveformdata; 第四个是判断使用fftdata, 第三\四个均与回调的返回值有关
//        int rate = Visualizer.getMaxCaptureRate() / 2; // Visualizer.getMaxCaptureRate() 20000
        int rate = Visualizer.getMaxCaptureRate() / 2;
        mVisualizer.setDataCaptureListener(this, rate, true, true);

        // Enabled Visualizer and disable when we're done with the stream
        mVisualizer.setEnabled(true);
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            Log.i("ArHui", "init: --> 播放完");
            mVisualizer.setEnabled(false);
        });

        mVisualizerView = findViewById(R.id.audio02_VisualizerView);
        mVisualizerView2 = findViewById(R.id.audio02_VisualizerView2);
        //设置允许波形表示，并且捕获它
        mVisualizerView.setVisualizer(mVisualizer);
//        mVisualizerView2.setVisualizer(mVisualizer);
        mVisualizerView.setDataCaptureListener(this);
//        mVisualizerView2.setDataCaptureListener(this);

        duration = mPlayer.getDuration();
        progress.setMax(duration);
        startSyncProgress();
    }

    private void startSyncProgress() {
        if (mHandler.hasMessages(UPDATE_PROGRESS)) return;
        mHandler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 200);
    }

    private void stopSyncProgress() {
        mHandler.removeMessages(UPDATE_PROGRESS);
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
        startSyncProgress();
    }

    public void pause(View view) {
        mPlayPause = true;
        mPlayer.pause();
        stopSyncProgress();
    }

    public void stop(View view) {
        mPlayPause = false;
        mPlayer.stop();
        mVisualizer.setEnabled(false);
        stopSyncProgress();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopSyncProgress();
    }

    //捕获波形数,时域波形数据
    @Override
    public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int i) {
        mWaveformView.setWaveform(waveform);
        mVisualizerWaveView.updateVisualizer(waveform);

        Visualizer.MeasurementPeakRms measurementPeakRms = new Visualizer.MeasurementPeakRms();
        int x = mVisualizer.getMeasurementPeakRms(measurementPeakRms);
        int rmsLevel = calculateRMSLevel(waveform);
        Log.i("ArHui", "onWaveFormDataCapture: --> Rms = " + x + ",  " + rmsLevel);
    }

    //捕获傅里叶数据,频域波形数据
    @Override
    public void onFftDataCapture(Visualizer visualizer, byte[] fft, int i) {
//        mWaveformView2.setWaveform(fft);
        mVisualizerFFTView.updateVisualizer(fft);
    }

    public int calculateRMSLevel(byte[] audioData) {
        //System.out.println("::::: audioData :::::"+audioData);
        double amplitude = 0;
        for (int i = 0; i < audioData.length; i++) {
            amplitude += Math.abs((double) (audioData[i] / 32768.0));
        }
        Log.w("ArHui", "onWaveFormDataCapture: --> amplitude = " + amplitude);
        amplitude = amplitude / audioData.length;
        //Add this data to buffer for display
        return (int)amplitude;
    }
}