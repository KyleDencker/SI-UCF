package com.miketriana.tankgame.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public abstract class GameObject implements Disposable {
    Sprite sprite;

    float x, y;

    public abstract void draw (SpriteBatch batch);

    public abstract void dispose ();

    public Vector2 getPosition () {return new Vector2(x, y); }
}
