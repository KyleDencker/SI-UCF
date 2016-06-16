package com.c4cheats.mathyfish.Resources;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kyledencker on 6/16/16.
 */
public class TextureManager {

    public static Texture splash;

    public static Texture basicFish;
    public static Texture platform;

    public static Texture startButton;

    public static void loadSplash() {
        splash = new Texture("splash.jpg");
    }

    public static void disposeSplash() {

        splash.dispose();
    }

    public static void loadGame() {
        basicFish = new Texture("fish");
    }

    public static void disposeGame() {

        basicFish.dispose();
    }

    public static void loadMenus() {
        platform = new Texture("badlogic.jpg");
        basicFish = new Texture("fish");
        startButton = new Texture("menu/StartButton.png");
    }

    public static void disposeMenu() {
        platform.dispose();
        basicFish.dispose();
        startButton.dispose();
    }

    public static void loadGameOver() {

    }

    public static void disposeGameOver() {

    }
}
