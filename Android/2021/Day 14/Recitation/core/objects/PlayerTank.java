package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.miketriana.tankgame.utils.AssetManager;

public class PlayerTank extends Tank {

    Sprite targetSprite;
    float targetX, targetY;

    public PlayerTank(float x, float y, Array<RectangleMapObject> walls) {
        super(x, y, walls);

        // Configure sprite
        sprite = AssetManager.getSprite("Player tank");
        sprite.setSize(32, 32);
        sprite.setCenter(x, y);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        targetX = x;
        targetY = y;
        targetSprite = AssetManager.getSprite("Target");
        targetSprite.setCenter(targetX, targetY);
    }

    @Override
    public void setPath (Queue<Vector2> newPath) {
        super.setPath(newPath);
        if (path.size > 0) {
            Vector2 last = path.last();
            targetX = last.x;
            targetY = last.y;
            targetSprite.setCenter(last.x, last.y);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        targetSprite.draw(batch);
        super.draw(batch);
    }
}
