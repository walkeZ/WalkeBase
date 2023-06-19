package walke.demolibrary.wifi;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import walke.demolibrary.AppLog;
import walke.demolibrary.R;


/**
 * https://freesion.com/article/9601846822/
 * wifi 调试。ANDROID之通过用户名和密码连接指定WIFI热点
 */
public class WifiActivity01 extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi01);
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void init() {

    }

    public void connect(View view) {
        WifiApUtil.connectWifiApByNameAndPwd(this, "HeT-iot", "iot@1234", connectResult -> {
            AppLog.w("connectResult" + connectResult);
        });

    }

    public void disconnect(View view) {
    }
}