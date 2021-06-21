package com.fhsps.clicker.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    public static Sound die, hit, point, swoosh, wing;

    public static void loadSounds() {
        die = Gdx.audio.newSound(Gdx.files.internal("audio/die.ogg"));
        hit = Gdx.audio.newSound(Gdx.files.internal("audio/hit.ogg"));
        point = Gdx.audio.newSound(Gdx.files.internal("audio/point.ogg"));
        swoosh = Gdx.audio.newSound(Gdx.files.internal("audio/swoosh.ogg"));
        wing = Gdx.audio.newSound(Gdx.files.internal("audio/wing.ogg"));
    }

    public static void dispose() {
        die.dispose();
        hit.dispose();
        point.dispose();
        swoosh.dispose();
        wing.dispose();
    }
}
