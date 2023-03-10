package walke.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import walke.widget.R;
import walke.widget.marquee.MarqueeText;

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
