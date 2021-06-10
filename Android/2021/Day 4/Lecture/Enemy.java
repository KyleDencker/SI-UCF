package com.fhsps.clicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Enemy {
    float x, y, speed;
    static Texture texture;
    Sprite sprite;
    boolean isDead = false;
    Rectangle hitbox;

    public Enemy(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;

        if (texture == null) {
            texture = new Texture("badlogic.jpg");
        }
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(50, 50);

        hitbox = new Rectangle(x, y, 50, 50);
    }

    public void update(float delta) {
        y -= speed * delta;
        if (y < -50) {
            isDead = true;
        }
        sprite.setPosition(x, y);
        hitbox.y = y;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean isDead() {
        return isDead;
    }

    public void isHit(Bullet b) {
        if (b.hitBox.overlaps(hitbox)) {
            isDead = true;
            b.kill();
        }
    }

}
