#ifndef TOOLBOX_NODE_H_
#define TOOLBOX_NODE_H_

#include <string>

class node
{

    private:
        int x_;
        int y_;
        node parent_;
        double f_ = 0d;

    public:
        Node(const int& x, const int& y);

        std::string to_string();

        const int get_x() const { return x_; }
        const int get_y() const { return y_; }

        node get_parent() const { return parent_; }
        set_parent(const node& parent) { parent_ = parent; }

        //! return true if the nodes are the same, false otherwise
        inline bool operator == (const node& n) const
        {
            return x_ == n.get_x() && y_ == n.get_y();
        }
}

#endif // TOOLBOX_NODE_H_