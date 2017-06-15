package com.c4cheats.spacegame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.spacegame.GameController;
import com.c4cheats.spacegame.manager.TextureManager;
import com.c4cheats.spacegame.objects.Player;

/**
 * Created by kyledencker on 6/14/17.
 */

public class MainMenu implements Screen {

    GameController myGame;
    SpriteBatch batch;
    BitmapFont font;
    int score = 0;
    Player player;
    Rectangle touchPos;
    OrthographicCamera camera;

    public MainMenu(GameController g) {
        myGame = g;
    }

    public void show() {
        TextureManager.loadMenu();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        player = new Player();
        touchPos = new Rectangle(-1000, -1000, 50, 50);
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
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Super Awesome Game", 100, 100);
        font.draw(batch, "Score: " + score, 100, 200);
        player.draw(batch);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            myGame.setScreen(new MainGame(myGame));
        }
        if (Gdx.input.isTouched()) {
            score++;
            Vector3 temp = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(temp);
            touchPos.x = temp.x - 25;
            touchPos.y = temp.y - 25;
            if (player.doesHit(touchPos)) {
                myGame.setScreen(new MainGame(myGame));
            }
        }
    }

    public void dispose() {
        TextureManager.unloadMenu();
    }
}
