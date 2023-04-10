package walke.demolibrary.movedsp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author walker
 * @date 2023/4/4
 * @desc 定制显示频谱fft的自定义view，分频段的获取频谱的和
 */
public class VisualizerFFTCustomView extends View {
    private float[] mPoints;
    private Rect mRect = new Rect();

    private Paint mForePaint = new Paint();
    private int[] mPdAmp;
    //    private int mSpectrumNum = 26;

    public VisualizerFFTCustomView(Context context) {
        this(context, null);
    }

    public VisualizerFFTCustomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisualizerFFTCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mForePaint.setStrokeWidth(8f);
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(Color.rgb(0, 128, 255));
    }

    public void updateVisualizer(int[] pdAmp) {
        mPdAmp = pdAmp;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPdAmp == null) return;

        if (mPoints == null || mPoints.length < mPdAmp.length * 4) {
            mPoints = new float[mPdAmp.length * 4]; // 4个点一个音量柱
        }
        mRect.set(0, 0, getWidth(), getHeight());

        //绘制频谱
        final int baseX = mRect.width() / mPdAmp.length;
        final int height = mRect.height();
        mForePaint.setTextSize(20);
        for (int p = 0; p < mPdAmp.length; p++) {
            final int xi = baseX * p + baseX / 2;
            mPoints[p * 4] = xi;
            mPoints[p * 4 + 1] = height;
            mPoints[p * 4 + 2] = xi;
            int sumDuan = mPdAmp[p];
            int top = height - sumDuan;
            mPoints[p * 4 + 3] = top;
            canvas.drawText("" + sumDuan, xi - 10, top - 5, mForePaint);
        }
        canvas.drawLines(mPoints, mForePaint);
    }
}
