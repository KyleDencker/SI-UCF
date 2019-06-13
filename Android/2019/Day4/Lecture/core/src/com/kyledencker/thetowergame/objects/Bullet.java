package com.kyledencker.thetowergame.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kyledencker.thetowergame.manager.TextureManager;

public class Bullet {
    Vector2 position, velocity;
    Sprite sprite;
    Rectangle hitBox;
    int speed = 200;

    public Bullet(int x, int y, Vector2 target) {

        position = new Vector2(x, y);
        sprite = new Sprite(TextureManager.bullet);
        sprite.setColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
        sprite.setPosition(x, y);
        sprite.setSize(10, 10);
        hitBox = new Rectangle(x, y, 10, 10);


        float deltaX = target.x - position.x;
        float deltaY = target.y - position.y;
        float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

        velocity = new Vector2((deltaX / distance)*speed, (deltaY / distance)*speed);
    }

    public void update(float deltaTime) {
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
        sprite.setPosition(position.x, position.y);
        hitBox.x = position.x;
        hitBox.y = position.y;
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
