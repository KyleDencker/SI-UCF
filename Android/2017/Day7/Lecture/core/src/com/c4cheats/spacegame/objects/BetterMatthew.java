package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.c4cheats.spacegame.manager.TextureManager;

/**
 * Created by kyledencker on 6/16/17.
 */

public class BetterMatthew {

    public int lives;
    public Sprite sprite;
    private Rectangle hitBox;
    private float nextframe;
    private int headIndex = 0;
    public Sprite head;

    public BetterMatthew( ) {
        sprite = new Sprite(TextureManager.matthewTexture);
        head = new Sprite(TextureManager.matthewHeadTexture[0]);

        sprite.setSize(200, 200);
        head.setSize(200, 200);
        sprite.setPosition(400, 400);
        head.setPosition(400, 400);

        hitBox = new Rectangle(400, 400, 200, 200);
        nextframe = .2f;
    }

    public void draw(SpriteBatch b) {

        sprite.draw(b);
        head.draw(b);
    }

    public void update() {
        float newX = (float)(Math.random()*600-300)* Gdx.graphics.getDeltaTime();
        float newY = (float)(Math.random()*600-300)* Gdx.graphics.getDeltaTime();
        if (sprite.getX() + newX > 800 - sprite.getWidth()) {
            newX *= -1;
        }
        if (sprite.getX() + newX < 0) {
            newX *= -1;
        }
        if (sprite.getY() + newY > 600 - sprite.getHeight()) {
            newY *= -1;
        }
        if (sprite.getY() + newY < 0) {
            newY *= -1;
        }
        sprite.setPosition(sprite.getX()+newX, sprite.getY()+newY);
        head.setPosition(sprite.getX(), sprite.getY());
        hitBox.setPosition(sprite.getX(), sprite.getY());
        nextframe -= Gdx.graphics.getDeltaTime();
        if (nextframe <= 0) {
            headIndex++;
            head.setTexture(TextureManager.matthewHeadTexture[headIndex%TextureManager.matthewHeadTexture.length]);
            nextframe = .2f;
        }

    }

    public boolean doesHit(Rectangle o) {
        return o.overlaps(hitBox);
    }
}
