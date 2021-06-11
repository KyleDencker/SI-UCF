package com.fhsps.clicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Timer {
    static Texture texture;
    Sprite sprite;
    boolean isDead = false;
    float time;

    public Timer(float x, float y) {
        if (texture == null) {
            texture = new Texture("badlogic.jpg");
        }
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(50, 50);
        time = 1;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void update(float delta) {
        time-= delta;
        if (time < 0) {
            isDead = true;
        }
    }

    public boolean isDead() {
        return isDead;
    }

    public void reset(float x, float y) {
        sprite.setPosition(x, y);
        isDead = false;
        time = 1;
    }
}
