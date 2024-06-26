# Sets the minimum version of CMake required to build your native library.
# This ensures that a certain set of CMake features is available to
# your build.

cmake_minimum_required(VERSION 3.4.1)

# Specifies a library name, specifies whether the library is STATIC or
# SHARED, and provides relative paths to the source code. You can
# define multiple libraries by adding multiple add_library() commands,
# and CMake builds them for you. When you build your app, Gradle
# automatically packages shared libraries with your APK.

add_library( # Specifies the name of the library.
        hello-jni

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/hello-jni.c)
# 调试Jni byte[]
add_library( # Specifies the name of the library.
        hello-lzo

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/lzo-test/hello-lzo.c)

# Specifies a path to native header files.
#include_directories(src/main/cpp/include/)

# ndk 系统库
#find_library( # Defines the name of the path variable that stores the
#        # location of the NDK library.
#        log-lib
#
#        # Specifies the name of the NDK library that
#        # CMake needs to locate.
#        log )
# 关联库：https://github.com/android/ndk-samples/blob/main/hello-libs/app/src/main/cpp/CMakeLists.txt
target_link_libraries(
        hello-lzo
        android
        log)

# 接入miniLZO库
add_library( # Specifies the name of the library.
        mini-lzo

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        src/main/cpp/minilzo-2.10/minilzo.c )

target_link_libraries(
        mini-lzo
        android
        log)