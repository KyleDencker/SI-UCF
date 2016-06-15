package com.c4cheats.samplegame.com.c4cheats.samplegame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.samplegame.Dots;
import com.c4cheats.samplegame.GameClass;

import java.util.ArrayList;

/**
 * Created by kyledencker on 6/15/16.
 */
public class MainGame implements Screen {


    SpriteBatch batch;
    public static Texture crate, chris;
    boolean crateTouched = false, isPaused = false;
    Rectangle touchPos, crateHitBox;
    int XSpeed = 150, YSpeed = 150;
    ArrayList<Dots> dropList = new ArrayList<Dots>();
    ArrayList<Dots> delete = new ArrayList<Dots>();

    OrthographicCamera camera;
    GameClass myGame;

    public MainGame(GameClass g) {
        myGame = g;
    }


    public void render(float delta) {
// draw
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        camera.update();

        batch.begin();

        if (crateTouched) {
            batch.draw(chris, 0, 0, 500, 500);
        } else {
            batch.draw(crate, crateHitBox.getX(), crateHitBox.getY());
        }
        for (Dots d : dropList) d.draw(batch);

        batch.end();


        // move
        if (!isPaused) {
            crateHitBox.y -= YSpeed * Gdx.graphics.getDeltaTime();

            for (Dots d : dropList) {
                d.update(Gdx.graphics.getDeltaTime());
                if (d.lifeSpan < 0)
                    delete.add(d);
            }
            while (!delete.isEmpty()){
                dropList.remove(delete.remove(0));
            }



            // check input
            if (Gdx.input.isTouched()) {
                Vector3 touch3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touch3);

                touchPos.x = touch3.x - 3;
                touchPos.y = touch3.y - 3;
                if (touchPos.overlaps(crateHitBox)) {
                    crateTouched = true;
                }

            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                crateHitBox.x -= XSpeed * Gdx.graphics.getDeltaTime();
                ;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                crateHitBox.x += XSpeed * Gdx.graphics.getDeltaTime();
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                crateHitBox.x = 250;
                crateHitBox.y = 600;
                crateTouched = false;
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
                myGame.setScreen(new GameOver(myGame, 10));
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                Vector3 touch3 = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touch3);

                dropList.add(new Dots((float  )(Math.random()*10+1), (int)touch3.x, (int)touch3.y));
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            isPaused= !isPaused;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }
    public void show() {
        batch = new SpriteBatch();
        touchPos = new Rectangle(-1000, -1000, 6, 6);
        crateHitBox = new Rectangle(250, 700, 36, 36);
        crate = new Texture("SmallCrate.png");
        chris = new Texture("chris.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 500, 500);
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
