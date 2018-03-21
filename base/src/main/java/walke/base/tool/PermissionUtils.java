package walke.base.tool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.security.Permission;

/**
 * @author View
 * @date 2016/12/12 11:42
 */
public class PermissionUtils {

    public static final int PERMISSIONS_REQUEST_CODE = 71;//全部权限请求码

    public static final String[] Permissions = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.WRITE_SETTINGS,

//            Manifest.permission.RECORD_AUDIO,
//            Manifest.permission.GET_ACCOUNTS,
//            Manifest.permission.CALL_PHONE,
    };


    /**
     * 检查系统权限是，判断当前是否缺少权限(PERMISSION_DENIED:权限是否足够)
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean isLackPermission(Context context, String permission) {
        boolean hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED;//denied--拒绝
        return hasPermission;
    }


    /**
     * 检查权限时，判断该app的权限集合,是否有缺少的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean checkPermissionSetLack(Context context, String... permissions) {
        for (String permission : permissions) {
            if (isLackPermission(context, permission)) {//是否添加完全部权限集合
                return true;//有缺少的权限
            }
        }
        return false;//表示全部权限已有齐
    }

    /**
     * 检查权限时，判断该app的权限集合,是否有缺少的权限
     *
     * @param context
     * @param permissions
     * @return
     */
    public static boolean isFitPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (isLackPermission(context, permission)) {//是否添加完全部权限集合
                return false;
            }
        }
        return true;//表示全部权限已有齐
    }


    /** android6.0以内判断是否具有权限 假的
     * @param context
     * @param permission
     * @return
     */
    public static boolean isHasPermission(Context context,String permission) {
        PackageManager pm = context.getPackageManager();
        int i = pm.checkPermission(permission, context.getPackageName());
        boolean hasPermission = (PackageManager.PERMISSION_GRANTED == i);
        if (hasPermission) {
            LogUtil.i("11111","有这个权限");
            return true;
            //showToast("有这个权限");
        } else {
            LogUtil.i("11111","木有这个权限");
            return false;
            //showToast("木有这个权限");
        }
    }

    /** android6.0以内判断是否具有权限 假的
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, final String permission) {
        SecurityManager securityManager = System.getSecurityManager();
        RuntimePermission runtimePermission = new RuntimePermission(permission);
        boolean implies = runtimePermission.implies(new Permission(permission) {
            @Override
            public String getActions() {
                return permission;
            }

            @Override
            public boolean implies(Permission permission) {

                return false;
            }
        });
        return implies;
    }

    public static boolean isGetAllPermission(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                //只要存在一个权限未授权 则返回false
                return false;
            }
        }
        return true;
    }


    public interface PermissionGrant {
        void onPermissionGranted(int requestCode);
    }


}
