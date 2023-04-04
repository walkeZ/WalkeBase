package walke.demolibrary.audio.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.Arrays;

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
    /**
     * 采样值
     */
    private int mCaptureSize;
    /**
     * 最大采样频率
     */
    private int mMaxCaptureRate;

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
        mVisualizerView = findViewById(R.id.audio02_VisualizerView);
        mVisualizerView2 = findViewById(R.id.audio02_VisualizerView2);


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

        // 实例化mVisualizer
        int audioSessionId = mPlayer.getAudioSessionId();
        mVisualizer = new Visualizer(audioSessionId);
        // 设置内容长度为1024
//        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mCaptureSize = Visualizer.getCaptureSizeRange()[1]; // Visualizer.getCaptureSizeRange() [128, 1024]
        mVisualizer.setCaptureSize(mCaptureSize);
        // Visualizer.getMaxCaptureRate()-获取最大采样率,采样速率为512MHz，(3、4 -> true, true)设置同时获取时域、频域波形数据
        // 第一个参数是回调, 使用waveformdata或fftdata; 第二个是更新率; 第三个是判断使用waveformdata; 第四个是判断使用fftdata, 第三\四个均与回调的返回值有关
//        int rate = Visualizer.getMaxCaptureRate() / 2; // Visualizer.getMaxCaptureRate() 20000
        mMaxCaptureRate = Visualizer.getMaxCaptureRate() / 2; // 10000
        //2023-04-03 16:49:40.031 12931-12931 E init: --> onFftDataCapture =[128, 1024], 20000
        Log.e("ArHui", "init: --> onFftDataCapture =" + Arrays.toString(Visualizer.getCaptureSizeRange()) + ", " + Visualizer.getMaxCaptureRate());
        mVisualizer.setDataCaptureListener(this, mMaxCaptureRate, true, true);

        // Enabled Visualizer and disable when we're done with the stream
        mVisualizer.setEnabled(true);
//        int scalingMode = mVisualizer.getScalingMode();
//        mVisualizer.setScalingMode(SCALING_MODE_NORMALIZED);
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            Log.i("ArHui", "init: --> 播放完");
//            mVisualizer.setEnabled(false);
        });


        //设置允许波形表示，并且捕获它
        mVisualizerView.setVisualizer(mVisualizer);
//        mVisualizerView2.setVisualizer(mVisualizer);
        mVisualizerView.setDataCaptureListener(this);
//        mVisualizerView2.setDataCaptureListener(this);

        duration = mPlayer.getDuration();
        progress.setMax(duration);
        startSyncProgress();

        // 以下两行音量是0也能有返回fft、waveform返回
        Equalizer mEqualizer = new Equalizer(0, audioSessionId);
        mEqualizer.setEnabled(true); // need to enable equalizer
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

    /**
     * 捕获波形数,时域波形数据
     * 时域（时间域-time domain）
     * ——自变量是时间，即横轴是时间，纵轴是信号的变化。其动态信号 x（t）是描述信号在不同时刻取值的函数。
     * <p>
     * <p>
     * 2023-04-03 16:47:03.219 27079-27079 I onFftDataCapture: --> 44100000, [86132, 172265, 258398, 344531, 430664, 516796, 602929, 689062]
     * 2023-04-03 16:47:03.319 27079-27079 W onFftDataCapture: --> waveform 44100000
     *
     * @param visualizer
     * @param waveform     waveform 是波形采样的字节数组，它包含一系列的 8 位（无符号）的 PCM 单声道样本
     * @param samplingRate 采样频率 samplingRate 44100000, log发现：其他信息：音频CD 的采样率为 44100样本/秒。https://en.wikipedia.org/wiki/Nyquist_frequency
     */
    @Override
    public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
        mWaveformView.setWaveform(waveform);
        mVisualizerWaveView.updateVisualizer(waveform);
        double amp = computedbAmp(waveform);
        Log.w("ArHui", "onFftDataCapture: --> waveform " + samplingRate + ", amp = " + amp);
