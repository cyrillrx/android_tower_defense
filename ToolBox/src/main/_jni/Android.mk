
LOCAL_PATH := $(call my-dir)

# Clear used variables
include $(CLEAR_VARS)

# Library name
LOCAL_MODULE := pathfinding

# Used source files
LOCAL_SRC_FILES := path_finding.cpp \
node.cpp

LOCAL_CPP_FEATURES += exceptions
LOCAL_CPPFLAGS += -std=c++11

# Build type (BUILD_PACKAGE, BUILD_EXECUTABLE, BUILD_SHARED_LIBRARY)
include $(BUILD_SHARED_LIBRARY)