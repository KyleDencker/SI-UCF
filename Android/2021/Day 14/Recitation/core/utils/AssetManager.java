package com.miketriana.tankgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.HashMap;

public class AssetManager {
    static Texture objectSprites;
    static Texture hudSprites;
    static HashMap<String, Sprite> spriteSet;
    static HashMap<String, Sound> soundSet;

    public static void loadSprites () {
        objectSprites = new Texture("objects.png");
        hudSprites = new Texture("hud.png");
        spriteSet = new HashMap<>();
        spriteSet.put("Player tank", new Sprite(objectSprites, 0, 0, 32, 32));
        spriteSet.put("Enemy tank", new Sprite(objectSprites, 32, 0, 32, 32));
        spriteSet.put("Target", new Sprite(objectSprites, 32, 32, 16, 16));
        spriteSet.put("Mine", new Sprite(objectSprites, 0, 32, 16, 16));
        spriteSet.put("Blue gem", new Sprite(objectSprites, 16, 32, 16, 16));
        spriteSet.put("Pink gem", new Sprite(objectSprites, 16, 48, 16, 16));
        spriteSet.put("Missile", new Sprite(objectSprites, 0, 48, 16, 16));

        spriteSet.put("Health bar bg", new Sprite(hudSprites, 0, 0, 64, 8));
        spriteSet.put("Health bar", new Sprite(hudSprites, 0, 8, 64, 8));
        spriteSet.put("Arrow button", new Sprite(hudSprites, 0, 16, 16, 16));
        spriteSet.put("Plus button", new Sprite(hudSprites, 16, 16, 16, 16));
        spriteSet.put("Minus button", new Sprite(hudSprites, 32, 16, 16, 16));

        soundSet = new HashMap<>();
        soundSet.put("Explosion", Gdx.audio.newSound(Gdx.files.internal("boom.wav")));
        soundSet.put("Pickup", Gdx.audio.newSound(Gdx.files.internal("pickup.wav")));
    }

    public static Sprite getSprite(String name) {
        return spriteSet.get(name);
    }

    public static Sound getSound(String name) { return soundSet.get(name); }

    public static void disposeAssets() {
        objectSprites.dispose();
        hudSprites.dispose();
        for (Sound s : soundSet.values()) {
            s.dispose();
        }
    }
}
