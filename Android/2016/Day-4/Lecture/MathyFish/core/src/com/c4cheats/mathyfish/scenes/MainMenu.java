package com.c4cheats.mathyfish.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.mathyfish.GameController;
import com.c4cheats.mathyfish.Resources.TextureManager;
import com.c4cheats.mathyfish.objects.Fish;
import com.c4cheats.mathyfish.tools.FishPool;

import java.util.ArrayList;

/**
 * Created by kyledencker on 6/16/16.
 */
public class MainMenu implements Screen {

    GameController myGame;

    SpriteBatch batch;
    OrthographicCamera camera;
    FishPool fishyPool;
    ArrayList<Fish> fishList;
    ArrayList<Fish> delete = new ArrayList<Fish>();


    public MainMenu(GameController g) {
        this.myGame = g;
    }

    @Override
    public void show() {
        TextureManager.loadMenus();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameController.WIDTH, GameController.HEIGHT);

        fishyPool = new FishPool();
        fishList = new ArrayList<Fish>();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Fish f : fishList) f.draw(batch);
        batch.end();

        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            Fish newFish = fishyPool.obtain();
            newFish.setPosition(touchPos.x, touchPos.y);
            newFish.alive();
            fishList.add(newFish);
        }

        for (Fish f : fishList) {
            f.update(delta);
            if (f.isDead()) delete.add(f);
        }

        while (!delete.isEmpty()) {
            fishyPool.free(delete.get(0));
            fishList.remove(delete.remove(0));

        }

        System.out.println("Fish in the Pool" + fishyPool.getFree());
     //   System.out.println("Max Fish in the Pool" + fishyPool.max);


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
