package com.miketriana.tankgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miketriana.tankgame.GameController;

public class WinScreen extends ScreenAdapter {
    GameController game;

    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont text;

    int score = 0;

    public WinScreen (GameController game) {
        this.game = game;
    }

    public void setScore (int score) { this.score = score; }

    @Override
    public void show () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        text = new BitmapFont();
        text.setColor(1, 1, 1, 1);
    }

    @Override
    public void render (float delta) {
        if (Gdx.input.justTouched()) {
            this.dispose();
            game.setScreen(game.gameScreen);
        }

        ScreenUtils.clear(0.3f, 0.7f, 0.3f, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        text.getData().setScale(4);
        String msg = "YOU WIN! SCORE: " + score;
        GlyphLayout gl = new GlyphLayout();
        gl.setText(text, msg);
        text.draw(batch, msg, camera.position.x - gl.width / 2, camera.position.y + 50);
        text.getData().setScale(2);
        msg = "CLICK TO PLAY AGAIN";
        gl.setText(text, msg);
        text.draw(batch, msg, camera.position.x - gl.width / 2, camera.position.y - 50);
        batch.end();
    }

    @Override
    public void resize (int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
        text.dispose();
    }
}
