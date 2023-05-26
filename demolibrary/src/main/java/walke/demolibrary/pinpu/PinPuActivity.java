package walke.demolibrary.pinpu;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.audiofx.Visualizer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import walke.demolibrary.R;

/**
 * @author Walker - 2023/3/22 11:22 AM
 * @email 1126648815@qq.ocm
 * @desc : 音频频谱
 */
public class PinPuActivity extends AppCompatActivity {
    private static final float VISUALIZER_HEIGHT_DIP = 150f;//频谱View高度
    private MediaPlayer mMediaPlayer; // 音频
    private Visualizer mVisualizer; // 频谱器
    private Equalizer mEqualizer; // 均衡器
    private LinearLayout mLayout; // 代码布局
    VisualizerView mBaseVisualizerView;

    ImageButton play;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        PriorityQueueTest.test1();
        PriorityQueueTest.test12();
        PriorityQueueTest.test2();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);//设置音频流 - STREAM_MUSIC：音乐回放即媒体音量

        mLayout = new LinearLayout(this);//代码创建布局
        mLayout.setOrientation(LinearLayout.VERTICAL);//设置为线性布局-上下排列
        mLayout.setBackgroundResource(R.drawable.bg);//设置界面背景
        mLayout.setGravity(Gravity.CENTER);
        setContentView(mLayout);//将布局添加到 Activity

        mMediaPlayer = MediaPlayer.create(this, R.raw.aaaass);//实例化 MediaPlayer 并添加音频

        setupVisualizerFxAndUi();//添加频谱到界面
        setupEqualizeFxAndUi();//添加均衡器到界面
        setupPlayButton();//添加按钮到界面

        mVisualizer.setEnabled(true);//false 则不显示
        mMediaPlayer.setOnCompletionListener(mp -> {
        });

        mMediaPlayer.start();//开始播放
        mMediaPlayer.setLooping(true);//循环播放
    }

    /**
     * 通过mMediaPlayer返回的AudioSessionId创建一个优先级为0均衡器对象 并且通过频谱生成相应的UI和对应的事件
     */
    private void setupEqualizeFxAndUi() {

        TextView kongge = new TextView(this);
        kongge.setText("");
        kongge.setTextSize(10);
        mLayout.addView(kongge);

        mEqualizer = new Equalizer(0, mMediaPlayer.getAudioSessionId());
        mEqualizer.setEnabled(true);// 启用均衡器

        // 通过均衡器得到其支持的频谱引擎
        short bands = mEqualizer.getNumberOfBands();

        // getBandLevelRange 是一个数组，返回一组频谱等级数组，
        // 第一个下标为最低的限度范围
        // 第二个下标为最大的上限,依次取出
        final short minEqualizer = mEqualizer.getBandLevelRange()[0];
        final short maxEqualizer = mEqualizer.getBandLevelRange()[1];

        for (short i = 0; i < bands; i++) {
            final short band = i;

            TextView freqTextView = new TextView(this);
            freqTextView.setTextColor(Color.WHITE);
            freqTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            freqTextView.setGravity(Gravity.CENTER_HORIZONTAL);

            // 取出中心频率
            freqTextView.setText((mEqualizer.getCenterFreq(band) / 1000) + "HZ");
            mLayout.addView(freqTextView);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);

            TextView minDbTextView = new TextView(this);
            minDbTextView.setTextColor(Color.WHITE);
            minDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            minDbTextView.setText((minEqualizer / 100) + " dB");

            TextView maxDbTextView = new TextView(this);
            maxDbTextView.setTextColor(Color.WHITE);
            maxDbTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            maxDbTextView.setText((maxEqualizer / 100) + " dB");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 40);

            layoutParams.weight = 1;
            SeekBar seekbar = new SeekBar(this);
            seekbar.setLayoutParams(layoutParams);
            seekbar.setPadding(15, 0, 15, 0);
            seekbar.setThumb(getResources().getDrawable(R.drawable.seek_bar_dian_selector));
            seekbar.setThumbOffset(20);
            seekbar.setMax(maxEqualizer - minEqualizer);
            seekbar.setProgress(mEqualizer.getBandLevel(band));
            seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    mEqualizer.setBandLevel(band, (short) (progress + minEqualizer));
                }
            });
            row.addView(minDbTextView);
            row.addView(seekbar);
            row.addView(maxDbTextView);

            mLayout.addView(row);

        }
        TextView eqTextView = new TextView(this);
        eqTextView.setTextColor(Color.WHITE);
        eqTextView.setText("均衡器");
        eqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
        eqTextView.setTextSize(20);

        mLayout.addView(eqTextView);
    }

    /**
     * 生成一个VisualizerView对象，使音频频谱的波段能够反映到 VisualizerView上
     */
    private void setupVisualizerFxAndUi() {
        mBaseVisualizerView = new VisualizerView(this);
        mBaseVisualizerView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.FILL_PARENT,//宽度
                (int) (VISUALIZER_HEIGHT_DIP * getResources().getDisplayMetrics().density)//高度
        ));
        //将频谱View添加到布局
        mLayout.addView(mBaseVisualizerView);
        //实例化Visualizer，参数SessionId可以通过MediaPlayer的对象获得
        mVisualizer = new Visualizer(mMediaPlayer.getAudioSessionId());
        //采样 - 参数内必须是2的位数 - 如64,128,256,512,1024
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        //设置允许波形表示，并且捕获它
        mBaseVisualizerView.setVisualizer(mVisualizer);
    }

    //播放按钮
    private void setupPlayButton() {
        play = new ImageButton(this);
        play.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        play.setBackgroundResource(R.drawable.new_main_activity_stop_up);
        play.setOnClickListener(view -> {
            if (mMediaPlayer.isPlaying()) {
                play.setBackgroundResource(R.drawable.new_main_activity_play_up);
                mMediaPlayer.pause();
            } else {
                play.setBackgroundResource(R.drawable.new_main_activity_stop_up);
                mMediaPlayer.start();
            }
        });

        mLayout.addView(play);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing() && mMediaPlayer != null) {
            mVisualizer.release();
            mMediaPlayer.release();
            mEqualizer.release();
            mMediaPlayer = null;
        }
    }

}