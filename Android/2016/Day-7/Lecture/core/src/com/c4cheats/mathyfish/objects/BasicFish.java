package com.c4cheats.mathyfish.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.c4cheats.mathyfish.Resources.TextureManager;

/**
 * Created by kyledencker on 6/16/16.
 */
public class BasicFish implements Fish{

    Rectangle hitBox;
    Vector2 velocity;
    Sprite sprite;
    int hitPoints;
    private double touchable;
    float lifeCounter;
    int value;



    public BasicFish(int x, int y) {
        hitBox = new Rectangle(x, y, 125, 100);
        sprite = new Sprite(TextureManager.basicFish);
        sprite.setColor((float)Math.random()*.6f+.4f, (float)Math.random()*.6f+.4f, (float)Math.random()*.6f+.4f, 1);
        sprite.setSize(125, 100);
        velocity = new Vector2(0,0);
        this.setPosition(x, y);
        this.hitPoints = 3;
        lifeCounter = 3;
    }

    @Override
    public void update(float delta) {
        float x = hitBox.x + velocity.x*delta;
        float y = hitBox.y + velocity.y*delta;
        setPosition(x,y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void hit() {
        hitPoints--;
    }

    @Override
    public void hit(int option) {
        hitPoints--;
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(hitBox.x, hitBox.y);
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public boolean isDead() {
        if (lifeCounter > 0) return false;
        return true;
    }

    @Override
    public void setPosition(float x, float y) {
        hitBox.x = x;
        hitBox.y = y;
        sprite.setPosition(x, y);
    }

    @Override
    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    public void alive() {
        lifeCounter = 3;
    }
}
