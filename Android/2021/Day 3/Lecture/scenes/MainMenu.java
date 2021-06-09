package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;

public class MainMenu implements Screen {

    SpriteBatch batch;
    Controller myGame;

    public MainMenu(Controller g) {
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 1, 0, 1);
        batch.begin();

        batch.end();

        if (Gdx.input.justTouched() && Gdx.input.getX() < 400) {
           myGame.setScreen(new BubblePopGame(myGame));
        } else if (Gdx.input.justTouched()) {
            myGame.setScreen(new SpaceGame(myGame));

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
        batch.dispose();
    }
}
