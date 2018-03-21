package walke.demolibrary.interface3;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * Created by walke.Z on 2017/9/13.
 * 里氏替换原则
 */

public class Demo3Activity extends TitleActivity {
    private Button btTest,btOther,btFather,btSon;
    private TextView tvResult,tvResult2;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_demo3;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        btTest = ((Button) findViewById(R.id.demo3_test));
        btOther = ((Button) findViewById(R.id.demo3_other));
        tvResult = ((TextView) findViewById(R.id.demo3_result));
        btFather = ((Button) findViewById(R.id.demo3_father));
        btSon = ((Button) findViewById(R.id.demo3_son));
        tvResult2 = ((TextView) findViewById(R.id.demo3_result2));
        btTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test();
            }

        });
        btOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other();
            }
        });
        btFather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                father();
            }

        });
        btSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                son();
            }
        });
    }
    @Override
    protected void initData() {

    }

    private void other() {
        Son son=new Son();
        /*Map map=new Map() {
            ...
        };*/
        //HashMap map=new HashMap();
        Map map=new HashMap();
        son.doSomething(tvResult,map);
    }

    private void test() {
        Father father = new Father();
        father.doSomething(tvResult,new HashMap());
    }


    private void father() {
        Father father = new Father();
        father.doSomething2(tvResult2,new HashMap());
    }

    private void son() {
        Son son=new Son();
        Map map=new HashMap();
//        HashMap map=new HashMap();
        //son.doSomething2(tvResult2,map);
        son.doSomething2(tvResult2,new HashMap());
    }



}
