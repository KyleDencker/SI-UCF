package com.libgdxprojects.learning;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Player
{
    Texture texture;
    Sprite sprite;

    Rectangle hitBox;

    int movementSpeed = 1;

    public float velX = 0, velY = 0;


    public Player(int x, int y)
    {
        texture = new Texture("playerSprite.png");
        sprite = new Sprite(texture);

        sprite.setRegion(0, 0, 64, 64);



        sprite.setSize(24,24);
        sprite.setPosition(x, y);

        hitBox = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());

    }

    public void update()
    {
        if (velX > 0)
        {
            sprite.setRegion(0, 128, 64, 64);
        }

        if (velX < 0)
        {
            sprite.setRegion(0, 64, 64, 64);
        }

        if (velY < 0)
        {
            sprite.setRegion(0, 0, 64, 64);
        }

        if (velY > 0)
        {
            sprite.setRegion(0, 196, 64, 64);
        }

        sprite.setPosition(sprite.getX() + velX, sprite.getY() + velY);
        hitBox.setPosition(sprite.getX() + velX, sprite.getY() + velY);
    }

    public void draw(SpriteBatch batch)
    {
        sprite.draw(batch);
    }


}
