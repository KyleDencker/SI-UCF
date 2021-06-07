package com.fhsps.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	Sprite sprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		sprite.setPosition(200, 200);
		sprite.setSize(100, 100);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 1000);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0.5f, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		sprite.draw(batch);

		batch.end();

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			sprite.setPosition(300, 300);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
			sprite.setPosition(500, 300);
		}

		if (Gdx.input.justTouched()) {
			Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touched);
			sprite.setPosition(touched.x - sprite.getWidth()/2, touched.y - sprite.getHeight()/2);
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
