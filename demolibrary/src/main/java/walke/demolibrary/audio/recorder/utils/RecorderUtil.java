package walke.demolibrary.audio.recorder.utils;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 录音工具类
 * Created by chenmj on 2016/4/14 0014.
 */
public class RecorderUtil {

    private final MediaRecorder recorder = new MediaRecorder();
    private final String path;
    private String folder;
    private static int SAMPLE_RATE_IN_HZ = 8000;

    public RecorderUtil(String folder, String path) {
        this.folder = folder;
        this.path = sanitizePath(folder, path);
    }

    private String sanitizePath(String folder, String path) {
        if (!folder.startsWith("/")) {
            folder = "/" + folder;
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.contains(".")) {
            path += ".amr";
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath() + folder + path;
    }

    /**
     * 开始录音
     */
    public void start() {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            return;
        }

        try {
            //设置声音的来源
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            //设置声音的输出格式
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //设置声音的编码格式
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //设置音频采样率
            recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
            //设置输出文件
            recorder.setOutputFile(path);
            //准备录音
            recorder.prepare();
            //开始录音
            recorder.start();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 结束录音
     */
    public void stop() {
        try {
            //停止录音
            recorder.stop();
            //释放资源
            recorder.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double getAmplitude() {
        if (recorder != null) {
            return (recorder.getMaxAmplitude());
        } else {
            return 0;
        }
    }
}
