package com.kyledencker.thetowergame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {

    private Vector2 position, target;
    private Texture texture;
    private Sprite sprite;
    int size = 50;
    private Rectangle hitBox;

    public Player(int x, int y) {
        position = new Vector2(x, y);
        target   = new Vector2(x, y);
        texture  = new Texture("badlogic.jpg");
        sprite   = new Sprite(texture);
        sprite.setSize(50, 50);
        sprite.setPosition(x, y);
        hitBox = new Rectangle(x, y, 50, 50);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void update(float deltaTime) {

        float deltaX = target.x - position.x;
        float deltaY = target.y - position.y;

        float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

        if (distance < 1) {
            distance = 0;
        }

        if (distance != 0) {
            float px = deltaX / distance;
            float py = deltaY / distance;

            position.x += px; // * speed * deltaTime;
            position.y += py; // * speed * deltaTime;
            sprite.setPosition(position.x, position.y);
            hitBox.x = position.x;
            hitBox.y = position.y;

        } else { // grow and strink


            size++;
            if (size > 100) {
                size = -100;
            }
            sprite.setSize(10 + Math.abs(size), 10 + Math.abs(size));
            //sprite.setSize(size, size);
            hitBox.width = sprite.getWidth();
            hitBox.height = sprite.getHeight();
        }
    }

    public void setTarget(Vector2 t) {
        target = t;
    }

    public boolean doesHit(Rectangle check) {
        return check.overlaps(hitBox);
    }

    public void reset() {
        sprite.setPosition(0, 0);
        position.x = 0;
        position.y = 0;
        hitBox.x = position.x;
        hitBox.y = position.y;
        hitBox.width = sprite.getWidth();
        hitBox.height = sprite.getHeight();
    }


}
