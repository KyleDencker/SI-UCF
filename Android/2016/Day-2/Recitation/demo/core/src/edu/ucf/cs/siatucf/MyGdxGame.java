package edu.ucf.cs.siatucf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chris, kyle, gameover;
	OrthographicCamera camera;

	boolean isAlive = true;

	Sprite kyleSprite, chrisSprite;

	Music music;
	Sound death;

	int xKSpeed = 1, yKSpeed = 1;
	int xCSpeed = 5, yCSpeed = 5;

	float scale;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 500, 500);
		batch = new SpriteBatch();

		// Create textures
		kyle = new Texture("kyle.png");
		chris = new Texture("chris.jpg");
		gameover = new Texture("gameover.png");

		// Create Sprites
		kyleSprite = new Sprite(kyle);
		kyleSprite.setSize(200, 200);
		chrisSprite = new Sprite(chris);
		chrisSprite.setSize(100, 100);

		// Position sprites
		chrisSprite.setPosition(0, 0);
		kyleSprite.setPosition(250, 250);

		// Music reference: https://github.com/libgdx/libgdx/wiki/Streaming-music
		music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		music.setLooping(true);
		music.play();

		// Sound reference: https://github.com/libgdx/libgdx/wiki/Sound-effects
		death = Gdx.audio.newSound(Gdx.files.internal("death.wav"));
	}



	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		camera.update();

		// If player is still alive
		if(isAlive) {

			// Player Movement
			if (Gdx.input.isKeyPressed(Input.Keys.A)) {
				chrisSprite.translateX(-xCSpeed);
				chrisSprite.setFlip(false, chrisSprite.isFlipY());
			}
			if (Gdx.input.isKeyPressed(Input.Keys.D)) {
				chrisSprite.translateX(xCSpeed);
				chrisSprite.setFlip(true, chrisSprite.isFlipY());

			}
			if (Gdx.input.isKeyPressed(Input.Keys.S)) {
				chrisSprite.translateY(-yCSpeed);
				chrisSprite.setFlip(chrisSprite.isFlipX(), false);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.W)) {
				chrisSprite.translateY(yCSpeed);
				chrisSprite.setFlip(chrisSprite.isFlipX(), true);
			}

			// Kyle following movement
			if (chrisSprite.getX() < kyleSprite.getX())
				kyleSprite.translateX(-xKSpeed);
			else
				kyleSprite.translateX(xKSpeed);

			if (chrisSprite.getY() < kyleSprite.getY())
				kyleSprite.translateY(-yKSpeed);
			else
				kyleSprite.translateY(yKSpeed);


			// Overlap checking
			if (kyleSprite.getBoundingRectangle().overlaps(chrisSprite.getBoundingRectangle())) {
				music.pause();
				isAlive = false;
			}

		} else {
			// Game Over
			death.play();
		}

		// Draw everything
		batch.begin();
		chrisSprite.draw(batch);
		kyleSprite.draw(batch);
		// Draw gameover screen
		if(!isAlive) {
			kyleSprite.setScale(scale, scale);
			batch.draw(gameover, 250 - 100, 250 - 100, 200, 200);
			scale += 0.1f;
		}
		batch.end();
	}

}
