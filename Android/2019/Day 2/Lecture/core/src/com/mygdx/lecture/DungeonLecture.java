package com.mygdx.lecture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import Entities.Hole;
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
	Hole blockedSquare;
	Sprite blocked;
	Rectangle blockedRect;

	Music music;

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
		blockedSquare = new Hole(w/16 * 5, h/9 *3, w/16, h/9);

		// blocked = new Sprite(new Texture("block.png:"));
		blockedRect = new Rectangle(w/16 *3, h/9 *4, w/16, h/9);

		/*
		spriteKnight = new Sprite(new Texture("Knight.png"));
		spriteKnight.setPosition(0,0);
		spriteKnight.setSize(w/16, h/9);
        */

		//Set up music
		// music = Gdx.audio.newMusic(Gdx.files.internal("Medieval Melancholy.wav"));
		//music.setLooping(true);
		//music.play();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Draw stuff
		batch.begin();
		background.draw(batch);
		knight.draw(batch);
		blockedSquare.draw(batch);
		// blocked.draw(batch);
		batch.end();

		//Archive Knight Position
		float oldx = knight.x;
		float oldy = knight.y;

		// Handle Input
		// Keyboard
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            knight.moveLeft();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            knight.moveRight();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            knight.moveUp();
            // music.pause();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
            knight.moveDown();
            // music.play();
		}

		//Wrapping
		if(knight.x  < 0 ){
			knight.updatePos(w-w/16, knight.y);
		}
		if(knight.x >= w){
			knight.updatePos(0, knight.y);
		}
		if(knight.y <0){
			knight.updatePos(knight.x, h-h/9);
		}
		if(knight.y >= h){
			knight.updatePos(knight.x, 0);
		}

		if(knight.hitBox.overlaps(blockedSquare.hitBox)){
			knight.updatePos(oldx, oldy);
		}



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
