
#include "path_finding.h"

#include <jni.h>

bool add_object(const jobject& obj, const jobject& arrayList);
bool add_point(JNIEnv* env, const int& x, const int& y, const jobject& arrayList);

// ArrayList class
jclass clsArrayList;
// ArrayList methods
jmethodID constructorArrayList;
jmethodID arrayListAdd;
// Point class
jclass clsPoint;
jmethodID constructorPoint;

jstring Java_org_es_engine_toolbox_pathfinding_ShortestPath_nativeFindShortestPath(JNIEnv* env, jobject thiz)
{
	return env->NewStringUTF("Hello NDK :) ");
}

JNIEXPORT jobject JNICALL
Java_org_es_engine_toolbox_pathfinding_ShortestPath_JNItest2(JNIEnv* env, jobject thiz)
{
	// Caching
	// ArrayList class
	jclass clsArrayList = env->FindClass("java/util/ArrayList");
	// ArrayList methods
	jmethodID constructorArrayList = env->GetMethodID(clsArrayList, "<init>", "()V");
	jmethodID arrayListAdd = env->GetMethodID(clsArrayList, "add", "(Landroid/graphics/Point;)Z");
	// Point class
	jclass clsPoint = env->FindClass("android/graphics/Point");
	jmethodID constructorPoint = env->GetMethodID(clsPoint, "<init>", "()V");

    // Just for adding the multiple elements into arraylist
    int objIndex;
    const int endIndex = 10;

    // Create a java.util.ArrayList
    jobject arrayList = env->NewObject(clsArrayList, constructorArrayList, "");

    for (objIndex = 0; objIndex < endIndex; ++objIndex) {
		
		// Create and add a point to the list
		add_point(env, 1, 2, arrayList);
    }

    env->DeleteLocalRef(clsArrayList);
    env->DeleteLocalRef(clsRect);

    return objArrayList;
}


bool add_point(JNIEnv* env, const int& x, const int& y, const jobject& arrayList)
{
	// TODO cache
	jfieldID xFieldId = env->GetFieldID(clsPoint, "x", "I");
	jfieldID yFieldId = env->GetFieldID(clsPoint, "y", "I");

	// Create a android.graphics.Point
    jobject point = env->NewObject(clsPoint, constructorPoint, "");

	env->SetIntField(point, xFieldId, x);
	env->SetIntField(point, yFieldId, y);
	
	add_object(env, point, arrayList);
}

bool add_object(JNIEnv* env, const jobject& obj, const jobject& arrayList)
{
	return env->CallObjectMethod(arrayList, arrayListAdd, obj);
}

std::vector<point> path_finding::find_shortest_path(const int& startX, const int& startY, const int& goalX, const int& goalY, const std::vector<int>& tiles)
{
	std::vector<point> destinations { point(1, 2), point(1,3) };
	return destinations;
}