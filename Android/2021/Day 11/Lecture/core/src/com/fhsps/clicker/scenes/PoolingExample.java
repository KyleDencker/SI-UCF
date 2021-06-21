package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;
import com.fhsps.clicker.Timer;
import com.fhsps.clicker.tools.TimerPool;

import java.util.ArrayList;

public class PoolingExample implements Screen {

    Controller myGame;
    SpriteBatch batch;
    OrthographicCamera camera;
    TimerPool pool;
    ArrayList<Timer> timeList = new ArrayList<Timer>();

    public PoolingExample(Controller g) {
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        pool = new TimerPool();
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Timer t : timeList) {
            t.draw(batch);
        }

        batch.end();

        for (int i=0; i<timeList.size(); i++) {
            timeList.get(i).update(delta);
            if (timeList.get(i).isDead()) {
                pool.free(timeList.remove(i));
            }
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            Timer temp = pool.obtain();
            temp.reset(touchPos.x, touchPos.y);
            timeList.add(temp);
        }
        System.out.println("Active: " + timeList.size() + " Pool: " + pool.getFree() + " Max: " + pool.peak);
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
