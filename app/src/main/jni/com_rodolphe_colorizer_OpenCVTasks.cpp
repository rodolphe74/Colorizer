//
// Created by rodolphe on 16/08/18.
//

#include <string.h>
#include <iostream>
#include <jni.h>
#include "com_rodolphe_colorizer_OpenCVTasks.h"

#include <opencv2/opencv.hpp>

using namespace cv;
using namespace cv::dnn;
using namespace std;

// the 313 ab cluster centers from pts_in_hull.npy (already transposed)
static float hull_pts[] = { -90., -90., -90., -90., -90., -80., -80., -80., -80., -80., -80., -80., -80., -70., -70.,
    -70., -70., -70., -70., -70., -70., -70., -70., -60., -60., -60., -60., -60., -60., -60., -60., -60., -60., -60.,
    -60., -50., -50., -50., -50., -50., -50., -50., -50., -50., -50., -50., -50., -50., -50., -40., -40., -40., -40.,
    -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -40., -30., -30., -30., -30., -30., -30., -30., -30.,
    -30., -30., -30., -30., -30., -30., -30., -30., -20., -20., -20., -20., -20., -20., -20., -20., -20., -20., -20.,
    -20., -20., -20., -20., -20., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10., -10.,
    -10., -10., -10., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 0., 10., 10., 10., 10., 10.,
    10., 10., 10., 10., 10., 10., 10., 10., 10., 10., 10., 10., 10., 20., 20., 20., 20., 20., 20., 20., 20., 20., 20.,
    20., 20., 20., 20., 20., 20., 20., 20., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30., 30.,
    30., 30., 30., 30., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40., 40.,
    40., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 50., 60., 60., 60.,
    60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 60., 70., 70., 70., 70., 70., 70.,
    70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 70., 80., 80., 80., 80., 80., 80., 80., 80., 80.,
    80., 80., 80., 80., 80., 80., 80., 80., 80., 80., 90., 90., 90., 90., 90., 90., 90., 90., 90., 90., 90., 90., 90.,
    90., 90., 90., 90., 90., 90., 100., 100., 100., 100., 100., 100., 100., 100., 100., 100., 50., 60., 70., 80., 90.,
    20., 30., 40., 50., 60., 70., 80., 90., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., -20., -10., 0., 10., 20.,
    30., 40., 50., 60., 70., 80., 90., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., 100., -40.,
    -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., 100., -50., -40., -30., -20., -10., 0., 10., 20.,
    30., 40., 50., 60., 70., 80., 90., 100., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80.,
    90., 100., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., 100., -70., -60.,
    -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., 100., -80., -70., -60., -50., -40.,
    -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., 80., 90., -80., -70., -60., -50., -40., -30., -20., -10.,
    0., 10., 20., 30., 40., 50., 60., 70., 80., 90., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20.,
    30., 40., 50., 60., 70., 80., 90., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30.,
    40., 50., 60., 70., 80., 90., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40.,
    50., 60., 70., 80., -110., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50.,
    60., 70., 80., -110., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60.,
    70., 80., -110., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70.,
    -110., -100., -90., -80., -70., -60., -50., -40., -30., -20., -10., 0., 10., 20., 30., 40., 50., 60., 70., -90.,
    -80., -70., -60., -50., -40., -30., -20., -10., 0. };


jobject createJniReturn(JNIEnv *env, int value, const char *errorMessage, const char *imagePath) {

    jclass c = (env)->FindClass("com/rodolphe/colorizer/JniReturn");
    if (c == 0) {
        printf("Find Class Failed.\n");
     } else {
        printf("Found class.\n");
     }

    jmethodID cnstrctr = (env)->GetMethodID(c, "<init>", "(ILjava/lang/String;Ljava/lang/String;)V");
    if (cnstrctr == 0) {
        printf("Find method Failed.\n");
    } else {
        printf("Found method.\n");
    }

    jstring jerrorMessage = NULL;
    if (errorMessage) {
        jerrorMessage = env->NewStringUTF(errorMessage);
    }

    jstring jImagePath = NULL;
        if (imagePath) {
            jImagePath = env->NewStringUTF(imagePath);
        }

    return (env)->NewObject(c, cnstrctr, value, jerrorMessage, jImagePath);
}


JNIEXPORT jint JNICALL Java_com_rodolphe_colorizer_OpenCVTasks_test(JNIEnv *, jclass) {
   return 13;
}