//        Visualizer.MeasurementPeakRms measurementPeakRms = new Visualizer.MeasurementPeakRms();
//        int x = mVisualizer.getMeasurementPeakRms(measurementPeakRms);
//        int rmsLevel = calculateRMSLevel(waveform);
//        Log.i("ArHui", "onWaveFormDataCapture: --> Rms = " + x + ",  " + rmsLevel);
    }


    /**
     * 捕获傅里叶数据,频域波形数据
     * <p> https://xie.infoq.cn/article/386cc569321fbf0a0f0dbe7e8
     * 频域（频率域- frequency domain）
     * ——自变量是频率，【即横轴是频率，纵轴是该频率信号的幅度】，也就是通常说的频谱图。频谱图描述了信号的频率结构及频率与该频率信号幅度的关系。
     * ——对信号进行时域分析时，有时一些信号的时域参数相同，但并不能说明信号就完全相同。因为信号不仅随时间变化，还与频率、相位等信息有关，这就
     * ——需要进一步分析信号的频率结构，并在频率域中对信号进行描述。动态信号从时间域变换到频率域主要通过傅立叶级数和傅立叶变换等来实现。很简单
     * ——时域分析的函数是参数是 t，也就是 y=f(t)，频域分析时，参数是 w，也就是 y=F(w)两者之间可以互相转化。
     * 傅立叶变换：将一个表示波的函数从时域（时间与振幅的关系）转化为频域（频率与振幅的关系）的数学操作
     *
     * @param visualizer
     * @param fft          包含频率表示的字节数组;是经过 FFT 转换后频率采样的字节数组，频率范围为 0（直流）到采样值的一半！
     * @param samplingRate 采样率每秒采集音频流的点数。 第 k 个频率为(k*Fs)/(n/2)
     *                     采样频率 samplingRate 44100000, log发现：其他信息：音频CD 的采样率为 44100样本/秒。https://en.wikipedia.org/wiki/Nyquist_frequency
     */
    @Override
    public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {
        mWaveformView2.setWaveform(fft);
        mVisualizerFFTView.updateVisualizer(fft);
        int[] ks = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] pinS = new int[ks.length];
        for (int i = 0; i < ks.length; i++) {
            //获得的频率范围= 0~采样率/2 = 0~22.05kHz之间   https://blog.csdn.net/gkw421178132/article/details/71081628
            //即513个频率分布在 [ 0Hz，22.05kHz ]之间
            //每相邻两个频率间隔(mHz) = 采样率 / (1024 / 2) = 44 100 000 / 512 = 86.132Hz分辨率为86.132Hz，再小的频率间隔将无法分辨
            // k为0~512中的某个点，第k个点对应的频率 = k * frequencyEach，亦即
            // k(Hz)=getSamplingRate() * k /(getCaptureSize()/2)
            //版权声明：本文为CSDN博主「gkw421178132」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
            pinS[i] = ks[i] * samplingRate / (mCaptureSize / 2);
            //
//            pinS[i] = ks[i] * mMaxCaptureRate / (mCaptureSize / 2);
//            pinS[i] = ks[i] * samplingRate / mCaptureSize;
        }
        double amp = computedbAmp(fft);
        // FFT 输出样本 k 处的频率由下式给出：https://stackoverflow.com/questions/4720512/android-2-3-visualizer-trouble-understanding-getfft?rq=1
        // Fk = k * Fs / N,    k = 0,1,...,N-1 ; Fs是时间序列输入的采样频率；N是用于计算 FFT 的样本数
        Log.i("ArHui", "onFftDataCapture: --> " + samplingRate + ", " + Arrays.toString(pinS) + ",  --> " + fft.length + ", amp = " + amp);
    }

    /**
     * http://www.360doc.com/content/19/1027/22/13328254_869435992.shtml
     *
     * @param audioData
     * @return
     */
    public double computedbAmp(byte[] audioData) {
        //System.out.println("::::: audioData :::::" audioData);
        double amplitude = 0;
        for (int i = 0; i < audioData.length / 2; i++) {
            double y = (audioData[i * 2] | audioData[i * 2 + 1] << 8) / 32768.0;
            // depending on your endianness:
            // double y = (audioData[i*2]<<8 | audioData[i*2 1]) / 32768.0;
//            amplitude = Math.abs(y);
            amplitude += y * y;
        }
        double rms = Math.sqrt(amplitude / audioData.length / 2);
        double dbAmp = 10.0 * Math.log10(rms);
        return dbAmp;
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
        return (int) amplitude;
    }

    public void music1(View view) {
        mMaxCaptureRate = Visualizer.getMaxCaptureRate() / 2;
        startMusic(R.raw.aaaass);
    }

    public void music2(View view) {
        startMusic(R.raw.boom_clap);
    }

    public void music3(View view) {
        startMusic(R.raw.another_day);
    }

    public void music4(View view) {
        startMusic(R.raw.move_your_body);
    }

    public void music5(View view) {
        mMaxCaptureRate = Visualizer.getMaxCaptureRate() / 4;
        startMusic(R.raw.aaaass);
    }

    private void startMusic(int raw) {
        stopSyncProgress();
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
        mVisualizer.setEnabled(false);
        mVisualizer = null;

        mPlayer = MediaPlayer.create(this, raw);
        mPlayer.setLooping(true);
        mPlayer.start();

        // 实例化mVisualizer
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());
        // 设置内容长度为1024
        mCaptureSize = Visualizer.getCaptureSizeRange()[1]; // Visualizer.getCaptureSizeRange() [128, 1024]
        mVisualizer.setCaptureSize(mCaptureSize);
        // Visualizer.getMaxCaptureRate()【20000】-获取最大采样率,采样速率为512MHz，(3、4 -> true, true)设置同时获取时域、频域波形数据
        // 第一个参数是回调, 使用waveformdata或fftdata; 第二个是更新率; 第三个是判断使用waveformdata; 第四个是判断使用fftdata, 第三\四个均与回调的返回值有关

        mVisualizer.setDataCaptureListener(this, mMaxCaptureRate, true, true);

        // Enabled Visualizer and disable when we're done with the stream
        mVisualizer.setEnabled(true);
        mPlayer.setOnCompletionListener(mediaPlayer -> {
            Log.i("ArHui", "init: --> 播放完");
            mVisualizer.setEnabled(false);
        });
        //设置允许波形表示，并且捕获它
        mVisualizerView.setVisualizer(mVisualizer);
//        mVisualizerView2.setVisualizer(mVisualizer);
        mVisualizerView.setDataCaptureListener(this);
//        mVisualizerView2.setDataCaptureListener(this);
        duration = mPlayer.getDuration();
        progress.setMax(duration);
        startSyncProgress();
    }
}