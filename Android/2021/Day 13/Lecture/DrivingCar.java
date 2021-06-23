package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;
import com.fhsps.clicker.objects.Car;

public class DrivingCar implements Screen {

    Controller myGame;

    SpriteBatch batch;
    OrthographicCamera camera;
    Car player;

    public DrivingCar(Controller g) {
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        player = new Car(300, 300);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        batch.end();

        player.update(delta);

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.accelerate();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.reverse();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.turnLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.turnRight();
        } else {
            player.straight();
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
