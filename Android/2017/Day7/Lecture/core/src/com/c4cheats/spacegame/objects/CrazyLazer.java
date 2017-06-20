package com.c4cheats.spacegame.objects;

import com.badlogic.gdx.Gdx;
import com.c4cheats.spacegame.manager.TextureManager;

/**
 * Created by kyledencker on 6/20/17.
 */

public class CrazyLazer extends Bullets {

    float timeOfLife = 0;

    public CrazyLazer(float lifeStart, int startX, int startY, float startA) {
        super(TextureManager.playerTexture, lifeStart, startX, startY, startA, 10, 10);
        setSpeed(150);
    }

    public void reset(float lifeStart, int startX, int startY, float startA) {
        super.reset(lifeStart, startX, startY, startA, 10, 10);
    }

    public void update() {
        super.update();
        timeOfLife += Gdx.graphics.getDeltaTime()*25;

        setPosition(getSprite().getX() ,
                    getSprite().getY() + (float)Math.cos(timeOfLife)*100 * Gdx.graphics.getDeltaTime());
    }
}
