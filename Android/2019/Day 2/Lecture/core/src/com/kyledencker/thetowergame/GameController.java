package com.kyledencker.thetowergame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kyledencker.thetowergame.objects.Player;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Player[] player;
	OrthographicCamera camera;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player[5];
		for (int i=0; i<5; i++)
			player[i] = new Player(0+ 200* i, 300);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1600, 1200);
	}

	@Override
	public void render () {

		// Draw
		Gdx.gl.glClearColor(1, 0, 1, 1);
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		for (int i=0; i<5; i++) {
			player[i].draw(batch);
		}
		batch.end();

		// update
		for (int i=0; i<5; i++) {
			player[i].update(Gdx.graphics.getDeltaTime());
		}
		// handle inputs
		for (int i=0; i<5; i++) {
			if (Gdx.input.isTouched(i)) {
				Vector3 touched = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touched);
				for (int k=0; k<5; k++) {
					if (player[k].doesHit(new Rectangle(touched.x, touched.y, 10, 10))) {
						player[k].reset();
					} else {
						//		player.setTarget(new Vector2(touched.x, touched.y));
					}
				}
			}
		}


	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
