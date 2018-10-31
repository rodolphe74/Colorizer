## Colorizer

### Presentation

An Android Studio project aimed at colorizing black and white pictures.
The result is not always as it should be in reality and that is what is fun.

The application works with neural network to infer colors. It uses the OpenCV library for that. The "thinking part" is written in native c++ android sdk with the help of JNI.
OpenCV is available [here](https://github.com/opencv/opencv).

More informations on how the colorization process works here :
* [Richard Zhang - Colorful Image Colorization](http://videolectures.net/eccv2016_zhang_image_colorization/)
* [Learn OpenCV - Convolutional Neural Network based Image Colorization using OpenCV](https://www.learnopencv.com/convolutional-neural-network-based-image-colorization-using-opencv/)

### Compilation

First, opencv4 native shared libs are needed for the application to work :
*  get opencv : `git clone https://github.com/opencv/opencv`
*  create a build directory inside opencv folder and cd to it
*  for each android architecture (x86, armeabi-v7a, arm64-v8a), use the following command to build the shared libs :

`cmake -DINSTALL_ANDROID_EXAMPLES=ON -DANDROID_EXAMPLES_WITH_LIBS=ON -DBUILD_EXAMPLES=ON -DBUILD_DOCS=OFF -DWITH_OPENCL=OFF -DWITH_IPP=ON -DCMAKE_TOOLCHAIN_FILE=${ANDROID_NDK}/build/cmake/android.toolchain.cmake -DANDROID_TOOLCHAIN=clang "-DANDROID_STL=c++_static" -DANDROID_ABI=arm64-v8a -DANDROID_SDK_TARGET=18 -DPYTHON_INCLUDE_DIR=/usr/include/python2.7 -DPYTHON_LIBRARY=/usr/lib/x86_64-linux-gnu/libpython2.7.so.1.0 -DBUILD_JAVA=OFF -DBUILD_ANDROID_EXAMPLES=OFF ../`