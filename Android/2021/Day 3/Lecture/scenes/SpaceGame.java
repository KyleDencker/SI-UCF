package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;
import com.fhsps.clicker.Ship;

import sun.rmi.rmic.Main;

public class SpaceGame implements Screen {
    SpriteBatch batch;
    Ship spaceShip;
    OrthographicCamera camera;

    Controller myGame;

    public SpaceGame(Controller g) {
        myGame = g;
    }






    @Override
    public void show() {
        batch = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        spaceShip = new Ship(475, 475);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        spaceShip.draw(batch);
        batch.end();

        spaceShip.update(Gdx.graphics.getDeltaTime());

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            spaceShip.input(0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            spaceShip.input(1);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            spaceShip.input(2);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            spaceShip.input(3);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
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

    public void dispose () {
        batch.dispose();
        spaceShip.dispose();
    }
}
