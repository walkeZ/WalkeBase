package walke.demolibrary.audio.waveform;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;
import walke.demolibrary.audio.waveform.utils.BaseAudioSurfaceView;
import walke.demolibrary.audio.waveform.utils.Constant;
import walke.demolibrary.audio.waveform.utils.FileUtils;
import walke.demolibrary.audio.waveform.utils.NormalDialog;

public class DoubleFileActivity extends TitleActivity implements View.OnClickListener {
    private BaseAudioSurfaceView bsv_doubleFile, bsv_doubleFile2;
    private ImageView iv_doubleFile;
    private File file;
    private Button btn_doubleFile;
    private NormalDialog rePlayDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case FileUtils.GET_WAVEFORM_DATA_ING:
                    break;
                case FileUtils.GET_WAVEFORM_DATA_STOP:
                    bsv_doubleFile.addAudioDatas((int[]) msg.obj, msg.arg1, Constant.DOUBLE_CHANNEL_SAMPLEER_RATE, Constant.DOUBLE_CHANNLE_BIT_WIDTH, false);
                    bsv_doubleFile2.addAudioDatas((int[]) msg.obj, msg.arg1, Constant.DOUBLE_CHANNEL_SAMPLEER_RATE, Constant.DOUBLE_CHANNLE_BIT_WIDTH, true);
                    break;
                case FileUtils.PLAY_STOP:
                    status = PALYED;
                    tempCount = 0;
                    iv_doubleFile.setImageDrawable(getResources().getDrawable(R.drawable.start_play));
                    bsv_doubleFile.reDraws();
                    bsv_doubleFile2.reDraws();
                    break;
                case FileUtils.PLAYING:
                    Bundle playBundle = msg.getData();
                    if (playBundle != null) {
                        int size = msg.arg1;
                        if (size > 0) {
                            tempCount += size;
                            bsv_doubleFile.startHasReadAudioDataThread(msg.arg2);
                            bsv_doubleFile2.startHasReadAudioDataThread(msg.arg2);
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected int rootLayoutId() {
        return R.layout.double_file_layout;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("双声道文件波形绘制");
        initView();
        file = new File(Environment.getExternalStorageDirectory(), Constant.DOUBLE_FILE);
        FileUtils.getWaveformData(file.getAbsolutePath(), handler, Constant.DOUBLE_CHANNLE_BIT_WIDTH);
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        btn_doubleFile = (Button) findViewById(R.id.btn_doubleFile);
        btn_doubleFile.setOnClickListener(this);
        bsv_doubleFile = (BaseAudioSurfaceView) findViewById(R.id.bsv_doubleFile);
        bsv_doubleFile2 = (BaseAudioSurfaceView) findViewById(R.id.bsv_doubleFile2);
        iv_doubleFile = (ImageView) findViewById(R.id.iv_doubleFile);
        iv_doubleFile.setOnClickListener(this);
    }

    private final int PLAYING = 0;
    private final int PALYED = 1;
    private int status = PALYED;
    private int tempCount = 0;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tempCount = 0;
        FileUtils.stopGetWaveform();
        FileUtils.stopPalyWav();
        bsv_doubleFile.stopDrawsThread();
        bsv_doubleFile2.stopDrawsThread();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_doubleFile) {
            if (status == PLAYING) {
                FileUtils.stopPalyWav();
                status = PALYED;
                btn_doubleFile.setVisibility(View.VISIBLE);
                iv_doubleFile.setImageDrawable(getResources().getDrawable(R.drawable.start_play));
            } else if (status == PALYED) {
                status = PLAYING;
                btn_doubleFile.setVisibility(View.GONE);
                iv_doubleFile.setImageDrawable(getResources().getDrawable(R.drawable.stop_play));
                FileUtils.getWavDataAndPlay(file.getAbsolutePath(), handler, Constant.DOUBLE_CHANNEL_SAMPLEER_RATE, Constant.DOUBLE_CAHNNEL_FORMAT, Constant.DOUBLE_CHANNEL_CONFIG, tempCount);
            }
        } else if (id == R.id.btn_doubleFile) {
            if (rePlayDialog == null) {
                rePlayDialog = new NormalDialog();
            }
            rePlayDialog.show(getSupportFragmentManager(), "", "确认真的要重新录制吗？", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_NEGATIVE:
                            rePlayDialog.dismiss();
                            break;
                        case DialogInterface.BUTTON_POSITIVE:
                            bsv_doubleFile.reDraws();
                            bsv_doubleFile2.reDraws();
                            status = PLAYING;
                            tempCount = 0;
                            FileUtils.getWavDataAndPlay(file.getAbsolutePath(), handler, Constant.DOUBLE_CHANNEL_SAMPLEER_RATE, Constant.DOUBLE_CAHNNEL_FORMAT, Constant.DOUBLE_CHANNEL_CONFIG, tempCount);
                            btn_doubleFile.setVisibility(View.GONE);
                            iv_doubleFile.setImageDrawable(getResources().getDrawable(R.drawable.stop_play));
                            break;
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tempCount = 0;
        FileUtils.stopGetWaveform();
        FileUtils.stopPalyWav();
        bsv_doubleFile.stopDrawsThread();
        bsv_doubleFile2.stopDrawsThread();
    }
}
