package com.c4cheats.mathyfish.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.mathyfish.GameController;
import com.c4cheats.mathyfish.Resources.TextureManager;
import com.c4cheats.mathyfish.objects.BasicFish;
import com.c4cheats.mathyfish.objects.Fish;

/**
 * Created by kyledencker on 6/16/16.
 */
public class Splash implements Screen {

    GameController myGame;

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture img;
    Sprite splashScreen;

    float timer;

    public Splash(GameController g) {
        this.myGame = g;
    }

    @Override
    public void show() {
        TextureManager.loadSplash();
        timer = 2f;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameController.WIDTH, GameController.HEIGHT);

        batch = new SpriteBatch();
        splashScreen = new Sprite(TextureManager.splash);
        splashScreen.setSize(camera.viewportWidth, camera.viewportHeight);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        splashScreen.draw(batch);
        batch.end();

        timer -= Gdx.graphics.getDeltaTime();

        if ((timer < 1.5f && Gdx.input.justTouched()) || timer < 0) {
            TextureManager.disposeSplash();
            this.myGame.setScreen(new MainMenu(myGame));
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
        TextureManager.disposeSplash();
    }
}
