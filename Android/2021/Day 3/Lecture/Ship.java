package com.fhsps.clicker;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Ship {
    private int speed = 300, acc = 3;
    private float vX, vY, posX, posY;
    private Texture img;
    private Sprite sprite;

    public Ship(int x, int y) {
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);
        sprite.setSize(50, 50);
        sprite.setPosition(x, y);
        vX = 0;
        vY = 0;
        posX = x;
        posY = y;
    }

    public void update(float deltaTime) {
        posX += vX * deltaTime;
        posY += vY * deltaTime;
        if (posX > 1000) {
            posX = -50;
        }
        if (posY > 1000) {
            posY = -50;
        }
        if (posX < -50) {
            posX = 1000;
        }
        if (posY < -50) {
            posY = 1000;
        }
        sprite.setPosition(posX, posY);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void dispose() {
        img.dispose();
    }

    public void input(int direction) {
        switch (direction) {
            case 0:
                vY+= acc;
                break;
            case 1:
                vX+= acc;
                break;
            case 2:
                vY-= acc;
                break;
            case 3:
                vX-= acc;
        }
    }
}
