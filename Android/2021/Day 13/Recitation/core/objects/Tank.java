package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.miketriana.tankgame.utils.SpriteManager;

public abstract class Tank extends GameObject {

    // Use a circle for hitbox to make things easier with rotation
    Circle hitBox;
    Array<RectangleMapObject> walls;
    Queue<Vector2> path;

    ShapeRenderer sr;


    float respawnTime = 0;
    float tankSpeed = 1;

    float health = 1000;
    int score = 0;

    public Tank (float x, float y, Array<RectangleMapObject> walls) {

        this.walls = walls;
        path = new Queue<>();

        this.x = x;
        this.y = y;

        // Configure hitbox
        hitBox = new Circle(0, 0, 12.8f);
        hitBox.setPosition(x, y);

        sr = new ShapeRenderer();
    }

    // Set the path for the tank to follow
    public void setPath (Queue<Vector2> newPath) {
        path = newPath;
    }

    public void move (Tank otherTank) {
        if (path.size > 0) {
            Vector2 next = path.first();
            float oldX = x;
            float oldY = y;
            Vector2 distanceVector = new Vector2(next.x - x, next.y - y);
            float distanceFromTarget = distanceVector.dst(0, 0);
            if (distanceFromTarget > 1) {
                x += distanceVector.x / distanceFromTarget * tankSpeed;
                y += distanceVector.y / distanceFromTarget * tankSpeed;
            } else {
                path.removeFirst();
            }

            // Check collisions
            hitBox.setPosition(x, y);
            for (RectangleMapObject wall : walls) {
                if (collidesWithRect(wall.getRectangle())) {
                    x = oldX;
                    y = oldY;
                }
            }
            if (otherTank.getHealth() > 0 && otherTank.getHitBox().overlaps(hitBox)) {
                x = oldX;
                y = oldY;
            }

            sprite.setRotation(distanceVector.angleDeg() - 90);
            sprite.setCenter(x, y);
            hitBox.setPosition(x, y);
        }
    }

    public boolean collidesWithRect (Rectangle r) {
        return Intersector.overlaps(hitBox, r);
    }

    public void draw (SpriteBatch batch) {
        sprite.draw(batch);
    }

    public void drawHitBox (OrthographicCamera camera) {
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLACK);
        sr.circle(hitBox.x, hitBox.y, hitBox.radius);
        sr.end();
    }

    public void drawPath (OrthographicCamera camera) {
        sr.setProjectionMatrix(camera.combined);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.BLUE);
        if (path.size > 0)
            sr.line(new Vector2(x, y), path.first());
        for (int i = 0; i < path.size - 1; i++) {
            sr.line(path.get(i), path.get(i + 1));
        }
        sr.end();
    }

    public void dispose () {
        sr.dispose();
    }

    public void damage (float amount) {
        if (health > 0) {
            health -= amount;
            sprite.setColor(1, health / 1000, health / 1000, 1);
        }
        if (health <= 0) {
            respawnTime = 5;
            path.clear();
            score -= 100;
            if (score < 0) {
                score = 0;
            }
        }
    }

    public void waitForRespawn (float x, float y, float delta) {
        respawnTime -= delta;
        if (respawnTime <= 0) {
            this.x = x;
            this.y = y;
            hitBox.setPosition(x, y);
            sprite.setCenter(x, y);
            sprite.setRotation(0);
            sprite.setColor(1, 1, 1, 1);
            health = 1000;
        }
    }

    public void addPoints (int amount) {
        score += amount;
        System.out.println(score);
    }

    public float getRotation () { return sprite.getRotation(); }

    public float getHealth () { return health; }

    public int getScore () { return score; }

    public Circle getHitBox () { return hitBox; }

    public float getRespawnTime () { return respawnTime; }

}
