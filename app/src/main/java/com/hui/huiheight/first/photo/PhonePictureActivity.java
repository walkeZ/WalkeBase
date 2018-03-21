package com.hui.huiheight.first.photo;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hui.huiheight.R;
import com.hui.huiheight.config.Contants;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import walke.base.activity.TitleActivity;
import walke.base.tool.BitmapUtil;
import walke.base.tool.PermissionUtils;
import walke.base.widget.TitleLayout;

/**
 * Created by walke.Z on 2017/8/17.
 */

public class PhonePictureActivity extends TitleActivity {

    private Uri mImageUri;
    private String mAbsolutePath;
    private static final int TAKE_PICTURE = 61;//启动照相机意图请求码
    private static final int REQUEST_CODE_PICK_IMAGE = 62;//启动照相机意图请求码

    @Override
    protected int rootLayoutId() {
        return R.layout.activity_phone_pictrue;
    }

    @Override
    protected void initView(TitleLayout titleLayout) {
        titleLayout.setTitleText("PhonePictureActivity");
    }

    @Override
    protected void initData() {
        checkAndRequestImportancePermission();
    }

    public void checkAndRequestImportancePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissionSDCard = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            boolean b = PermissionUtils.checkPermissionSetLack(this, permissionSDCard);
            if (b) {
                requestPermissions(permissionSDCard, Contants.PERMISSION_SDCARD_REQUEST_CODE);
            }
            String[] permissionCamera = new String[]{Manifest.permission.CAMERA};
            if (PermissionUtils.checkPermissionSetLack(this, permissionCamera)) {
                requestPermissions(permissionCamera, Contants.PERMISSION_CAMERA_REQUEST_CODE);
            }
        }
    }

    public void getPhoto(View v) {
        takePhoto();
    }

    public void selectPhoto(View v) {
        getImageFromAlbum();
    }

    protected void getImageFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
    }

    private void takePhoto() {
        //图片名称 时间命名
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);

        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            Log.v("TestFile", "SD card is not avaiable/writeable right now.");
            return;
        }

        File fileDir = new File(Contants.APP_LOCATION);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        File outputImage = new File(Contants.APP_LOCATION, filename + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAbsolutePath = outputImage.getAbsolutePath();
        //将File对象转换为Uri并启动照相程序
        mImageUri = Uri.fromFile(outputImage);
        Intent cameras = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //照相
        cameras.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri); //指定图片输出地址
        startActivityForResult(cameras, TAKE_PICTURE); //启动照相
        //拍完照startActivityForResult() 结果返回onActivityResult()函数
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (TAKE_PICTURE == requestCode) {
                try {
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    Bitmap mPhotoImg = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                    Toast.makeText(PhonePictureActivity.this, mImageUri.toString(), Toast.LENGTH_SHORT).show();

                    Bitmap bitmap = BitmapUtil.getBitmap(mAbsolutePath, 130);
                    float sizeOfBitmap = BitmapUtil.getSizeOfBitmap(bitmap);
                    ((ImageView) findViewById(R.id.app_ivTest)).setImageBitmap(bitmap);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }else if (REQUEST_CODE_PICK_IMAGE==requestCode){
                Uri uri = data.getData();
                // Bitmap bitmap = BitmapUtil.decodeUriAsBitmap(this, uri);
                Bitmap bitmap = BitmapUtil.decodeUri(this, uri,200,200);
                ((ImageView) findViewById(R.id.app_ivTest)).setImageBitmap(bitmap);
            }
        }
    }




}
