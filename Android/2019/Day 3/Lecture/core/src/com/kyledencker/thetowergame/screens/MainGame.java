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
import com.kyledencker.thetowergame.objects.Player;

public class MainGame implements Screen {

    SpriteBatch batch;
    Player[] player;
    OrthographicCamera camera;
    BitmapFont font;

    GameController myGame;

    public MainGame(GameController g){
        myGame = g;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        TextureManager.loadUnits();



        reset();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 1200);
    }

    public void reset() {
        player = new Player[14];
        for (int i=0; i<player.length/2; i++) {
            Vector2[] path = {
                    new Vector2(200, 300),
                    new Vector2(200, 1000),
                    new Vector2(1200, 600)
            };
            player[i] = new Player(-200, 300, path);
        }
        for (int i=7; i<player.length; i++) {
            Vector2[] path = {
                    new Vector2(400, 800),
                    new Vector2(400, 200),
                    new Vector2(1200, 600)
            };
            player[i] = new Player(-200, 800, path);
        }
    }

    @Override
    public void render(float delta) {
// Draw
        Gdx.gl.glClearColor(1, 0, 1, 1);
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        for (int i=0; i<player.length; i++) {
            player[i].draw(batch);
        }
        batch.end();

        // update
        for (int i=0; i<player.length; i++) {
            player[i].update(Gdx.graphics.getDeltaTime());
            if (player[i].isDone()) {
                myGame.setScreen(new GameOver(myGame, i%7+1));
            }
        }
        // handle inputs
        for (int i=0; i<5; i++) {
            if (Gdx.input.isTouched(i)) {
                Vector3 touched = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
                camera.unproject(touched);
                for (int k=0; k<player.length; k++) {
                    if (player[k].doesHit(new Rectangle(touched.x, touched.y, 10, 10))) {
                        player[k].reset();
                    } else {
                        //		player.setTarget(new Vector2(touched.x, touched.y));
                    }
                }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) reset();
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
