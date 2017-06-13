package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kyledencker on 6/13/17.
 */

public class Player {

    public int lives;
    public Texture text;
    public Sprite sprite;

    public Player( ) {
        text = new Texture("spaceship.png");
        sprite = new Sprite(text);
        sprite.setSize(50, 75);
        sprite.setPosition(400, 10);
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public void update() {

    }

    public void moveLeft() {
        sprite.setPosition(sprite.getX()-250* Gdx.graphics.getDeltaTime(), sprite.getY());
    }

    public void moveRight() {
        sprite.setPosition(sprite.getX()+250 * Gdx.graphics.getDeltaTime(), sprite.getY());

    }
}
