package com.fhsps.clicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class BadCircles {
    private int speed = 300, acc = 10;
    private float vX, vY, posX, posY, radius;
    ShapeRenderer renderer;
    Color active;


    public BadCircles(float x, float y) {

        vX = 0;
        vY = 0;
        posX = x;
        posY = y;
        radius = 50;
        renderer = new ShapeRenderer();
        active = Color.RED;
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
    }

    public void draw(OrthographicCamera camera) {

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(active);
        renderer.circle(posX, posY, radius);
        renderer.end();
    }

    public void dispose() {

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

    public float getX() {
        return posX;
    }
    public float getY() {
        return posY;
    }
    public float getSize() {
        return radius;
    }


}
