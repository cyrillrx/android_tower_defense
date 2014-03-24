#ifndef TOOLBOX_PATHFINDING_H_
#define TOOLBOX_PATHFINDING_H_

#include <vector>

#include "point.h"
#include "node.h"

class path_finding
{
    private:
       static float heuristic(const node& start, const node& goal);
       static bool compare_score(const float& nextNode, const float& stackNode);
       static std::vector<point> find_shortest_path(const int& startX, const int& startY,
           const int& goalX, const int& goalY,
           const std::vector<std::vector<int>>& tiles, bool cut);

    public:
//        std::vector<point> find_shortest_path(const int& startX, const int& startY, const int& goalX, const int& goalY, const std::vector<std::vector<int>>& tiles);

};

#endif // TOOLBOX_PATHFINDING_H_