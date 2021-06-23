package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;
import com.fhsps.clicker.objects.Jumper;
import com.fhsps.clicker.objects.Car;

import java.util.ArrayList;

public class DrivingCar implements Screen {

    Controller myGame;

    SpriteBatch batch;
    OrthographicCamera camera;
    Jumper player;

    ArrayList<Rectangle> rectList;
    Texture rectangles = new Texture("sprites/0.png");
    int deathLine = 0;

    public DrivingCar(Controller g) {
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        player = new Jumper(300, 100);

        rectList = new ArrayList<Rectangle>();
        for (int i=0; i<100; i++) {
            rectList.add(new Rectangle((float) Math.random()* 700, (float) Math.random() * 1100, 100, 20));
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(batch);
        for (Rectangle r : rectList) {
            batch.draw(rectangles, r.x, r.y, r.width, r.height);
        }
        batch.end();


        player.update(delta);
        for (Rectangle r: rectList) {
            if (player.checkHit(r)) {
                player.jump();
            }
        }

        if (player.getY() < deathLine) {
            player.jump();
        }

        if (player.getY() - 300 > deathLine) {
            deathLine = (int) player.getY() - 300;
            camera.position.y = player.getY()+12.5f;
            camera.update();

            rectList.add(new Rectangle((float) Math.random()* 700, (float) Math.random() * 200 + deathLine+640, 100, 20));

        }



        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.left();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.right();
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
