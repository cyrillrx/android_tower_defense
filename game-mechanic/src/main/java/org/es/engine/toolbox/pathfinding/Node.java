package org.es.engine.toolbox.pathfinding;

/**
 * Created by lebronneck on 24/02/14.
 * <p/>
 * Node's structure
 * Gets the position and keeps the parent node
 */
public class Node {

    private int mX;
    private int mY;
    private Node mParentNode;
    private double mG = 0d;
    private double mF = 0d;

    public Node(int x, int y) {
        mX = x;
        mY = y;
    }

    public String nodeName() { return mX + "x" + mY; }

    public void setParentNode(Node parentNode) { mParentNode = parentNode; }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Node) {
            Node node = (Node) object;
            return mX == node.mX && mY == node.mY;
        }
        return false;
    }

    public int getX() { return mX; }

    public void setX(int x) { mX = x; }

    public int getY() { return mY; }

    public Node getParentNode() { return mParentNode; }

    public double getG() { return mG; }

    public void setG(double g) { mG = g; }

    public double getF() { return mF; }

    public void setF(double f) { mF = f; }
}
