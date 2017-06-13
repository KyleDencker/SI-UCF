package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by kyledencker on 6/13/17.
 */

public class Lazer {

    Sprite sprite;
    Texture texture;
    public float life;
    private float speed = 150;

    public Lazer(float lifeStart, int startX, int startY) {
        life = lifeStart;
        texture = new Texture("spaceship.png");
        sprite = new Sprite(texture);
        sprite.setPosition(startX, startY);
        sprite.setSize(10, 30);
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public void update() {
        sprite.setPosition(sprite.getX(), sprite.getY()+speed * Gdx.graphics.getDeltaTime());
        life -= Gdx.graphics.getDeltaTime();
    }

}
