package walke.widget.img;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import walke.widget.R;


/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2016/12/1.
 * 圆角图片
 */
public class RoundImageView4 extends ImageView {

    private static final int NORMAL = 0;
    private static final int CIRCLE = 1;
    private static final int RADIUS = 2;
    private int imgType = NORMAL;
    private int mBorderWidth = 2;//边框宽度
    private int mBorderRadius = 2;//圆角宽度
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    private int mBorderColor;
    private int mWidth;
    private int mHeight;

    public RoundImageView4(Context context) {
        this(context, null);
    }

    public RoundImageView4(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView4(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int index = a.getIndex(i);
            if (index == R.styleable.RoundImageView_imgType) {
                imgType = a.getInt(index, NORMAL);
            } else if (index == R.styleable.RoundImageView_borderColor)
                mBorderColor = a.getColor(index, Color.GRAY);
            else if (index == R.styleable.RoundImageView_borderRadius)
                mBorderRadius = a.getInt(index, 0);
            else if (index == R.styleable.RoundImageView_border_Width)
                mBorderWidth = (int) a.getDimension(index, 0f);
        }
        a.recycle();//资源回收，必要


        float density = context.getResources().getDisplayMetrics().density;
//        mBorderWidth = (int) (mBorderWidth * density);
        mBorderRadius = (int) (mBorderRadius * density);

        paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setAntiAlias(true);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
//        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));

        paint2 = new Paint();
        paint2.setXfermode(null);
//        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        paint3 = new Paint();
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setAntiAlias(true);
        paint3.setStrokeWidth(mBorderWidth);
        paint3.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        paint3.setColor(mBorderColor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;//控件宽度
        mHeight = h;//控件高度
    }

    @Override
    public void draw(Canvas canvas) {
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(bitmap);
        switch (imgType) {
            case CIRCLE://圆图片
                super.draw(canvas2);
                eraseAround(canvas2);
                canvas.drawBitmap(bitmap, 0, 0, paint2);
                bitmap.recycle();
                break;
            case RADIUS://圆角图片
                super.draw(canvas2);
                drawLiftUp(canvas2);
                drawRightUp(canvas2);
                drawLiftDown(canvas2);
                drawRightDown(canvas2);
                canvas.drawBitmap(bitmap, 0, 0, paint2);
                bitmap.recycle();
                break;
            default:
                super.draw(canvas);
        }

    }

    /**
     * @param canvas 参考：https://blog.csdn.net/zhuhai__yizhi/article/details/43412461
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void eraseAround(Canvas canvas) {
        Path path = new Path();
        //第二象限[西北方向]
        path.moveTo(0, mHeight / 2);//第一点：控件左边中点坐标
        path.lineTo(0, 0);//第二点：控件左上角坐标
        path.lineTo(mWidth, 0);//第三点：控件顶边中心点坐标
        path.arcTo(new RectF(0, 0, mWidth, mHeight), -90, -90);
        path.close();
        canvas.drawPath(path, paint1);//画轨迹

        //第一象限
//        path.moveTo(mWidth/2, 0);//第1点：控件顶边中心点坐标
        path.moveTo(mWidth, mHeight / 2);//第1点：控件顶边中心点坐标
        path.lineTo(mWidth, 0);//第2点：控件右上角坐标
        path.lineTo(mWidth / 2, 0);//第三点：控件顶边中心点坐标
//        path.lineTo(mWidth, mHeight/2);//第三点：控件顶边中心点坐标
        path.arcTo(new RectF(0, 0, mWidth, mHeight), -90, 90);
//        path.arcTo(new RectF(0, 0, mWidth, mHeight), 90, 0);//第一象限  角度：90, 0，逆时针，所以,第一点是区域的右下角坐标
        path.close();
        canvas.drawPath(path, paint1);


        //第三象限
//        path.moveTo(0, mHeight/2);//第1点：控件左边中心点坐标       ---成功
//        path.lineTo(0, mHeight);//第2点：控件左下角坐标
//        path.lineTo(mWidth/2, mHeight);//第3点：控件底边中心点坐标  ---成功
//        path.arcTo(new RectF(0, 0, mWidth, mHeight), 90, 90);//  ---成功

        path.moveTo(mWidth / 2, mHeight);//第1点：控件底边中心点坐标
        path.lineTo(0, mHeight);//第2点：控件左下角坐标
        path.lineTo(0, mHeight / 2);//第3点：控件顶左边边中心点坐标
        path.arcTo(new RectF(0, 0, mWidth, mHeight), 180, -90);// 发现点的坐标顺序与顺逆时针有关："相反"
        //startAngle：开始角度，sweepAngle滑过的角度，sweepAngle是正数--按顺时针方向旋转，是负数--逆时针方向旋转
        path.close();
        canvas.drawPath(path, paint1);

        path.moveTo(mWidth, mHeight / 2);//第1点：控件底边中心点坐标
        path.lineTo(mWidth, mHeight);//第2点：控件左下角坐标
        path.lineTo(mWidth / 2, mHeight);//第3点：控件顶左边边中心点坐标
        path.arcTo(new RectF(0, 0, mWidth, mHeight), 90, -90);// 发现点的坐标顺序与顺逆时针有关："相反"
        //startAngle：开始角度，sweepAngle滑过的角度，sweepAngle是正数--按顺时针方向旋转，是负数--逆时针方向旋转
        path.close();
        canvas.drawPath(path, paint1);



//        int radius = mWidth > mHeight ? mHeight / 2 : mWidth / 2;
//        canvas.drawCircle(mWidth / 2, mHeight / 2, radius,paint1);
        ViewGroup.LayoutParams lp = getLayoutParams();
        int width = lp.width;
        int height = lp.height;
        int paddingStart = getPaddingStart();
        int paddingEnd = getPaddingEnd();
        int i = mBorderWidth/2;
        int paddingTop = getPaddingTop()+ i;
        int paddingLeft = getPaddingLeft()+ i;
        int paddingRight = getPaddingRight()+ i;
        int paddingBottom = getPaddingBottom()+ i;
        //之前用 paint1 出现奇怪现象：颜色设置不起效,原因估计：paint绘图的PorterDuff.Mode.XX原因，用一个新的paint即可
        canvas.drawOval(new RectF(paddingLeft, paddingTop, mWidth- paddingRight, mHeight- paddingBottom),paint3);


    }

    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, mBorderRadius);
        path.lineTo(0, 0);
        path.lineTo(mBorderRadius, 0);
        int right = mBorderRadius * 2;
        int bottom = mBorderRadius * 2;
        path.arcTo(new RectF(0, 0, right, bottom), -90, -90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - mBorderRadius);
        path.lineTo(0, getHeight());
        path.lineTo(mBorderRadius, getHeight());
        int top = getHeight() - mBorderRadius * 2;
        int right = 0 + mBorderRadius * 2;
        int bottom = getHeight();
        path.arcTo(new RectF(0, top, right, bottom), 90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }


    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - mBorderRadius, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - mBorderRadius);
        path.arcTo(new RectF(getWidth() - mBorderRadius * 2, getHeight() - mBorderRadius * 2, getWidth(), getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), mBorderRadius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - mBorderRadius, 0);
        path.arcTo(new RectF(getWidth() - mBorderRadius * 2, 0, getWidth(), 0 + mBorderRadius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint1);
    }


}
