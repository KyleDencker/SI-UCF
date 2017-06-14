package com.c4cheats.spacegame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.spacegame.GameController;
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
    GameController myGame;

    public MainGame(GameController g) {
        myGame = g;
    }

    public void show() {
        batch = new SpriteBatch();
        player = new Player();
        lazerList = new ArrayList<Lazer>();
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
                lazerList.remove(i);
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
            lazerList.add(new Lazer(3, (int)(player.sprite.getX() + player.sprite.getWidth()/2), (int)player.sprite.getHeight()));
        }

        // chage screens
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            myGame.setScreen(new GameOver(myGame));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            myGame.setScreen(new MainGame(myGame));
        }
    }

    public void dispose() {
        batch.dispose();
    }
}
