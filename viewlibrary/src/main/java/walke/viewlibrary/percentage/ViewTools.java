package walke.viewlibrary.percentage;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import walke.base.tool.ViewUtil;

import static walke.base.tool.WindowUtil.getWindowHeight;
import static walke.base.tool.WindowUtil.getWindowWidth;

/**
 * Created by View on 2016/10/31.
 * 发现:布局父类是 RelativeLayout 的对应的LayoutParams是：RelativeLayout.LayoutParams
 *     布局父类是 LinearLayout 的对应的LayoutParams是：LinearLayout.LayoutParams
 */
public class ViewTools extends ViewUtil {
    private static final String TAG = ViewTools.class.getName();


    public static void resetRelativeLayoutChildSize(View view, float width, float height, float leftMargin, float topMargin){
        int windowWidth = getWindowWidth(view.getContext());
        int windowHeight = getWindowHeight(view.getContext());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height= (int) (windowHeight*height);
        params.width= (int) (windowWidth*width);
        params.leftMargin= (int) (windowWidth*leftMargin);
        params.topMargin= (int) (windowHeight*topMargin);
        view.setLayoutParams(params);
    }
    public static void resetRelativeLayoutChildSize(View view, float topMargin,float rightMargin){
        int windowWidth = getWindowWidth(view.getContext());
        int windowHeight = getWindowHeight(view.getContext());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.topMargin= (int) (windowHeight*topMargin);
        params.rightMargin= (int) (windowWidth*rightMargin);
        view.setLayoutParams(params);
    }
    public static void resetRelativeLayoutChildSize(View view, float width, float height, float leftMargin){
        int windowWidth = getWindowWidth(view.getContext());
        int windowHeight = getWindowHeight(view.getContext());
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.height= (int) (windowHeight*height);
        params.width= (int) (windowWidth*width);
        params.leftMargin= (int) (windowWidth*leftMargin);
        view.setLayoutParams(params);
    }
    public static void resetLinearLayoutChildSize(View view, float width, float height, float leftMargin, float topMargin){
        int windowWidth = getWindowWidth(view.getContext());
        int windowHeight = getWindowHeight(view.getContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height= (int) (windowHeight*height);
        params.width= (int) (windowWidth*width);
        params.leftMargin= (int) (windowWidth*leftMargin);
        params.topMargin= (int) (windowHeight*topMargin);
        view.setLayoutParams(params);
    }
    public static void resetLinearLayoutChildSize(View view, float width, float height){
        int windowWidth = getWindowWidth(view.getContext());
        int windowHeight = getWindowHeight(view.getContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height= (int) (windowHeight*height);
        params.width= (int) (windowWidth*width);
        view.setLayoutParams(params);
    }
    public static void resetLinearLayoutChildSize(View view, float width, float height, float rightMargin){
        int windowWidth = getWindowWidth(view.getContext());
        int windowHeight = getWindowHeight(view.getContext());
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.height= (int) (windowHeight*height);
        params.width= (int) (windowWidth*width);
        params.rightMargin=(int) (windowWidth*rightMargin);
        view.setLayoutParams(params);
    }
    public static void resetVSize(View v, float width, float height, float leftMargin, float topMargin){
        int windowWidth = getWindowWidth(v.getContext());
        int windowHeight = getWindowHeight(v.getContext());
        ViewGroup.LayoutParams params = v.getLayoutParams();
        params.height= (int) (windowHeight*height);
        params.width= (int) (windowWidth*width);

        v.setLayoutParams(params);
        v.setPadding((int) (windowWidth*leftMargin), (int) (windowHeight*topMargin),0,0);
    }

    /**
     * 增大文本水平间距
     *
     * @param view        TextView | Button | EditText
     * @param letterSpace 字间距 [-0.5, 4F] 之间较为合适， 精度为 0.001F
     */
    public static void addLetterSpacing(View view, float letterSpace) {
        if ((view == null)) {
            return;
        }
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            addLetterSpacing(view, textView.getText().toString(), letterSpace);
        }
        if (view instanceof Button) {
            Button button = (Button) view;
            addLetterSpacing(view, button.getText().toString(), letterSpace);
        }
        if (view instanceof EditText) {
            EditText editText = (EditText) view;
            addLetterSpacing(view, editText.getText().toString(), letterSpace);
        }
    }

    /**
     * 增大文本水平间距
     *
     * @param view        TextView | Button | EditText
     * @param letterSpace 字间距 [-0.5, 4F] 之间较为合适， 精度为 0.001F
     */
    public static void addLetterSpacing(View view, String text, float letterSpace) {
        if ((view == null) || (text == null)) {
            return;
        }
        if (letterSpace == 0F) {
            /*0 没有效果， 0.001F是最接近0 的 数了，在小一些，也就没有效果了*/
            letterSpace = 0.001F;
        }
        /*
        * 先把 String 拆成 字符数组，在每个字符后面添加一个空格，并对这个进来的空格进行 X轴上 缩放
        * */
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            builder.append(text.charAt(i));
            if (i + 1 < text.length()) {
                builder.append("\u00A0");
            }
        }
        SpannableString finalText = new SpannableString(builder.toString());
        for (int i = 1; (builder.toString().length() > 1) && (i < builder.toString().length()); i += 2) {
            finalText.setSpan(new ScaleXSpan(letterSpace), i, i + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            textView.setText(finalText, TextView.BufferType.SPANNABLE);
        }
    }



}
