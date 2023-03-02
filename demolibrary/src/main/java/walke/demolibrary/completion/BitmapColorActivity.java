package walke.demolibrary.completion;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * @author walker
 * @date 2023/3/2 Android编程之图片颜色处理方法
 * @description https://www.zhangshengrong.com/p/nDa9odrXjb/
 */
public class BitmapColorActivity extends TitleActivity {
    private SeekBar sb1, sb2, sb3, sb4, sb5;
    private ImageView iv;
    private Bitmap bitmap, updateBitmap;
    private Canvas canvas;
    private Paint paint;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_bitmap_color;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        iv = findViewById(R.id.bitmapColor_iv);
        sb1 = findViewById(R.id.bitmapColor_sb1);
        sb2 = findViewById(R.id.bitmapColor_sb2);
        sb3 = findViewById(R.id.bitmapColor_sb3);
        sb4 = findViewById(R.id.bitmapColor_sb4);
        sb5 = findViewById(R.id.bitmapColor_sb5);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.nvshen);
        updateBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), bitmap.getConfig());
        canvas = new Canvas(updateBitmap);
        paint = new Paint();


    }

    @Override
    protected void initData() {
        final ColorMatrix cm = new ColorMatrix();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        final Matrix matrix = new Matrix();
        canvas.drawBitmap(bitmap, matrix, paint);
        iv.setImageBitmap(updateBitmap);
        /**
         * RGB三原色 红色值的设置
         */
        sb1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cm.set(new float[]{progress / 128f, 0, 0, 0, 0,// 红色值
                        0, 1, 0, 0, 0,// 绿色值
                        0, 0, 1, 0, 0,// 蓝色值
                        0, 0, 0, 10f, 0 // 透明度
                });
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(bitmap, matrix, paint);
                iv.setImageBitmap(updateBitmap);
            }
        });
        /**
         * RGB三原色 绿色值的设置
         */
        sb2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cm.set(new float[]{1, 0, 0, 0, 0,// 红色值
                        0, progress / 128f, 0, 0, 0,// 绿色值
                        0, 0, 1, 0, 0,// 蓝色值
                        0, 0, 0, 0.5f, 0 // 透明度
                });
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(bitmap, matrix, paint);
                iv.setImageBitmap(updateBitmap);
            }
        });
        /**
         * RGB三原色 蓝色值的设置
         */
        sb3.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cm.set(new float[]{1, 0, 0, 0, 0,// 红色值
                        0, 1, 0, 0, 0,// 绿色值
                        0, 0, progress / 128f, 0, 0,// 蓝色值
                        0, 0, 0, 1, 0 // 透明度
                });
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(bitmap, matrix, paint);
                iv.setImageBitmap(updateBitmap);
            }
        });
        /**
         * RGB三原色 三个值都改变为设置饱和度（亮度）
         */
        sb4.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                cm.set(new float[]{progress / 128f, 0, 0, 0, 0,// 红色值
                        0, progress / 128f, 0, 0, 0,// 绿色值
                        0, 0, progress / 128f, 0, 0,// 蓝色值
                        0, 0, 0, 1, 0 // 透明度
                });
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(bitmap, matrix, paint);
                iv.setImageBitmap(updateBitmap);
            }
        });
        /**
         * RGB三原色 设置透明度
         */
        sb5.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float alpha = progress / 128f;
                Log.i("ArHui", "onStopTrackingTouch: -----> alpha = " + alpha);
                cm.set(new float[]{1, 0, 0, 0, 0,// 红色值
                        0, 1, 0, 0, 0,// 绿色值
                        0, 0, 1, 0, 0,// 蓝色值
                        0, 0, 0, alpha, 0 // 透明度
                });
                paint.setColorFilter(new ColorMatrixColorFilter(cm));
                canvas.drawBitmap(bitmap, matrix, paint);
                iv.setImageBitmap(updateBitmap);
            }
        });
    }

    /**
     * https://www.zhangshengrong.com/p/q0XpoekNKZ/
     * @param view
     */
    public void heiBai(View view) {
        iv.setImageBitmap(convertToBlackWhite(BitmapFactory.decodeResource(getResources(), R.mipmap.nvshen)));
    }

    public void corner(View view) {
        iv.setImageBitmap(convertToRoundedCorner(BitmapFactory.decodeResource(getResources(), R.mipmap.nvshen), 60));
    }

    public void blur(View view) {
        iv.setImageBitmap(convertToBlur(BitmapFactory.decodeResource(getResources(), R.mipmap.nvshen)));
    }

    /**
     * 将彩色图转换为黑白图
     *
     * @param bmp 位图
     * @return 返回转换好的位图
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高

        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];

                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);

                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        Bitmap newBmp = Bitmap.createBitmap(width, height, Config.RGB_565);
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }
    /**
     * 转换成圆角
     *
     * @param bmp
     * @param roundPx
     * @return
     */
    public static Bitmap convertToRoundedCorner(Bitmap bmp, float roundPx) {

        Bitmap newBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(),
                Config.ARGB_8888);
        // 得到画布
        Canvas canvas = new Canvas(newBmp);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bmp.getWidth(), bmp.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // 第二个和第三个参数一样则画的是正圆的一角，否则是椭圆的一角
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bmp, rect, rect, paint);

        return newBmp;
    }

    /**
     * 高斯模糊,肉眼没有发现差异
     *
     * @param bmp
     * @return
     */
    public static Bitmap convertToBlur(Bitmap bmp) {
        // 高斯矩阵
        int[] gauss = new int[] { 1, 2, 1, 2, 4, 2, 1, 2, 1 };
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        Bitmap newBmp = Bitmap.createBitmap(width, height,
                Bitmap.Config.RGB_565);
        int pixR = 0;
        int pixG = 0;
        int pixB = 0;
        int pixColor = 0;

        int newR = 0;
        int newG = 0;
        int newB = 0;
        int delta = 16; // 值越小图片会越亮，越大则越暗
        int idx = 0;
        int[] pixels = new int[width * height];
        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        for (int i = 1, length = height - 1; i < length; i++) {
            for (int k = 1, len = width - 1; k < len; k++) {
                idx = 0;
                for (int m = -1; m <= 1; m++) {
                    for (int n = -1; n <= 1; n++) {
                        pixColor = pixels[(i + m) * width + k + n];
                        pixR = Color.red(pixColor);
                        pixG = Color.green(pixColor);
                        pixB = Color.blue(pixColor);

                        newR = newR + pixR * gauss[idx];
                        newG = newG + pixG * gauss[idx];
                        newB = newB + pixB * gauss[idx];
                        idx++;
                    }
                }
                newR /= delta;
                newG /= delta;
                newB /= delta;
                newR = Math.min(255, Math.max(0, newR));
                newG = Math.min(255, Math.max(0, newG));
                newB = Math.min(255, Math.max(0, newB));
                pixels[i * width + k] = Color.argb(255, newR, newG, newB);
                newR = 0;
                newG = 0;
                newB = 0;
            }
        }
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBmp;
    }
}