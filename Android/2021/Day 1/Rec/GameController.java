package com.miketriana.playermoving;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Texture playerTexture;
	Sprite playerSprite;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		playerTexture = new Texture("player.png");
		playerSprite = new Sprite(playerTexture);
		playerSprite.setPosition(100, 150);
	}

	@Override
	public void render () {

		// Print the framerate to the console
		//System.out.println(Gdx.graphics.getFramesPerSecond());

		float pixelsToMove = 600 * Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerSprite.setPosition(playerSprite.getX() - pixelsToMove, playerSprite.getY());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerSprite.setPosition(playerSprite.getX() + pixelsToMove, playerSprite.getY());
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			playerSprite.setPosition(playerSprite.getX(), playerSprite.getY() + pixelsToMove);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			playerSprite.setPosition(playerSprite.getX(), playerSprite.getY() - pixelsToMove);
		}

		ScreenUtils.clear(0.5f, 0.5f, 1, 1);

		batch.begin();
		playerSprite.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		playerTexture.dispose();
	}
}
