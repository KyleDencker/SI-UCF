package com.libgdxprojects.learning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	Player player;

	OrthographicCamera camera;

	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		player = new Player(64, 64);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 256, 256);

		tiledMap = new TmxMapLoader().load("dungeonTileMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);

		if (Gdx.input.isKeyPressed(Input.Keys.W))
		{
			player.velY = player.movementSpeed;
		}

		else if (Gdx.input.isKeyPressed(Input.Keys.S))
		{

			player.velY = -player.movementSpeed;
		}

		else
		{
			player.velY = 0;
		}

		if (Gdx.input.isKeyPressed(Input.Keys.A))
		{

			player.velX = -player.movementSpeed;
		}

		else if (Gdx.input.isKeyPressed(Input.Keys.D))
		{

			player.velX = player.movementSpeed;
		}

		else
		{
			player.velX = 0;
		}

		player.update();


		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		player.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
