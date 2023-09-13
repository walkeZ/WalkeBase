package com.example.cdemo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * JNI hello-jni demo
 * 参考：
 * https://developer.android.com/studio/projects/add-native-code?hl=zh-cn
 * https://developer.android.com/ndk/samples/sample_hellojni?hl=zh-cn
 * https://github.com/android/ndk-samples/tree/android-mk/hello-jni
 *
 * https://developer.android.com/studio/projects/configure-cmake?hl=zh-cn
 * https://developer.android.com/studio/projects/gradle-external-native-builds?hl=zh-cn
 * 流程：
 * ① 在原项目接入新的C/C++。在main下新建cpp，新建c文件(可直接改后缀为.c)
 * ② 参考事例写jni文件夹下的代码。【.c文件上 要对应自己的包名、类名】
 * ③ 配置cmake，没做 会报错：xxx .so not found
 * ④ 关联gradle，没做 会报错：xxx .so not found
 */
public class HelloJniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_jni);

        /* Create a TextView and set its content.
         * the text is retrieved by calling a native
         * function.
         */
//        TextView tv = new TextView(this);
//        tv.setText( stringFromJNI() );
//        setContentView(tv);

        TextView tv = (TextView) findViewById(R.id.jni_tvTips);

        tv.setText(stringFromJNI());
    }
    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native String stringFromJNI();

    /* This is another native method declaration that is *not*
     * implemented by 'hello-jni'. This is simply to show that
     * you can declare as many native methods in your Java code
     * as you want, their implementation is searched in the
     * currently loaded native libraries only the first time
     * you call them.
     *
     * Trying to call this function will result in a
     * java.lang.UnsatisfiedLinkError exception !
     */
    public native String unimplementedStringFromJNI();

    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("hello-jni");
    }

}