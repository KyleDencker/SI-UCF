package com.kyledencker.thetowergame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kyledencker.thetowergame.GameController;

public class GameOver implements Screen {

    SpriteBatch batch;
    GameController myGame;
    Texture winner;

    public GameOver(GameController g, int w) {
        myGame = g;
        this.winner = new Texture("units/"+w+".jpg");
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(winner, 0, 0, 300, 300);
        batch.end();

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
