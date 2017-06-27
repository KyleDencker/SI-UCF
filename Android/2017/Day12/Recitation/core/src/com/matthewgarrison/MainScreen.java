package com.matthewgarrison;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.Arrays;
import java.util.Random;

public class MainScreen implements Screen {
	public  GameHandler game;
	public  OrthographicCamera camera;
	public  SpriteBatch batch;
	public  Texture whiteSquare, blackSquare, blueSquare, greenSquare, graySquare;
	public  char[][] grid;
	public  Random rand;
	public  char type;
	public  int startX, startY, endX, endY;

	public MainScreen(GameHandler g) {
		this.game = g;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);
		batch = new SpriteBatch();

		whiteSquare = new Texture(Gdx.files.internal("white.png"));
		blackSquare = new Texture(Gdx.files.internal("black.png"));
		blueSquare = new Texture(Gdx.files.internal("blue.png"));
		greenSquare = new Texture(Gdx.files.internal("green.png"));
		graySquare = new Texture(Gdx.files.internal("gray.png"));

		rand = new Random();

		type = 'W';
		startX = startY = endX = endY = -1;

		grid = new char[12][20];
		for (char[] arr : grid) Arrays.fill(arr, 'F');
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int y = 0; y < 12; y++) {
			for (int x = 0; x < 20; x++) {
				if (grid[y][x] == 'F') batch.draw(whiteSquare, 40*x, 40*y);
				if (grid[y][x] == 'V') batch.draw(blackSquare, 40*x, 40*y);
				if (grid[y][x] == 'S') batch.draw(blueSquare, 40*x, 40*y);
				if (grid[y][x] == 'E') batch.draw(greenSquare, 40*x, 40*y);
				if (grid[y][x] == 'W') batch.draw(graySquare, 40*x, 40*y);
			}
		}
		batch.end();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(0), Gdx.input.getY(0), 0);
			camera.unproject(touchPos);
			int y = (int)(touchPos.y) / GameHandler.SQUARE_DIMENSION;
			int x = (int)(touchPos.x) / GameHandler.SQUARE_DIMENSION;
			if (x == startX && y == startY && type != 'S') {
				startX = startY = -1;
			}
			if (x == endX && y == endY && type != 'E') {
				endX = endY = -1;
			}
			grid[y][x] = type;
			if (type == 'S' && (startX != x || startY != y)) {
				if (isValid(startX, startY)) {
					grid[startY][startX] = 'F';
				}
				startX = x;
				startY = y;
			}
			if (type == 'E' && (endX != x || endY != y)) {
				if (isValid(endX, endY)) {
					grid[endY][endX] = 'F';
				}
				endX = x;
				endY = y;
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			type = 'W';
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			type = 'S';
		}
		if (Gdx.input.isKeyPressed(Input.Keys.E)) {
			type = 'E';
		}
		if (Gdx.input.isKeyPressed(Input.Keys.F)) {
			type = 'F';
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
			BFS.go(this);
		}
	}

	public boolean isValid(int x, int y) {
		return (x >= 0 && x < 20 && y >= 0 && y < 12);
	}

	public void draw() {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int y = 0; y < 12; y++) {
			for (int x = 0; x < 20; x++) {
				if (grid[y][x] == 'F') batch.draw(whiteSquare, 40*x, 40*y);
				if (grid[y][x] == 'V') batch.draw(blackSquare, 40*x, 40*y);
				if (grid[y][x] == 'S') batch.draw(blueSquare, 40*x, 40*y);
				if (grid[y][x] == 'E') batch.draw(greenSquare, 40*x, 40*y);
				if (grid[y][x] == 'W') batch.draw(graySquare, 40*x, 40*y);
			}
		}
		batch.end();
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