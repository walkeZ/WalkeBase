package walke.demolibrary.audio.waveform;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import walke.demolibrary.R;
import walke.demolibrary.audio.waveform.utils.BasePermissionActivity;

/**
 * 声波页面(录制)，form ：https://github.com/WTCool666/WaveformDraw； https://blog.csdn.net/qq_33750826/article/details/86643348
 */
public class WaveformActivity extends BasePermissionActivity implements View.OnClickListener {
    private Button btn_singleChannel, btn_singleFile, btn_doubleChannel, btn_doubleFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waveform);
        initView();
    }

    private void initView() {
        btn_singleChannel = (Button) findViewById(R.id.btn_singleChannel);
        btn_singleFile = (Button) findViewById(R.id.btn_singleFile);
        btn_doubleChannel = (Button) findViewById(R.id.btn_doubleChannel);
        btn_doubleFile = (Button) findViewById(R.id.btn_doubleFile);
        btn_singleChannel.setOnClickListener(this);
        btn_singleFile.setOnClickListener(this);
        btn_doubleChannel.setOnClickListener(this);
        btn_doubleFile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        int id = v.getId();
        if (id == R.id.btn_singleChannel) {
            intent.setClass(this, SingleChannelActivity.class);
        } else if (id == R.id.btn_singleFile) {
            intent.setClass(this, SingleFileActivity.class);
        } else if (id == R.id.btn_doubleChannel) {
            intent.setClass(this, DoubleChannelActivity.class);
        } else if (id == R.id.btn_doubleFile) {
            intent.setClass(this, DoubleFileActivity.class);
        }
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
