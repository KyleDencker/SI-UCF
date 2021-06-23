package com.miketriana.tankgame.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Queue;
import java.util.HashMap;
import java.util.Map;

public class Graph implements Disposable {
    Array<Node> nodes;
    Array<RectangleMapObject> navZones;

    ShapeRenderer sr;   // Used for debugging

    public Graph (Array<RectangleMapObject> navZones) {
        nodes = new Array<>();
        this.navZones = navZones;
        sr = new ShapeRenderer();
    }

    public void addNode (Node n) {
        if (!nodes.contains(n, true)) {
            nodes.add(n);
        }
    }

    public void removeNode (Node n) {
        for (Node others : nodes) {
            others.removeNeighbor(n);
        }
        if (nodes.contains(n, true)) {
            nodes.removeValue(n, true);
        }
    }

    public void addEdge (Node a, Node b) { a.addNeighbor(b); }

    public void removeEdge (Node a, Node b) { a.removeNeighbor(b); }

    public int getSize () { return nodes.size; }

    public void setUpNodes () {
        // INCREDIBLY inefficient probably

        // Add pathfinding nodes at the corners of each navZone
        for (int i = 0; i < navZones.size; i++) {
            // Check to see which corners of the rectangle are in the open
            Rectangle rect = navZones.get(i).getRectangle();
            Vector2 bottomLeft = new Vector2(rect.getX(), rect.getY());
            Vector2 bottomRight = new Vector2(rect.getX() + rect.width, rect.getY());
            Vector2 topLeft = new Vector2(rect.getX(), rect.getY() + rect.height);
            Vector2 topRight = new Vector2(rect.getX() + rect.width, rect.getY() + rect.height);
            boolean bLClear = true, bRClear = true, tLClear = true, tRClear = true;
            for (int j = 0; j < navZones.size; j++) {
                if (i != j) {
                    Rectangle otherRect = navZones.get(j).getRectangle();
                    if (otherRect.contains(bottomLeft)) { bLClear = false; }
                    if (otherRect.contains(bottomRight)) { bRClear = false; }
                    if (otherRect.contains(topLeft)) { tLClear = false; }
                    if (otherRect.contains(topRight)) { tRClear = false; }
                }
            }
            // Add a slight amount of padding to prevent the nodes from overlapping their own navZone
            float padding = 0.5f;
            if (bLClear) { addNode(new Node(bottomLeft.x - padding, bottomLeft.y - padding));}
            if (bRClear) { addNode(new Node(bottomRight.x + padding, bottomRight.y - padding));}
            if (tLClear) { addNode(new Node(topLeft.x - padding, topLeft.y + padding));}
            if (tRClear) { addNode(new Node(topRight.x + padding, topRight.y + padding));}
        }
    }

    public void setUpVisibilityEdges () {
        // For each node
        for (Node n : nodes) {
            // Look at each other node
            connectVisibleNeighbors(n);
        }
        System.out.println("Finished establishing visibility connections");
    }

    private void connectVisibleNeighbors (Node a) {
        // Cannot nest iterators, so use a for loop this time
        for (int i = 0; i < nodes.size; i++) {
            if (nodes.get(i) != a) {
                Node b = nodes.get(i);
                // Make sure line between nodes does not cross any other navZones
                boolean los = true;
                for (RectangleMapObject navZone : navZones) {
                    if (Intersector.intersectSegmentRectangle(a.getX(), a.getY(), b.getX(), b.getY(), navZone.getRectangle()))
                        los = false;
                }
                if (los) {
                    // Connect the two nodes
                    a.addNeighbor(b);
                    b.addNeighbor(a);
                }
            }
        }
    }

    public Queue<Vector2> findPath (float x1, float y1, float x2, float y2) {
        // Add the start and goal to the graph as new nodes
        Node start = new Node(x1, y1);
        Node goal = new Node(x2, y2);
        addNode(start);
        addNode(goal);
        connectVisibleNeighbors(start);
        connectVisibleNeighbors(goal);

        Queue<Vector2> path = new Queue<>();

        // Find a path to the goal
        // A*
        HashMap<Node, Float> frontier = new HashMap<>();    // Not a priority queue, but we will use it like one
        HashMap<Node, Node> cameFrom = new HashMap<>();     // The node that precedes a given node in the path
        HashMap<Node, Float> costs = new HashMap<>();       // Total cost to reach a given node

        frontier.put(start, 0.0f);
        cameFrom.put(start, null);  // Start node has no predecessor
        costs.put(start, 0.0f);     // Start node has a cost of 0

        while (frontier.size() > 0) {
            // Get the node from the frontier with the lowest f-score
            Map.Entry<Node, Float> min = null;
            for ( Map.Entry<Node, Float> entry : frontier.entrySet()) {
                if (min == null || min.getValue() > entry.getValue()) {
                    min = entry;
                }
            }
            Node current = min.getKey();
            // ...and remove it from the frontier
            frontier.remove(current);

            // Check to see if that is our destination
            if (current == goal) {
                // Reconstruct the path, working backwards from the goal to the start
                path.addFirst(new Vector2(goal.getX(), goal.getY()));
                Node previous = cameFrom.get(goal);
                while (previous != null) {
                    path.addFirst(new Vector2(previous.getX(), previous.getY()));
                    previous = cameFrom.get(previous);
                }
                // Stop looking
                break;
            }

            // Check each of this node's neighbors
            for (Node next : current.neighbors) {
                // Calculate the total cost to get here
                float pathCost = costs.get(current) + current.getDistanceTo(next);
                if (!costs.containsKey(next) || pathCost < costs.get(next)) {
                    // Either we haven't visited this node yet, or we found a cheaper path
                    // Add node to frontier along with its f-score
                    frontier.put(next, pathCost + next.getDistanceTo(goal));    // Euclidean distance used as heuristic
                    cameFrom.put(next, current);
                    costs.put(next, pathCost);
                }
            }
        }

        // Remove start and goal from the graph
        removeNode(start);
        removeNode(goal);

        return path;
    }

    public void drawGraph (OrthographicCamera camera) {
        sr.setProjectionMatrix(camera.combined);
        sr.setAutoShapeType(true);
        sr.begin();
        for (Node n : nodes) {
            sr.set(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.GREEN);
            sr.circle(n.getX(), n.getY(), 3.0f);

            sr.set(ShapeRenderer.ShapeType.Line);
            sr.setColor(Color.RED);
            for (Node o : n.neighbors) {
                sr.line(n.getX(), n.getY(), o.getX(), o.getY());
            }
        }
        sr.end();
    }

    public void dispose () {
        sr.dispose();
    }
}
