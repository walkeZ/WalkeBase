package com.hui.huiheight.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.hui.huiheight.R;


/**
 * Created by Walke.Z
 * on 2017/6/16. 20.
 * email：1126648815@qq.com
 */
public class ImageTextView extends LinearLayout {

    private ImageView img;
    private TextView text;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ImageTextView(Context context) {
        this(context,null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ImageTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ImageTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_image_text,this);
        img = ((ImageView) findViewById(R.id.vit_image));
        text = ((TextView) findViewById(R.id.vit_text));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ImageTextView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.ImageTextView_img:
                    int resourceId = a.getResourceId(attr, R.mipmap.ic_launcher);
                    img.setImageResource(resourceId);
                    break;
                case R.styleable.ImageTextView_text:
                    String str = a.getString(attr);
                    if (!TextUtils.isEmpty(str))
                        text.setText(str);
                    break;
                case R.styleable.ImageTextView_textsColor:
                    int color = a.getColor(attr, Color.BLACK);
                    text.setTextColor(color);
                    break;
            }
        }
        a.recycle();

    }

    public ImageView getImg() {
        return img;
    }

    public TextView getText() {
        return text;
    }
}
