package com.c4cheats.spacegame.manager;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kyledencker on 6/15/17.
 */

public class TextureManager {
    public static Texture bulletTexture;
    public static Texture playerTexture;
    public static Texture matthewTexture;
    public static Texture[] matthewHeadTexture;

    public static void loadGame() {
        bulletTexture = new Texture("player.png");
        playerTexture = new Texture("player.png");
        matthewTexture = new Texture("better_matthew_body.png");
        matthewHeadTexture = new Texture[6];
        matthewHeadTexture[0] = new Texture("better_matthew_head1.png");
        matthewHeadTexture[1] = new Texture("better_matthew_head2.png");
        matthewHeadTexture[2] = new Texture("better_matthew_head3.png");
        matthewHeadTexture[3] = new Texture("better_matthew_head4.png");
        matthewHeadTexture[4] = new Texture("better_matthew_head3.png");
        matthewHeadTexture[5] = new Texture("better_matthew_head2.png");

    }

    public static void unloadGame() {
        bulletTexture.dispose();
        playerTexture.dispose();
        matthewTexture.dispose();
    }

    public static void loadMenu() {

        playerTexture = new Texture("player.png");
        bulletTexture = new Texture("better_matthew_body.png");
    }

    public static void unloadMenu() {
        playerTexture.dispose();
    }

}
