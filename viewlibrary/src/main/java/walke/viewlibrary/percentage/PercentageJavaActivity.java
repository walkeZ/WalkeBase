package walke.viewlibrary.percentage;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import walke.base.activity.AppActivity;
import walke.viewlibrary.R;

/**
 * Created by Walke.Z
 * on 2017/9/15. 43.
 * email：1126648815@qq.com
 */
public class PercentageJavaActivity extends AppActivity {
    private ImageView img;
    private TextView tv;
    private LinearLayout linearLayout;
    private Button bt,bt2;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_percentage_java;
    }

    @Override
    protected void initView() {
        img = ((ImageView) findViewById(R.id.apj_iv));
        tv = ((TextView) findViewById(R.id.apj_tv));
        linearLayout = ((LinearLayout) findViewById(R.id.apj_LinearLayout));
        bt = ((Button) findViewById(R.id.apj_bt));
        bt2 = ((Button) findViewById(R.id.apj_bt2));
        img.setOnClickListener(this);
        tv.setOnClickListener(this);
        bt.setOnClickListener(this);
        bt2.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.apj_tv) {
            ViewTools.resetRelativeLayoutChildSize(img, 0.5f, 0.5f, 0.0f, 0.0f);
            Toast.makeText(this, "改變 img", Toast.LENGTH_LONG).show();

        } else if (i == R.id.apj_iv) {
            ViewTools.resetRelativeLayoutChildSize(tv, 0.5f, 0.5f, 0.1f, 0.1f);
            Toast.makeText(this, "改變 tv", Toast.LENGTH_LONG).show();

        } else if (i == R.id.apj_bt) {
            ViewTools.resetLinearLayoutChildSize(linearLayout, 0.5f, 0.5f, 0.3f, 0.1f);
            Toast.makeText(this, "改變 linearLayout", Toast.LENGTH_LONG).show();

        } else if (i == R.id.apj_bt2) {
            ViewTools.resetRelativeLayoutChildSize(img, 0.2f, 0.2f, 0.5f, 0.1f);
            ViewTools.resetRelativeLayoutChildSize(tv, 0.3f, 0.2f, 0.45f, 0.1f);
            ViewTools.resetLinearLayoutChildSize(linearLayout, 0.8f, 0.4f, 0.01f, 0.1f);
            Toast.makeText(this, "重置所有", Toast.LENGTH_LONG).show();
        }
       /* switch (v.getId()) {//library的资源id并不是唯一或者静态常量(也就是说可能会变)不能这样使用
            case R.id.test2_tv:
                ViewTools.resetRelativeLayoutChildSize(img,0.5f,0.5f,0.0f,0.0f);
                Toast.makeText(this,"改變 img",Toast.LENGTH_LONG).show();
                break;
            case R.id.test2_iv:
                ViewTools.resetRelativeLayoutChildSize(tv,0.5f,0.5f,0.1f,0.1f);
                Toast.makeText(this,"改變 tv",Toast.LENGTH_LONG).show();
                break;
            case R.id.test2_bt:
                ViewTools.resetLinearLayoutChildSize(linearLayout,0.5f,0.5f,0.3f,0.1f);
                Toast.makeText(this,"改變 linearLayout",Toast.LENGTH_LONG).show();
                break;
            case R.id.test2_bt2:
                ViewTools.resetRelativeLayoutChildSize(img,0.2f,0.2f,0.5f,0.1f);
                ViewTools.resetRelativeLayoutChildSize(tv,0.3f,0.2f,0.45f,0.1f);
                ViewTools.resetLinearLayoutChildSize(linearLayout,0.8f,0.4f,0.01f,0.1f);
                Toast.makeText(this,"重置所有",Toast.LENGTH_LONG).show();
                break;
        }*/
    }
}