JNIEXPORT jobject JNICALL Java_com_rodolphe_colorizer_OpenCVTasks_colorize (JNIEnv *env, jclass cls, jstring imageFilePath, jstring protoFilePath, jstring weightsFilePath) {
    jboolean iscopy;
    const char *pstr_imageFilePath = env->GetStringUTFChars(imageFilePath, &iscopy);
    const char *pstr_protoFilePath = env->GetStringUTFChars(protoFilePath, &iscopy);
    const char *pstr_weightsFilePath = env->GetStringUTFChars(weightsFilePath, &iscopy);

    string str_imageFileName(pstr_imageFilePath);
    string str_protoFilePath(pstr_protoFilePath);
    string str_weightsFilePath(pstr_weightsFilePath);

    Mat img = imread(str_imageFileName);
    if(img.empty()) {
        cout << "Can't read image from file: " << str_imageFileName << endl;
        return createJniReturn(env, 1, (char *)"Can't read image from file", 0);
    }

    double t = (double)cv::getTickCount();

    // fixed input size for the pretrained network
    const int W_in = 224;
    const int H_in = 224;

    Net net;
    try {
        net = dnn::readNetFromCaffe(str_protoFilePath, str_weightsFilePath);
    } catch (cv::Exception& e) {
        return createJniReturn(env, 2, (char *) e.what(), 0);
    }

    // setup additional layers:
    int sz[] = { 2, 313, 1, 1 };
    const Mat pts_in_hull(4, sz, CV_32F, hull_pts);

    Ptr<dnn::Layer> class8_ab = net.getLayer("class8_ab");
    class8_ab->blobs.push_back(pts_in_hull);
    Ptr<dnn::Layer> conv8_313_rh = net.getLayer("conv8_313_rh");
    conv8_313_rh->blobs.push_back(Mat(1, 313, CV_32F, Scalar(2.606)));

    // extract L channel and subtract mean
    Mat lab, L, input;
    img.convertTo(img, CV_32F, 1.0 / 255);
    cvtColor(img, lab, COLOR_BGR2Lab);
    extractChannel(lab, L, 0);
    resize(L, input, Size(W_in, H_in));
    input -= 50;

    // run the L channel through the network
    Mat inputBlob = blobFromImage(input);
    net.setInput(inputBlob);
    Mat result = net.forward();

    // retrieve the calculated a,b channels from the network output
    Size siz(result.size[2], result.size[3]);
    Mat a = Mat(siz, CV_32F, result.ptr(0, 0));
    Mat b = Mat(siz, CV_32F, result.ptr(0, 1));
    resize(a, a, img.size());
    resize(b, b, img.size());

    // merge, and convert back to BGR
    Mat color, chn[] = { L, a, b };
    merge(chn, 3, lab);
    cvtColor(lab, color, COLOR_Lab2BGR);

    t = ((double)cv::getTickCount() - t) / cv::getTickFrequency();
    cout << "Time taken : " << t << " secs" << endl;


    size_t position = str_imageFileName.find(".");
    string str_pathWithoutExt = (string::npos == position)? str_imageFileName : str_imageFileName.substr(0, position);

    string str = str_pathWithoutExt;
    str = str + "_colorized.jpg";

    imwrite(str, color * 255);

    cout << "Colorized image saved as " << str << endl;


    return createJniReturn(env, 0, 0, (char *) str.c_str());
}


/*
 * Class:     com_rodolphe_colorizer_OpenCVTasks
 * Method:    bwize
 * Signature: (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/rodolphe/colorizer/JniReturn;
 */
JNIEXPORT jobject JNICALL Java_com_rodolphe_colorizer_OpenCVTasks_bwize (JNIEnv *env, jclass cls, jstring imageFilePath, jstring bwImageFilePath) {
    jboolean iscopy;
    const char *pstr_imageFilePath = env->GetStringUTFChars(imageFilePath, &iscopy);
    const char *pstr_bwImageFilePath = env->GetStringUTFChars(bwImageFilePath, &iscopy);

    string str_imageFileName(pstr_imageFilePath);
    string str_bwImageFilePath(pstr_bwImageFilePath);

    Mat im_gray = imread(str_imageFileName, CV_LOAD_IMAGE_GRAYSCALE);
    if(im_gray.empty()) {
        cout << "Can't read image from file: " << str_imageFileName << endl;
        return createJniReturn(env, 1, (char *) "Can't read image from file", 0);
    }

    imwrite(str_bwImageFilePath, im_gray);

    return createJniReturn(env, 0, 0, (char *) str_bwImageFilePath.c_str());
}
