package walke.demolibrary.audio.activity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import walke.demolibrary.R;
import walke.demolibrary.audio.renderer.BarGraphRenderer;
import walke.demolibrary.audio.renderer.CircleBarRenderer;
import walke.demolibrary.audio.renderer.CircleRenderer;
import walke.demolibrary.audio.renderer.LineRenderer;
import walke.demolibrary.audio.utils.TunnelPlayerWorkaround;
import walke.demolibrary.audio.widget.AudioVisualizerView;


/**
 * Demo to show how to use VisualizerView
 * https://blog.csdn.net/qq_25497621/article/details/78623175
 * audio：音频， form https://github.com/walkeZ/AudioWaveShow
 */
public class AudioActivity01 extends AppCompatActivity {
    private MediaPlayer mPlayer;
    /**
     * Silent:不说话的，无声的；tunnel 地下通道
     */
    private MediaPlayer mSilentPlayer;  /* to avoid tunnel player issue */
    private AudioVisualizerView mAudioVisualizerView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio01);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initTunnelPlayerWorkaround();
        init();
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
        mPlayer = MediaPlayer.create(this, R.raw.test);
        mPlayer.setLooping(true);
        mPlayer.start();

        // We need to link the visualizer view to the media player so that
        // it displays something
        mAudioVisualizerView = (AudioVisualizerView) findViewById(R.id.audio01_visualizerView);
        mAudioVisualizerView.link(mPlayer);

        // Start with just line renderer
        addLineRenderer();
    }

    private void cleanUp() {
        if (mPlayer != null) {
            mAudioVisualizerView.release();
            mPlayer.release();
            mPlayer = null;
        }

        if (mSilentPlayer != null) {
            mSilentPlayer.release();
            mSilentPlayer = null;
        }
    }

    // Workaround (for Galaxy S4)
    //
    // "Visualization does not work on the new Galaxy devices"
    //    https://github.com/felixpalmer/android-visualizer/issues/5
    //
    // NOTE:
    //   This code is not required for visualizing default "test.mp3" file,
    //   because tunnel player is used when duration is longer than 1 minute.
    //   (default "test.mp3" file: 8 seconds)
    //
    private void initTunnelPlayerWorkaround() {
        // Read "tunnel.decode" system property to determine
        // the workaround is needed
        if (TunnelPlayerWorkaround.isTunnelDecodeEnabled(this)) {
            mSilentPlayer = TunnelPlayerWorkaround.createSilentMediaPlayer(this);
        }
    }

    // Methods for adding renderers to visualizer
    private void addBarGraphRenderers() {
        //底部柱状条
        Paint paint = new Paint();
        paint.setStrokeWidth(50f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(200, 56, 138, 252));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
        mAudioVisualizerView.addRenderer(barGraphRendererBottom);

        //顶部柱状条
        Paint paint2 = new Paint();
        paint2.setStrokeWidth(12f);
        paint2.setAntiAlias(true);
        paint2.setColor(Color.argb(200, 181, 111, 233));
        BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, true);
        mAudioVisualizerView.addRenderer(barGraphRendererTop);
    }

    private void addCircleBarRenderer() {
        Paint paint = new Paint();
        paint.setStrokeWidth(8f);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(Mode.LIGHTEN));
        paint.setColor(Color.argb(255, 222, 92, 143));
        CircleBarRenderer circleBarRenderer = new CircleBarRenderer(paint, 32, true);
        mAudioVisualizerView.addRenderer(circleBarRenderer);
    }

    private void addCircleRenderer() {
        Paint paint = new Paint();
        paint.setStrokeWidth(3f);
        paint.setAntiAlias(true);
        paint.setColor(Color.argb(255, 222, 92, 143));
        CircleRenderer circleRenderer = new CircleRenderer(paint, true);
        mAudioVisualizerView.addRenderer(circleRenderer);
    }

    private void addLineRenderer() {
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.argb(88, 0, 128, 255));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(5f);
        lineFlashPaint.setAntiAlias(true);
        lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, true);
        mAudioVisualizerView.addRenderer(lineRenderer);
    }

    // Actions for buttons defined in xml
    public void startPressed(View view) throws IllegalStateException, IOException {
        if (mPlayer.isPlaying()) {
            return;
        }
        mPlayer.prepare();
        mPlayer.start();
    }

    public void stopPressed(View view) {
        mPlayer.stop();
    }

    public void barPressed(View view) {
        addBarGraphRenderers();
    }

    public void circlePressed(View view) {
        addCircleRenderer();
    }

    public void circleBarPressed(View view) {
        addCircleBarRenderer();
    }

    public void linePressed(View view) {
        addLineRenderer();
    }

    public void clearPressed(View view) {
        mAudioVisualizerView.clearRenderers();
    }
}