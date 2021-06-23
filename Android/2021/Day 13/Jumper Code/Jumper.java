package com.fhsps.clicker.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Jumper {

    Rectangle hitBox;
    static Texture texture;
    Sprite sprite;
    float vY;

    public Jumper(int x, int y) {
        hitBox = new Rectangle(x, y, 25, 25);
        texture = new Texture("sprites/0.png");
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
        sprite.setSize(25, 25);
        vY = 0;
    }

    public void update(float delta) {
        vY -= 200 * delta;
        hitBox.y += vY *delta;
        sprite.setPosition(hitBox.x, hitBox.y);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public boolean checkHit(Rectangle r) {
        if (vY > 0) return false;
        return r.overlaps(hitBox);
    }
    public void jump() {
        vY = 200;
    }

    public void left() {
        hitBox.x -= 5;
    }
    public void right() {
        hitBox.x += 5;
    }

    public float getY() {
        return hitBox.y;
    }
}
