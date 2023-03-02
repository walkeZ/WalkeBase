package walke.demolibrary.completion;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
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
}
