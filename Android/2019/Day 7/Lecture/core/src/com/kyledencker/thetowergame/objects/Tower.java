package com.kyledencker.thetowergame.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kyledencker.thetowergame.manager.TextureManager;
import com.kyledencker.thetowergame.screens.MainGame;

import java.util.ArrayList;

public class Tower extends Shooter {

    public Tower(int x, int y) {
       super(x, y, "tower");
    }

}
