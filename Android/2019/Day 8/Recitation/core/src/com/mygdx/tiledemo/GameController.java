package com.mygdx.tiledemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Archer archer;
	OrthographicCamera camera;

	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;
	TiledMapTileLayer  collision;

	float w;
	float h;

	ArrayList<Rectangle> blockedCells;

	@Override
	public void create () {
		batch = new SpriteBatch();

		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		archer = new Archer(0,0);

		tiledMap = new TmxMapLoader().load("GauntletMap.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		collision = (TiledMapTileLayer) tiledMap.getLayers().get("Collision");
		blockedCells = new ArrayList<Rectangle>();
		genBlockedCell();

		camera = new OrthographicCamera(w,h);
		camera.translate(w/2, h/2);
		camera.update();

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		archer.draw(batch);
		batch.end();

		float dt = Gdx.graphics.getDeltaTime();

		//Archive old position
		float oldX = archer.getX();
		float oldY = archer.getY();

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			archer.updatePos(archer.getX() + (-100 * dt ), archer.getY());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			archer.updatePos(archer.getX() + (100 * dt ), archer.getY());
		}
		if(Gdx.input.isKeyPressed(Input.Keys.UP)){
			archer.updatePos(archer.getX(), archer.getY() + (100 * dt ));
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			archer.updatePos(archer.getX(), archer.getY() + (-100 * dt ));
		}



		if(isCellBlocked(archer.getX(), archer.getY())){
			archer.updatePos(oldX, oldY);
		}
		if(isCellBlocked(archer.getX() + 16, archer.getY() +16)){
			archer.updatePos(oldX, oldY);
		}
/*
		for(Rectangle blocked : blockedCells){
			if(archer.hitbox.overlaps(blocked)){
				archer.updatePos(oldX, oldY);
			}
		}
*/
	}


	private boolean isCellBlocked(float x, float y) {
		TiledMapTileLayer.Cell cell = collision.getCell((int) (x / collision.getTileWidth()), (int) (y / collision.getTileHeight()));
		return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
	}

	private void genBlockedCell(){
		for(int i = 0; i < collision.getWidth(); i++){
			for(int j = 0; j < collision.getHeight(); j++){
				TiledMapTileLayer.Cell cell = collision.getCell(i, j);
				if(cell !=null && cell.getTile().getProperties().containsKey("blocked")){
					blockedCells.add(new Rectangle(i*16, j*16, 16, 16));
				}
			}
		}
	}


	@Override
	public void dispose () {
		batch.dispose();
	}
}
