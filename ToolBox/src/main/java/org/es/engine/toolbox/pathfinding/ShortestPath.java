package org.es.engine.toolbox.pathfinding;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by lebronneck on 24/02/14.
 *
 */
public class ShortestPath {

    public ShortestPath()
    {
        // TODO Parameters
    }

    /**
     * Find the neighbors of the node
     * @param node
     * @param goal
     * @param tiles
     * @return
     */
    public ArrayList<Node> findNeighbors(Node node, Node goal, int[][] tiles)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();

        // TODO Replace "1" not buidable by enum
        // look EST
        if(node.getX() - 1 >= 0)
        {
            if(tiles[node.getY()][node.getX() - 1] != 1)
            {
                Node n = new Node(node.getX() - 1, node.getY());
                nodes.add(n);
            }
        }

        // look WEST
        if(node.getX() + 1 < tiles[0].length)
        {
            if(tiles[node.getY()][node.getX() + 1] != 1)
            {
                Node n = new Node(node.getX() + 1, node.getY());
                nodes.add(n);
            }
        }

        // look NORTH
        if(node.getY() -1 >= 0)
        {
            if(tiles[node.getY() - 1][node.getX()] != 1)
            {
                Node n = new Node(node.getX(), node.getY() - 1);
                nodes.add(n);
            }
        }

        // look SOUTH
        if(node.getY() + 1 < tiles.length)
        {
            if(tiles[node.getY() + 1][node.getX()] != 1)
            {
                Node n = new Node(node.getX(), node.getY() + 1);
                nodes.add(n);
            }
        }
        return nodes;
    }

    /**
     * Compute the manhattan heuristic
     * @param start
     * @param goal
     * @return
     */
    public double heuristic(Node start, Node goal)
    {
        int dx1 = start.getX() - goal.getX();
        int dy1 = start.getY() - goal.getY();
        int dx2 = start.getX() - goal.getX();
        int dy2 = start.getY() - goal.getY();
        int cross = Math.abs(dx1 * dy2 - dx2 * dy1); // Added Tie-Breaker

        return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - goal.getY()) + cross * 0.001;
    }

    /**
     * Who's the best ? HEEEEEEE
     * @param nextNode
     * @param stackNode
     * @return true if the nextNode score is lower than a node score in the open stack
     */
    public boolean compareScore(double nextNode, double stackNode)
    {
        if(nextNode <= stackNode)
        {
            return true;
        }
        return false;
    }

    /**
     * Calculate the shortest path from a start node and a goal node
     * @return the list of the shortest path the wave will take
     */
    public ArrayList<Point> findShortestPath(int startX, int startY, int goalX, int goalY, int[][] tiles)
    {
        // f(n) = g(n) + h(n)

        Stack<Node> open = new Stack<>();
        Map<String,Boolean> closed = new HashMap<>();
        Node nodeStart = new Node(startX, startY);
        Node nodeGoal = new Node(goalX, goalY);
        ArrayList<Node> bestPath = new ArrayList<>();
        ArrayList<Point> listPoints = new ArrayList<>();

        // Node score path
        //Map<String,Double> g = new HashMap<>();
        //Map<String,Double> f = new HashMap<>();

        // debug Scoot
        //ArrayList<Point> debugNode = new ArrayList<>();

        // Push the start Node
        open.push(nodeStart);

        // First cost at 0
        //g.put(nodeStart.nodeName(),0d);
        // Compute the estimated cost to the goal
        //f.put(nodeStart.nodeName(),heuristic(nodeStart, nodeGoal));
        nodeStart.setF(heuristic(nodeStart, nodeGoal));

        while(open.size() > 0)
        {
            Node node = open.pop();

            if(node.equals(nodeGoal))
            {
                bestPath.add(nodeGoal);

                while(node.getParentNode() != null) {
                    bestPath.add(node.getParentNode());
                    node = node.getParentNode();
                }

                for(int i = bestPath.size() - 1; i >= 0; i--)
                {
                    listPoints.add(new Point(bestPath.get(i).getX(),bestPath.get(i).getY()));
                }

                return listPoints;
            }
            else
            {
                // Add the actual node to the closed list
                closed.put(node.nodeName(),true);

                // Find all neighbors of the actual popped node
                for(Node nextNode : findNeighbors(node, nodeGoal,tiles))
                {
                    boolean lesserScore = false;

                    // Skip if the neighbor node is in the closed list
                    if(closed.containsKey(nextNode.nodeName())) continue;

                    // + 1 (weight) to next
                    //double temp_f = g.get(node.nodeName()) + 1;
                    double temp_f = node.getG() + 1;

                    int index = open.indexOf(nextNode);

                    if(index < 0) {
                        lesserScore = true;
                    }
                    else if(temp_f < nextNode.getG()) {
                        open.removeElementAt(index);
                        lesserScore = true;
                    }

                    if(lesserScore)
                    {
                        nextNode.setParentNode(node);
                        //g.put(nextNode.nodeName(),temp_f);
                        nextNode.setG(temp_f);
                        //f.put(nextNode.nodeName(),g.get(nextNode.nodeName()) + heuristic(nextNode,nodeGoal));
                        nextNode.setF(nextNode.getG() + heuristic(nextNode,nodeGoal));

                        if(open.size() < 1 )
                        {
                            open.push(nextNode);
                            //debugNode.add(new Point(nextNode.getX(),nextNode.getY()));
                            continue;
                        }

                        boolean inserted = false;
                        // Compare the nextNode score with the stack
                        for(int i = open.size() - 1; i >= 0; i--)
                        {
                            // Sort the priority of the nextNode in the open list
                            if(compareScore(nextNode.getF(),open.get(i).getF()))
                            {
                                open.add(i + 1,nextNode);
                                inserted = true;
                                break;
                                //debugNode.add(new Point(nextNode.getX(),nextNode.getY()));
                            }
                        }
                        // No Way ! Your nextNode got the baddest score in the open list :P
                        if(!inserted)
                        {
                            open.add(0,nextNode);
                        }
                    }
                }
            }
        }
        return listPoints;
    }
}
