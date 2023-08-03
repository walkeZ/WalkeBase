package walke.demolibrary.audio.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import androidx.annotation.ColorInt;

/**
 * 项目名称：AudioWaveShow
 * 类描述：
 * 创建人：TCX
 * 创建时间：2017/11/20 11:03
 * 修改人：TCX
 * 修改备注：
 */

public class SimpleWaveformRenderer implements WaveformRenderer {

    private static final int Y_FACTOR = 0xFF; // 2的8次方 = 256
    private static final float HALF_FACTOR = 0.5f;
    @ColorInt
    private final int mBackgroundColor;
    private final Paint mForegroundPaint;
    private final Path mWaveformPath;
    private final Paint mTestPaint;

    public SimpleWaveformRenderer(@ColorInt int backgroundColor, Paint foregroundPaint, Path waveformPath) {
        mBackgroundColor = backgroundColor;
        mForegroundPaint = foregroundPaint;
        mWaveformPath = waveformPath;


        mTestPaint = new Paint();//初始化画笔工具
        mTestPaint.setAntiAlias(true);//抗锯齿
        mTestPaint.setColor(Color.MAGENTA);//画笔颜色

        mTestPaint.setStrokeJoin(Paint.Join.BEVEL); //画笔圆角
        mTestPaint.setStrokeCap(Paint.Cap.SQUARE); //画笔圆角-ROUND、SQUARE方形
        mTestPaint.setStrokeWidth(30); //设置画笔宽度
    }

    public static SimpleWaveformRenderer newInstance(@ColorInt int backgroundColor, @ColorInt int foregroundColour) {
        Paint paint = new Paint();
        paint.setColor(foregroundColour);
        paint.setAntiAlias(true); // 抗锯齿
        paint.setStrokeWidth(8.0f); // 设置宽度
        paint.setStyle(Paint.Style.STROKE); // 填充
        Path waveformPath = new Path();
        return new SimpleWaveformRenderer(backgroundColor, paint, waveformPath);
    }

    @Override
    public void render(Canvas canvas, byte[] waveform) {
        canvas.drawColor(mBackgroundColor);
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        mWaveformPath.reset();
        // 没有数据
        if (waveform != null) {
            // 绘制波形
            renderWaveform(waveform, width, height);
        } else {
            // 绘制直线
            renderBlank(width, height);
        }
        canvas.drawPath(mWaveformPath, mForegroundPaint);
        drawVolume(canvas, waveform);
        drawVolume2(canvas, waveform);
    }

    /**
     * 画声量
     *
     * @param canvas
     * @param waveform
     */
    private void drawVolume(Canvas canvas, byte[] waveform) {
        if (waveform == null) return;
        float width = canvas.getWidth();
        float height = canvas.getHeight();
//        int sum = 0;
//        for (byte b : waveform) {
//            // bite & 255
//            int v = b & 255;
//            sum += v;
//        }
//        if (sum > 100000) {
//            sum = sum - 100000;
//        }
//        sum = sum / 300;
//        AppLog.w("drawVolume: --> " + sum);
//        canvas.drawLine(50, height, 50, height - sum, mTestPaint);//绘制频谱块

        int count = 0;
        for (byte b : waveform) {
            // bite & 255
            if (b > 0) count++;
        }
        if (count > 200) {
            count -= 200;
        }
        count = count / 2;
        mTestPaint.setStrokeCap(Paint.Cap.BUTT);
//        AppLog.w("drawVolume: --> count " + count);
        canvas.drawLine(50, height - 20, 50, height - count, mTestPaint);//绘制频谱块
    }

    /**
     * 画声量
     *
     * @param canvas
     * @param waveform
     */
    private void drawVolume2(Canvas canvas, byte[] waveform) {
        if (waveform == null) return;
        float amp = (float) computedbAmp(waveform);
        float height = canvas.getHeight();
//        AppLog.w("drawVolume: --> count " + amp);
        // 设置笔刷的图形样式
        mTestPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(150, height - 30, 150, height + amp, mTestPaint);//绘制频谱块
    }

    /**
     * http://www.360doc.com/content/19/1027/22/13328254_869435992.shtml
     *
     * @param audioData
     * @return
     */
    public double computedbAmp(byte[] audioData) {
        //System.out.println("::::: audioData :::::" audioData);
        double amplitude = 0;
        for (int i = 0; i < audioData.length / 2; i++) {
            double y = (audioData[i * 2] | audioData[i * 2 + 1] << 8) / 32768.0;
            // depending on your endianness:
            // double y = (audioData[i*2]<<8 | audioData[i*2 1]) / 32768.0;
//            amplitude = Math.abs(y);
            amplitude = y * y;
        }
        double rms = Math.sqrt(amplitude / audioData.length / 2);
        double dbAmp = 20.0 * Math.log10(rms);
        return dbAmp;
    }


    private void renderWaveform(byte[] waveform, float width, float height) {
        float xIncrement = width / (float) (waveform.length); // 水平块数
        float yIncrement = height / Y_FACTOR; // 竖直块数
        int halfHeight = (int) (height * HALF_FACTOR); // 居中位置


        mWaveformPath.moveTo(0, halfHeight);

        for (int i = 1; i < waveform.length; ++i) {
            float yPosition = waveform[i] > 0 ?
                    height - (yIncrement * waveform[i]) : -(yIncrement * waveform[i]);
            mWaveformPath.lineTo(xIncrement * i, yPosition);
        }
        mWaveformPath.lineTo(width, halfHeight); // 最后的点, 水平居中
    }


    // 居中画一条直线
    private void renderBlank(float width, float height) {
        int y = (int) (height * HALF_FACTOR);
        mWaveformPath.moveTo(0, y);
        mWaveformPath.lineTo(width, y);
    }
}
