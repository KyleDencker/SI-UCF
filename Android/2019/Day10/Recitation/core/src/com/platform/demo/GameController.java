package com.platform.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float blckSize = 16f;
	Archer archer;
	Boolean startJump;
	Boolean isJumping;

	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	TiledMapTileLayer collision;

	OrthographicCamera camera;

	@Override
	public void create () {
		camera = new OrthographicCamera(800, 800);
		camera.translate(400, 400);
		camera.update();

		batch = new SpriteBatch();
		archer = new Archer(0, 14*blckSize);

		tiledMap = new TmxMapLoader().load("PlatformerMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		collision = (TiledMapTileLayer) tiledMap.getLayers().get("Collision");


		startJump = false;
		isJumping = false;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		startJump = false;

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		archer.draw(batch);
		batch.end();

		float dt = Gdx.graphics.getDeltaTime();
		Vector2 oldPos = new Vector2(archer.position.x, archer.position.y);


		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isJumping){
			archer.jump();
			startJump = true;
			isJumping = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && !isJumping){
			archer.position.x = archer.position.x + -100 * dt;
			archer.velocity.x = -100;
			//archer.velocity.x = -100;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !isJumping){
			//archer.velocity.x = 100;
			archer.velocity.x = 100;
			archer.position.x = archer.position.x + 100 * dt;
		}

		archer.update(dt);

		if(isCellBlocked(archer.position.x, archer.position.y) && archer.velocity.y <= 0){
			archer.position.y = oldPos.y;
			archer.velocity.y = 0;
			archer.friction();
			isJumping = false;
		}
		if(isCellBlocked(archer.position.x, archer.position.y + 16) && archer.velocity.y > 0){
			archer.position.y = oldPos.y;
			archer.velocity.y = 0;
		}
		if(isCellBlocked(archer.position.x, archer.position.y+2) && archer.velocity.x < 0 && !startJump){
			archer.position.x = oldPos.x;
			//archer.velocity.x = 0;
		}
		if(isCellBlocked(archer.position.x + 16, archer.position.y+2) && archer.velocity.x > 0){
			archer.position.x = oldPos.x;
			//archer.velocity.x = 0;
		}

		camera.position.set(archer.position.x, 400, 0);

	}

	private boolean isCellBlocked(float x, float y) {
		TiledMapTileLayer.Cell cell = collision.getCell((int) (x / collision.getTileWidth()), (int) (y / collision.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
