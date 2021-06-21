package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;

public class BPGameOver implements Screen {

    Controller myGame;

    public BPGameOver(Controller g, int score) {
        myGame = g;
        System.out.println(score);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 1, 1);

        if (Gdx.input.justTouched()) {
            myGame.setScreen(new MainMenu(myGame));
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
