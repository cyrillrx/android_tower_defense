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
    private Node mParentNode;
    private double mG = 0d;
    private double mF = 0d;

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
        mParentNode = parentNode;
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
        return mParentNode;
    }

    public double getG() {
        return mG;
    }

    public void setG(double g) {
        this.mG = g;
    }

    public double getF() {
        return mF;
    }

    public void setF(double f) {
        this.mF = f;
    }
}
