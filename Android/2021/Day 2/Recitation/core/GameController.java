package com.miketriana.tankgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miketriana.tankgame.objects.Mine;
import com.miketriana.tankgame.objects.Tank;
import com.sun.org.apache.xpath.internal.operations.Or;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;

	Tank tank;
	Mine mine;

	OrthographicCamera camera;

	@Override
	public void create () {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);

		batch = new SpriteBatch();

		tank = new Tank(100, 100);
		mine = new Mine(300, 200);

	}

	@Override
	public void render () {
		// Inputs
		if (Gdx.input.isTouched()) {
			Vector3 touchPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			tank.setTarget(touchPos.x, touchPos.y);
		}


		// Update
		tank.move();

		// Check collision
		if (tank.getHitBox().overlaps(mine.getHitBox())) {
			System.out.println("Hit!");
			tank.damage(1);
		}

		ScreenUtils.clear(1, 0.9f, 0.7f, 1);
		batch.begin();
		mine.draw(batch);
		tank.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		// Dispose textures
		tank.dispose();
		mine.dispose();
	}
}
