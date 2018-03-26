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
import android.util.AttributeSet;
import android.widget.ImageView;

import walke.widget.R;


/**
 * 吾日三省吾身：看脸，看秤，看余额。
 * Created by lanso on 2016/12/1.
 * 圆角图片
 */
public class RoundImageView2 extends ImageView {

    private static final int NORMAL=0;
    private static final int CIRCLE=1;
    private static final int RADIUS=2;
    private int imgType =NORMAL;
    private Paint paint;
    private int mBorderWidth = 2;//边框宽度
    private int mBorderRadius = 2;//圆角宽度
    private Paint paint2;
    private int mBorderColor;

    public RoundImageView2(Context context) {
        this(context, null);
    }

    public RoundImageView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        for (int i = 0; i < a.getIndexCount(); i++) {
            int index = a.getIndex(i);
            if (index==R.styleable.RoundImageView_imgType){
                imgType = a.getInt(index, NORMAL);
            }else if (index==R.styleable.RoundImageView_borderColor)
                mBorderColor = a.getColor(index, Color.GRAY);
            else if (index==R.styleable.RoundImageView_borderRadius)
                mBorderRadius = a.getInt(index,0);
            else if (index==R.styleable.RoundImageView_border_Width)
                mBorderWidth = (int) a.getDimension(index,0f);
        }
        a.recycle();//资源回收，必要


        float density = context.getResources().getDisplayMetrics().density;
//        mBorderWidth = (int) (mBorderWidth * density);
        mBorderRadius = (int) (mBorderRadius * density);

        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);

    }

    @Override
    public void draw(Canvas canvas) {
        switch (imgType){
            case CIRCLE://圆图片

                break;
            case RADIUS://圆角图片
                Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(bitmap);
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
//        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas2 = new Canvas(bitmap);
//        super.draw(canvas2);
//        drawLiftUp(canvas2);
//        drawRightUp(canvas2);
//        drawLiftDown(canvas2);
//        drawRightDown(canvas2);
//        canvas.drawBitmap(bitmap, 0, 0, paint2);
//        bitmap.recycle();

    }


    private void drawLiftUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, mBorderRadius);
        path.lineTo(0, 0);
        path.lineTo(mBorderWidth, 0);
        int right = mBorderWidth * 2;
        int bottom = mBorderRadius * 2;
        path.arcTo(new RectF(0, 0, right, bottom), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLiftDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, getHeight() - mBorderRadius);
        path.lineTo(0, getHeight());
        path.lineTo(mBorderWidth, getHeight());
        int top = getHeight() - mBorderRadius * 2;
        int right = 0 + mBorderWidth * 2;
        int bottom = getHeight();
        path.arcTo(new RectF(0, top, right, bottom), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }


    private void drawRightDown(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth() - mBorderWidth, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - mBorderRadius);
        path.arcTo(new RectF(getWidth() - mBorderWidth * 2, getHeight() - mBorderRadius * 2, getWidth(), getHeight()), 0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        Path path = new Path();
        path.moveTo(getWidth(), mBorderRadius);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - mBorderWidth, 0);
        path.arcTo(new RectF(getWidth() - mBorderWidth * 2, 0, getWidth(), 0 + mBorderRadius * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }


}
