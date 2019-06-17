package com.kyledencker.campshooter;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;

	public static Texture img;
	public static Texture[] players;


	ArrayList<Boulder> chips;
	float lastBoulder = 0, boulderPerSecond = 1f;
	int score = 0;

	Player player;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("rock.png");
		chips = new ArrayList<Boulder>();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1200, 800);
		players = new Texture[7];
		players[0] = new Texture("units/1.jpg");
		players[1] = new Texture("units/2.jpg");
		players[2] = new Texture("units/3.jpg");
		players[3] = new Texture("units/4.jpg");
		players[4] = new Texture("units/5.jpg");
		players[5] = new Texture("units/6.jpg");
		players[6] = new Texture("units/7.jpg");

		player = new Player( 500, 0, 0);
		//chips.add(new Boulder( 200, 200, 50, 0));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Boulder c : chips)
			c.draw(batch);
		player.draw(batch);
		batch.end();

		for (Boulder c : chips) {


			c.update(Gdx.graphics.getDeltaTime());
			if (c.getHitbox().overlaps(player.getHitBox())) {
				player.gotHit();
			}
		}
		player.update(Gdx.graphics.getDeltaTime());

		lastBoulder+=Gdx.graphics.getDeltaTime();
		if (lastBoulder >= 1 / boulderPerSecond) {
			lastBoulder -= 1 / boulderPerSecond;
			int size = (int) (Math.random()*100) + 25;
			chips.add( new Boulder(-size, (int)(Math.random()*600)+200, size, (float)Math.random()*200+25));
		}

		if (Gdx.input.isTouched()) {
			Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touched);
			if (touched.x< 600)
				player.left();
			else
				player.right();
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.left();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.right();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
			player.updateSprite(0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
			player.updateSprite(1);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
			player.updateSprite(2);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_4)) {
			player.updateSprite(3);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_5)) {
			player.updateSprite(4);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_6)) {
			player.updateSprite(5);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.NUM_7)) {
			player.updateSprite(6);
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
