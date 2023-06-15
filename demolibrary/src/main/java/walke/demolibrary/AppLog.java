package walke.demolibrary;

import android.util.Log;

import java.util.Locale;

/**
 * @author walker
 * @date 2023/4/12
 * @description 项目自定义日志工具
 */
public class AppLog {
    public static String TAG = "HET_IB";
    public static boolean isDebug;

    public static void e(String msg) {
        if (isDebug) {
            String method = getClassMethod();
            Log.e(TAG, buildMessage(method, msg));
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            String method = getClassMethod();
            Log.i(TAG, buildMessage(method, msg));
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            String method = getClassMethod();
            Log.w(TAG, buildMessage(method, msg));
        }
    }

    public static void d( String msg) {
        if (isDebug) {
            String method = getClassMethod();
            Log.d(TAG, buildMessage(method, msg));
        }
    }

    public static void v(String msg) {
        if (isDebug) {
            String method = getClassMethod();
            Log.v(TAG, buildMessage(method, msg));
        }
    }

    private static String buildMessage(String method, String msg) {
        return String.format(Locale.CHINA, "%s %s", method, msg);
    }

    public static void st() {
        try {
            String classMethod = getClassMethod();
            String stackTrace = Log.getStackTraceString(new Throwable());
            if (stackTrace.length() > 500) {
                int indexOf = stackTrace.indexOf("\n", 100);
                int indexEnd = stackTrace.indexOf("\n", 500);
                stackTrace = stackTrace.substring(indexOf, indexEnd);
            }
            Log.i(TAG, classMethod + stackTrace);
        } catch (Exception e) {
        }
    }

    private static String getClassMethod() {
        StackTraceElement caller = getCallerStackTraceElement();
        if (caller == null) return "";
        return generateTag(caller);
    }

    private static StackTraceElement getCallerStackTraceElement() {
        Thread cThread = Thread.currentThread();
        if (cThread != null) {
            StackTraceElement[] stackTrace = cThread.getStackTrace();
            if (stackTrace != null && stackTrace.length >= 6) {
                return stackTrace[5];
            }
        }
        return null;
    }

    private static String generateTag(StackTraceElement caller) {
        String tag = "(%s:%d)";
        String callerClazzName = caller.getFileName();
        tag = String.format(Locale.getDefault(), tag, callerClazzName, caller.getLineNumber());
        return tag;
    }
}
