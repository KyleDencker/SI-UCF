package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Tank extends GameObject {
    Texture targetTexture;
    Sprite targetSprite;

    float targetX, targetY;
    float tankSpeed = 2;

    float health = 1000;

    public Tank (float x, float y) {
        texture = new Texture("tank.png");
        sprite = new Sprite(texture);
        targetTexture = new Texture("target.png");
        targetSprite = new Sprite(targetTexture);

        this.x = x;
        this.y = y;
        targetX = x;
        targetY = y;


        sprite.setSize(100, 100);
        sprite.setCenter(x, y);
        sprite.setOrigin(50, 50);
        targetSprite.setSize(32, 32);
        targetSprite.setCenter(targetX, targetY);

        hitBox = new Rectangle(x, y, 100, 100);
        hitBox.setCenter(x, y);
    }

    public void setTarget (float x, float y) {
        targetX = x;
        targetY = y;
        targetSprite.setCenter(x, y);
    }

    public void move () {
        Vector2 distanceVector = new Vector2(targetX - x, targetY - y);
        float distanceFromTarget = distanceVector.dst(0, 0);
        if (distanceFromTarget > 5) {
            x += distanceVector.x / distanceFromTarget * tankSpeed;
            y += distanceVector.y / distanceFromTarget * tankSpeed;
        }
        sprite.setRotation(distanceVector.angleDeg() - 90);
        sprite.setCenter(x, y);
        hitBox.setCenter(x, y);
        targetSprite.setCenter(targetX, targetY);
    }

    public void draw (SpriteBatch batch) {
        targetSprite.draw(batch);
        sprite.draw(batch);
    }

    public void dispose () {
        texture.dispose();
        targetTexture.dispose();
    }

    public void damage (float amount) {
        if (health > 0) {
            health -= amount;
            sprite.setColor(1, health / 1000, health / 1000, 1);
        }
    }

    public float getRotation () { return sprite.getRotation(); }
}
