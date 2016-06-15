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
public class Menu implements Screen {

    GameClass myGame;
    SpriteBatch batch;
    OrthographicCamera camera;
    double timer = 3;

    public Menu(GameClass g) {
        myGame = g;
    }

    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 500, 500);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();
        batch.end();

        timer -= delta;

        if (timer < 0) {
            myGame.setScreen(new MainGame(myGame));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            myGame.setScreen(new MainGame(myGame));
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