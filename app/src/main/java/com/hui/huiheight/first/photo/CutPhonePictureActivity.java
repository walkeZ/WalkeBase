package com.hui.huiheight.first.photo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hui.huiheight.R;

import java.io.File;

import walke.base.activity.TitleActivity;
import walke.base.tool.PermissionUtils;
import walke.base.widget.TitleLayout;

/**
 * Created by Walke.Z
 * on 2017/7/28. 27.
 * email：1126648815@qq.com
 *
 * 魅族note3 可以
 * 华为5c、OPPO1107(默认SD路径是手机机身内存),最后截取--失败，提示无法获取该图片  原因output_X、output_X过大了
 * --https://www.2cto.com/kf/201410/347861.html
 */
public class CutPhonePictureActivity extends TitleActivity {

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "custom_head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_RESULT_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    private ImageView ivCamera,ivTuKu;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_cut_phone_pictrue;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("MainsActivity");
        ivCamera = ((ImageView) findViewById(R.id.acpp_ivCamera));
        ivTuKu = ((ImageView) findViewById(R.id.acpp__ivTuKu));

    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissionSDCard = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            boolean b = PermissionUtils.checkPermissionSetLack(this, permissionSDCard);
            if (b) {
                requestPermissions(permissionSDCard, 123);
            }
            String[] permissionCamera = new String[]{Manifest.permission.CAMERA};
            if (PermissionUtils.checkPermissionSetLack(this, permissionCamera)) {
                requestPermissions(permissionCamera, 123);
            }
        }
    }


    public void getPhoto(View v) {
        takePhoto();
    }

    public void selectPhoto(View v) {
        getImageFromAlbum();
    }

    // 从本地相册选取图片作为头像
    protected void getImageFromAlbum() {
        Intent intentFromGallery = new Intent();
        // 设置文件类型
        intentFromGallery.setType("image/*");
        intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
        /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.setType("image*//*");//相片类型
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);*/
    }
    // 启动手机相机拍摄照片作为头像
    private void takePhoto() {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }

        switch (requestCode) {
            case CODE_GALLERY_REQUEST:
                ivTuKu.setTag(1);
                ivCamera.setTag(null);
                cropRawPhoto(data.getData());
                break;
            case CODE_CAMERA_REQUEST:
                ivCamera.setTag(2);
                ivTuKu.setTag(null);
                if (hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                    cropRawPhoto(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG).show();
                }

                break;

            case CODE_RESULT_REQUEST:
                if (data != null) {
                    setImageToHeadView(data);
                }

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");

        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", output_X);
        intent.putExtra("outputY", output_Y);
        intent.putExtra("return-data", true);

        startActivityForResult(intent, CODE_RESULT_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            if (null!=ivCamera.getTag())
                ivCamera.setImageBitmap(photo);
            else if (null!=ivTuKu.getTag())
                ivTuKu.setImageBitmap(photo);
        }
    }

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            return true;
        } else {
            return false;
        }
    }

}
