package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;
import com.fhsps.clicker.Player;
import com.fhsps.clicker.objects.Jack;
import com.fhsps.clicker.tools.TextureManager;

public class FlappyJack implements Screen {

    Controller myGame;

    SpriteBatch batch;
    OrthographicCamera camera;

    Jack player;
    Sprite background, start, gameOver;
    int gameState = 0;


    public FlappyJack(Controller g) {
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);

        TextureManager.loadNumbers();
        TextureManager.loadSceen();

        player = new Jack();


        background = new Sprite(TextureManager.BACKGROUND[(int)(Math.random()*TextureManager.BACKGROUND.length)]);
        background.setSize(480, 800);
        background.setPosition(0, 0);

        start = new Sprite(TextureManager.start);
        start.setSize(480, 800);
        start.setPosition(0, 0);

        gameOver = new Sprite(TextureManager.gameover);
        gameOver.setSize(480, 800);
        gameOver.setPosition(0, 0);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.draw(batch);
        player.draw(batch);
        if (gameState == 0) {
            start.draw(batch);
        }
        if (gameState == 2) {
            gameOver.draw(batch);
        }
        batch.end();

        if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 1) {
            player.update(Gdx.graphics.getDeltaTime());
            if (Gdx.input.justTouched()) {
                player.touch();
            }
            if (player.getY() <= 0) {
                gameState = 2;
            }
        } else {
            if (Gdx.input.justTouched()) {
                player = new Jack();
                gameState = 0;
            }
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
        TextureManager.disposeNumbers();
        TextureManager.disposeScreen();
    }
}
