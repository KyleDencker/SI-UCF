package com.c4cheats.spacegame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.spacegame.GameController;

/**
 * Created by kyledencker on 6/14/17.
 */

public class GameOver implements Screen {
    GameController myGame;
    SpriteBatch batch;

    public GameOver(GameController g) {
        myGame = g;
    }

    public void show() {
        batch = new SpriteBatch();
    }

    public void hide() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void resize(int x, int y) {

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            myGame.setScreen(new MainMenu(myGame));
        }
    }

    public void dispose() {

    }
}


