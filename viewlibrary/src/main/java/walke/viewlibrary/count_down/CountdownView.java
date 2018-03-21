package walke.viewlibrary.count_down;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;


/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2017/2/9.
 */
public class CountdownView extends TextView {
    private Context context;
    private Handler mHandler=new Handler();
    private boolean isTimeDowning=false;
    private int time= 30;
    private Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            if (time>=0) {
                timeDown();//先减1，再赋值//startTime--:先赋值，再减1
                isTimeDowning=true;
            }else {
                isTimeDowning=false;
                setViewEnable("获取验证码");
                mHandler.removeCallbacks(mRunnable);
            }
        }
    };

    private void timeDown() {
        setViewUnable(time);
        mHandler.postDelayed(mRunnable,1000);
    }

    public CountdownView(Context context) {
        this(context,null);
    }

    public CountdownView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CountdownView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
    }

    public boolean isTimeDowning() {
        return isTimeDowning;
    }

    public void startTimeDown(int startTime) {
        time=startTime;
        time--;
        setViewUnable(startTime);
        mHandler.postDelayed(mRunnable,1000);
    }
    /** 不可点击时的显示
     * @param startTime 倒计时间
     */
    public void setViewUnable(int startTime) {
        CountdownView.this.setEnabled(false);//不可用
        CountdownView.this.setText("重新发送(" + startTime + "s)");
        CountdownView.this.setBackgroundColor(Color.GRAY);
    }

    /** 可点击时的显示
     * @param text 文本
     */
    public void setViewEnable( String text) {
        CountdownView.this.setEnabled(true);//可用
        CountdownView.this.setText(text);
        CountdownView.this.setBackgroundColor(Color.GREEN);
    }

    public void pauseCountdownTime(){
        setViewEnable("获取验证码");
        mHandler.removeCallbacks(mRunnable);
    }
    public void stopCountdownTime(){
        time=0;
        setViewEnable("获取验证码");
        mHandler.removeCallbacks(mRunnable);
    }
    public void removeRunable(){
        mHandler.removeCallbacks(mRunnable);
    }

}
