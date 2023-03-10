package walke.demolibrary.interface2;

import android.widget.Button;
import android.widget.TextView;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * Created by walke.Z on 2017/9/13.
 *  2. java之interface--接口作为方法的参数传递,就像平时封装网络请求的callBack
 */
public class Demo2Activty extends TitleActivity {
    private Button btCar,btPlane,btShip;
    private TextView tvResult;

    @Override
    protected void initData() {

    }

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_demo2;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        btCar = ((Button) findViewById(R.id.demo1_car));
        btPlane = ((Button) findViewById(R.id.demo1_plane));
        btShip = ((Button) findViewById(R.id.demo1_ship));
        tvResult = ((TextView) findViewById(R.id.demo1_result));
//        btCar.setOnClickListener(this);
//        btPlane.setOnClickListener(this);
//        btShip.setOnClickListener(this);
        btCar.setOnClickListener(v -> clickCar());
        btPlane.setOnClickListener(v -> {

        });
        btShip.setOnClickListener(v -> {

        });
    }
    /**
     * 这里(非主model)在xml中使用onClick属性报错 是因为你的library中的R字段的id值不是final类型的，
     * 但是你自己的应用module中却是final类型的。
     *
     * */
    public void clickCar() {
        CheckBroad checkBroad = new CheckBroad();
        checkBroad.getMainMessage(() -> {

        });
    }
}
