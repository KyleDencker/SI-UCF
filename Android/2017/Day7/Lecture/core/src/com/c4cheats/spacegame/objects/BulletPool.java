package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.utils.Pool;

/**
 * Created by kyledencker on 6/15/17.
 */

public class BulletPool extends Pool<Lazer> {
    public Lazer newObject() {
        return new Lazer(0, 0, 0, 0);
    }
}
