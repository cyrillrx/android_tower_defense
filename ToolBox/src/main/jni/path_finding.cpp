
#include <path_finding.h>

#include <string.h>
#include <jni.h>

jstring Java_org_es_engine_toolbox_pathfinding_ShortestPath_nativeFindShortestPath(JNIEnv* env, jobject thiz)
{
	return (*env)->NewStringUTF(env, "Hello NDK :) ");
}

JNIEXPORT jobject JNICALL
Java_org_es_engine_toolbox_pathfinding_ShortestPath_JNItest2(JNIEnv* env, jobject thiz)
{
    // Just for adding the multiple elements into arraylist
    int objIndex;
    const int endIndex = 10;

    // Create a java.util.ArrayList
    jclass clsArrayList = env->FindClass("java/util/ArrayList");
    jmethodID constructor = env->GetMethodID(clsArrayList, "<init>", "()V");
    jobject objArrayList = env->NewObject(clsArrayList, constructor, "");
    jmethodID arrayListAdd = env->GetMethodID(clsArrayList, "add", "(Ljava/lang/Object;)Z");

    // Create a android.graphics.Point
    jclass clsPoint = env->FindClass("android/graphics/Point");
    jmethodID constructorPoint = env->GetMethodID(clsPoint, "<init>", "()V");

    // Create a android.graphics.Rect
    jclass clsRect = env->FindClass("android/graphics/Rect");
    jmethodID constructorRect = env->GetMethodID(clsRect, "<init>", "()V");

    for (objIndex = 0; objIndex < endIndex; ++objIndex) {

        // Create a new Rect and add it into ArrayList
        jobject objRect = env->NewObject(clsRect, constructorRect, "");
        const int bottom = 1;
        const int left   = 2;
        const int right  = 3;
        const int top   = 4;

        env->SetIntField(objRect, fields.rectBottom, bottom);
        env->SetIntField(objRect, fields.rectLeft,   left);
        env->SetIntField(objRect, fields.rectRight,  right);
        env->SetIntField(objRect, fields.rectTop,    top);

        env->CallObjectMethod(objArrayList, arrayListAdd, objRect);
    }

    env->DeleteLocalRef(clsArrayList);
    env->DeleteLocalRef(clsRect);

    return objArrayList;
}

path_finding::find_shortest_path(const int& startX, const int& startY, const int& goalX, const int& goalY, const int[][]& tiles)
{

}