package com.kyledencker.thetowergame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kyledencker.thetowergame.GameController;

public class MainMenu implements Screen {
    SpriteBatch batch;

    GameController myGame;
    float red = 0, time = 0;
    boolean changing;

    public MainMenu(GameController g) {
        myGame = g;
        changing = false;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(red, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();

        if (Gdx.input.justTouched()) {
            changing = true;
        }

        if (changing) {
            time += Gdx.graphics.getDeltaTime();
            red  += time;
            if (red > 1) red = 1;
        }
        if (time > 1f) {
            myGame.setScreen(new MainGame(myGame));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
