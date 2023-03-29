package walke.demolibrary.audio.recorder.widget;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.Handler;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import walke.demolibrary.R;
import walke.demolibrary.audio.recorder.utils.RecorderUtil;

/**
 * Created by chenmj on 2016/4/22 0022.
 */
public class RecorderDialog {
    /**
     * 录制停止/未开始
     */
    private static final int MSG_RECORD_STOP = 0;
    /**
     * 录制中
     */
    private static final int MSG_RECORDING = 1;

    private Context context;

    /**
     * RecorderUtil
     */
    private RecorderUtil recorderUtil;
    /**
     * MediaPlayer
     */
    private MediaPlayer media;
    /**
     * 更新时间的线程
     */
    private Thread timeThread;
    /**
     * 更新进度条的线程
     */
    private Thread barThread;
    /**
     * 更新的语音图标
     */
    private ImageButton dialog_image;
    /**
     * 保存录音的文件名
     **/
    private String fileName;
    /**
     * 保存录音的文件
     **/
    private File file;
    /**
     * 保存录音的文件夹
     **/
    private String voice_folder = "myvoice";

    private static int MAX_TIME = 0; // 最长录制时间，单位秒，0为无时间限制
    private static int MIX_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1

    private static int RECORD_NO = 0; // 不在录音
    private static int RECORD_ING = 1; // 正在录音
    private static int RECODE_ED = 2; // 完成录音

    private static int RECODE_STATE = 0; // 录音的状态

    private static float recodeTime = 0.0f; // 录音的时间
    private static double voiceValue = 0.0; // 麦克风获取的音量值

    private static boolean playState = false; // 播放状态

    private Dialog dialog;
    private Display display;

    private TextView second;
    private TextView lable;
    private ImageButton recorder;
    private ImageButton delete;
    private View frequency;
    private ProgressBar voiceProgressBar;

    private boolean isPlay = false;
    private float y1;
    private float y2;

    public RecorderDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public RecorderDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.chat_recorder_dialog, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 获取自定义Dialog布局中的控件
        second = (TextView) view.findViewById(R.id.voice_second);
        lable = (TextView) view.findViewById(R.id.voice_label);
        recorder = (ImageButton) view.findViewById(R.id.voice_recorder);
        delete = (ImageButton) view.findViewById(R.id.voice_delete);
        frequency = (View) view.findViewById(R.id.voice_frequency);
        voiceProgressBar = (ProgressBar) view.findViewById(R.id.voice_progress_bar);

        //事件
        recorderListener();

