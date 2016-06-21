package com.c4cheats.mathyfish.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyledencker on 6/16/16.
 */
public interface Fish {

    public void update(float delta);
    public void draw(SpriteBatch batch);
    public void hit();
    public void hit(int option);
    public Vector2 getPosition();
    public Rectangle getHitBox();
    public Vector2 getVelocity();
    public boolean isDead();
    public void setPosition(float x, float y);
    public void setVelocity(float x, float y);
    public void alive();
    public int getValue();
    public void setValue(int value);


}
