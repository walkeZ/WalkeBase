package walke.demolibrary.glide;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by walke.Z on 2017/10/16.
 */

interface ImageDownLoadCallBack {
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