        //删除
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除文件
                file = null;
                voiceProgressBar.setProgress(0);
                voiceProgressBar.setMax(0);
                frequency.setVisibility(View.GONE);
                second.setVisibility(View.GONE);
                second.setText("");
                delete.setVisibility(View.GONE);
                recorder.setBackgroundResource(R.drawable.chat_record_bg);
                lable.setText("按住录音");

            }
        });


        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        return this;
    }

    public void show() {
        dialog.show();
    }

    /**
     * 录音事件
     */
    private void recorderListener() {
        recorder.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:// 按下

                    if (delete.getVisibility() == View.VISIBLE) {

                        //在播放
                        if (isPlay) {
                            isPlay = false;
                            recorder.setBackgroundResource(R.drawable.record_play);
                            lable.setText("按下播放");
                            playVoice();
                        }
                        //不在播放，则播放
                        else {
                            isPlay = true;
                            recorder.setBackgroundResource(R.drawable.record_pause);
                            lable.setText("按下暂停");
                            playVoice();
                        }

                    } else {
                        lable.setText("松开结束");
                        y1 = event.getY();
                        // 如果当前不是正在录音状态，开始录音
                        if (RECODE_STATE != RECORD_ING) {
                            startRecorder();
                        }
                    }
                    break;

                case MotionEvent.ACTION_UP:// 离开

                    if (delete.getVisibility() == View.VISIBLE) {

                    } else {
                        y2 = event.getY();
                        //向上
                        if (y1 - y2 > 100) {
                            //放弃
                            abandonRecorder();
                        } else {
                            //停止录音
                            stopRecorder();
                        }
                    }
                    break;
            }
            return false;
        });
    }

    /**
     * 开始录音
     */
    private void startRecorder() {
        fileName = String.valueOf(System.currentTimeMillis());
        recorderUtil = new RecorderUtil(voice_folder, fileName);  //文件名voice
        RECODE_STATE = RECORD_ING;
        // 显示录音情况
        showVoiceDialog();
        // 开始录音
        recorderUtil.start();
        // 计时线程
        timeThread();
    }

    /**
     * 放弃录音
     */
    private void abandonRecorder() {
        // 如果是正在录音
        if (RECODE_STATE == RECORD_ING) {
            RECODE_STATE = RECODE_ED;
            // 如果录音图标正在显示,关闭
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            // 停止录音
            recorderUtil.stop();
            voiceValue = 0.0;
            RECODE_STATE = RECORD_NO;
            //放弃后续操作
            lable.setText("按住录音");
        }
    }

    /**
     * 停止录音
     */
    private void stopRecorder() {
        // 如果是正在录音
        if (RECODE_STATE == RECORD_ING) {
            RECODE_STATE = RECODE_ED;
            // 如果录音图标正在显示,关闭
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            // 停止录音
            recorderUtil.stop();
            voiceValue = 0.0;

            if (recodeTime < MIX_TIME) {
                showWarnToast();
                RECODE_STATE = RECORD_NO;
                lable.setText("按住录音");
            } else {
                MediaPlayer player = new MediaPlayer();
                file = new File(Environment
                        .getExternalStorageDirectory(), voice_folder + "/" + fileName + ".amr");
                try {
                    player.setDataSource(file.getAbsolutePath());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                frequency.setVisibility(View.VISIBLE);
                second.setVisibility(View.VISIBLE);
                second.setText((int) recodeTime + "s");
                delete.setVisibility(View.VISIBLE);
                recorder.setBackgroundResource(R.drawable.record_play);
                lable.setText("按下播放");
            }
        }
    }

    /**
     * 播放录音
     */
    private void playVoice() {

        // 如果不是正在播放
        if (!playState) {
            // 实例化MediaPlayer对象
            media = new MediaPlayer();
//          File file = new File(Environment.getExternalStorageDirectory(), voice_folder + "/" + fileName + ".amr");
            try {
                // 设置播放资源
                media.setDataSource(file.getAbsolutePath());
                // 准备播放
                media.prepare();
                // 开始播
                media.start();
                // 改变播放的标志位
                playState = true;
                voiceProgressBar.setMax(media.getDuration());
                barUpdate();

                // 设置播放结束时监
                media.setOnCompletionListener(mp -> {
                    if (playState) {
                        playState = false;
                        isPlay = false;
                        recorder.setBackgroundResource(R.drawable.record_play);
                        lable.setText("按下播放");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // 如果正在播放
            if (media.isPlaying()) {
                media.stop();
                playState = false;
            } else {
                playState = false;
            }

        }
    }

    /**
     * 显示正在录音的图标
     */
    private void showVoiceDialog() {
        dialog = new Dialog(context, R.style.DialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.chat_record_dialog);
        dialog_image = dialog.findViewById(R.id.record_dialog_log);
        dialog.show();
    }

    /**
     * 播放进度条线程
     */
    private void barUpdate() {
        barThread = new Thread(BarUpdateThread);
        barThread.start();
    }

    /**
     * 播放进度条更新线程
     */
    private Runnable BarUpdateThread = new Runnable() {
        private static final int UPDATE_PLAY_PROGRESS = 10;

        @Override
        public void run() {
            for (voiceProgressBar.getProgress(); voiceProgressBar.getProgress() <= voiceProgressBar.getMax(); ) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(UPDATE_PLAY_PROGRESS);
            }
        }

        Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                if (msg.what == UPDATE_PLAY_PROGRESS) {
                    voiceProgressBar.setProgress((media.getCurrentPosition()));
                }
            }
        };
    };

    /**
     * 录音计时线程
     */
    private void timeThread() {
        timeThread = new Thread(ImageThread);
        timeThread.start();
    }

    /**
     * 录音线程
     */
    private Runnable ImageThread = new Runnable() {

        @Override
        public void run() {
            recodeTime = 0.0f;
            // 如果是正在录音状态
            while (RECODE_STATE == RECORD_ING) {
                if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
                    handler.sendEmptyMessage(MSG_RECORD_STOP);
                } else {
                    try {
                        Thread.sleep(200);

                        recodeTime += 0.2;
                        if (RECODE_STATE == RECORD_ING) {
                            // 获取录制音量，// 得到录音时的最大振幅
                            // getMaxAmplitude()返回值就是从上一次调用getMaxAmplitude( )的时刻到这一次调用getMaxAmplitude( )时刻之间的音频振幅绝对值的最大值，
                            // 如果控制两个getMaxAmplitude()调用的间隔比较小的话，就可以实时得到当前的音频的振幅的绝对值了。
                            //——：https://blog.csdn.net/weixin_42371234/article/details/114572625
                            voiceValue = recorderUtil.getAmplitude();
                            handler.sendEmptyMessage(MSG_RECORDING);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

        }

        Handler handler = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case MSG_RECORD_STOP:
                        // 录音超过60秒自动停止,录音状态设为语音完成
                        if (RECODE_STATE == RECORD_ING) {
                            RECODE_STATE = RECODE_ED;
                            // 如果录音图标正在显示的话,关闭显示图标
                            if (dialog.isShowing()) {
                                dialog.dismiss();
                            }

                            // 停止录音
                            recorderUtil.stop();
                            voiceValue = 0.0;

                            // 如果录音时长小于1秒，显示录音失败的图标
                            if (recodeTime < 1.0) {
//                                showWarnToast();
                                RECODE_STATE = RECORD_NO;
                            } else {
                            }
                        }
                        break;

                    case MSG_RECORDING:
                        setDialogImage();
                        break;
                }
            }
        };
    };

    // 录音Dialog图片随声音大小切换
    private void setDialogImage() {
        if (voiceValue < 200.0) {
            dialog_image.setImageResource(R.drawable.record_animate_01);
        } else if (voiceValue > 200.0 && voiceValue < 400) {
            dialog_image.setImageResource(R.drawable.record_animate_02);
        } else if (voiceValue > 400.0 && voiceValue < 800) {
            dialog_image.setImageResource(R.drawable.record_animate_03);
        } else if (voiceValue > 800.0 && voiceValue < 1600) {
            dialog_image.setImageResource(R.drawable.record_animate_04);
        } else if (voiceValue > 1600.0 && voiceValue < 3200) {
            dialog_image.setImageResource(R.drawable.record_animate_05);
        } else if (voiceValue > 3200.0 && voiceValue < 5000) {
            dialog_image.setImageResource(R.drawable.record_animate_06);
        } else if (voiceValue > 5000.0 && voiceValue < 7000) {
            dialog_image.setImageResource(R.drawable.record_animate_07);
        } else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
            dialog_image.setImageResource(R.drawable.record_animate_08);
        } else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
            dialog_image.setImageResource(R.drawable.record_animate_09);
        } else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
            dialog_image.setImageResource(R.drawable.record_animate_10);
        } else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
            dialog_image.setImageResource(R.drawable.record_animate_11);
        } else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
            dialog_image.setImageResource(R.drawable.record_animate_12);
        } else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
            dialog_image.setImageResource(R.drawable.record_animate_13);
        }
    }

    /**
     * 语音太短Toast
     */
    private void showWarnToast() {
        Toast toast = new Toast(context);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);

        // 定义一个ImageView
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.icon_chat_talk_no); // 图标

        // 将ImageView和ToastView合并到Layout中
        linearLayout.addView(imageView);
        linearLayout.setGravity(Gravity.CENTER);// 内容居中

        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间 100为向下移100dp
        toast.show();
    }

}
