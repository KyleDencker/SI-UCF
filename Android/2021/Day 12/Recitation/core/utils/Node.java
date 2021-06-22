package com.miketriana.tankgame.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.Set;

public class Node {
    private float x, y;
    Set<Node> neighbors;

    public Node (float x, float y) {
        this.x = x;
        this.y = y;
        neighbors = new HashSet<>();
    }

    // Another version of the constructor, for if I ever
    // want to pass a vector instead of two floats
    public Node (Vector2 v) {
        this.x = v.x;
        this.y = v.y;
        neighbors = new HashSet<>();
    }

    public float getX () { return x; }

    public float getY () { return y; }

    public void addNeighbor (Node n) {
        neighbors.add(n);
    }

    public void removeNeighbor (Node n) {
        neighbors.remove(n);
    }

    public float getDistanceTo (Node n) {
        return (new Vector2(x, y).dst(n.getX(), n.getY()));
    }
}
