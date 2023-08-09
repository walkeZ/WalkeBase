package walke.demolibrary.pinpu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;

import walke.demolibrary.AppLog;

/**
 * 频谱渐变
 */
public class VisualizerGradientView2 extends View {

    protected Paint mGradientPaint = null; // 画笔

    protected int[] mData = new int[100];//音量柱 数组
    private int mWidth;
    private int mHeight;

    public VisualizerGradientView2(Context context) {
        this(context, null);
    }

    public VisualizerGradientView2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisualizerGradientView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mGradientPaint = new Paint();//初始化画笔工具
        mGradientPaint.setAntiAlias(true);//抗锯齿
        mGradientPaint.setColor(Color.MAGENTA);//画笔颜色
        mWidth = getWidth();
        mHeight = getHeight();
        mGradientPaint.setStrokeJoin(Paint.Join.BEVEL); //画笔圆角
        mGradientPaint.setStrokeCap(Paint.Cap.ROUND); //画笔圆角-ROUND、SQUARE方形
        mGradientPaint.setStrokeWidth(8); //设置画笔宽度
    }

    public void bindData(int[] formFft) {
        AppLog.w("bindData size " + formFft.length + ", " + Arrays.toString(formFft));
        mData = formFft;
        invalidate();
    }

    //执行 Layout 操作
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        float[] position = {0f, 0.6f, 1.0f};
        // 在能有效获取到getMeasuredWidth()后就可以设置了
        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
        mGradientPaint.setShader(linearGradient);
    }

    @Override
    public void onDraw(Canvas canvas) {
        int intervalWidth = 11;
        int x = 0;
        int startY = getHeight() - 20;
        for (int i = 0; i < mData.length; i++) {
            x = i * intervalWidth;
            int stopY = startY - mData[i];
            canvas.drawLine(x, startY, x, stopY, mGradientPaint); // 绘制频谱块
            canvas.drawText("" + mData[i], x - 10, stopY - 5, mGradientPaint);
        }

    }
}

