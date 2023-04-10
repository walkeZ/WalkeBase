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
 * @desc
 */
public class VisualizerFFTView extends View {
    private byte[] mBytes;
    private float[] mPoints;
    private Rect mRect = new Rect();

    private Paint mForePaint = new Paint();
    private int mSpectrumNum = 108; // 观察柱之间的间隙
//    private int mSpectrumNum = 26;

    public VisualizerFFTView(Context context) {
        this(context, null);
    }

    public VisualizerFFTView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisualizerFFTView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mBytes = null;
        mForePaint.setStrokeWidth(8f);
        mForePaint.setAntiAlias(true);
        mForePaint.setColor(Color.rgb(0, 128, 255));
    }

    public void updateVisualizer(byte[] fft) {
        byte[] model = new byte[fft.length / 2 + 1];
        // 快速傅里叶变换返回的是512个复数，下标为单是实数，下标为双的是虚数，对每一组复数进行计算即为最终可绘制的数据：
        model[0] = (byte) Math.abs(fft[0]);
        for (int i = 2, j = 1; j < mSpectrumNum; ) {
            model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
            i += 2;
            j++;
        }
        mBytes = model;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mBytes == null) {
            return;
        }

        if (mPoints == null || mPoints.length < mBytes.length * 4) {
            mPoints = new float[mBytes.length * 4]; // 4个点一个音量柱
        }

        mRect.set(0, 0, getWidth(), getHeight());

        //绘制频谱  
        final int baseX = mRect.width() / mSpectrumNum;
        final int height = mRect.height();
        mForePaint.setTextSize(18);
        for (int i = 0; i < mSpectrumNum; i++) {
            int intValue =  mBytes[i] & 255;
            final int xi = baseX * i + baseX / 2;
            mPoints[i * 4] = xi;
            mPoints[i * 4 + 1] = height;
            mPoints[i * 4 + 2] = xi;
            int top = height - intValue;
            mPoints[i * 4 + 3] = top;
            canvas.drawText("" + intValue, xi - 10, top - 5, mForePaint);
        }
        canvas.drawLines(mPoints, mForePaint);
    }
}
