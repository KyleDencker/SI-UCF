package com.kyledencker.thetowergame.manager;

import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
    public static Texture[] units;
    public static Texture map, tower, bullet;

    public static void loadUnits() {

        tower = new Texture("tower.png");
        bullet = new Texture("bullet.png");
        map = new Texture("map.jpg");
        units = new Texture[7];
        for (int i=1; i<=7; i++) {
            units[i-1] = new Texture("units/"+ i + ".jpg");
        }
    }
}
