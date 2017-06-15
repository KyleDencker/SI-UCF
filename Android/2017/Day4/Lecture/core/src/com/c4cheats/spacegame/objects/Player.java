package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.c4cheats.spacegame.manager.TextureManager;

/**
 * Created by kyledencker on 6/13/17.
 */

public class Player {

    public int lives;
    public Sprite sprite;
    private Rectangle hitBox;

    public Player( ) {
        sprite = new Sprite(TextureManager.playerTexture);
        sprite.setSize(50, 75);
        sprite.setPosition(400, 10);
        hitBox = new Rectangle(400, 10, 50, 75);
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public void update() {

    }

    public boolean doesHit(Rectangle o) {
        return o.overlaps(hitBox);
    }

    public void moveLeft() {
        sprite.setPosition(sprite.getX()-250* Gdx.graphics.getDeltaTime(), sprite.getY());
    }

    public void moveRight() {
        sprite.setPosition(sprite.getX()+250 * Gdx.graphics.getDeltaTime(), sprite.getY());

    }
}
