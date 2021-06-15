package com.miketriana.dungeontest;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.css.Rect;

public class GameController extends ApplicationAdapter {
	TiledMap map;

	TiledMapRenderer renderer;
	SpriteBatch batch;

	OrthographicCamera camera;

	Texture spriteSheet;
	Sprite player;
	Rectangle playerHitBox;

	Array<RectangleMapObject> walls;
	RectangleMapObject spawnPoint;
	Array<RectangleMapObject> spikeZones;
	Array<RectangleMapObject> buttons;

	TiledMapTileLayer spikeLayer;
	TiledMapTile spikeActive, spikeInactive;
	
	@Override
	public void create () {
		// Set up world and camera
		map = new TmxMapLoader().load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);

		batch = new SpriteBatch();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 256, 128);

		// Create player
		spriteSheet = new Texture("player.png");
		player = new Sprite(spriteSheet);
		player.setSize(16, 16);
		playerHitBox = new Rectangle(0, 0, 16, 16);

		//player.setPosition(32, 32);

		// Get tile map elements
		// Add rectangles for wall collisions
		walls = map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class);
		// Get spawn point
		spawnPoint = map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class).get(0);
		// Get spikes
		spikeZones = map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class);
		// Get buttons
		buttons = map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class);

		// Get active and inactive spike tiles
		spikeLayer = (TiledMapTileLayer) map.getLayers().get(1);
		spikeActive = spikeLayer.getCell(0, 7).getTile();
		spikeInactive = spikeLayer.getCell(1, 7).getTile();

		player.setPosition(spawnPoint.getRectangle().getX(), spawnPoint.getRectangle().getY());
	}

	@Override
	public void render () {
		ScreenUtils.clear(0, 0, 0, 1);

		// Player inputs
		float oldX = player.getX();
		float oldY = player.getY();

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			player.translate(-2, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			player.translate(2, 0);
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			player.translate(0, 2);
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			player.translate(0, -2);

		/*
		// Method 1: Using mouse to toggle spikes on / off
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
			for (RectangleMapObject button : buttons) {
				if (button.getRectangle().contains(touchPos.x, touchPos.y)) {
					System.out.println("Pressed");

					int buttonId = (int) button.getProperties().get("id");
					for (RectangleMapObject spikeZone : spikeZones) {
						if ((int) spikeZone.getProperties().get("id") == buttonId) {
							boolean active = (boolean) spikeZone.getProperties().get("active");
							spikeZone.getProperties().put("active", !active);
							updateSpikeTiles(spikeZone.getRectangle(), !active);
						}
					}
				}
			}
		}
		*/

		/*
		// Method 2: Standing on buttons to toggle spikes on / off
		for (RectangleMapObject button : buttons) {
			if (button.getRectangle().overlaps(playerHitBox) && !(boolean) button.getProperties().get("pressed")) {
				System.out.println("Pressed");
				button.getProperties().put("pressed", true);

				int buttonId = (int) button.getProperties().get("id");
				for (RectangleMapObject spikeZone : spikeZones) {
					if ((int) spikeZone.getProperties().get("id") == buttonId) {
						boolean active = (boolean) spikeZone.getProperties().get("active");
						spikeZone.getProperties().put("active", !active);
						updateSpikeTiles(spikeZone.getRectangle(), !active);
					}
				}
			}
			else if (!button.getRectangle().overlaps(playerHitBox)) {
				button.getProperties().put("pressed", false);
			}
		}
		 */

		// Method 3: Standing on buttons to turn spikes off, leaving turns them back on
		for (RectangleMapObject button : buttons) {
			if (button.getRectangle().overlaps(playerHitBox)) {
				int buttonId = (int) button.getProperties().get("id");
				for (RectangleMapObject spikeZone : spikeZones) {
					if ((int) spikeZone.getProperties().get("id") == buttonId) {
						spikeZone.getProperties().put("active", false);
						updateSpikeTiles(spikeZone.getRectangle(), false);
					}
				}
			}
			else {
				int buttonId = (int) button.getProperties().get("id");
				for (RectangleMapObject spikeZone : spikeZones) {
					if ((int) spikeZone.getProperties().get("id") == buttonId) {
						spikeZone.getProperties().put("active", true);
						updateSpikeTiles(spikeZone.getRectangle(), true);
					}
				}
			}
		}

		// Update player hitbox
		playerHitBox.setPosition(player.getX(), player.getY());
		// Check collision with walls
		for (RectangleMapObject wall : walls) {
			if (playerHitBox.overlaps(wall.getRectangle())) {
				player.setPosition(oldX, oldY);
			}
		}

		// Check collision with spikes
		for (RectangleMapObject spikeZone : spikeZones) {
			if (playerHitBox.overlaps(spikeZone.getRectangle()) && (boolean) spikeZone.getProperties().get("active")) {
				player.setPosition(spawnPoint.getRectangle().getX(), spawnPoint.getRectangle().getY());
			}
		}


		// Update the camera
		camera.update();

		// Render tilemap
		renderer.setView(camera);
		renderer.render();

		// Render player
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.draw(batch);
		batch.end();
	}

	private void updateSpikeTiles(Rectangle rect, boolean active) {
		int x = (int) rect.getX() / 16;
		int y = (int) rect.getY() / 16;
		int width = (int) rect.getWidth() / 16;
		int height = (int) rect.getHeight() / 16;

		for (int i = x; i < x + width; i++) {
			for (int j = y; j < y + height; j++) {
				if (active)
					spikeLayer.getCell(i, j).setTile(spikeActive);
				else
					spikeLayer.getCell(i, j).setTile(spikeInactive);
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		spriteSheet.dispose();
		map.dispose();
	}
}
