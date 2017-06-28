package com.c4cheats.spacegame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.spacegame.GameController;

/**
 * Created by kyledencker on 6/28/17.
 */

public class MenuTween implements Screen {

    GameController myGame;
    Sprite sprite;
    Texture text;
    SpriteBatch batch;
    OrthographicCamera camera;
    int state = 0;
    boolean facingLeft = true;

    float time = 0;

    public MenuTween(GameController g) {
        myGame = g;
    }

    @Override
    public void show() {
        text = new Texture(Gdx.files.internal("badlogic.jpg"));
        sprite = new Sprite(text);

        sprite.setSize(0, 0);
        sprite.setPosition(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();

        time += Gdx.graphics.getDeltaTime();

        //sprite.setPosition((float)(sprite.getX()+Math.sin(Math.pow(time, .9))),
        //                   (float)(sprite.getY()+Math.cos(Math.pow(time, .9))));

        if (state == 0) {
            if (time < 3f/8f) {
                sprite.setSize(400 * time*(8f/3f), 400 * time*(8f/3f));

            } else {
                sprite.setSize(400, 400);
                //done = true;
                state = 1;
            }
            sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2
                    , Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
        } else if (state == 1 ) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
                state = 2;
                time = 0;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.F)){
                sprite.flip(true, false);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)){
                sprite.setPosition(sprite.getX()-3, sprite.getY());
                if (!facingLeft) {
                    sprite.flip(true, false);
                    facingLeft = true;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D)){
                sprite.setPosition(sprite.getX()+3, sprite.getY());
                if (facingLeft) {
                    sprite.flip(true, false);
                    facingLeft = false;
                }
            }
        } else if (state == 2) {
            if (time < 3f/8f) {
                sprite.setSize(400 * (1-time*(8f/3f)), 400 * (1-time*(8f/3f)));

            } else {
                sprite.setSize(0, 0);
                //done = true;
                //go to next sceen;
                state = 0;
                time = 0;
            }
            sprite.setPosition(Gdx.graphics.getWidth() / 2 - sprite.getWidth() / 2
                    , Gdx.graphics.getHeight() / 2 - sprite.getHeight() / 2);
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
