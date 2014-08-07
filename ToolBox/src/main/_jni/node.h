#ifndef TOOLBOX_NODE_H_
#define TOOLBOX_NODE_H_

#include <string>
#include <vector>

#include "point.h"

class node
{
    private:
        point position_;
        float f_;
        node* parent_;

    public:
        node(const int& x, const int& y);

        std::string to_string();
        std::vector<node> neighbors(const std::vector<std::vector<int> >& tiles);

        const point get_position() const { return position_; }

        const int get_x() const { return position_.get_x(); }
        const int get_y() const { return position_.get_y(); }

        const float get_f() const { return f_; }
        void set_f(const int& f) { f_ = f; }

        node* get_parent() const { return parent_; }
        void set_parent(const node& parent) { *parent_ = parent; }

        //! Return true if the nodes are the same, false otherwise
        inline bool operator == (const node& n) const
        {
            return position_ == n.get_position();
        }
};

#endif // TOOLBOX_NODE_H_