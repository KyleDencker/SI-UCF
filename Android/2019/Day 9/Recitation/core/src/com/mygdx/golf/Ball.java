package com.mygdx.golf;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Ball {

    Vector2 position;
    Vector2 unit;
    float radius;
    Sprite sprite;
    public Circle circle;
    float speed = 0;


    public Ball(float x, float y){

        position = new Vector2();
        unit = new Vector2();

        position.x = x;
        position.y = y;

        unit.x = 1;
        unit.y = 0;

        radius = 50;
        sprite = new Sprite(new Texture("Ball.png"));
        sprite.setPosition(x,y);
        sprite.setSize(50, 50);

        speed = 50;

        circle = new Circle();
        circle.x =  x + 25;
        circle.y = y + 25;
        circle.radius = 50;
    }

    public void update(float deltaTime){
        position.x += unit.x * speed * deltaTime;
        position.y += unit.y * speed * deltaTime;
        sprite.setPosition(position.x, position.y);
        circle.x = position.x + 25;
        circle.y = position.y + 25;
    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
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
