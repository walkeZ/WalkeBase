package walke.demolibrary.movedsp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author walker
 * @date 2023/4/4
 * @desc 定制显示频谱fft的自定义view，分频段的获取频谱的和
 */
public class CustomVisualizerFFTView extends View {

    /**
     * 频段，0~oinDuan[1]...
     */
    private int[] pinDuan = new int[]{240, 500, 2000, 8000};
    private byte[] mBytes;
    private float[] mPoints;
    private Rect mRect = new Rect();

    private Paint mForePaint = new Paint();
    private int mSpectrumNum = 108; // 观察柱之间的间隙
//    private int mSpectrumNum = 26;

    public CustomVisualizerFFTView(Context context) {
        this(context, null);
    }

    public CustomVisualizerFFTView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomVisualizerFFTView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
//        final int baseX = mRect.width() / mSpectrumNum;
        final int baseX = mRect.width() / pinDuan.length;
        final int height = mRect.height();
        // 一个柱的频段：  samplingRate / (mCaptureSize / 2);
        int interval = 44100 / 1024 * 2;

        for (int p = 0; p < pinDuan.length; p++) {
            final int xi = baseX * p + baseX / 2;
            mPoints[p * 4] = xi;
            mPoints[p * 4 + 1] = height;

            mPoints[p * 4 + 2] = xi;
            // 频段
            int sumDuan = 0;
            int duanNum = 0;
            List<Integer> integerList = new ArrayList<>();
            for (int i = 0; i < mBytes.length; i++) {
                if (mBytes[i] < 0) mBytes[i] = 127;
                if (p == 0) {
                    if (interval * i < pinDuan[p]) {
                        sumDuan += mBytes[i];
                        duanNum++;
                        integerList.add(i);
                    } else {
                        break;
                    }
                } else {
                    if (interval * i < pinDuan[p - 1]) {
                        continue;
                    }
                    if (interval * i < pinDuan[p]) {
                        sumDuan += mBytes[i];
                        duanNum++;
                        integerList.add(i);
                    } else {
                        break;
                    }
                }
            }
//            sumDuan = (int) (sumDuan / 3.0);
            Log.i("ArHui", "onDraw: --> sum =" + sumDuan + ", duan " + p + ", duanNum = " + duanNum + ", duan i " + Arrays.toString(integerList.toArray()));
            sumDuan = sumDuan / duanNum;
            sumDuan = sumDuan * 2;
            int top = height - sumDuan;
            mPoints[p * 4 + 3] = top;
        }

        canvas.drawLines(mPoints, mForePaint);
    }
}
