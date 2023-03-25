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

public class SingleChannelActivity extends TitleActivity implements View.OnClickListener {
    private BaseAudioSurfaceView bsv_singleChannel;
    private ImageView iv_singleChannel;
    private MicManager manager = null;
    private NormalDialog rePlayDialog;
    private Button btn_singleChannel;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MicManager.MODE_RECORD_FILE:
                    bsv_singleChannel.addAudioData((byte[]) msg.obj, msg.arg1, Constant.SINGLE_CHANNEL_SAMPLEER_RATE, Constant.SINGLE_CHANNLE_BIT_WIDTH, false);
                    break;
            }
        }
    };

    @Override
    protected int rootLayoutId() {
        return R.layout.single_channel_layout;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("单声道波形绘制");
        initView();
        manager = new MicManager(Constant.SINGLE_FILE);
        manager.setHandler(handler);
        manager.setAudioParameters(Constant.SINGLE_CHANNEL_SAMPLEER_RATE, Constant.SINGLE_CAHNNEL_FORMAT, Constant.SINGLE_CHANNEL_CONFIG);
    }

    @Override
    protected void initData() {

    }

    private void initView() {
        btn_singleChannel = (Button) findViewById(R.id.btn_singleChannel);
        btn_singleChannel.setOnClickListener(this);
        bsv_singleChannel = (BaseAudioSurfaceView) findViewById(R.id.bsv_singleChannel);
        iv_singleChannel = (ImageView) findViewById(R.id.iv_singleChannel);
        iv_singleChannel.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager.destroy();
        bsv_singleChannel.stopDrawThread();
        this.finish();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bsv_singleChannel) {
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
                        bsv_singleChannel.reDrawThread();
                        btn_singleChannel.setVisibility(View.GONE);
                        iv_singleChannel.setImageDrawable(getResources().getDrawable(R.drawable.stop_play));
                        manager.startRecord(MicManager.MODE_RECORD_FILE);
                        break;
                }
            });
        } else if (id == R.id.iv_singleChannel) {
            if (manager.getOperationStatus() == MicManager.MODE_RECORD_FILE) {
                manager.stopRecord();
                btn_singleChannel.setVisibility(View.VISIBLE);
                iv_singleChannel.setImageDrawable(getResources().getDrawable(R.drawable.start_play));
            } else {
                btn_singleChannel.setVisibility(View.GONE);
                manager.startRecord(MicManager.MODE_RECORD_FILE);
                iv_singleChannel.setImageDrawable(getResources().getDrawable(R.drawable.stop_play));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.destroy();
        bsv_singleChannel.stopDrawThread();
    }
}
