package com.libgdxprojects.learning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;

	Player player;

	OrthographicCamera camera;

	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	MapLayer collisionLayer, doorLayer;
	MapObjects objects, doorObjects;

	Array<RectangleMapObject> array;

	Array<RectangleMapObject> doorArray;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 256, 256);

		tiledMap = new TmxMapLoader().load("dungeonTileMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		// Layer 3 is our collision layer
		collisionLayer = tiledMap.getLayers().get(3);
		objects = collisionLayer.getObjects();

		doorLayer = tiledMap.getLayers().get(4);
		doorObjects = doorLayer.getObjects();

		array = objects.getByType(RectangleMapObject.class);
		doorArray = doorObjects.getByType(RectangleMapObject.class);

		player = new Player(64, 64, array, doorArray);



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

		player.update(Gdx.graphics.getDeltaTime());

		// Code to have camera follow the player:
		//camera.position.x = player.getPosition().x;
		//camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2, (camera.viewportWidth * 2) - camera.viewportWidth / 2);

		if (player.foundDoor())
		{
			System.out.println(player.getPosition().x);
			if (player.getPosition().x < camera.viewportWidth)
			{
				camera.position.x += camera.viewportWidth;
				player.setPosition(camera.viewportWidth + 16, player.getPosition().y);

			}

			else
			{
				camera.position.x -= camera.viewportWidth;
				player.setPosition(camera.viewportWidth - 64, player.getPosition().y);
			}

			player.foundDoor = false;
		}

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
		player.dispose();
	}
}
