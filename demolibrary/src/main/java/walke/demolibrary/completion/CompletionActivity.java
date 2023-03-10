package walke.demolibrary.completion;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * Created by walke.Z on 2017/10/16.
 * Android 填空题实现
 * 参考：http://www.jianshu.com/p/28b7082b7563
 */
public class CompletionActivity extends TitleActivity {
    private TextView tvQuestion;
    private TextView tvText;
    private String mTextTest = "我不仅能变色，还能点击";
    private String mText = "分分扬扬的_____下了半尺多厚" ;
    private String tagLine="_____";//原来的下划线
    private int answerIndex1=mText.indexOf(tagLine);
    private SpannableStringBuilder mSsbQuestion;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_completion;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("android  填空题实现");
        tvText = (TextView) findViewById(R.id.demo_ac_test);
        tvQuestion = (TextView) findViewById(R.id.demo_ac_tvQuestion);
    }

    @Override
    protected void initData() {

        SpannableString stringBuilder = new SpannableString(mTextTest);
        //设置颜色
        ForegroundColorSpan colorSp=new ForegroundColorSpan(Color.GREEN);
        //Spanned.SPAN_INCLUSIVE_INCLUSIVE： 前面包括，后面不包括
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括
        //Spanned.SPAN_INCLUSIVE_EXCLUSIVE：前后都包括
        //Spanned.SPAN_EXCLUSIVE_INCLUSIVE：前面不包括，后面包括
        stringBuilder.setSpan(colorSp,4,6, Spanned.SPAN_INCLUSIVE_INCLUSIVE);//包括下标5

        //点击事件
        ClickableSpanTest clickableSpanTest = new ClickableSpanTest();
        //包括下标最后2个
        stringBuilder.setSpan(clickableSpanTest, mTextTest.length()-2, mTextTest.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //
        tvText.setMovementMethod(LinkMovementMethod.getInstance());
        tvText.setText(stringBuilder);

        //设置下划线
        mSsbQuestion = new SpannableStringBuilder(mText);
        // 答案设置下划线
        mSsbQuestion.setSpan(new UnderlineSpan(), answerIndex1, answerIndex1+tagLine.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//前后都包括
        //下划线颜色
        ForegroundColorSpan cs=new ForegroundColorSpan(Color.parseColor("#4DB6AC"));
        mSsbQuestion.setSpan(cs,answerIndex1,answerIndex1+tagLine.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);//前后都包括
        mSsbQuestion.setSpan(new MyClickableSpan(),answerIndex1,answerIndex1+tagLine.length(),Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvQuestion.setMovementMethod(LinkMovementMethod.getInstance());
        tvQuestion.setText(mSsbQuestion);


    }

    private class MyClickableSpan extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            toast("MyClickableSpan");

            LayoutInflater inflater = LayoutInflater.from(widget.getContext());
            View inflate = inflater.inflate(R.layout.input_completion_answer, null);
            Button btSure = (Button) inflate.findViewById(R.id.ica_btSure);
            final EditText etAnswer = (EditText) inflate.findViewById(R.id.ica_etAnswer);
            final PopupWindow pw = new PopupWindow(inflate, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            //获取焦点
            pw.setFocusable(true);
            //为防止弹出菜单获取焦点后，点击activity的其他控件不做响应
            pw.setBackgroundDrawable(new BitmapDrawable());
            //设置pw显示在软键盘上面
            pw.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            //弹出pw位置(参数1随意一个原界面控件，2屏幕方位，3、4偏移量)
            pw.showAtLocation(tvQuestion, Gravity.BOTTOM,0,0);
            btSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textAnswer = etAnswer.getText().toString().trim();
                    //填写答案
                    fillAnswer(textAnswer);
                    pw.dismiss();
                }
            });
            //显示软键盘
            InputMethodManager imm= (InputMethodManager) widget.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    private void fillAnswer(String textAnswer) {
        textAnswer=" "+textAnswer+" ";//答案前后保留一个空格
        int answerLength = textAnswer.length();
        mSsbQuestion.replace(mText.indexOf(tagLine),answerIndex1+tagLine.length(),textAnswer);
        tvQuestion.setText(mSsbQuestion);
        //它们的区别在于 SpannableString像一个String一样，构造对象的时候传入一个String，
        // 之后再无法更改String的内容，也无法拼接多个 SpannableString；
        // 而SpannableStringBuilder则更像是StringBuilder，它可以通过其append()方法来拼接多个String：

//        tvQuestion.setText();
    }
}
