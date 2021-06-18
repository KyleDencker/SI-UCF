package com.fhsps.clicker.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.fhsps.clicker.scenes.FlappyJack;
import com.fhsps.clicker.tools.TextureManager;


public class Pipe {

    Sprite sprite, sprite2;
    Rectangle hitBox, hitBox2;
    boolean isPassed = false;

    public Pipe(int height, int color) {
        sprite = new Sprite(TextureManager.PIPE[color]);
        sprite.setSize(50, height);
        sprite.setPosition(500, 0);

        hitBox = new Rectangle(500, 0, 50, height);
        hitBox2 = new Rectangle(500, height + 150, 50, 900 - height - 150);
        sprite2 = new Sprite(TextureManager.PIPE[color]);
        sprite2.flip(true, true);
        sprite2.setPosition(hitBox2.x, hitBox.y);
        sprite2.setSize(hitBox2.width, hitBox2.height);
    }

    public void update(float delta) {
        hitBox.x -= FlappyJack.SPEED * delta;
        sprite.setPosition(hitBox.x, hitBox.y);
        hitBox2.x -= FlappyJack.SPEED * delta;
        sprite2.setPosition(hitBox2.x, hitBox2.y);
    }

    public float getX() {
        return hitBox.x;
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
        sprite2.draw(batch);
    }

    public boolean doesHit(Rectangle pHitbox) {
        return hitBox.overlaps(pHitbox) || hitBox2.overlaps(pHitbox);
    }

    public boolean isPassed() {
        return isPassed;
    }

    public void pass(){
        isPassed = true;
    }

}
