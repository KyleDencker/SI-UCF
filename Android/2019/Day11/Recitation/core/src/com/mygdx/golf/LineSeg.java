package com.mygdx.golf;

import com.badlogic.gdx.math.Vector2;


public class LineSeg {
    public Vector2 start;
    public Vector2 end;
    public Vector2 vector;

    public LineSeg(Vector2 start, Vector2 end){
        this.start = start;
        this.end = end;

        vector = makeVector(start, end);
    }

    public Vector2 makeVector(Vector2 a, Vector2 b){

        float deltaX = b.x - a.x;
        float deltaY = b.y - a.y;
        Vector2 vect = new Vector2(deltaX, deltaY);
        vect = vect.nor();

        return vect;
    }

    public Vector2 normalVector(){
      return new Vector2(vector.y, -vector.x);
    }

}
