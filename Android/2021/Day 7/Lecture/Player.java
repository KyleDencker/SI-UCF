package com.fhsps.clicker;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private int speed = 300, acc = 10;
    private float vX, vY, posX, posY, radius;
    ShapeRenderer renderer;
    Color active;


    public Player(int x, int y) {

        vX = 0;
        vY = 0;
        posX = x;
        posY = y;
        radius = 50;
        renderer = new ShapeRenderer();
        active = Color.GOLD;
    }

    public void update(float deltaTime) {
        float speed = (float) (Math.sqrt(vX * vX + vY * vY));
        if (speed > 500) {
            float angle = (float) Math.atan2(vY, vX);
            vX = (float) (500 * Math.cos(angle));
            vY = (float) (500 * Math.sin(angle));
        }
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
        active = Color.GOLD;

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

    public boolean checkHit(float x, float y, float r) {

        float deltaX = this.posX - x;
        float deltaY = this.posY - y;
        float distance = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY));

        float radiusTotal = r + this.radius;
        if (distance <= radiusTotal) {
            active = Color.GREEN;

            float angle = (float) Math.atan2(deltaY, deltaX);
            posX = x + (float) (radiusTotal * Math.cos(angle));
            posY = y + (float) (radiusTotal * Math.sin(angle));

            Vector2 tangentVector = new Vector2(y - this.posY, -(x - this.posX));
            tangentVector = tangentVector.nor();
            Vector2 velocity = new Vector2(vX, vY);

            float length = velocity.dot(tangentVector);

            Vector2 VelocityComponentOnTangent = tangentVector.scl(length);

            Vector2 answer = velocity.sub(VelocityComponentOnTangent);


            vX = -2*answer.x;
            vY = -2*answer.y;



            return true;
        }
        return false;
    }
}
