#ifndef TOOLBOX_PATHFINDING_H_
#define TOOLBOX_PATHFINDING_H_

#include <vector>

struct point
{
    public:
		point(int xVal, int yVal) : x(xVal), y(yVal) { }

        int x;
		int y;
};

class path_finding
{

    public:
        std::vector<point> find_shortest_path(const int& startX, const int& startY, const int& goalX, const int& goalY, const std::vector<int>& tiles);
        
    private:
};

#endif // TOOLBOX_PATHFINDING_H_