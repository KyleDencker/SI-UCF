package com.libgdxprojects.learning;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Player
{
    Texture texture;
    Sprite sprite;

    Rectangle hitBox;

    int movementSpeed = 50;

    public float velX = 0, velY = 0;

    int row, col;
    float timer;
    int offset;

    boolean foundDoor;

    Array<RectangleMapObject> array, doorArray;


    public Player(int x, int y, Array<RectangleMapObject> arr, Array<RectangleMapObject> doorArr)
    {
        texture = new Texture("playerSprite.png");
        sprite = new Sprite(texture);

        sprite.setRegion(0, 0, 64, 64);

        sprite.setSize(24,24);
        sprite.setPosition(x, y);

        hitBox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());

        array = arr;
        doorArray = doorArr;

    }

    public void update(float deltaTime)
    {
        if (velX > 0)
        {
            row = 2;
            offset = 6;
        }

        if (velX < 0)
        {
            row = 1;
            offset = 5;
        }

        if (velY < 0)
        {
            row = 0;
            offset = 0;

        }

        if (velY > 0)
        {
            row = 3;
            offset = 10;
        }

        if (velX != 0 || velY != 0)
        {
            timer += deltaTime;

            if (timer >= 0.1f)
            {
                timer = 0;
                col++;

                if (col == 4)
                {
                    col = 0;
                }

                sprite.setRegion(col * 64, row * 64 + offset, 64, 64);
            }
        }

        float oldX = sprite.getX();
        float oldY = sprite.getY();

        sprite.setPosition(sprite.getX() + velX * deltaTime, sprite.getY() + velY * deltaTime);
        hitBox.setPosition(sprite.getX() + velX * deltaTime, sprite.getY() + velY * deltaTime);

        for (RectangleMapObject rectangleObject : array)
        {
            Rectangle rectangle = rectangleObject.getRectangle();

            if (rectangle.overlaps(hitBox))
            {
                sprite.setPosition(oldX, oldY);
                hitBox.setPosition(oldX, oldY);
            }
        }

        for (RectangleMapObject rectangleMapObject : doorArray)
        {
            Rectangle rectangle = rectangleMapObject.getRectangle();

            if (rectangle.overlaps(hitBox))
            {
                System.out.println("Walked through a door");
                sprite.setPosition(oldX, oldY);
                hitBox.setPosition(oldX, oldY);

                foundDoor = true;
            }


        }
    }

    public Vector2 getPosition()
    {
        return new Vector2(sprite.getX(), sprite.getY());
    }

    public void setPosition(float x, float y)
    {
        sprite.setPosition(x, y);
        hitBox.setPosition(x, y);
    }

    public boolean foundDoor()
    {
        return foundDoor;
    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public void dispose()
    {
        texture.dispose();
    }


}
