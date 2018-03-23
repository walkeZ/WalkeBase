package walke.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import walke.widget.R;
import walke.widget.marquee.AutoHorizontalScrollTextView;
import walke.widget.marquee.AutoVerticalScrollTextView;

/**
 *
 */
public class MarqueeActivity1 extends AppCompatActivity {

    private int number =0;
    private boolean isRunning=true;

    private String[] strings={"我的剑，就是你的剑!","俺也是从石头里蹦出来得!","我用双手成就你的梦想!","人在塔在!","犯我德邦者，虽远必诛!","我会让你看看什么叫残忍!","我的大刀早已饥渴难耐了!"};
    private String string="我的剑，就是你的剑!   俺也是从石头里蹦出来得!    我用双手成就你的梦想!    人在塔在!    犯我德邦者，虽远必诛!    我会让你看看什么叫残忍!    我的大刀早已饥渴难耐了!";

    private AutoVerticalScrollTextView verticalScrollTV;
    private AutoHorizontalScrollTextView horizontalScrollTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee1);

        initView();
    }

    private void initView() {

        horizontalScrollTV = (AutoHorizontalScrollTextView) findViewById(R.id.marquee1_textView);
        horizontalScrollTV.setText(string);

        verticalScrollTV = (AutoVerticalScrollTextView) findViewById(R.id.marquee1_textView_auto_roll);
        verticalScrollTV.setText(strings[0]);

        new Thread(){
            @Override
            public void run() {
                while (isRunning){
                    SystemClock.sleep(3000);
                    handler.sendEmptyMessage(199);
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 199) {
                verticalScrollTV.next();
                number++;
                verticalScrollTV.setText(strings[number%strings.length]);
            }

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunning=false;
    }
}
