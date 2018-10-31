LOCAL_PATH := $(call my-dir)


include $(CLEAR_VARS)
LOCAL_C_INCLUDES :=/home/rodolphe/Developpement/opencv/include /home/rodolphe/Developpement/opencv/build /home/rodolphe/Developpement/opencv/modules/core/include /home/rodolphe/Developpement/opencv/modules/calib3d/include /home/rodolphe/Developpement/opencv/modules/features2d/include /home/rodolphe/Developpement/opencv/modules/flann/include /home/rodolphe/Developpement/opencv/modules/dnn/include /home/rodolphe/Developpement/opencv/modules/highgui/include /home/rodolphe/Developpement/opencv/modules/imgcodecs/include /home/rodolphe/Developpement/opencv/modules/videoio/include /home/rodolphe/Developpement/opencv/modules/imgproc/include /home/rodolphe/Developpement/opencv/modules/ml/include /home/rodolphe/Developpement/opencv/modules/objdetect/include /home/rodolphe/Developpement/opencv/modules/photo/include /home/rodolphe/Developpement/opencv/modules/shape/include /home/rodolphe/Developpement/opencv/modules/stitching/include /home/rodolphe/Developpement/opencv/modules/superres/include /home/rodolphe/Developpement/opencv/modules/video/include /home/rodolphe/Developpement/opencv/modules/videostab/include
LOCAL_MODULE    := native
LOCAL_SRC_FILES := com_rodolphe_colorizer_OpenCVTasks.cpp
LOCAL_SHARED_LIBRARIES := opencv_calib3d opencv_core opencv_dnn opencv_features2d opencv_flann opencv_highgui opencv_imgcodecs opencv_imgproc opencv_ml opencv_objdetect opencv_photo opencv_shape opencv_stitching opencv_superres opencv_video opencv_videoio opencv_videostab
include $(BUILD_SHARED_LIBRARY)


include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_calib3d
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_calib3d.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_core
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_core.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_dnn
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_dnn.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_features2d
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_features2d.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_flann
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_flann.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_highgui
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_highgui.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_imgcodecs
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_imgcodecs.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_imgproc
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_imgproc.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_ml
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_ml.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_objdetect
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_objdetect.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_photo
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_photo.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_shape
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_shape.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_stitching
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_stitching.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_superres
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_superres.so
include $(PREBUILT_SHARED_LIBRARY)

# include $(CLEAR_VARS)
# LOCAL_PATH = .
# LOCAL_MODULE := opencv_ts
# OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
# LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_ts.so
# include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_video
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_video.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_videoio
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_videoio.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_PATH = .
LOCAL_MODULE := opencv_videostab
OPENVCV_LIB_ROOT=/home/rodolphe/Developpement/opencv/build/lib/
LOCAL_SRC_FILES := ${OPENVCV_LIB_ROOT}/$(TARGET_ARCH_ABI)/libopencv_videostab.so
include $(PREBUILT_SHARED_LIBRARY)


