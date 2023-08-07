package walke.demolibrary.wifi;

import static android.content.Context.WIFI_SERVICE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSuggestion;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import walke.demolibrary.AppLog;

/**
 * @author walker
 * @date 2023/6/19
 * @desc wifi配网工具 https://codeleading.com/article/3754735959/
 */
public class WifiUtil {
    public interface CallBack {
        void connectResult(boolean connectResult);
    }

    /**
     * 通过热点用户名和密码连接热点
     *
     * @param context
     * @param wifiApName
     * @param password
     * @param callBack
     */
    @RequiresApi(29)
    public static void connectWifiApByNameAndPwd(Context context, String wifiApName, String password, CallBack callBack) {
        if (context == null || callBack == null) {
            AppLog.i("context == null || callBack == null");
            return;
        }
        WifiManager mWifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiNetworkSuggestion build = new WifiNetworkSuggestion.Builder()
                .setSsid(wifiApName).setWpa2Passphrase(password)
                .setIsAppInteractionRequired(true).build();

        List<WifiNetworkSuggestion> suggestionList = new ArrayList<>();
        suggestionList.add(build);
        int status = mWifiManager.addNetworkSuggestions(suggestionList);
        AppLog.d(" " + status);
        if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
        }
        // 广播
        IntentFilter intentFilter = new IntentFilter();
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!WifiManager.ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION.equals(intent.getAction())) {
                    return;
                }
                AppLog.d("WIFI_NETWORK_SUGGESTION onReceive " + status);
            }
        };
        context.registerReceiver(broadcastReceiver, intentFilter);
    }
}
