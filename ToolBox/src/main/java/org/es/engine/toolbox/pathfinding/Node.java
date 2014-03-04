package org.es.engine.toolbox.pathfinding;

/**
 * Created by lebronneck on 24/02/14.
 *
 * Node's structure
 * Gets the position and keeps the parent node
 */
public class Node {

    private int mX;
    private int mY;
    private Node _parentNode;
    private Double g = 0d;
    private Double f = 0d;

    public Node(int x, int y)
    {
        this.mX = x;
        this.mY = y;
    }

    public String nodeName()
    {
        return mX + "x" + mY;
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

            if(this.mX == node.mX && this.mY == node.mY)
            {
                return true;
            }
        }
        return false;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        this.mY = y;
    }

    public Node getParentNode() {
        return _parentNode;
    }

    public Double getG() {
        return g;
    }

    public void setG(Double g) {
        this.g = g;
    }

    public Double getF() {
        return f;
    }

    public void setF(Double f) {
        this.f = f;
    }
}
