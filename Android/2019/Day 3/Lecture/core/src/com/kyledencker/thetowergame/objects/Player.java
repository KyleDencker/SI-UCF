package com.kyledencker.thetowergame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kyledencker.thetowergame.manager.TextureManager;

public class Player {

    private Vector2 position, target;
    private Sprite sprite;
    int size = 50;
    private Rectangle hitBox;
    private static int amount=0;
    private Vector2[] path;
    private int leg = 0;
    private int speed;
    boolean isDone = false;

    public Player(int x, int y, Vector2[] path) {
        this.path = path;
        position = new Vector2(x, y);
        target   = new Vector2(x, y);
        sprite   = new Sprite(TextureManager.units[amount%7]);
        amount++;
        sprite.setSize(50, 50);
        sprite.setPosition(x, y);
        hitBox = new Rectangle(x, y, 50, 50);
        speed = 50 + (int)(Math.random() * 200);

    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void update(float deltaTime) {
        speed = 50 + (int)(Math.random() * 500);

        target = path[leg];
        float deltaX = target.x - position.x;
        float deltaY = target.y - position.y;

        float distance = (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);

        if (distance < 3) {
            distance = 0;
            leg++;
            if (leg >= path.length) {
                leg = path.length-1;
                isDone = true;
            }
        }

        if (distance != 0) {
            float px = deltaX / distance;
            float py = deltaY / distance;

            position.x += px * speed * deltaTime;
            position.y += py * speed * deltaTime;
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

    public boolean isDone() {
        return isDone;
    }

}
