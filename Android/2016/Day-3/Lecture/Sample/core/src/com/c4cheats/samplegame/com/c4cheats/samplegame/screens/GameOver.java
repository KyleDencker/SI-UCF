package com.c4cheats.samplegame.com.c4cheats.samplegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.samplegame.GameClass;

/**
 * Created by kyledencker on 6/15/16.
 */
public class GameOver implements Screen {

    GameClass myGame;
    SpriteBatch batch;
    OrthographicCamera camera;


    public GameOver(GameClass g, int score) {
        myGame = g;
    }

    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 500, 500);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            myGame.setScreen(new Menu(myGame));
        }
    }

    public void hide(){

    }
    public void resize(int x, int y) {

    }
    public void pause() {

    }
    public void resume() {

    }
    public void dispose() {

    }
}