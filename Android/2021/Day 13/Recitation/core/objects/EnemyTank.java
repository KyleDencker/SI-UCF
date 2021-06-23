package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.miketriana.tankgame.utils.Graph;
import com.miketriana.tankgame.utils.SpriteManager;

public class EnemyTank extends Tank {

    Pool<Missile> missilePool;
    Array<Missile> activeMissiles;
    float reloadTimer = 0;

    public EnemyTank(float x, float y, Array<RectangleMapObject> walls) {
        super(x, y, walls);

        // Configure sprite
        sprite = SpriteManager.getSprite("Enemy tank");
        sprite.setSize(32, 32);
        sprite.setCenter(x, y);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        missilePool = new Pool<Missile>() {
            @Override
            protected Missile newObject() {
                return new Missile();
            }
        };
        missilePool.fill(6);
        activeMissiles = new Array<>();
    }

    public void findGem(Array<Gem> gems, Graph graph) {
        if (path.size > 0)
            return;

        Gem closest = null;
        float closestDistance = Float.MAX_VALUE;
        for (Gem g : gems) {
            float distance = Vector2.dst(x, y, g.x, g.y);
            if (distance < closestDistance) {
                closest = g;
                closestDistance = distance;
            }
        }
        if (closest != null) {
            setPath(graph.findPath(x, y, closest.x, closest.y));
        }
    }

    public void attack (Tank t, float delta) {
        float distance = Vector2.dst(x, y, t.x, t.y);

        if (t.getHealth() > 0 && distance < 300 && missilePool.getFree() > 0 && reloadTimer <= 0) {
            Missile m = missilePool.obtain();
            m.setPosition(x, y);
            m.setDirection(t.x - x, t.y - y);
            activeMissiles.add(m);
            reloadTimer = 1;
        }

        for (Missile m : activeMissiles) {

            if (t.getHealth() > 0) {
                m.setDirection(t.x - m.x, t.y - m.y);
            }

            m.move();
            if (t.getHealth() > 0 && m.getHitBox().overlaps(t.getHitBox())) {
                t.damage(250);
                activeMissiles.removeValue(m, true);
                missilePool.free(m);
            }
            for (RectangleMapObject wall : walls) {
                if (wall.getRectangle().contains(m.x, m.y)) {
                    activeMissiles.removeValue(m, true);
                    missilePool.free(m);
                }
            }
        }
        if (reloadTimer > 0)
            reloadTimer -= delta;
    }

    @Override
    public void draw (SpriteBatch batch) {
        super.draw(batch);
        for (Missile m : activeMissiles) {
            m.draw(batch);
        }
    }
}
