package com.kyledencker.thetowergame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kyledencker.thetowergame.GameController;
import com.kyledencker.thetowergame.manager.TextureManager;
import com.kyledencker.thetowergame.objects.Bullet;
import com.kyledencker.thetowergame.objects.Player;
import com.kyledencker.thetowergame.objects.Tower;

import java.awt.Color;
import java.util.ArrayList;

public class MainGame implements Screen {

    SpriteBatch batch;
    ArrayList<Player> player;
    OrthographicCamera camera;
    BitmapFont font;
    int score;

    GameController myGame;
    public static ArrayList<Bullet> bulletList;
    private ArrayList<Tower> towerList;

    public MainGame(GameController g){
        myGame = g;
    }

    Vector2[] path = {
            new Vector2(300, 300),
            new Vector2(300, 1000),
            new Vector2(1400, 1000),
            new Vector2(1400, 500)
    };
    Vector2[] path2 = {
            new Vector2(500, 800),
            new Vector2(500, 300),
            new Vector2(1400, 300),
            new Vector2(1400, 500)
    };

    float spawnTime = 1, currentTime;

    @Override
    public void show() {
        batch = new SpriteBatch();
        player = new ArrayList<Player>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 1200);
        bulletList = new ArrayList<Bullet>();
        towerList = new ArrayList<Tower>();
        score = 5;
        font = new BitmapFont();
        font.setColor(1,1,1,1);
        font.getData().setScale(3);
    }


    @Override
    public void render(float delta) {
// Draw
        Gdx.gl.glClearColor(1, 0, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(TextureManager.map, 0, 0);
        for (Bullet b : bulletList) {
            b.draw(batch);
        }
        for (Tower t : towerList) {
            t.draw(batch);
        }
        for (int i=0; i<player.size(); i++) {
            player.get(i).draw(batch);
        }
        font.draw(batch, "Score: " + score + " Next Tower: " + (Math.pow(2, towerList.size())), 10, 300);
        batch.end();

        // update
        for (Tower t : towerList) {
            t.update(Gdx.graphics.getDeltaTime(), player);
        }
        for (Bullet b : bulletList) {
            b.update(Gdx.graphics.getDeltaTime());
        }

        for (int i=0; i<player.size(); i++) {
            player.get(i).update(Gdx.graphics.getDeltaTime());


        }

        goBackPlayers: for (int i=0; i<player.size(); i++) {
            for (int j=0; j<bulletList.size(); j++) {
                if (player.get(i).doesHit(bulletList.get(j).getHitBox())) {
                    bulletList.remove(j);
                    player.remove(i);
                    j--;
                    i--;
                    score++;
                    continue goBackPlayers;
                }
            }
        }

        currentTime += Gdx.graphics.getDeltaTime();
       // spawnTime = spawnTime * (float)Math.pow(.99f, Gdx.graphics.getDeltaTime());

        if (currentTime >= spawnTime) {
            if (Math.random() > .5) {
                player.add(new Player(-200, 300, path));
            } else {
                player.add(new Player(-200, 800, path2));
            }
            currentTime -= spawnTime;
            spawnTime *= .995f;
        }


        // handle inputs
            if (Gdx.input.justTouched()) {
                Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touched);
                if (score >= Math.pow(2, towerList.size())) {
                    score -= Math.pow(2, towerList.size());
                    towerList.add(new Tower((int) touched.x-25, (int) touched.y-25));
                }
            }


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            myGame.setScreen(new GameOver(myGame, 1));
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
    }
}
