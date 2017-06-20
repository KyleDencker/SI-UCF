package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by kyledencker on 6/20/17.
 */

public abstract class Bullets {
    private Sprite sprite;
    private float life;
    private float speed;
    private Rectangle hitBox;
    private float angle;

    public Bullets(Texture t, float lifeStart, int startX, int startY, float startA, int sizeX, int sizeY) {
        sprite = new Sprite(t);
        sprite.setSize(sizeX, sizeY);
        hitBox = new Rectangle(0, 0, sizeX, sizeY);
        this.setPosition(startX, startY);
        angle = startA;
        life = lifeStart;
        speed = 250;
    }
    public void reset(float lifeStart, int startX, int startY, float startA, int sizeX, int sizeY) {
        sprite.setSize(sizeX, sizeY);
        hitBox = new Rectangle(0, 0, sizeX, sizeY);
        this.setPosition(startX, startY);
        angle = startA;
    }

    public void update() {
        setPosition(sprite.getX() + (float)(speed * Math.cos(angle))* Gdx.graphics.getDeltaTime(), sprite.getY()+(float)(speed * Math.sin(angle))*Gdx.graphics.getDeltaTime());
        life -= Gdx.graphics.getDeltaTime();
    }

    public void setPosition(float x, float y) {
        sprite.setPosition(x, y);
        hitBox.setPosition(x, y);
    }

    public float getAngle() {
        return angle;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isDead() {
        return (life <= 0);
    }

    public void setSpeed(float s) {
        speed = s;
    }

    public Rectangle getHitBox() {
       return hitBox;
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public float getSpeed() {
        return speed;
    }
}
