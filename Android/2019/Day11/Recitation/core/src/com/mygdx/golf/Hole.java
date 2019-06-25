package com.mygdx.golf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Hole {
    public Vector2 position;
    float radius;
    Sprite sprite;
    public Circle circle;

    public Hole(float x, float y){
        position = new Vector2();


        position.x = x;
        position.y = y;


        radius = 25;
        sprite = new Sprite(new Texture("Hole.png"));
        sprite.setPosition(x - 25,y-25);
        sprite.setSize(50, 50);

        circle = new Circle();
        circle.x = x;
        circle.y = y;
        circle.radius = 25;
    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
    }



}
