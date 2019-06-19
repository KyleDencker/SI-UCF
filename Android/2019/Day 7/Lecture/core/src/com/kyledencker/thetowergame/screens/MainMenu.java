package com.kyledencker.thetowergame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kyledencker.thetowergame.GameController;

public class MainMenu implements Screen {
    SpriteBatch batch;
    OrthographicCamera camera;

    GameController myGame;
    Sprite logo;
    float red = 0, time = 0;
    boolean changing;
    float[] rgb = {1, 1, 1};
    int value;
    Sprite[] towers;

    float angle=0;

    public MainMenu(GameController g) {
        myGame = g;
        changing = false;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho( false, 1600, 1200);
        logo = new Sprite(new Texture("logo.png"));
        logo.setPosition(500, 500);
        value = (int)(Math.random()*6);

        int numOfTowers = (int)(Math.random() * 10) + 5;
        towers = new Sprite[numOfTowers];
        for (int i=0; i<numOfTowers; i++) {
            towers[i] = new Sprite(new Texture("tower.png"));
            towers[i].setColor((float)Math.random(), (float)Math.random(), (float)Math.random(), 1);
            towers[i].setPosition((float)Math.random()*1250, (float)Math.random()*850);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i=0; i<towers.length; i++) {
            towers[i].draw(batch);
        }
        logo.draw(batch);
        batch.end();

        angle+=Gdx.graphics.getDeltaTime();
        for (int i=0; i<towers.length; i++) {
            if (i%2 == 0)
                towers[i].setPosition(towers[i].getX() + 25* (float) Math.cos(angle*(i+1)), towers[i].getY() + 25*(float)Math.sin(angle*(i+1)));
            else
                towers[i].setPosition(towers[i].getX() + 25* (float) Math.cos(-angle*(i+1)), towers[i].getY() + 25*(float)Math.sin(-angle*(i+1)));

        }


        float changeSpeed = Gdx.graphics.getDeltaTime() * 4;

        switch (value) {
            case 0:
                rgb[0] += changeSpeed;
                if (rgb[0] > 1) {
                    rgb[0] = 1;
                    value = (int)(Math.random()*6);
                }
                break;
            case 1:
                rgb[1] += changeSpeed;
                if (rgb[1] > 1) {
                    rgb[1] = 1;
                    value = (int)(Math.random()*6);
                }
                break;
            case 2:
                rgb[2] += changeSpeed;
                if (rgb[2] > 1) {
                    rgb[2] = 1;
                    value = (int)(Math.random()*6);
                }
                break;
            case 3:
                rgb[0] -= changeSpeed;
                if (rgb[0] < 0) {
                    rgb[0] = 0;
                    value = (int)(Math.random()*6);
                }
                break;
            case 4:
                rgb[1] -= changeSpeed;
                if (rgb[1] < 0) {
                    rgb[1] = 0;
                    value = (int)(Math.random()*6);
                }
                break;
            case 5:
                rgb[2] -= changeSpeed;
                if (rgb[2] < 0) {
                    rgb[2] = 0;
                    value = (int)(Math.random()*6);
                }
                break;
        }
        logo.setColor(rgb[0], rgb[1], rgb[2], 1);


        if (changing) {
            time += Gdx.graphics.getDeltaTime();
            red  += time;
            if (red > 1) red = 1;
        }
        if (time > 1f) {
            myGame.setScreen(new MainGame(myGame));
        }

        if (Gdx.input.justTouched()) {
            changing = true;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
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
