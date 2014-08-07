
#include "node.h"

const int WALKABLE = 0;

node::node(const int& x, const int& y)
    : position_(x, y) { }

std::string node::to_string() const
{
    return std::to_string(x_) + "x" + std::to_string(y_);
}

//! Find the neighbors of the node
std::vector<node> node::neighbors(const std::vector<std::vector<int> >& tiles) const
{
    const int minX = 0;
    const int maxX = tiles[0].size() - 1;
    const int minY = 0;
    const int maxY = tiles.size() - 1;

    std::vector<node> nodes;

    // look EST
    if ((x_ - 1 >= minX) && (tiles[y_][x_ - 1] == WALKABLE))
    {
        node n(x_ - 1, y_);
        nodes.push_back(n);
    }

    // look WEST
    if ((x_ + 1 <= maxX) && (tiles[y_][x_ + 1] == WALKABLE))
    {
        node n(x_ + 1, y_);
        nodes.push_back(n);
    }

    // look NORTH
    if ((y_ -1 >= minY) && (tiles[y_ - 1][x_] == WALKABLE))
    {
        node n(x_, y_ - 1);
        nodes.push_back(n);
    }

    // look SOUTH
    if ((y_ + 1 <= maxY) && (tiles[y_ + 1][x_] == WALKABLE))
    {
        node n(x_, y_ + 1);
        nodes.push_back(n);
    }

    return nodes;
}

