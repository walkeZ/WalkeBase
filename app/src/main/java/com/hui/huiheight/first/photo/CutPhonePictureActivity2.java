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

import com.hui.huiheight.MainsActivity;
import com.hui.huiheight.R;

import java.io.File;

import walke.base.activity.TitleActivity;
import walke.base.tool.PermissionUtils;
import walke.base.widget.TitleLayout;

/**
 * Created by Walke.Z
 * on 2017/7/28. 27.
 * email：1126648815@qq.com
 * <p>
 *     参考--http://www.open-open.com/lib/view/open1352201409844.html
 *
 *  遇到 获取手机相册图片--部分机型不适配(LG G5 SE)，然后修复
 */
public class CutPhonePictureActivity2 extends TitleActivity {

    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "head_image.jpg";

    /* 请求识别码 */
    private static final int CODE_GALLERY_REQUEST = 0xa0;
    private static final int CODE_CAMERA_REQUEST = 0xa1;
    private static final int CODE_CROP_REQUEST = 0xa2;

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    private static int output_X = 480;
    private static int output_Y = 480;

    private ImageView ivTest;
    private Uri mUri;

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_cut_phone_pictrue2;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("CutPhonePictureActivity2");
        ivTest = ((ImageView) findViewById(R.id.acpp2_ivTest));
        ivTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CutPhonePictureActivity2.this, MainsActivity.class));
            }
        });
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
        //Intent intentFromGallery = new Intent();
        // 设置文件类型--部分机型不适配(LG G5 SE)
        //intentFromGallery.setType("image/*");
        //intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);

        //可适配
        Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
        intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
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

    /**
     * 检查设备是否存在SDCard的工具方法
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED))// 有存储的SDCard
            return true;
        else
            return false;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {

            case CODE_CAMERA_REQUEST:
                if (hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
                    Uri uri = Uri.fromFile(tempFile);
                    cropRawPhoto3(uri);//2
                   /* Bitmap bm= BitmapUtil.decodeUri(this, uri,200,200);
                    ivTest.setImageBitmap(bm);*/
                } else
                    Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG).show();
                break;

            case CODE_GALLERY_REQUEST:
                Uri data1 = data.getData();
                cropRawPhoto3(data1);
                /*Bitmap bitmap = BitmapUtil.decodeUriAsBitmap(this, data1);
                ivTest.setImageBitmap(bitmap);*/
                break;
            case CODE_CROP_REQUEST:

                /*if (mUri == null){
                    toast("获取失败请重新操作或更换选取照片方式");
                    return;
                }*/
                //Bitmap bitmap = BitmapUtil.decodeUri(this, mUri,200,200);
                //ivTest.setImageBitmap(bitmap);
                if (data != null)
                    setImageToHeadView(data);
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

        startActivityForResult(intent, CODE_CROP_REQUEST);
    }

    /**
     * 裁剪原始的图片,将截取出来的图片保存(uri相同，覆盖选图)
     */
    public void cropRawPhoto2(Uri uri) {

        int  dp = 500;

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);//输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", dp);//输出X方向的像素
        intent.putExtra("outputY", dp);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        //mUri = uri;//uri相同，覆盖选图
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        intent.putExtra("return-data", false);//设置为不返回数据

        startActivityForResult(intent, CODE_CROP_REQUEST);
    }
    /**
     * 裁剪原始的图片,将截取出来的图片保存
     */
    public void cropRawPhoto3(Uri uri) {
        int  dp = 300;//这个太大会导致一些机型无法正常返回数据
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);// 去黑边
        intent.putExtra("scaleUpIfNeeded", true);// 去黑边
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);//输出是X方向的比例
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，切忌不要再改动下列数字，会卡死
        intent.putExtra("outputX", dp);//输出X方向的像素
        intent.putExtra("outputY", dp);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        //mUri = uri;//uri相同，覆盖选图
        /*
        File fileDir = new File(Contants.APP_LOCATION);
        if (!fileDir.exists())
            fileDir.mkdir();
        File outputImage = new File(Contants.APP_LOCATION, IMAGE_FILE_NAME + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将File对象转换为Uri并启动照相程序
        mUri = Uri.fromFile(outputImage);*/
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
        intent.putExtra("return-data", true);//设置为不返回数据
        startActivityForResult(intent, CODE_CROP_REQUEST);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            ivTest.setImageBitmap(photo);
        }
    }


}
