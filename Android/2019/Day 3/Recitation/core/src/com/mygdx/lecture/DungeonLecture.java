package com.mygdx.lecture;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

import Entities.Hole;
import Entities.PlayerKnight;

public class DungeonLecture extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera camera;

	// Background and screen width/height
	Sprite background;
	Sprite background2;
	float w;
	float h;

	// Game entities
	PlayerKnight knight;
	Sprite spriteKnight;
	Hole blockedSquare;
	Sprite blocked;
	Rectangle blockedRect;
	ArrayList<Rectangle> edges;
	Rectangle door;

	//Score variables
	private int score;
	private String gameScore;
	BitmapFont scoreBitmap;

	Music music;

	@Override
	public void create () {

		// Make batch and get device width and height
		batch = new SpriteBatch();
		w = Gdx.graphics.getWidth();
		h = Gdx.graphics.getHeight();

		// Set up Camera
		camera = new OrthographicCamera(w, h);
		camera.translate(w/2, h/2);
		camera.update();

		// Score instantiation
		score = 0;
		gameScore = "Your score: ";
		scoreBitmap = new BitmapFont();



		// Set up background
		background = new Sprite(new Texture("SingleScreenRoom1.png"));
		background.setSize(w, h);
		background2 = new Sprite(new Texture("SingleScreenRoom2.png"));
		background2.setSize(w, h);
		background2.setPosition(0, h);

		// Make a knight
		knight = new PlayerKnight(w/16, h/9, w/16, h/9);
		blockedSquare = new Hole(w/16 * 5, h/9 *3, w/16, h/9);
		door = new Rectangle(w/16 *7, h/9 * 7, w/16 *2, h/9 * 2);

		// blocked = new Sprite(new Texture("block.png:"));
		blockedRect = new Rectangle(w/16 *3, h/9 *4, w/16, h/9);

		edges = new ArrayList<Rectangle>();
		edges.add(new Rectangle(0, h/9 * 7, w, h/9 * 2));
		edges.add(new Rectangle(0, 0, w, h/9));
		edges.add(new Rectangle(0,0, w/16, h));
		edges.add(new Rectangle(w/16*15, 0, w/16, h));
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

		camera.update();

		// Draw stuff
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		background.draw(batch);
		background2.draw(batch);
		knight.draw(batch);
		blockedSquare.draw(batch);
		scoreBitmap.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		scoreBitmap.draw(batch, gameScore, 0, h/9*8);
		// blocked.draw(batch);
		batch.end();

		//Archive Knight Position
		float oldx = knight.x;
		float oldy = knight.y;

		scoreBitmap.getData().setScale(2);

		// Handle Input
		// Keyboard
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            knight.moveLeft();
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            knight.moveRight();
            score++;
            gameScore = "Your Score: "+score;

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
		/*
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
		*/

		// Transport knight to touched position.
		/*
		if(Gdx.input.isTouched()){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			knight.updatePos(touchPos.x, touchPos.y);
		}
		*/

		// Collision detection for the edges
		if(knight.hitBox.overlaps(door)){
			camera.translate(0f, h);
			knight.updatePos(w/16, h/9 + h);
		}
		else {
			for (int i = 0; i < edges.size(); i++) {
				if (knight.hitBox.overlaps(edges.get(i))) {
					knight.updatePos(oldx, oldy);
				}
			}
		}
		// Wrap out of bounds

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
