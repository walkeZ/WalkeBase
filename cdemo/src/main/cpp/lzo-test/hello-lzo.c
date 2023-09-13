/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
#include <string.h>
#include <jni.h>
#include <android/log.h>

#define LOGI(...) \
  ((void)__android_log_print(ANDROID_LOG_INFO, "hello-lzo ", __VA_ARGS__))

/* This is a trivial JNI example where we use a native method
 * to return a new VM String. See the corresponding Java source
 * file located at:
 *
 *   apps/samples/hello-jni/project/src/com/example/hellojni/HelloJni.java
 */

unsigned data[10];

JNIEXPORT jbyteArray JNICALL
Java_com_example_cdemo_HelloLzo_compress(JNIEnv
                                         *env,
                                         jclass clazz, jbyteArray
                                         original_bytes) {
    jbyteArray array = (*env)->NewByteArray(env, 4);
    return array;
}


JNIEXPORT jbyteArray JNICALL
Java_com_example_cdemo_HelloLzo_uncompress(JNIEnv *env, jclass clazz, jbyteArray original_bytes) {
    // TODO: implement uncompress()
}


typedef struct {
    int a;
} DataStruct;

const DataStruct tab[] =
        {
                {1},
                {2},
        };


JNIEXPORT jbyteArray JNICALL
Java_com_example_cdemo_HelloLzo_testCompress(JNIEnv *env, jclass clazz, jbyteArray original_bytes) {
//    jbyteArray array = (*env)->NewByteArray(env, 4);
    // https://blog.csdn.net/kangming07/article/details/8889489
    //获取数组的长度
    int len = (*env)->GetArrayLength(env, original_bytes);
    LOGI("testCompress len: %d", len);
    LOGI("testCompress len: %s", "str");

//    unsigned char arr[len];
//    // 直接将Java端的数组copy到本地的数据总，建议使用这种方式，更加安全
//    (*env)->GetByteArrayRegion(env, original_bytes, 0, len, arr);
//    int i=0;
//    for (i =0 ; i < len; i++) {
//        arr[i] = i;
//    }
//    (*env)->GetByteArrayRegion(env, arr, 0, len, original_bytes);
//    (*env)->SetByteField(env, original_bytes, 0, 3);
//    return original_bytes;

    // https://blog.csdn.net/a15874647/article/details/9851587
    jbyteArray firstMacArray = (*env)->NewByteArray(env, 6);
    jbyte *bytes = (*env)->GetByteArrayElements(env, firstMacArray, 0);
    for (int i = 0; i < 6; i++) {
        bytes[i] = 6 - i;
    }
    (*env)->SetByteArrayRegion(env, firstMacArray, 0, 6, bytes);
    return firstMacArray;
}