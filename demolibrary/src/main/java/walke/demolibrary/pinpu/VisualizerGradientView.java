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

/**
 * 频谱渐变
 */
public class VisualizerGradientView extends View {

    private static final int DN_W = 470;//view宽度与单个音频块占比 - 正常480 需微调
    private static final int DN_H = 360;//view高度与单个音频块占比
    private static final int DN_SL = 8;//单个音频块宽度
    private static final int DN_SW = 3;//单个音频块高度

    private int hgap = 0;
    private int vgap = 0;
    private int levelConvert = 255 / MAX_LEVEL; // byte除以最大强度级别，强度级别转换比率 // 音量强度级别转换
    private float strokeWidth = 0;
    private float strokeLength = 0;

    protected final static int MAX_LEVEL = 26; // 音量柱·音频块 - 最大个数

    protected final static int CYLINDER_NUM = 64; // 音量柱 - 最大个数

    protected Paint mPaint = null; // 画笔

    protected Paint mGradientPaint = null; // 画笔

    protected byte[] mData = new byte[CYLINDER_NUM];//音量柱 数组

    public VisualizerGradientView(Context context) {
        this(context, null);
    }

    public VisualizerGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VisualizerGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //构造函数初始化画笔
        mPaint = new Paint();//初始化画笔工具
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setColor(Color.BLUE);//画笔颜色

        mPaint.setStrokeJoin(Paint.Join.ROUND); //频块圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); //频块圆角

//        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
//        float[] position = {0f, 0.6f, 1.0f};
//        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
//        mPaint.setShader(linearGradient);


        mGradientPaint = new Paint();//初始化画笔工具
        mGradientPaint.setAntiAlias(true);//抗锯齿
        mGradientPaint.setColor(Color.MAGENTA);//画笔颜色

        mGradientPaint.setStrokeJoin(Paint.Join.BEVEL); //画笔圆角
        mGradientPaint.setStrokeCap(Paint.Cap.ROUND); //画笔圆角-ROUND、SQUARE方形
        mGradientPaint.setStrokeWidth(12); //设置画笔宽度
    }

    public void setFftData(byte[] fft) {
        // http://events.jianshu.io/p/c95bb166fb28
        // https://xie.infoq.cn/article/386cc569321fbf0a0f0dbe7e8
        //1.快速傅里叶变换返回的是512个复数，下标为单是实数，下标为双的是虚数，对每一组复数进行计算即为最终可绘制的数据：

        byte[] model = new byte[fft.length / 2 + 1]; // 512个复数 加上第一个
        model[0] = (byte) Math.abs(fft[0]); // 第一个。
        int j = 1;
        // 2~513个数值
        for (int i = 2; i < fft.length; ) {
            // Math.hypot(a,b); -> （a平方+b平方）的开方
            model[j] = (byte) Math.hypot(fft[i], fft[i + 1]);
            i += 2;
            j++;
        }
        // 能量柱，CYLINDER_NUM 个
        for (int i = 0; i < CYLINDER_NUM; i++) {
            if (i >= model.length) break;
            final byte a = (byte) (Math.abs(model[i]) / levelConvert);
            // mData[i] 音量柱强度数据组赋值
            final byte b = mData[i];
            if (a > b) {
                // 该音量柱最高/当前强度,升高
                mData[i] = a;
            } else {
//                if (b > a) {
//                    mData[i] = a; // 先快速降低, 会占了 慢慢减1，飘落的效果
//                } else
                if (b > 0) {
                    // 慢慢减1，飘落的效果
                    mData[i]--;
                }
            }
        }
        invalidate();
    }

    //执行 Layout 操作
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        float w, h, xr, yr;
        w = right - left;
        h = bottom - top;
        xr = w / (float) DN_W;
        yr = h / (float) DN_H;

        strokeWidth = DN_SW * yr;
        strokeLength = DN_SL * xr;
        hgap = (int) ((w - strokeLength * CYLINDER_NUM) / (CYLINDER_NUM + 1));
        vgap = (int) (h / (MAX_LEVEL + 2));//频谱块高度

        mPaint.setStrokeWidth(strokeWidth); //设置频谱块宽度


        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
        float[] position = {0f, 0.6f, 1.0f};
        // 在能有效获取到getMeasuredWidth()后就可以设置了
        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
        mGradientPaint.setShader(linearGradient);
    }

    //绘制频谱块和倒影
    protected void drawCylinder(Canvas canvas, float x, byte value) {
        if (value == 0) {
            value = 1;
        }//最少有一个频谱块
        float y = 0;
        float y1 = 0;
        for (int i = 0; i < value; i++) { //每个能量柱绘制value个能量块
            y = (getHeight() / 2 - i * vgap - vgap);//计算y轴坐标
            y1 = (getHeight() / 2 + i * vgap + vgap);
            //绘制频谱块
            mPaint.setColor(Color.BLUE);//画笔颜色
            canvas.drawLine(x, y, (x + strokeLength), y, mPaint);//绘制频谱块

            //绘制音量柱倒影
            if (i <= 6 && value > 0) {
                mPaint.setColor(Color.BLUE);//画笔颜色
                mPaint.setAlpha(100 - (100 / 6 * i));//倒影颜色
                canvas.drawLine(x, y1, (x + strokeLength), y1, mPaint);//绘制频谱块
            }
        }

//        canvas.drawLine(x, getHeight(), x, y, mGradientPaint); // 绘制频谱块， 有效
        canvas.drawLine(x, y1, x, y, mGradientPaint); // 绘制频谱块
    }

    @Override
    public void onDraw(Canvas canvas) {
//        int[] colors = {Color.RED, Color.GREEN, Color.BLUE};
//        float[] position = {0f, 0.6f, 1.0f};
//        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), 0, colors, position, Shader.TileMode.CLAMP);
//        mPaint.setShader(linearGradient);
        // canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
//        canvas.drawText("Android绘图小糊涂", 0, getMeasuredHeight() / 2, mPaint);
        int height = getHeight();

        int j = -4;
        // 前一半的音量柱
        for (int i = 0; i < CYLINDER_NUM / 2 - 4; i++) { //绘制25个能量柱
            //绘制 CYLINDER_NUM 个能量柱
            float startX = strokeWidth / 2 + hgap + i * (hgap + strokeLength);
            drawCylinder(canvas, startX, mData[i]);
//            canvas.drawLine(startX, height - 20, startX, mData[i], mGradientPaint);//绘制频谱块
        }
        // 后一半声量柱
        for (int i = CYLINDER_NUM; i >= CYLINDER_NUM / 2 - 4; i--) {
            j++;
            drawCylinder(canvas, strokeWidth / 2 + hgap + (CYLINDER_NUM / 2 + j - 1) * (hgap + strokeLength), mData[i - 1]);
        }

//        canvas.drawLine(150, height - 30, 150, height + mData[i], mGradientPaint);//绘制频谱块
    }
}

