LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_ROOT:=/home/rodolphe/Developpement/OpenCV-android-sdk/
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include ${OPENCV_ROOT}/sdk/native/jni/OpenCV.mk

NDK_MODULE_PATH=/home/rodolphe/Android/Sdk/ndk-bundle
LOCAL_ARM_NEON := true
LOCAL_SRC_FILES := com_rodolphe_colorizer_OpenCVTasks.cpp
LOCAL_CPPFLAGS := -std=gnu++0x
LOCAL_CFLAGS += -O2
LOCAL_LDLIBS += -llog -ldl
LOCAL_MODULE := native

include $(BUILD_SHARED_LIBRARY)