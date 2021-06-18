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
import com.fhsps.clicker.objects.Pipe;
import com.fhsps.clicker.tools.SoundManager;
import com.fhsps.clicker.tools.TextureManager;

import java.util.ArrayList;

public class FlappyJack implements Screen {

    Controller myGame;

    SpriteBatch batch;
    OrthographicCamera camera;

    Jack player;
    Sprite background, start, gameOver, ground1, ground2;
    int gameState = 0, score = 0;
    float timer = 0, last = 400;
    public static final int SPEED = 200;

    ArrayList<Pipe> activePipe = new ArrayList<Pipe>();


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
        SoundManager.loadSounds();

        player = new Jack();


        background = new Sprite(TextureManager.BACKGROUND[(int)(Math.random()*TextureManager.BACKGROUND.length)]);
        background.setSize(480, 800);
        background.setPosition(0, 0);

        start = new Sprite(TextureManager.start);
        start.setSize(480, 800);
        start.setPosition(0, 0);

        gameOver = new Sprite(TextureManager.gameover);
        gameOver.setPosition(150, 400);

        ground1 = new Sprite(TextureManager.ground);
        ground1.setSize(480, 112);
        ground1.setPosition(0, -12);

        ground2 = new Sprite(TextureManager.ground);
        ground2.setSize(480, 112);
        ground2.setPosition(480, -12);


    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.draw(batch);
        player.draw(batch);

        for (Pipe p : activePipe) {
            p.draw(batch);
        }

        if (gameState == 0) {
            start.draw(batch);
        }
        if (gameState == 2) {
            gameOver.draw(batch);
        }

        int temp = score, pass = 0;
        while (temp > 0) {
            int digit = temp % 10;
            temp /= 10;

            batch.draw(TextureManager.NUMBERS[digit], 440 - pass * 18, 750);
            pass++;
        }
        if (score == 0) {
            batch.draw(TextureManager.NUMBERS[0], 440, 750);
        }

        ground1.draw(batch);
        ground2.draw(batch);

        batch.end();

        if (gameState == 0) {
            if (Gdx.input.justTouched()) {
                gameState = 1;
            }
        } else if (gameState == 1) {
            player.update(Gdx.graphics.getDeltaTime());

            for (int i=0; i<activePipe.size(); i++) {
                activePipe.get(i).update(delta);
                if (activePipe.get(i).doesHit(player.getHitBox())) {
                    gameState = 2;
                    SoundManager.hit.play();
                }
                if (activePipe.get(i).getX() < 25) {
                    if (!activePipe.get(i).isPassed()) {
                        score++;
                        SoundManager.point.play();
                    }
                    activePipe.get(i).pass();
                }
                if (activePipe.get(i).getX() < -100) {
                    activePipe.remove(i);
                    i--;
                }
            }

            timer+= delta;

            if (timer > 1) {
                timer = 0;
                last = last + (float) (Math.random() * 400 - 200);
                if (last > 640) {
                    last = 640;
                }

                if (last < 110) {
                    last = 110;
                }
                activePipe.add(new Pipe((int) last, (int) (Math.random() * TextureManager.PIPE.length)));
            }

            ground1.setPosition(ground1.getX() - SPEED * delta, ground1.getY());
            ground2.setPosition(ground2.getX() - SPEED * delta, ground2.getY());

            if (ground1.getX() <= -480) {
                ground1.setPosition(ground2.getX()+480, ground1.getY());
            }

            if (ground2.getX() <= -480) {
                ground2.setPosition(ground1.getX()+480, ground2.getY());
            }





            if (Gdx.input.justTouched()) {
                player.touch();
                SoundManager.wing.play();
            }
            if (player.getY() <= 100) {
                player.setY(100);
                gameState = 2;
            }
        } else {
            if (Gdx.input.justTouched()) {
                player = new Jack();
                gameState = 0;
                score = 0;
                activePipe.clear();
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
        SoundManager.dispose();
    }
}
