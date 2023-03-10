package walke.base.tool;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.util.List;

import walke.base.activity.BaseActivity;

/**
 * Created by Walke.Z
 * on 2017/7/21. 56.
 * email：1126648815@qq.com
 */
public class CameraUtil {

    public static final int PERMISSION_REQUEST_CODE = 31;
    private static final String[] PERMISSIONS = new String[]{Manifest.permission.CAMERA};

    private static boolean checkCameraFacing(final int facing) {
        if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
            return false;
        }
        final int cameraCount = Camera.getNumberOfCameras();
        Camera.CameraInfo info = new Camera.CameraInfo();
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, info);
            if (facing == info.facing) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否具有后置摄像头
     *
     * @return
     */
    public static boolean hasBackFacingCamera() {
        final int CAMERA_FACING_BACK = 0;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    /**
     * 是否具有前置摄像头
     *
     * @return
     */
    public static boolean hasFrontFacingCamera() {
        final int CAMERA_FACING_BACK = 1;
        return checkCameraFacing(CAMERA_FACING_BACK);
    }

    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断系统中是否存在可以启动的相机应用
     *
     * @return 存在返回true，不存在返回false
     */
    public static boolean hasCamera(AppCompatActivity activity) {
        PackageManager packageManager = activity.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    /**
     * Open camera  permission.WRITE_SETTINGS
     */
    public static void showCameraAction(BaseActivity ba, int request_camera, File mTmpFile) {
        try {
            if (mTmpFile!=null) {
                if (!mTmpFile.exists()){
                    mTmpFile.createNewFile();
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, getUriForFile(ba, mTmpFile));
                ba.startActivityForResult(intent, request_camera);
            }
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (intent.resolveActivity(ba.getPackageManager()) != null) {
//                boolean exists = mTmpFile.exists();
//                boolean b1 = mTmpFile != null;
//                boolean b = b1 && exists;
//                if (b1) {
//                    ContentValues contentValues = new ContentValues(1);
//                    contentValues.put(MediaStore.Images.Media.DATA, mTmpFile.getAbsolutePath());
////                    Uri uri=Uri.parse(mTmpFile.getAbsolutePath());
////                  Uri uri = ba.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                    //通过FileProvider创建一个content类型的Uri
//                    Uri uri = FileProvider.getUriForFile(ba, ba.getPackageName() + ".file_provider", mTmpFile);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                    ba.startActivityForResult(intent, request_camera);
//
//                } else {
//                    ba.toast("获取失败");
//                }
//            } else {
//                ba.toast("没有相机可用");
//            }
        }catch (Exception exc){
            exc.printStackTrace();
        }

    }

    private static Uri getUriForFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= 24) {
            uri = FileProvider.getUriForFile(context, context.getPackageName()+".file_provider", file);
            Log.i("walke", "getUriForFile: ");
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }



}
