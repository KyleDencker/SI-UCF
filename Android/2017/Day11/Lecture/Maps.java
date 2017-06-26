package com.c4cheats.spacegame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.spacegame.GameController;

/**
 * Created by kyledencker on 6/26/17.
 */

public class Maps implements Screen {
    GameController myGame;

    OrthographicCamera camera;
    SpriteBatch batch;
    Texture texture;

    int[][] map;

    public Maps(GameController g) {
        myGame = g;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        batch = new SpriteBatch();
        camera.position.x = 5000;
        camera.position.y = 5000;
        camera.update();
        map = new int[1000][1000];

        // generate a map.

        // 3x3
        /*
           *
          ***
          ****
          ***
         */

        int rooms = (int)(Math.random()*5 + 2);

        generate(100, 100, 20, -1);
        map[101][101] = 0;

    }

    public void generate(int x, int y, int rooms, int dir) {
        if (map[y][x] == 1) return;
        for (int i=x; i<x+3; i++) {
                for (int j=y; j<y+3; j++) {
                    map[j][i] = 1;
                }
            }
        if (rooms == 0) return;

        boolean needsMore = false;

        while (!needsMore) {
            if (Math.random() < .5 && dir != 0 && map[y+3][x+1] != 1) {
                map[y + 3][x + 1] = 1;
                generate(x, y + 4, rooms - 1, 0);
                needsMore = true;
            }

            if (Math.random() < .5 && dir != 2 && map[y-1][x+1] != 1) {
                map[y - 1][x + 1] = 1;
                generate(x, y - 4, rooms - 1, 2);
                needsMore = true;

            }
            if (Math.random() < .5 && dir != 1 && map[y+1][x+3] != 1) {
                map[y + 1][x + 3] = 1;
                generate(x + 4, y, rooms - 1, 1);
                needsMore = true;

            }
            if (Math.random() < .5 && dir != 3 && map[y+1][x-1] != 1) {
                map[y + 1][x - 1] = 1;
                generate(x - 4, y, rooms - 1, 3);
                needsMore = true;
            }
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i=0; i<1000; i++) {
            for (int j=0; j<1000; j++) {
                if (map[j][i] == 1) {
                    batch.draw(texture, i*50, j*50, 50, 50);
                }
            }
        }
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y+=5;
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y-=5;
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.x+=5;
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x-=5;
            camera.update();
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
