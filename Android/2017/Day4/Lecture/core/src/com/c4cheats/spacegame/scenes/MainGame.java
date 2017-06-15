package com.c4cheats.spacegame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.spacegame.GameController;
import com.c4cheats.spacegame.manager.TextureManager;
import com.c4cheats.spacegame.objects.BulletPool;
import com.c4cheats.spacegame.objects.Lazer;
import com.c4cheats.spacegame.objects.Player;

import java.util.ArrayList;

/**
 * Created by kyledencker on 6/14/17.
 */

public class MainGame implements Screen {

    SpriteBatch batch;
    Player player;
    ArrayList<Lazer> lazerList;
    BulletPool bpool;
    GameController myGame;
    OrthographicCamera camera;

    public MainGame(GameController g) {
        myGame = g;
    }

    public void show() {
        TextureManager.loadGame();

        batch = new SpriteBatch();
        player = new Player();
        lazerList = new ArrayList<Lazer>();
        bpool = new BulletPool();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
    }

    public void hide() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void resize(int x, int y) {

    }

    public void render(float delta) {
// Draw
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i=0; i<lazerList.size(); i++) {
            lazerList.get(i).draw(batch);
        }
        player.draw(batch);
        batch.end();

        // Update
        player.update();
        for (int i=0; i<lazerList.size(); i++) {
            lazerList.get(i).update();
            if (lazerList.get(i).life <= 0){
                bpool.free(lazerList.remove(i));
                i--;
            }
        }

        // Handle input
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Lazer temp = bpool.obtain();
            temp.reset(3,(int)(player.sprite.getX() + player.sprite.getWidth()/2), (int)player.sprite.getHeight());
            lazerList.add(temp);
        }

        if (Gdx.input.isTouched()) {
            Lazer temp = bpool.obtain();
            Vector3 touchLoc = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchLoc);
            temp.reset(3,(int)touchLoc.x, (int)touchLoc.y);
            lazerList.add(temp);
        }

        // chage screens
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            myGame.setScreen(new GameOver(myGame));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            myGame.setScreen(new MainGame(myGame));
        }
        System.out.println("Array Size: " + lazerList.size() + " Pool Size: " + bpool.getFree() + " Max Pool: " + bpool.peak);
    }

    public void dispose() {
        batch.dispose();
        TextureManager.unloadGame();
    }
}
