package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.miketriana.tankgame.utils.SpriteManager;

public class Mine extends GameObject {

    Rectangle hitBox;

    public Mine (float x, float y) {
        sprite = SpriteManager.getSprite("Mine");
        this.x = x;
        this.y = y;
        hitBox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
    }

    @Override
    public void draw(SpriteBatch batch) {
        sprite.setPosition(x, y);
        sprite.draw(batch);
    }

    @Override
    public void dispose() {

    }

    public Rectangle getHitBox() { return hitBox; }
}
