package walke.demolibrary.interface3;

import android.util.Log;
import android.widget.TextView;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by walke.Z on 2017/9/13.
 */

public class Father {
    public Collection doSomething(TextView tvResult, HashMap hashMap){
        Log.i("Father", "doSomething: ");
        tvResult.setText("Father : doSomething");
        return hashMap.values();
    }
    public Collection doSomething2(TextView tvResult, Map map){
        Log.i("Father", "doSomething2: ");
        tvResult.setText("Father : doSomething2");
        return map.values();
    }
}
