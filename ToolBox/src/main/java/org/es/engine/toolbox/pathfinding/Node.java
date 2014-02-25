package org.es.engine.toolbox.pathfinding;

/**
 * Created by lebronneck on 24/02/14.
 *
 * Node's structure
 * Gets the position and keeps the parent node
 */
public class Node {

    protected int _x;
    protected int _y;
    protected Node _parentNode;

    public Node(int x, int y)
    {
        this._x = x;
        this._y = y;
    }

    public String nodeName()
    {
        return _x + "x" + _y;
    }

    public void setParentNode(Node parentNode)
    {
        _parentNode = parentNode;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o instanceof Node)
        {
            Node node = (Node) o;

            if(this._x == node._x && this._y == node._y)
            {
                return true;
            }
        }
        return false;
    }
}
