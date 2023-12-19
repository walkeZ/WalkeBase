package walke.demolibrary.vlc1;

import android.graphics.Rect;
import android.net.Uri;
import android.view.SurfaceView;
import android.view.View;
import android.widget.EditText;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

import walke.base.activity.TitleActivity;
import walke.base.tool.LogUtil;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * https://blog.csdn.net/u010855711/article/details/124946297
 *
 * @author walke
 * @date 2023-12-19
 */
public class VLC1Activity extends TitleActivity {
    private LibVLC mLibVLC = null;
    private MediaPlayer mMediaPlayer = null;
    private SurfaceView svVideo;
    private EditText etAddress;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_vlc1;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("VLC播放视频和直播流");
        etAddress = findViewById(R.id.vlc1_etAddress);
        svVideo = findViewById(R.id.vlc1_svVideo);
    }

    @Override
    protected void initData() {
        final ArrayList args = new ArrayList<>();//VLC参数
        args.add("--rtsp-tcp");//强制rtsp-tcp，加快加载视频速度
        args.add("--aout=opensles");
        args.add("--audio-time-stretch");
        //args.add("--sub-source=marq{marquee=\"%Y-%m-%d,%H:%M:%S\",position=10,color=0xFF0000,size=40}");//这行是可以再vlc窗口右下角添加当前时间的
        args.add("-vvv");

        mLibVLC = new LibVLC(this, args);
        mMediaPlayer = new MediaPlayer(mLibVLC);
        svVideo.post(() -> {
            Rect surfaceFrame = svVideo.getHolder().getSurfaceFrame();
            //设置vlc视频铺满布局
            mMediaPlayer.getVLCVout().setWindowSize(svVideo.getWidth(), svVideo.getHeight());//宽，高  播放窗口的大小
            //mMediaPlayer.setAspectRatio(layout_video.getWidth()+":"+layout_video.getHeight());//宽，高  画面大小
            mMediaPlayer.setScale(0);//这行必须加，为了让视图填满布局
            //添加视图
            IVLCVout vout = mMediaPlayer.getVLCVout();
            vout.setVideoView(svVideo);
            vout.attachViews();
        });
        Uri uri = Uri.parse("https://media.w3.org/2010/05/sintel/trailer.mp4");//rtsp流地址或其他流地址//"https://media.w3.org/2010/05/sintel/trailer.mp4"
        final Media media = new Media(mLibVLC, uri);
        int cache = 10;
        media.addOption(":network-caching=" + cache);
        media.addOption(":file-caching=" + cache);
        media.addOption(":live-cacheing=" + cache);
        media.addOption(":sout-mux-caching=" + cache);
        media.addOption(":codec=mediacodec,iomx,all");
        mMediaPlayer.setMedia(media);//
        media.setHWDecoderEnabled(false, false);//设置后才可以录制和截屏,这行必须放在mMediaPlayer.setMedia(media)后面，因为setMedia会设置setHWDecoderEnabled为true
        mMediaPlayer.setEventListener(event -> {
            if (event.type == MediaPlayer.Event.Playing) {
                LogUtil.w("main", "正在播放");
            } else {
                LogUtil.w("main", "其他");
            }

        });
        mMediaPlayer.play();
    }

    public void useTestRtsp(View view) {
        etAddress.setText("rtsp://192.168.1.107:8554/live");
    }

    public void playRtsp(View view) {
        Uri uri = Uri.parse(etAddress.getText().toString()); // rtsp流地址，使用"IP摄像头App"生成rtsp，注调试手机与摄像头手机在同一局域网下。
        final Media media = new Media(mLibVLC, uri);
        int cache = 10;
        media.addOption(":network-caching=" + cache);
        media.addOption(":file-caching=" + cache);
        media.addOption(":live-cacheing=" + cache);
        media.addOption(":sout-mux-caching=" + cache);
        media.addOption(":codec=mediacodec,iomx,all");
        mMediaPlayer.setMedia(media);//
        media.setHWDecoderEnabled(false, false); // 设置后才可以录制和截屏,这行必须放在mMediaPlayer.setMedia(media)后面，因为setMedia会设置setHWDecoderEnabled为true
        mMediaPlayer.setEventListener(event -> {
            if (event.type == MediaPlayer.Event.Playing) {
                LogUtil.w("main", "正在播放");
            } else {
                LogUtil.w("main", "其他");
            }

        });
        mMediaPlayer.play();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mMediaPlayer.stop();
        mMediaPlayer.detachViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mLibVLC.release();
    }
}