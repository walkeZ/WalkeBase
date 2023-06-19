package walke.demolibrary.wifi;

import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.NetworkSpecifier;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.PatternMatcher;
import android.provider.Settings;

import walke.demolibrary.AppLog;

/**
 * @author walker
 * @date 2023/6/19
 * @desc wifi配网工具 https://freesion.com/article/9601846822/
 *
 * Android 10 的新方案 参考官文：https://developer.android.google.cn/guide/topics/connectivity/wifi-bootstrap?hl=zh-cn
 *
 *  需要注意的地方
 * Andorid 10.0连接指定热点，需要先把wifi需要打开，不然没反应，没有回调，我们上面的代码在Android9.0以下连接指定热点之前，我们是通过下面的函数打开wifi
 * if (!mWifiManager.isWifiEnabled()) {
 *        mWifiManager.setWifiEnabled(true);
 *     }
 *
 * 在10.版本，isWifiEnabled()这个函数是生效的，但是setWifiEnabled(true)这个函数是失败的，没有效果，我们如果发现没有打开wifi，我们可以再连接之前进行引导用户打开，我们可以跳到设置面板提示用户启用和禁用 Wi-Fi
 * startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
 */
public class WifiApUtil {

    /**
     * 创建 WifiConfiguration，这里创建的是wpa2加密方式的wifi
     *
     * @param ssid     wifi账号
     * @param password wifi密码
     * @return
     */
    public static WifiConfiguration createWifiInfo(String ssid, String password) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + ssid + "\"";
        config.preSharedKey = "\"" + password + "\"";
        config.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        config.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        config.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        config.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        config.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        config.status = WifiConfiguration.Status.ENABLED;
        return config;
    }

    /**
     * connectWifiApByName函数只针对小于等于android 9.0，不能兼容android 10.0,所以connectWifiApByNameAndPwd这个函数支持所有版本类型
     *
     * @param context
     * @param wifiApName
     * @param password
     * @return
     */
    public static boolean connectWifiApByName(Context context, String wifiApName, String password) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        WifiConfiguration wifiNewConfiguration = createWifiInfo(wifiApName, password);//使用wpa2的wifi加密方式
        int newNetworkId = mWifiManager.addNetwork(wifiNewConfiguration);
        if (newNetworkId == -1) {
            AppLog.e("操作失败,需要您到手机wifi列表中取消对设备连接的保存");
            return false;
        }
        AppLog.i("newNetworkId is:" + newNetworkId);
        // 如果wifi权限没打开（1、先打开wifi，2，使用指定的wifi
        if (!mWifiManager.isWifiEnabled()) {
            mWifiManager.setWifiEnabled(true);
        }
        boolean enableNetwork = mWifiManager.enableNetwork(newNetworkId, true);
        if (!enableNetwork) {
            AppLog.e("切换到指定wifi失败");
            return false;
        } else {
            AppLog.e("切换到指定wifi成功");
            return true;
        }
    }


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
    public static void connectWifiApByNameAndPwd(Context context, String wifiApName, String password, CallBack callBack) {
        if (context == null || callBack == null) {
            AppLog.i("context == null || callBack == null");
            return;
        }
        WifiManager mWifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        //Andorid10.以下
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
            WifiConfiguration wifiNewConfiguration = createWifiInfo(wifiApName, password);//使用wpa2的wifi加密方式
            int newNetworkId = mWifiManager.addNetwork(wifiNewConfiguration);
            if (newNetworkId == -1) {
                AppLog.i("操作失败,需要您到手机wifi列表中取消对设备连接的保存");
                callBack.connectResult(false);
                return;
            }
            AppLog.i("newNetworkId is:" + newNetworkId);
            // 如果wifi权限没打开（1、先打开wifi，2，使用指定的wifi
            if (!mWifiManager.isWifiEnabled()) {
                mWifiManager.setWifiEnabled(true);
            }
            boolean enableNetwork = mWifiManager.enableNetwork(newNetworkId, true);
            if (!enableNetwork) {
                AppLog.i("切换到指定wifi失败");
                callBack.connectResult(false);
                return;
            }
            AppLog.i("切换到指定wifi成功");
            callBack.connectResult(true);
        } else {
            boolean isOpenWifi = mWifiManager.isWifiEnabled();
            if (!isOpenWifi) {
                AppLog.i("用户需要打开wifi开关");
                context.startActivity(new Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY));
                callBack.connectResult(false);
                return;
            }
            NetworkSpecifier specifier =
                    new WifiNetworkSpecifier.Builder()
                            .setSsidPattern(new PatternMatcher(wifiApName, PatternMatcher.PATTERN_PREFIX))
                            .setWpa2Passphrase(password)
                            .build();

            NetworkRequest request =
                    new NetworkRequest.Builder()
                            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                            .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .setNetworkSpecifier(specifier)
                            .build();

            ConnectivityManager connectivityManager = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    // do success processing here..
                    AppLog.i("onAvailable success");
                    AppLog.i("network" + network.toString());
                    callBack.connectResult(true);
                }

                @Override
                public void onUnavailable() {
                    // do failure processing here..
                    AppLog.i("onUnavailable fail");
                    callBack.connectResult(false);
                }
            };
            connectivityManager.requestNetwork(request, networkCallback);
        }
    }
}
