package com.kyledencker.thetowergame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class FancyTower extends Shooter {

    ArrayList<Bullet> orbs;
    float orbswanRate, orbswanTime;
    float angle;


    public FancyTower(int x, int y) {
        super(x, y, "rapid");
        orbs = new ArrayList<Bullet>();
        orbswanRate = 5;
        orbswanTime = 5;
    }

    public void update(float deltaTime, ArrayList<Player> playerList) {
        super.update(deltaTime, playerList);
        angle+= deltaTime;
        orbswanTime+= deltaTime;
        if (orbswanTime > orbswanRate) {
            orbswanTime-=orbswanRate;
            orbs.add(new Bullet((int)getHitBox().x, (int)getHitBox().y, new Vector2(getHitBox().x, getHitBox().y)));
        }

        for (Bullet b : orbs) {
            b.setHitBox(getHitBox().x + (float)Math.cos(angle)* 50, getHitBox().y + (float)Math.sin(angle)* 50);
        }

    }

    public void draw(SpriteBatch b) {
        super.draw(b);
        for (Bullet or : orbs) {
            or.draw(b);
        }
    }
}
