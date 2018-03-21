//package walke.demolibrary.glide;
//
//import android.graphics.Bitmap;
//import android.os.Handler;
//import android.os.Message;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.bumptech.glide.Glide;
//
//import java.io.File;
//
//import walke.base.activity.TitleActivity;
//import walke.base.widget.TitleLayout;
//import walke.demolibrary.R;
//
///**
// * Created by walke.Z on 2017/10/16.
// */
//
//public class GlideActivity extends TitleActivity {
//    private static final int MSG_VISIBLE = 11;
//    private static final int MSG_ERROR = 12;
//    String imgUrl = "http://images.liqucn.com/h013/h91/img201405061136280108_info1600X1280.JPG";
//
//    private long delayTime=500;//延迟500毫秒
//    private ImageView ivNet,ivFilePath;
//
//    private Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            //super.handleMessage(msg);
//            switch (msg.what){
//                case MSG_VISIBLE:
//                    toast("加载成功");
//                    ivNet.setImageBitmap((Bitmap) msg.obj);
//                    break;
//                case MSG_ERROR:
//                    toast("加载失败");
//                    break;
//                default:
//                    break;
//            }
//        }
//    };
//
//    @Override
//    protected int rootLayoutId() {
//        return R.layout.activity_glide;
//    }
//
//    @Override
//    protected void initView(TitleLayout titleLayout) {
//        titleLayout.setTitleText("Glide获取保存网络图片和本地图片");
//        ivNet = ((ImageView) findViewById(R.id.glide_ivNet));
//        ivFilePath = ((ImageView) findViewById(R.id.glide_ivFilePath));
//
//    }
//
//    @Override
//    protected void initData() {
//
//    }
//
//    @Override
//    public void leftClick() {
//        super.leftClick();
//    }
//
//    public void getImageFormNet(View v){
//        toast("getImageFormNet");
//        //onDownLoad();
//       // Picasso.with(this).load(imgUrl).into(ivNet);
//
//        Glide.with(this).load(imgUrl).into(ivNet);
//    }
//
//    /**
//     * 启动图片下载线程
//     */
//    private void onDownLoad(String url) {
//        DownLoadImageService service = new DownLoadImageService(getApplicationContext(),
//                url,
//                new ImageDownLoadCallBack() {
//
//                    @Override
//                    public void onDownLoadSuccess(File file) {
//                    }
//                    @Override
//                    public void onDownLoadSuccess(Bitmap bitmap) {
//                        // 在这里执行图片保存方法
//                        Message message = new Message();
//                        message.what = MSG_VISIBLE;
//                        message.obj=bitmap;
//                        handler.sendMessageDelayed(message, delayTime);
//                    }
//
//                    @Override
//                    public void onDownLoadFailed() {
//                        // 图片保存失败
//                        Message message = new Message();
//                        message.what = MSG_ERROR;
//                        handler.sendMessageDelayed(message, delayTime);
//                    }
//                });
//        //启动图片下载线程
//        new Thread(service).start();
//    }
//
//    public void getImageForSDCard(View v){
//        toast("getImageForSDCard");
//    }
//}
