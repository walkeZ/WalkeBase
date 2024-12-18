//package walke.demolibrary.glide;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.os.Environment;
//
//import com.bumptech.glide.Glide;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//import walke.base.tool.LogUtil;
//
///**
// * Created by walke.Z on 2017/10/16.
// * 图片下载
// */
//
//public class DownLoadImageService implements Runnable {
//    private String url;
//    private Context context;
//    private ImageDownLoadCallBack callBack;
//    private File currentFile;
//    public DownLoadImageService(Context context, String url, ImageDownLoadCallBack callBack) {
//        this.url = url;
//        this.callBack = callBack;
//        this.context = context;
//    }
//
//    @Override
//    public void run() {
//
//        Bitmap bitmap = null;
//        try {
////            file = Glide.with(context).load(url).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
//            bitmap = Glide.with(context)
//                    .load(url)
//                    .asBitmap()
//                    .into(1600, 1280)
//                    .get();
//            if (bitmap != null){
//                // 在这里执行图片保存方法
//                saveImageToGallery(context,bitmap);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//            if (bitmap != null && currentFile.exists()) {
//                callBack.onDownLoadSuccess(bitmap);
//            } else {
//                callBack.onDownLoadFailed();
//            }
//        }
//    }
//
//    public void saveImageToGallery(Context context, Bitmap bmp) {
//        // 首先保存图片
//        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
//        String dirName = "新建文件夹";
//        File appDir = new File(file ,dirName);
//        if (!appDir.exists()) {
//            appDir.mkdirs();
//        }
//        String fileName = System.currentTimeMillis() + ".jpg";
//        currentFile = new File(appDir, fileName);
//        String absolutePath = currentFile.getAbsolutePath();
//        LogUtil.i("123456","------absolutePath------>"+absolutePath);
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(currentFile);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        // 其次把文件插入到系统图库
////        try {
////            MediaStore.Images.Media.insertImage(context.getContentResolver(),
////                    currentFile.getAbsolutePath(), fileName, null);
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        }
//
//        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
//                Uri.fromFile(new File(currentFile.getPath()))));
//    }
//}
