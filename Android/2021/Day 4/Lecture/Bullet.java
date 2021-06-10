package com.fhsps.clicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet {
    float x, y, speed;
    static Texture texture;
    Sprite sprite;
    boolean isDead = false;
    Rectangle hitBox;

    public Bullet(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        if (texture == null) {
            texture = new Texture("badlogic.jpg");
        }
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(15, 15);

        hitBox = new Rectangle(x, y, 15, 15);
    }

    public void update(float delta) {
        y += speed * delta;
        if (y > 1010) {
            isDead = true;
        }
        sprite.setPosition(x, y);
        hitBox.y = y;

    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isDead() {
        return isDead;
    }

    public void kill() {
        isDead = true;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
