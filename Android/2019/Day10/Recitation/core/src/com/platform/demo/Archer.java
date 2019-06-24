package com.platform.demo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Archer {
    public Vector2 position;
    public Vector2 velocity;
    Sprite sprite;
    Rectangle[] hitBoxes;
    public float acceleration;
    public float accelerationX;

    public Archer(float x, float y){
        position = new Vector2(x, y);
        velocity = new Vector2(x,y);

        position.x = x;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;

        hitBoxes = new Rectangle[4];

        hitBoxes[0] = new Rectangle(x+6,y+26, 20, 6);
        hitBoxes[1] = new Rectangle(x+6, y, 20, 6);
        hitBoxes[2] = new Rectangle(x, y+6, 6, 20);
        hitBoxes[3] = new Rectangle(x+26, y+6, 6, 20);

        sprite = new Sprite(new Texture("archer1.png"));
        sprite.setPosition(x, y);
        sprite.setSize(32,32);
        acceleration = -50;
        accelerationX = 1;
    }

    public void update(float dt){

        velocity.y += acceleration * dt;

        float deltaX = velocity.x * dt;
        float deltaY = velocity.y * dt;

        position.x += deltaX;
        position.y += deltaY;

        for(int i = 0; i < 4; i++){
            hitBoxes[i].x += deltaX;
            hitBoxes[i].y += deltaY;
        }

        sprite.setPosition(position.x, position.y);
    }

    public void setPosition(float x, float y){
        position.x = x;
        position.y = y;

        hitBoxes[0].setPosition(x+6,y+26);
        hitBoxes[1].setPosition(x+6, y);
        hitBoxes[2].setPosition(x, y+6);
        hitBoxes[3].setPosition(x+26, y+6);
    }

    public void friction(){
        if(velocity.x > 0) {
            velocity.x -= accelerationX;
        }
        if(velocity.x < 0){
            velocity.x += accelerationX;
        }
    }

    public void jump(){
        velocity.y += 100;
    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
    }

}
