package com.fhsps.clicker.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.fhsps.clicker.tools.TextureManager;

import java.awt.Rectangle;

public class Jack {
    Sprite sprite;
    int vY, color, frame;
    float time;
    Rectangle hitBox;


    public Jack() {
        color = (int) (Math.random() * TextureManager.BIRD.length);
        sprite = new Sprite(TextureManager.BIRD[color][0]);
        sprite.setSize(25, 25);
        sprite.setPosition(25, 600);
        vY = 0;
        frame = 0;
        time = 0;
        hitBox = new Rectangle(25, 600, 25, 25);

    }

    public void update(float delta) {
        time+= delta;
        if (time < 1) {
            frame++;
            frame %= TextureManager.BIRD[color].length;
            sprite.setTexture(TextureManager.BIRD[color][frame]);
            time=0;
        }

        vY -= 25;
        hitBox.y += vY * delta;

        if (hitBox.y < 0) {
            hitBox.y = 0;
            vY = 0;
        }
        sprite.setY(hitBox.y);

    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void touch() {
        vY = 500;
    }

    public float getY() {
        return hitBox.y;
    }
}
