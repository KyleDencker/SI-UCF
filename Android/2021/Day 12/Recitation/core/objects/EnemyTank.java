package com.miketriana.tankgame.objects;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.miketriana.tankgame.utils.Graph;
import com.miketriana.tankgame.utils.SpriteManager;

public class EnemyTank extends Tank {

    public EnemyTank(float x, float y, Array<RectangleMapObject> walls) {
        super(x, y, walls);

        // Configure sprite
        sprite = SpriteManager.getSprite("Enemy tank");
        sprite.setSize(32, 32);
        sprite.setCenter(x, y);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
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
}
