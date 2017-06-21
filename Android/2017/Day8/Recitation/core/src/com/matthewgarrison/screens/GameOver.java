package com.matthewgarrison.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matthewgarrison.GameHandler;

import java.util.ArrayList;


public class GameOver implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;

	private Texture backgroundTexture;

	public GameOver(GameHandler g, int launchCount) {
		game = g;
		int maxL = Math.max(launchCount, game.prefs.getInteger("launch", -1));
		game.prefs.putInteger("launch", maxL);
		game.prefs.flush();
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);

		batch = new SpriteBatch();

		backgroundTexture = new Texture(Gdx.files.internal("gameover.jpg"));
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(backgroundTexture, 0, 0, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);

		batch.end();


		if (Gdx.input.isKeyPressed(Input.Keys.R)) {
			game.setScreen(new MainGame(game));
		}
	}

	public void resize(int width, int height) {
	}

	public void hide() {
		this.dispose();
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		batch.dispose();
	}
}