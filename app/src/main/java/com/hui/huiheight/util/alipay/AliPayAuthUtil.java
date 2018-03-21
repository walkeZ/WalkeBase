package com.hui.huiheight.util.alipay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author View
 * @date 2016/12/19 14:38
 */
public class AliPayAuthUtil {

    /**
     * @param pid
     * @param app_id
     * @param target_id
     * @return
     */
    public static Map<String, String> buildAuthInfoMap(String app_id, String pid, String target_id) {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put("apiname", "com.alipay.account.auth");
        keyValues.put("app_id", app_id);
        keyValues.put("app_name", "mc");
        keyValues.put("auth_type", "AUTHACCOUNT");
        keyValues.put("biz_type", "openservice");
        keyValues.put("method", "alipay.open.auth.sdk.code.get");
        keyValues.put("pid", pid);
        keyValues.put("product_id", "APP_FAST_LOGIN");
        keyValues.put("scope", "kuaijie");
        keyValues.put("sign_type", "RSA");
        keyValues.put("target_id", target_id);
//        String joinMap = joinMap(keyValues);
//        String sign=SignUtils.sign(joinMap, rsaKey);
//        joinMap+="sign="+sign;
        return keyValues;
    }


    /**
     * 参数信息
     *
     * @param map 参数
     * @return
     */
    public static String buildAuthInfoParam(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * 对支付参数信息进行签名
     *
     * @param map
     *            待签名授权信息
     *
     * @return
     */
    public static String getSign(Map<String, String> map, String rsaKey) {
        List<String> keys = new ArrayList<String>(map.keySet());
        // key排序
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey);
        String encodedSign = "";

        try {
            encodedSign = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "sign=" + encodedSign;
    }
}
