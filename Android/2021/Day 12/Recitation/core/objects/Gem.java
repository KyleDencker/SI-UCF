package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.miketriana.tankgame.utils.SpriteManager;

public class Gem extends GameObject {

    Circle hitBox;
    int value;

    public Gem (int value) {
        sprite = SpriteManager.getSprite("Blue gem");
        if (value == 25) {
            sprite = SpriteManager.getSprite("Pink gem");
        }
        hitBox = new Circle(x, y, 8);
        this.value = value;
    }

    public void setPosition (float x, float y) {
        this.x = x;
        this.y = y;
        hitBox.setPosition(x, y);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setCenter(x, y);
        sprite.draw(batch);
    }

    @Override
    public void dispose() {

    }

    public Circle getHitBox () { return hitBox; }

    public int getValue () { return value; }
}
