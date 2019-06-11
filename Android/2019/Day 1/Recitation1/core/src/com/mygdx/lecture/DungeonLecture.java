package com.mygdx.lecture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import Entities.PlayerKnight;

public class DungeonLecture extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;

	// Background and screen width/height
	Sprite background;
	float w;
	float h;

	// Game entities
	PlayerKnight knight;
	Sprite spriteKnight;

	@Override
	public void create () {

		// Make batch and get device width and height
		batch = new SpriteBatch();
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		// Set up Camera
		camera = new OrthographicCamera();

		// Set up background
		background = new Sprite(new Texture("SingleScreen.png"));
		background.setSize(w, h);

		// Make a knight
		knight = new PlayerKnight(0, 0, w/16, h/9);

		/*
		spriteKnight = new Sprite(new Texture("Knight.png"));
		spriteKnight.setPosition(0,0);
		spriteKnight.setSize(w/16, h/9);
        */

		//Set up music
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stuff
		batch.begin();
		background.draw(batch);
		knight.draw(batch);
		batch.end();

		// Update


		// Handle Input
		// Keyboard
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){

		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){

		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){

		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){

		}


		// Touch


		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			knight.updatePos(touchPos.x, touchPos.y);
		}

		// Wrap out of bounds

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
