package com.mygdx.golf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Ball {

    public Vector2 position;
    public Vector2 unit;
    float radius;
    Sprite sprite;
    public Circle circle;
    float speed = 0;
    float acceleration = 20;


    public Ball(float x, float y){

        position = new Vector2();
        unit = new Vector2();

        position.x = x;
        position.y = y;

        unit.x = 0;
        unit.y = 0;

        radius = 25;
        sprite = new Sprite(new Texture("Ball.png"));
        sprite.setPosition(x - 25,y-25);
        sprite.setSize(50, 50);

        speed = 0;

        circle = new Circle();
        circle.x = x;
        circle.y = y;
        circle.radius = 25;
    }

    /**
     * This method updates the ball position based on delta time
     * @param deltaTime The time that has passed since last render.
     * @return void.
     */
    public void update(float deltaTime){

        speed -= acceleration * deltaTime;

        if(speed < 0) speed = 0;

        position.x += unit.x * speed * deltaTime;
        position.y += unit.y * speed * deltaTime;
        sprite.setPosition(position.x - 25, position.y - 25);
        circle.x = position.x;
        circle.y = position.y;
    }


    public void draw(SpriteBatch b){
        sprite.draw(b);
    }

    public void reset(){
        position.x = 50;
        position.y = 250;

        unit.x = 0;
        unit.y = 0;

        speed = 0;
    }


    public void reflect( Vector2 normal){
        Vector2 vector = new Vector2(unit.x, unit.y);
        float dot = vector.dot(normal);
        float x = vector.x - 2.0f * dot * normal.x;
        float y = vector.y - 2.0f * dot * normal.y;
        unit.x = x;
        unit.y = y;
        unit  = unit.nor();

    }



}
