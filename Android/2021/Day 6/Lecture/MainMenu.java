package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.BadCircles;
import com.fhsps.clicker.Controller;
import com.fhsps.clicker.Player;

import org.graalvm.compiler.core.common.type.ArithmeticOpTable;

import java.util.ArrayList;

public class MainMenu implements Screen {

    SpriteBatch batch;
    Controller myGame;
    OrthographicCamera camera;

    Player player;
    ArrayList<BadCircles> bcList = new ArrayList<BadCircles>();

    public MainMenu(Controller g) {
        myGame = g;
    }

    @Override
    public void show() {

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);

        player = new Player(500, 500);
        for (int i=0; i<19; i++) {
            bcList.add(new BadCircles((float) (Math.random()*1000), (float) (Math.random() * 1000)));
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.draw(camera);
        for (BadCircles bc : bcList) {
            bc.draw(camera);
        }


        batch.end();

        player.update(delta);
        for (BadCircles bc : bcList) {
            bc.update(delta);
            player.checkHit(bc.getX(), bc.getY(), bc.getSize());
        }
        camera.position.x = 500;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.input(1);
            System.out.println("Test");
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.input(3);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.input(0);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.input(2);
        }

        if (Gdx.input.justTouched() && Gdx.input.getX() < 400) {
          // myGame.setScreen(new BubblePopGame(myGame));
        } else if (Gdx.input.justTouched()) {
          //  myGame.setScreen(new SpaceGame(myGame));
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
        batch.dispose();
    }
}
