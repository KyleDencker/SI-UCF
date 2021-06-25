package com.miketriana.tankgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miketriana.tankgame.GameController;

public class TitleScreen extends ScreenAdapter {

    GameController game;

    OrthographicCamera camera;
    SpriteBatch batch;
    BitmapFont text;

    public TitleScreen (GameController game) {
        this.game = game;
    }

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

        ScreenUtils.clear(0.3f, 0.5f, 0.2f, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        text.getData().setScale(4);
        String msg = "TREASURE TANKS";
        GlyphLayout gl = new GlyphLayout();
        gl.setText(text, msg);
        text.draw(batch, msg, camera.position.x - gl.width / 2, camera.position.y + 50);
        text.getData().setScale(2);
        msg = "CLICK TO PLAY";
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
