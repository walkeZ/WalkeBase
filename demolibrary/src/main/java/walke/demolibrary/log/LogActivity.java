package walke.demolibrary.log;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * @author Walker - 2023/3/10 10:20 AM
 * @email 1126648815@qq.ocm
 * @desc : 探探切片效果1
 * https://blog.csdn.net/m0_65146205/article/details/122005140
 * https://www.jianshu.com/p/879988f9c78c
 */
public class LogActivity extends TitleActivity {
    private static final int MSG_LOG = 11;
    private int mLogAccount = 0;
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_LOG) {
                mLogAccount++;
                Log.i("LogActivity", "handleMessage: " + mLogAccount);
                mHandler.sendEmptyMessageDelayed(MSG_LOG, 300);
            }
        }
    };

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_log;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("日志留存");
        LogcatHelper.getInstance(this).start();
    }

    @Override
    protected void initData() {
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    public void testLog(View view) {
        Log.i("LogActivity", "testLog: " + mLogAccount);
        mHandler.sendEmptyMessageDelayed(MSG_LOG, 300);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        LogcatHelper.getInstance(this).stop();
        super.onDestroy();
    }
}