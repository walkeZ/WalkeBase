package walke.viewlibrary.marquee;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import walke.viewlibrary.R;

public class MarqueeActivity2 extends AppCompatActivity {
    private MarqueeText test;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marquee2);
        test = (MarqueeText) this.findViewById(R.id.marquee2_test);
    }
    public void start(View v) {
        test.startScroll();
    }
    public void stop(View v) {
        test.stopScroll();
    }
    public void startFor0(View v){
        test.startFor0();
    }
}
