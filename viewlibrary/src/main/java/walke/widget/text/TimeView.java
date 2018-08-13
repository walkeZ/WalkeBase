package walke.widget.text;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by walke.Z on 2018/4/17.
 *
 * Android显示TextView文字的倒影效果 https://blog.csdn.net/slightfeverGK/article/details/51113069
 *
 */

public class TimeView extends TextView {

    private static final int MESSAGE_TIME = 1;


    public TimeView(Context context) {
        this(context,null);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        new TimeThread().start();
    }

    public class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Message msg = new Message();
                    msg.what = MESSAGE_TIME;
                    mHandler.sendMessage(msg);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_TIME:
                    setTime();
                    break;

                default:
                    break;
            }
        }
    };

    public void setTime() {
        long sysTime = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(sysTime);
        String sysTimeStr = DateFormat.format("hh:mm", sysTime).toString();
//        if(calendar.get(Calendar.AM_PM) == 0) {
        int i = calendar.get(Calendar.AM_PM);
        if(i == 0) {
            sysTimeStr += " AM";
        } else {
            sysTimeStr += " PM";
        }
        setText(sysTimeStr.replace("1", " 1"));
    }

}
