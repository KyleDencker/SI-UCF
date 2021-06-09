package com.miketriana.tankgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miketriana.tankgame.objects.Mine;
import com.miketriana.tankgame.objects.Tank;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;

	Tank tank;

	OrthographicCamera camera;

	static final int WORLD_WIDTH = 2000;
	static final int WORLD_HEIGHT = 1500;

	Array<Mine> mines = new Array<Mine>();

	boolean followPlayer = false;

	@Override
	public void create () {

		camera = new OrthographicCamera();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera.setToOrtho(false, 800, 800 * (h / w));

		batch = new SpriteBatch();

		tank = new Tank(100, 100);

		addMines();

	}

	@Override
	public void render () {
		// Inputs
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			tank.setTarget(touchPos.x, touchPos.y);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS))
			camera.zoom += 0.1f;
		if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS))
			camera.zoom -= 0.1f;

		camera.zoom = MathUtils.clamp(camera.zoom, 0.5f, 2);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			camera.position.x -= 10;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			camera.position.x += 10;
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			camera.position.y += 10;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			camera.position.y -= 10;

		if (Gdx.input.isKeyJustPressed(Input.Keys.Y))
			followPlayer = !followPlayer;


		// Update
		tank.move();
		if (followPlayer) {
			Vector2 tankPosition = tank.getPosition();
			camera.position.set(tankPosition, 0);
		}

		// Check collision
		for (Mine m : mines) {
			if (tank.getHitBox().overlaps(m.getHitBox())) {
				tank.damage(1);
			}
		}

		ScreenUtils.clear(1, 0.9f, 0.7f, 1);

		// Draw the game
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Mine m : mines) {
			m.draw(batch);
		}
		tank.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		// Dispose textures
		tank.dispose();
		for (Mine m : mines) {
			m.dispose();
		}
	}

	@Override
	public void resize (int width, int height) {
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	private void addMines () {
		for (int i = 0; i < 50; i++) {
			float x = MathUtils.random() * (WORLD_WIDTH - 50);
			float y = MathUtils.random() * (WORLD_HEIGHT - 50);
			while (x < 200 && y < 200)
				x = MathUtils.random() * (WORLD_WIDTH - 50);
			Mine m = new Mine(x, y);
			mines.add(m);
		}
	}

}
