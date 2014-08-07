
#include "path_finding.h"

#include <jni.h>
#include <cmath>
#include <map>
#include <stack>
#include <string>

bool add_object(JNIEnv* env, const jobject& obj, const jobject& arrayList);
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
    env->DeleteLocalRef(clsPoint);

    return arrayList;
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
	
	return add_object(env, point, arrayList);
}

bool add_object(JNIEnv* env, const jobject& obj, const jobject& arrayList)
{
	return env->CallObjectMethod(arrayList, arrayListAdd, obj);
}


//std::vector<point> path_finding::find_shortest_path(const int& startX, const int& startY, const int& goalX, const int& goalY, const std::vector<std::vector<int>>& tiles)
//{
//    point pt1(1, 2);
//    point pt2(3, 7);
//	std::vector<point> destinations { pt1, pt2 };
//	return destinations;
//}

/**
 * Compute the manhattan heuristic
 *
 * @param start
 * @param goal
 * @return
 */
float path_finding::heuristic(const node& start, const node& goal)
{
    int dx = start.get_x() - goal.get_x();
    int dy = start.get_y() - goal.get_y();
    int dx2 = start.get_x() - goal.get_x();
    int dy2 = start.get_y() - goal.get_y();
    int cross = std::abs(dx * dy2 - dx2 * dy); // Added Tie-Breaker

    return std::abs(start.get_x() - goal.get_x()) + std::abs(start.get_y() - goal.get_y()) + cross * 0.001;
}

/**
 * @param nextNode
 * @param stackNode
 * @return true if the nextNode score is lower than a node score in the open stack
 */
bool path_finding::compare_score(const float& nextNode, const float& stackNode)
{
    return nextNode <= stackNode;
}

/**
 * Calculate the shortest path from a start node and a goal node.
 *
 * @return The list of the shortest path the wave will take.
 */
std::vector<point> path_finding::find_shortest_path(const int& startX, const int& startY,
    const int& goalX, const int& goalY,
    const std::vector<std::vector<int> >& tiles, bool cut)
{
    // f(n) = g(n) + h(n)

    node start(startX, startY);
    node goal(goalX, goalY);

    std::vector<node> bestPath;
    std::vector<point> listPoints;
    std::vector<node> open;

    std::map<std::string, bool> closed;
    // Node score path
    std::map<std::string, float> g;

    // Push the start Node
    open.push_back(start);

    // First cost at 0
    g.insert(std::pair<std::string, float>(start.to_string(), 0.0));

    // Compute the estimated cost to the goal
    start.set_f(heuristic(start, goal));

    while (open.size() > 0) {

        node currentNode = open.back();
        open.pop_back();

        if (currentNode == goal) {
            bestPath.push_back(goal);

            // Look parentNode of each node
            while (currentNode.get_parent()) {
                auto parent = *currentNode.get_parent();
                bestPath.push_back(parent);
                currentNode = parent;
            }

            std::vector<point> destinations;
            // True : Cutting method
            // False : All points method
            if (cut) {
                // Cutting useless points, keep only new directions points
                int temp_direction, direction = -1;

                for (int i = bestPath.size() - 1; i >= 0; i--) {

                    // Stop to keep the last direction
                    if (i == 0) {
                        break;
                    }

                    temp_direction = direction;

                    // See N+1 movement
                    node n = bestPath[i];
                    node nn = bestPath[i - 1];

                    if (n.get_x() == nn.get_x()) {
                        direction = 1;
                    }

                    if (n.get_y() == nn.get_y()) {
                        direction = 2;
                    }

                    if (direction != temp_direction) {
                        destinations.push_back(point(bestPath[i].get_x(), bestPath[i].get_y()));
                    }
                }

                // Add the final point
                destinations.push_back(point(bestPath[0].get_x(), bestPath[0].get_y()));

                return destinations;

            } else {

                //Make the best path with all points
                for (int i = bestPath.size() - 1; i >= 0; i--) {
                    destinations.push_back(point(bestPath[i].get_x(), bestPath[i].get_y()));
                }

                return destinations;
            }

        } else {
            // Add the actual node to the closed list
            closed.insert(std::pair<std::string, bool>(currentNode.to_string(), true));

            // Find all neighbors of the actual popped node
            std::vector<node> neighbors = currentNode.neighbors(tiles);
            for (const auto& neighbor : neighbors) {
                bool lesserScore = false;

                // Skip if the neighbor node is in the closed list
                if (closed.find(neighbor.to_string()) != closed.end()) {
                    continue;
                }

                // + 1 (weight) to next
                auto tempF = g.find(currentNode.to_string()).second + 1;

                int index = std::find(open.begin(), open.end(), neighbor) - open.begin();

                if (index < 0) {
                    lesserScore = true;

                } else if (tempF < g.find(neighbor.to_string()).second) {

                    auto it = open.begin();
                    std::advance(it, index);
                    open.erase(it);

                    lesserScore = true;
                }

                if (lesserScore) {

                    neighbor.set_parent(currentNode);
                    g.insert(std::pair<std::string, float>(neighbor.to_string(), tempF));
                    neighbor.set_f(g.find(neighbor.to_string()).second + heuristic(neighbor, goal));

                    if (open.size() < 1) {
                        open.push_back(neighbor);
                        continue;
                    }

                    bool inserted = false;
                    // Compare the neighbor score with the stack
                    for (int i = open.size() - 1; i >= 0; i--) {
                        // Sort the priority of the neighbor in the open list
                        if (compare_score(neighbor.get_f(), open[i].get_f())) {
                            open.push_back(i + 1, neighbor);
                            inserted = true;
                            break;
                        }
                    }

                    // No Way ! Your neighbor got the baddest score in the open list :P
                    if (!inserted) {
                        open.push_back(0, neighbor);
                    }
                }
            }
        }
    }
    return listPoints;
}