package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Mine extends GameObject {

    public Mine (float x, float y) {
        texture = new Texture("mine.png");
        sprite = new Sprite(texture);
        this.x = x;
        this.y = y;
        sprite.setPosition(x, y);
        sprite.setSize(50, 50);
        hitBox = new Rectangle(x, y, 50, 50);
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
