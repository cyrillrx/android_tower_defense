
#include "node.h"

node::node(const int& x, const int& y)
    : x_(x), y_(y) { }

std::string node::to_string()
{
    return std::to_string(x_) + "x" + std::to_string(y_);
}

