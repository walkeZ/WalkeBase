package walke.demolibrary.audio.waveform;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;
import walke.demolibrary.audio.waveform.utils.BaseAudioSurfaceView;
import walke.demolibrary.audio.waveform.utils.Constant;
import walke.demolibrary.audio.waveform.utils.MicManager;
import walke.demolibrary.audio.waveform.utils.NormalDialog;

public class DoubleChannelActivity extends TitleActivity implements View.OnClickListener {
    private BaseAudioSurfaceView bsv_doubleChannel, bsv_doubleChannel2;
    private ImageView iv_doubleChannel;
    private MicManager manager = null;
    private Button btn_doubleChannel;
    private NormalDialog rePlayDialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MicManager.MODE_RECORD_FILE:
                    bsv_doubleChannel.addAudioData((byte[]) msg.obj, msg.arg1, Constant.DOUBLE_CHANNEL_SAMPLEER_RATE, Constant.DOUBLE_CHANNLE_BIT_WIDTH, false);
                    bsv_doubleChannel2.addAudioData((byte[]) msg.obj, msg.arg1, Constant.DOUBLE_CHANNEL_SAMPLEER_RATE, Constant.DOUBLE_CHANNLE_BIT_WIDTH, true);
                    break;
            }
        }
    };

    @Override
    protected int rootLayoutId() {
        return R.layout.double_channel_layout;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        initView();
        titleLayout.setTitleText("双声道波形绘制");
        manager = new MicManager(Constant.DOUBLE_FILE);
        manager.setHandler(handler);
        manager.setAudioParameters(Constant.DOUBLE_CHANNEL_SAMPLEER_RATE, Constant.DOUBLE_CAHNNEL_FORMAT, Constant.DOUBLE_CHANNEL_CONFIG);
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        btn_doubleChannel = (Button) findViewById(R.id.btn_doubleChannel);
        btn_doubleChannel.setOnClickListener(this);
        bsv_doubleChannel = (BaseAudioSurfaceView) findViewById(R.id.bsv_doubleChannel);
        bsv_doubleChannel2 = (BaseAudioSurfaceView) findViewById(R.id.bsv_doubleChannel2);
        iv_doubleChannel = (ImageView) findViewById(R.id.iv_doubleChannel);
        iv_doubleChannel.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager.destroy();
        bsv_doubleChannel.stopDrawThread();
        bsv_doubleChannel2.stopDrawsThread();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_doubleChannel) {
            if (rePlayDialog == null) {
                rePlayDialog = new NormalDialog();
            }
            rePlayDialog.show(getSupportFragmentManager(), "", "确认真的要重新录制吗？", (dialog, which) -> {
                switch (which) {
                    case DialogInterface.BUTTON_NEGATIVE:
                        rePlayDialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_POSITIVE:
                        manager.destroy();
                        bsv_doubleChannel.reDrawThread();
                        bsv_doubleChannel2.reDrawThread();
                        btn_doubleChannel.setVisibility(View.GONE);
                        iv_doubleChannel.setImageDrawable(getResources().getDrawable(R.drawable.stop_play));
                        manager.startRecord(MicManager.MODE_RECORD_FILE);
                        break;
                }
            });
        } else if (id == R.id.iv_doubleChannel) {
            if (manager.getOperationStatus() == MicManager.MODE_RECORD_FILE) {
                manager.stopRecord();
                btn_doubleChannel.setVisibility(View.VISIBLE);
                iv_doubleChannel.setImageDrawable(getResources().getDrawable(R.drawable.start_play));
            } else {
                btn_doubleChannel.setVisibility(View.GONE);
                manager.startRecord(MicManager.MODE_RECORD_FILE);
                iv_doubleChannel.setImageDrawable(getResources().getDrawable(R.drawable.stop_play));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.destroy();
        bsv_doubleChannel.stopDrawThread();
        bsv_doubleChannel2.stopDrawsThread();
    }
}
