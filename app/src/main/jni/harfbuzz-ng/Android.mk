##
## Copyright (C) 2012 The Android Open Source Project
##
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
##      http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
##

LOCAL_PATH:= $(call my-dir)

#############################################################
#   build the harfbuzz library
#

include $(CLEAR_VARS)

LOCAL_ARM_MODE := arm

LOCAL_MODULE_TAGS := optional

LOCAL_SRC_FILES:= \
	src/hb-blob.cc \
	src/hb-buffer.cc \
	src/hb-common.cc \
	src/hb-fallback-shape.cc \
	src/hb-font.cc \
	src/hb-ft.cc \
	src/hb-ot-tag.cc \
	src/hb-set.cc \
	src/hb-shape.cc \
	src/hb-shape-plan.cc \
	src/hb-shaper.cc \
	src/hb-tt-font.cc \
	src/hb-unicode.cc \
	src/hb-warning.cc \
	src/hb-ot-layout.cc \
	src/hb-ot-map.cc \
	src/hb-ot-shape.cc \
	src/hb-ot-shape-complex-arabic.cc \
	src/hb-ot-shape-complex-default.cc \
	src/hb-ot-shape-complex-indic.cc \
	src/hb-ot-shape-complex-thai.cc \
	src/hb-ot-shape-normalize.cc \
	src/hb-ot-shape-fallback.cc \
	src/hb-ucdn.cc \
	src/hb-ucdn/ucdn.c

LOCAL_CPP_EXTENSION := .cc

LOCAL_C_INCLUDES += \
        $(LOCAL_PATH)/src \
        $(LOCAL_PATH)/src/hb-ucdn \
	$(LOCAL_PATH)/../freetype/include

LOCAL_CFLAGS += -DHB_NO_MT -DHAVE_OT -DHAVE_UCDN

LOCAL_MODULE := libharfbuzz_ng

LOCAL_STATIC_LIBRARIES := ft2

include $(BUILD_STATIC_LIBRARY)

LOCAL_CPPFLAGS += -ffunction-sections -fdata-sections -fvisibility=hidden
LOCAL_CPPFLAGS += -ffunction-sections -fdata-sections -fvisibility=hidden
LOCAL_CFLAGS += -ffunction-sections -fdata-sections  LOCAL_LDFLAGS += -Wl,--gc-sections