#ifndef TOOLBOX_POINT_H_
#define TOOLBOX_POINT_H_

#include <string>
#include <vector>

class point
{
    private:
        int x_;
		int y_;

    public:
		point(const int& x, const int& y) : x_(x), y_(y) { }

        const int get_x() const { return x_; }
        void set_x(const int& x) { x_ = x; }

        const int get_y() const { return y_; }
        void set_y(const int& y) { y_ = y; }

        //! Return true if the points are the same, false otherwise
        inline bool operator == (const point& pt) const
        {
            return x_ == pt.get_x() && y_ == pt.get_y();
        }
};

#endif // TOOLBOX_POINT_H_