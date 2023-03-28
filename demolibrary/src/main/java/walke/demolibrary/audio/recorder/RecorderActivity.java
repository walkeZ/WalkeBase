package walke.demolibrary.audio.recorder;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

import walke.demolibrary.R;
import walke.demolibrary.audio.recorder.widget.RecorderDialog;

/**
 * @author walker
 * @date 2023/3/28
 * @desc 音频录制 https://blog.csdn.net/chmj1208/article/details/51386103
 */
public class RecorderActivity extends AppCompatActivity implements OnClickListener {

    private Button mButton;

    //录音相关
    private File mRecorderFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);

        mButton = findViewById(R.id.recorder_button);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.recorder_button) {
            recorderDialog();
        }
    }


    /**
     * 录音Dialog
     */
    private void recorderDialog() {
        new RecorderDialog(RecorderActivity.this)
                .builder()
                .show();
    }

}
