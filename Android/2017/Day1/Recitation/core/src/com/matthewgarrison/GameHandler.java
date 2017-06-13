package com.matthewgarrison;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

public class GameHandler extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, kyle;
	float kyleX, kyleY, speedY, speedX;
	int kyleDimensions;
	boolean kyleUp, kyleRight, isPaused;
	int color = 0; // 0 = red, 1 = green, 2 = blue
	Random rand;

	// intialize all the variables
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		kyle = new Texture("pics/kyle.jpg");
		kyleX = 0.0f;
		kyleY = 0.0f;
		speedX = 5;
		speedY = 5;
		kyleUp = true;
		kyleRight = true;
		isPaused = false;
		rand = new Random();
		kyleDimensions = 350;
	}

	@Override
	public void render () {
		// color the background red, green, or blue
		switch (color) {
			case 0 :
				Gdx.gl.glClearColor(1, 0, 0, 1); // red
				break;
			case 1:
				Gdx.gl.glClearColor(0, 1, 0, 1); // green
				break;
			case 2:
				Gdx.gl.glClearColor(0, 0, 1, 1); // blue
				break;
		}
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		// draw Kyle's picture, scaled relative to kyleDimensions
		batch.draw(kyle, kyleX, kyleY, kyleDimensions, kyleDimensions);
		batch.end();

		if (isPaused) {
			// unpause the game if P is pressed
			if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
				isPaused = false;
			}
		} else {
			// pause the game if P is pressed
			if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
				isPaused = true;
			}

			// move the picture
			if (kyleUp) kyleY += speedY;
			else kyleY -= speedY;
			if (kyleRight) kyleX += speedX;
			else kyleX -= speedX;

			// change the direction of movement if he would go off the screen
			if (kyleY + kyleDimensions > 600) {
				kyleUp = false;
				color = (color + 1) % 3;
				speedY += (rand.nextFloat() * 4) - 2;
				//kyleDimensions += rand.nextInt(10) - 50;
			}
			if (kyleY < 0) {
				kyleUp = true;
				color = (color + 1) % 3;
				speedY += (rand.nextFloat() * 4) - 2;
				//kyleDimensions += rand.nextInt(10) - 50;
			}
			if (kyleX + kyleDimensions > 1000) {
				kyleRight = false;
				color = (color + 1) % 3;
				speedX += (rand.nextFloat() * 4) - 2;
				//kyleDimensions += rand.nextInt(10) - 50;
			}
			if (kyleX < 0) {
				kyleRight = true;
				color = (color + 1) % 3;
				speedX += (rand.nextFloat() * 4) - 2;
				//kyleDimensions += rand.nextInt(10) - 50;
			}

			// cycles the colors when space is held
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				color = (color + 1) % 3;
			}
			// increases speed when 'N' is pressed
			if (Gdx.input.isKeyJustPressed((Input.Keys.N))) {
				speedX += 2;
				speedY += 2;
			}
		}

		// slows down the render method
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// prints out Kyle's speeds
		System.out.printf("speedx: %.3f, speedy: %.3f\n", speedX, speedY);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
