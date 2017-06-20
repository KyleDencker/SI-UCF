package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.c4cheats.spacegame.manager.TextureManager;

/**
 * Created by kyledencker on 6/13/17.
 */

public class Lazer extends Bullets {


    public Lazer(float lifeStart, int startX, int startY, float startA) {
        super(TextureManager.bulletTexture, lifeStart, startX, startY, startA, 10, 10);
        setSpeed(250);
    }

    public void reset(float lifeStart, int startX, int startY, float startA) {
        super.reset(lifeStart, startX, startY, startA, 10, 10);
    }




}
