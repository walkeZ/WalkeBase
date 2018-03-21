package com.hui.huiheight.first.oss;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.GridView;

import com.hui.huiheight.R;
import com.hui.huiheight.config.Contants;
import com.hui.huiheight.util.AppUtil;
import com.hui.huiheight.util.DialogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import walke.base.activity.TitleActivity;
import walke.base.tool.CameraUtil;
import walke.base.tool.FileSizeUtil;
import walke.base.tool.FileUtils;
import walke.base.tool.PermissionUtils;
import walke.base.tool.UriUtil;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2018/2/27.
 */

public class OSSActivity extends TitleActivity {

    private List<Bitmap> mBitmaps = new ArrayList<>();
    private GridView mGridView;
    private BitmapAdapter mBitmapAdapter;

    private File mPictureFile;//拍照保存的图片文件

    /**
     * 请求识别码
     */
    private static final int CODE_GALLERY_REQUEST = 0xa0;//启动相册意图请求码
    private static final int CODE_CAMERA_REQUEST = 0xa1; //启动照相机意图请求码
    private int REQUEST_CAMERA = 0xa2;
    private int REQUEST_SDCARD = 0xa3;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_oss;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("Android从相册中选取图片上传到阿里云OSS");
        mGridView = ((GridView) findViewById(R.id.oss_GridView));
        mBitmaps.add(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        mBitmapAdapter = new BitmapAdapter(mBitmaps);
        mGridView.setAdapter(mBitmapAdapter);
        mBitmapAdapter.setBitmapAddClickListener(new BitmapAdapter.BitmapAddClickListener() {
            @Override
            public void onBitmapAddClick() {
//                mBitmaps.add(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
//                mBitmapAdapter.notifyDataSetChanged();
                DialogUtil.showChangeHeaderTips(OSSActivity.this, mGridView, new DialogUtil.PopupWindowTwoButtonClickListener() {
                    @Override
                    public void OnTopClick() {//
                        toPhotos();
                    }

                    @Override
                    public void OnBottomClick() {
                        toCamera();
                    }
                });
            }

            @Override
            public void onBitmapDeleteClick() {
                if (mBitmaps.size() > 0) {
                    mBitmaps.remove(mBitmaps.get(mBitmaps.size() - 1));
                    mBitmapAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void initData() {

    }

    /**
     * 从本地相册选取图片作为头像
     */

    public void toPhotos() {
        if (PermissionUtils.isFitPermissions(this, PermissionUtils.Permissions)) {
            openPhotoWall();
        } else {
            toast("请授予必要权限后重试");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PermissionUtils.Permissions, REQUEST_SDCARD);
            }
        }
    }

    private void openPhotoWall() {
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
    }

    /**
     * 启动手机相机拍摄照片作为头像
     */
    public void toCamera() {
        if (!CameraUtil.hasCamera(OSSActivity.this)) {
            toast("抱歉，发现无可用相机");
            return;
        }
        if (PermissionUtils.isFitPermissions(this, PermissionUtils.Permissions)) {
            openCamera();
        } else {
            toast("请授予必要权限后重试");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PermissionUtils.Permissions, REQUEST_CAMERA);
            }
        }
    }

    private void openCamera() {
        if (android.os.Build.VERSION.SDK_INT < 24) {
            Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 判断存储卡是否可用，存储照片文件
            if (FileUtils.isSDcardAvailable()) {
                AppUtil.makeAppRootDir();
                mPictureFile = new File(Contants.APP_LOCATION, "bitmap1.jpg");
                intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPictureFile));
            }
            startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
        } else {//Android 7.0后 不能之前的，会报错

//          String cachePath = Environment.getExternalStorageDirectory() + File.separator+Contants.FILE_PROVIDER_PATHS  + File.separator + "bitmap.jpg";//
            String cachePath = (getExternalFilesDir(Contants.FILE_PROVIDER_PATHS) + File.separator + "mPictureFile.jpg");//
            mPictureFile = new File(cachePath);

            CameraUtil.showCameraAction(this, CODE_CAMERA_REQUEST, mPictureFile);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtils.isGetAllPermission(grantResults)) {
            logI("onRequestPermissionsResult: ---已获得获得全部授权");
            //取得全部权限
            if (requestCode == REQUEST_CAMERA) {
                openCamera();
            } else if (requestCode == REQUEST_SDCARD) {
                openPhotoWall();
            }

        } else {
            logI("onRequestPermissionsResult: ---未获得获得全部授权");
        }
    }

    /**
     * 1.以“content://”打头的是ContentProvider应用，可以表示数据库中的一张表，或者一条 数据。
     * 当表示具体的一条数据时，往往是以数据表的ID为结尾的：content://com.test.tab/1。
     * 2.以"file://"打头的表示引用的是一个文件的路径地址
     * 3.当然还有其他的格式：“http://”,"ftp://"等等
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == Activity.RESULT_CANCELED) {
            toast("取消");
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                Uri dataUri = data.getData();
                Bundle extras = data.getExtras();//extras=null
                String realFilePath = UriUtil.getRealFilePath(this, dataUri);
                Bitmap bitmap = BitmapFactory.decodeFile(realFilePath);
                mBitmaps.add(bitmap);
                mBitmapAdapter.notifyDataSetInvalidated();
                if (android.os.Build.VERSION.SDK_INT < 24) {
                    //华为6.0
                } else {//Android 7.0后一样获取图片，调用系统截图不同
                }
                break;
            case CODE_CAMERA_REQUEST:
                if (android.os.Build.VERSION.SDK_INT < 24) {
                    Uri uri = Uri.fromFile(mPictureFile);
                    boolean b = mPictureFile != null && mPictureFile.exists();
                    if (b) {
                        double filesSize = FileSizeUtil.getFileOrFilesSize(mPictureFile.getAbsolutePath(), FileSizeUtil.SIZETYPE_B);
                        if (filesSize > 0) {
                            Bitmap bm = BitmapFactory.decodeFile(mPictureFile.getAbsolutePath());
                            mBitmaps.add(bm);
                            mBitmapAdapter.notifyDataSetInvalidated();
                        }
                    }

                } else {//Android 7.0后
                    Uri uri = Uri.fromFile(mPictureFile);
                    boolean b1 = mPictureFile != null;
                    boolean b = mPictureFile.exists();
                    if (b) {
                        double filesSize = FileSizeUtil.getFileOrFilesSize(mPictureFile.getAbsolutePath(), FileSizeUtil.SIZETYPE_B);
                        if (filesSize > 0) {
                            Bitmap bm = BitmapFactory.decodeFile(mPictureFile.getAbsolutePath());
                            mBitmaps.add(bm);
                            mBitmapAdapter.notifyDataSetInvalidated();
                        }
                    }

                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
