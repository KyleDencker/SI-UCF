package com.kyledencker.thetowergame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kyledencker.thetowergame.manager.TextureManager;
import com.kyledencker.thetowergame.screens.MainGame;

import java.util.ArrayList;

public abstract class Shooter {

    private Sprite sprite;
    private Vector2 postion;
    private float fireRate, lastFire;

    public Shooter(int x, int y, String type) {
        if (type.equals("rapid")) {
            sprite = new Sprite(TextureManager.tower2);
        } else {
            sprite = new Sprite(TextureManager.tower);
        }
        sprite.setColor((float) Math.random(), (float) Math.random(), (float) Math.random(), 1);
        sprite.setPosition(x, y);
        sprite.setSize(50, 50);
        postion = new Vector2(x, y);
        fireRate = 1;
    }

    public void update(float deltaTime, ArrayList<Player> playerList) {
        lastFire += deltaTime;
        if (lastFire >= fireRate) {

            for (Player p : playerList) {
                float deltaX = p.getPosition().x - this.postion.x;
                float deltaY = p.getPosition().y - this.postion.y;
                double dist = Math.sqrt(deltaX*deltaX+deltaY*deltaY);
                if (dist <= 200) {
                    Vector2 center = new Vector2(p.getPosition().x + 25, p.getPosition().y + 25);

                    MainGame.bulletList.add(new Bullet((int)(postion.x + sprite.getWidth()/2), (int)(postion.y+sprite.getHeight()/2), center));
                    lastFire -= fireRate;
                    return;
                }
            }

        }
    }

    public void draw(SpriteBatch b) {
        sprite.draw(b);
    }

    public Rectangle getHitBox() {
        return new Rectangle(postion.x, postion.y, 50, 50);
    }

    public void clicked() {
        fireRate /= 2;
    }

    public float getFireRate() {
        return fireRate;
    }
    public float getLastFire() {
        return this.lastFire;
    }

    public void setFireRate(float f) {
        fireRate = f;
    }
    public void setLastFire(float f) {
        lastFire = f;
    }
}
