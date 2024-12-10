package walke.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

/**
 * Created by Walke.Z
 * on 2024/12/10. 20.
 * email：1126648815@qq.com
 */
public class HorizontalProgressView extends LinearLayout {

    private final int ALL_TEXT = 0;
    private final int NUMBER_BIG = 1;
    private View p2LL;
    private TextView p2TvTips;
    private TextView p2TvNumber;
    private TextView p2TvUnit;
    private ProgressBar pbBg;
    private ImageView img;
    private TextView p1Tv;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public HorizontalProgressView(Context context) {
        this(context,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public HorizontalProgressView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public HorizontalProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.tn_view_horizontal_pb,this);
        img =  findViewById(R.id.tvhp_icon);
        pbBg = findViewById(R.id.tvhp_pb);
        p1Tv = findViewById(R.id.tvhp_p1Tv);
        p2LL = findViewById(R.id.tvhp_p2LL);
        p2TvTips = findViewById(R.id.tvhp_p2TvTips);
        p2TvNumber = findViewById(R.id.tvhp_p2TvNumber);
        p2TvUnit = findViewById(R.id.tvhp_p2TvUnit);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.HorizontalProgressView_icon2) {
                int resourceId = a.getResourceId(attr, R.mipmap.ic_launcher);
                img.setImageResource(resourceId);
            } else if (attr == R.styleable.HorizontalProgressView_android_text) {
                String str = a.getString(attr);
                if (!TextUtils.isEmpty(str))
                    p2TvTips.setText(str);
            } else if (attr == R.styleable.HorizontalProgressView_pb_type) {
                int type = a.getInt(attr, ALL_TEXT);
                if (type == ALL_TEXT) {
                    p2LL.setVisibility(GONE);
                    p1Tv.setVisibility(VISIBLE);
                } else {
                    p2LL.setVisibility(VISIBLE);
                    p1Tv.setVisibility(GONE);
                }
            } else if (attr == R.styleable.HorizontalProgressView_android_progress) {
                int progress = a.getInt(attr, 0);
                pbBg.setProgress(progress);
            }
        }
        a.recycle();

    }

    public ImageView getImg() {
        return img;
    }

    public TextView getP1Tv() {
        return p1Tv;
    }

    public ProgressBar getPbBg() {
        return pbBg;
    }

    public TextView getP2TvNumber() {
        return p2TvNumber;
    }

    public TextView getP2TvTips() {
        return p2TvTips;
    }

    public TextView getP2TvUnit() {
        return p2TvUnit;
    }
}
