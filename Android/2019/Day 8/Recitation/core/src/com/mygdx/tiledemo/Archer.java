package com.mygdx.tiledemo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Archer {
    Vector2 position;
    public Rectangle hitbox;
    float width = 16;
    float height = 16;
    Sprite sprite;

    public Archer(float x, float y){
        position = new Vector2(x,y);

        sprite = new Sprite(new Texture("archer1.png"));
        sprite.setSize(width, height);
        sprite.setPosition(x, y);

        hitbox = new Rectangle(x,y, width,height);
        hitbox.setSize(width, height);
        hitbox.setPosition(x,y);

    }

    public void draw(SpriteBatch b){
        sprite.draw(b);
    }

    public void updatePos(float x, float y){
        position.x = x;
        position.y = y;

        sprite.setPosition(x, y);
        hitbox.setPosition(x,y);
    }

    public float getX(){
        return position.x;
    }
    public float getY(){
        return position.y;
    }
}
