package com.kyledencker.thetowergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kyledencker.thetowergame.objects.Player;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(300, 300);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);
	}

	@Override
	public void render () {

		// Draw
		Gdx.gl.glClearColor(1, 0, 1, 1);
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		player.draw(batch);
		batch.end();

		// update
		player.update(Gdx.graphics.getDeltaTime());

		// handle inputs
		if (Gdx.input.isTouched()) {
			Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touched);
			player.setTarget(new Vector2(touched.x, touched.y));
		}


	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
