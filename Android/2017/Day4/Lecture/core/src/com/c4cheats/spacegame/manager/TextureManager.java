package com.c4cheats.spacegame.manager;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kyledencker on 6/15/17.
 */

public class TextureManager {
    public static Texture bulletTexture;
    public static Texture playerTexture;

    public static void loadGame() {
        bulletTexture = new Texture("spaceship.png");
        playerTexture = new Texture("spaceship.png");
    }

    public static void unloadGame() {
        bulletTexture.dispose();
        playerTexture.dispose();
    }

    public static void loadMenu() {
        playerTexture = new Texture("spaceship.png");
    }

    public static void unloadMenu() {
        playerTexture.dispose();
    }

}
