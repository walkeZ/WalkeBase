package walke.demolibrary.interface1;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import walke.base.activity.TitleActivity;
import walke.base.widget.TitleLayout;
import walke.demolibrary.R;

/**
 * Created by walke.Z on 2017/9/13.
 *
 *  1.Java多态接口动态加载实例
 *
 *  编写一个通用程序，用来计算没一种交通工具运行1000公里所需的时间，已知每种交通工具的参数都为3个整数A、B、C
 *  的表达式。现有两种工具：Car和Plane，其中Car的速度运算公式为：A+B+C。需要编写三个类：ComputeTime.java，
 *  Palne.java，Car.java和接口Common.java。要求在未来如果增加第3中交通工具的时候，不必修改 以前的任何程序，
 *  只需要写新的交通工具的程序。其运行过程如下：
 *  从命令行输入ComputeTime的四个参数，第一个是交通工具的类型，第二、三、四个参数分别是整数A、B、C。举例如下：
 *  计算Plane的时间：“Plane 20 30 40”
 *  计算Car的时间："Car 23 34 45"
    如果第3中交通工具为Ship,则需要编写Ship.java，运行时输入："Ship 22 33 44"
    提示：充分利用接口的概念，接口对象充当参数。
        实例化一个对象的另外一中办法：Class.forName(str).newInstance();例如需要实例化
        一个Plane对象的话，则只要调用Class.forName("Plane").newInstance()便可。
 */

public class Demo1Activity extends TitleActivity {

    private Button btCar,btPlane,btShip;
    private TextView tvResult;

    @Override
    protected void initData() {

    }

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_demo1;
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
        btCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    clickCar();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        btPlane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPlane();
            }
        });
        btShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickShip();
            }
        });
    }
    /**
     * 这里(非主model)在xml中使用onClick属性报错 是因为你的library中的R字段的id值不是final类型的，
     * 但是你自己的应用module中却是final类型的。
     *
     * */
    public void clickCar() throws ClassNotFoundException {
        try {
            String name = Car.class.getName();
            Common d=(Common) Class.forName(name).newInstance();
            double v = d.runTimer(23, 34, 45);
            double t = 1000 / v;
            logI("car 平均速度: "+v+"km/h");
            logI("car 运行时间: "+t+"小时");
            tvResult.setText("car 平均速度: "+v+"km/h\n"+"car 运行时间: "+t+"小时");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public void clickPlane(){//
        Plane plane = new Plane();
        double v = plane.runTimer(90, 130, 40);
        double t = 1000 / v;
        logI("Plane 平均速度: "+v+"km/h");
        logI("Plane 运行时间: "+t+"小时");
        tvResult.setText("plane 平均速度: "+v+"km/h\n"+"plane 运行时间: "+t+"小时");
    }
    public void clickShip(){
        Ship ship=new Ship();
        double v = ship.runTimer(20, 30, 40);
        double t = 1000 / v;
        logI("Ship 平均速度: "+v+"km/h");
        logI("Ship 运行时间: "+t+"小时");
        tvResult.setText("ship 平均速度: "+v+"km/h\n"+"ship 运行时间: "+t+"小时");
    }

}
