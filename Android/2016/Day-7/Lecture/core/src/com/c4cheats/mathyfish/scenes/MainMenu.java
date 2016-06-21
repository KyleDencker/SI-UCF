package com.c4cheats.mathyfish.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.mathyfish.GameController;
import com.c4cheats.mathyfish.Resources.TextureManager;


import java.util.ArrayList;

/**
 * Created by kyledencker on 6/16/16.
 */
public class MainMenu implements Screen {

    GameController myGame;

    SpriteBatch batch;
    OrthographicCamera camera;

    Sprite bindStart;
    Rectangle touchRec;


    public MainMenu(GameController g) {
        this.myGame = g;
    }

    @Override
    public void show() {
        TextureManager.loadMenus();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameController.WIDTH, GameController.HEIGHT);

        bindStart = new Sprite(TextureManager.startButton);
        bindStart.setPosition(100, 500);
        touchRec = new Rectangle(-1000, -1000, 6, 6);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        bindStart.draw(batch);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            touchRec.x = touchPos.x - 3;
            touchRec.y = touchPos.y - 3;

            if (touchRec.overlaps(bindStart.getBoundingRectangle())) {
                TextureManager.disposeMenu();
                this.myGame.setScreen(new MainGame(myGame));
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

    }
}
