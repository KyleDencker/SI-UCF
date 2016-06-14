package com.c4cheats.samplegame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.c4cheats.samplegame.GameClass;

/**
 * Created by kyledencker on 6/14/16.
 */
public class Dots {

    Rectangle hitBox;
    Sprite sprite;
    float lifeSpan;

    public Dots(float life, int x, int y) {
        this.hitBox = new Rectangle(x, y, 36, 36);
        sprite = new Sprite(GameClass.crate);
        lifeSpan = life;
        setPosition(x, y);
    }

    public void update(double delta){
        lifeSpan -= delta;
    }

    public void setPosition(int x, int y) {
        hitBox.x = x-18;
        hitBox.y = y-18;
        sprite.setPosition(hitBox.x, hitBox.y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

}
