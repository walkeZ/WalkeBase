package walke.demolibrary.interface3;

import android.util.Log;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by walke.Z on 2017/9/13.
 */

public class Son extends Father {
    public Collection doSomething(TextView tvResult, Map map){//方法名与父类一样，但参数不同，故这是方法重载
        Log.i("Son", "doSomething: ");
        tvResult.setText("Son : doSomething");
        return map.values();
    }
    public Collection doSomething2(TextView tvResult, HashMap hashMap){//方法名与父类一样，但参数不同，故这是方法重载
        Log.i("Son", "doSomething2: ");
        tvResult.setText("Son : doSomething2");
        return hashMap.values();
    }

    @Override
    public Collection doSomething(TextView tvResult, HashMap hashMap) {//这才是方法重写
        return super.doSomething(tvResult, hashMap);
    }

    @Override
    public Collection doSomething2(TextView tvResult, Map map) {
        return super.doSomething2(tvResult, map);
    }
}
