package com.fhsps.clicker.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Car {

    private int turn;
    private float angle, speed, maxSpeed;
    private static Texture texture;
    private Sprite sprite;
    private Rectangle hitBox;

    public Car(int x, int y) {
        turn = 0;
        angle = 0;
        texture = new Texture("sprites/car2.png");
        sprite = new Sprite(texture);
        sprite.setSize(75, 40);
        sprite.setPosition(x, y);
        sprite.setOrigin(37.5f,20);
        speed = 0;
        maxSpeed = 1000;
        hitBox = new Rectangle(x, y, 75, 40);

    }

    public void update(float delta) {



        if (turn == -1) {
            angle -= 2.5 * (speed / maxSpeed);
        }

        if (turn == 1) {
            angle += 2.5 * (speed / maxSpeed);
        }
        sprite.setRotation(angle);

        if (speed > 0) {
            speed -= 5;
            if (speed < 0) {
                speed = 0;
            }
        } else if (speed < 0) {
            speed += 5;
            if (speed > 0) {
                speed = 0;
            }
        }

        double deltaX = speed * Math.cos(Math.toRadians(angle));
        double deltaY = speed * Math.sin(Math.toRadians(angle));

        hitBox.x += (float) (deltaX * delta);
        hitBox.y += (float) (deltaY * delta);
        
        sprite.setPosition(hitBox.x, hitBox.y);

        if (hitBox.x > 800) {
            hitBox.x = -75;
        }
        if (hitBox.x < -75) {
            hitBox.x = 800;
        }

        if (hitBox.y > 640) {
            hitBox.y = -40;
        }
        if (hitBox.y < -40) {
            hitBox.y = 640;
        }

    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void accelerate(){
        speed += 20;
        if (speed > maxSpeed) {
            speed = maxSpeed;
        }
    }
    public void turnLeft() {
        turn = 1;
    }
    public void turnRight() {
        turn = -1;
    }
    public void straight() {
        turn = 0;
    }
    public void reverse() {
        speed -= 20;
        if (speed < - maxSpeed) {
            speed = -maxSpeed;
        }
    }

}
