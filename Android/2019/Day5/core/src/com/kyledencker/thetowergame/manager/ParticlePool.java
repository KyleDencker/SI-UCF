package com.kyledencker.thetowergame.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Pool;

public class ParticlePool extends Pool<ParticleEffect> {

    @Override
    protected ParticleEffect newObject() {
        ParticleEffect temp = new ParticleEffect();
        temp.load(Gdx.files.internal("particle.p"), Gdx.files.internal(""));
        return temp;
    }



}
