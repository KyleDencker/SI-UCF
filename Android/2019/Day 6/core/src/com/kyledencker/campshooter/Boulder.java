package com.kyledencker.campshooter;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Boulder {

    private Sprite sprite;
    private Rectangle hitbox;
    private Vector2 velocity;
    private static float acceleration = -200;
    private float angle;

    public Boulder(int x, int y, int size, float push) {
        hitbox = new Rectangle(x, y, size, size);
        sprite = new Sprite(GameController.img);
        velocity = new Vector2(push, 0);
        sprite.setPosition(x, y);
        sprite.setSize(size, size);
        sprite.setOriginCenter();
        angle = (float)Math.random()*10-5;

    }

    public void update(float deltaTime) {
        hitbox.x += velocity.x * deltaTime;
        hitbox.y += velocity.y * deltaTime;

        velocity.y += acceleration * deltaTime;

        if (hitbox.y < 0) {
            hitbox.y = 0;
            velocity.y *= -1;
        }

        sprite.setPosition(hitbox.x, hitbox.y);


        sprite.rotate(angle);
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }
}
