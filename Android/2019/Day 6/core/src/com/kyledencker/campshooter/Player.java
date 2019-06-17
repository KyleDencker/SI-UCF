package com.kyledencker.campshooter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Player {

    Sprite sprite;
    Rectangle hitBox;
    boolean facingLeft = false;
    float life = 10, maxLife = 10;
    float timer = 0;
    int frameCount = 0;
    int animate = 0;

    public Player(int x, int y, int text) {
        sprite = new Sprite(GameController.players[text]);
        hitBox = new Rectangle(x, y, 100, 100);
        sprite.setPosition(x, y);
        sprite.setSize(100, 100);
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public void update(float deltaTime) {
        sprite.setPosition(hitBox.x, hitBox.y);
        if (life < maxLife/2) {
            timer+= deltaTime;

            if (timer >= life / maxLife) {
                sprite.setColor(1, 0, 0, 1);
                frameCount++;
                if (frameCount > 5) {
                    timer -= life / maxLife;
                    frameCount = 0;
                }
            } else {
                sprite.setColor(1, 1, 1, 1);

            }
        }
    }

    public void updateSprite(int player) {
        sprite.setTexture(GameController.players[player]);
    }

    public void left() {
        hitBox.x-= 300* Gdx.graphics.getDeltaTime();
        if (!facingLeft) {
            sprite.flip(true, false);
        }
        animate++;
        if (animate > 10) {
            updateSprite((int) (Math.random()*7));
            animate=0;
        }
        facingLeft = true;
    }
    public void right() {
        hitBox.x+= 300* Gdx.graphics.getDeltaTime();
        if (facingLeft) {
            sprite.flip(true, false);
        }
        facingLeft = false;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public void gotHit() {
        life -= Gdx.graphics.getDeltaTime();
        System.out.println(life);
    }

}
