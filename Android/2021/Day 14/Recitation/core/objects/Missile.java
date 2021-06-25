package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.miketriana.tankgame.utils.AssetManager;

public class Missile extends GameObject {

    Circle hitBox;
    Vector2 direction;
    float rotation;
    float speed = 1;

    public Missile () {
        sprite = AssetManager.getSprite("Missile");
        hitBox = new Circle(0, 0, 8);
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;
        hitBox.x = x;
        hitBox.y = y;
    }

    public void setDirection (float x, float y) {
        direction = new Vector2(x, y);
    }

    public void move () {
        rotation = direction.angleDeg();
        float magnitude = Vector2.dst(direction.x, direction.y, 0, 0);
        x += direction.x / magnitude * speed;
        y += direction.y / magnitude * speed;
        hitBox.x = x;
        hitBox.y = y;
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setOrigin(8, 8);
        sprite.setRotation(rotation - 90);
        sprite.setCenter(x, y);
        sprite.draw(batch);
    }

    @Override
    public void dispose() {

    }

    public Circle getHitBox () { return hitBox; }
}
