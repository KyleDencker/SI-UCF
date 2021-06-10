package com.libgdxprojects.learning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

public class MainScreen extends ApplicationAdapter {
	SpriteBatch batch;

	OrthographicCamera camera;

	Texture playTexture, pauseTexture, upTexture, downTexture;
	Sprite playButton, pauseButton, upButton, downButton;
	Rectangle playRect, pauseRect, upRect, downRect;

	Music song;
	float volume = 0.5f;

	Sound sound;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		playTexture = new Texture("play.png");
		playButton = new Sprite(playTexture);
		playButton.setSize(128, 128);
		playButton.setPosition(400, 300);

		playRect = new Rectangle(playButton.getX(), playButton.getY(), playButton.getWidth(), playButton.getHeight());


		pauseTexture = new Texture("pause.png");
		pauseButton = new Sprite(pauseTexture);
		pauseButton.setSize(128, 128);
		pauseButton.setPosition(200, 300);

		pauseRect = new Rectangle(pauseButton.getX(), pauseButton.getY(), pauseButton.getWidth(), pauseButton.getHeight());

		upTexture = new Texture("up.png");
		upButton = new Sprite(upTexture);
		upButton.setSize(128, 128);
		upButton.setPosition(700, 500);

		upRect = new Rectangle(upButton.getX(), upButton.getY(), upButton.getWidth(), upButton.getHeight());

		downTexture = new Texture("down.png");
		downButton = new Sprite(downTexture);
		downButton.setSize(128, 128);
		downButton.setPosition(700, 300);

		downRect = new Rectangle(downButton.getX(), downButton.getY(), downButton.getWidth(), downButton.getHeight());

		song = Gdx.audio.newMusic(Gdx.files.internal("Timed Challenge Beapbox.wav"));
		song.setVolume(volume);
		song.setLooping(true);

		sound = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 1000);
	}

	@Override
	public void render () {
		ScreenUtils.clear(0f, 0f, 0f, 1f);

		if (Gdx.input.justTouched())
		{
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			Rectangle touchBox = new Rectangle(touchPos.x, touchPos.y, 1, 1);
			if (touchBox.overlaps(playRect))
			{
				System.out.println("Play");
				song.play();
			}

			else if (touchBox.overlaps(pauseRect))
			{
				System.out.println("pause");
				song.pause();
			}

			else if (touchBox.overlaps(upRect))
			{
				System.out.println("up");
				volume += 0.1f;
				song.setVolume(volume);
			}

			else if (touchBox.overlaps(downRect))
			{
				System.out.println("down");
				volume -= 0.1f;
				song.setVolume(volume);
			}

			else
			{
				sound.play();
			}


		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		playButton.draw(batch);
		pauseButton.draw(batch);
		upButton.draw(batch);
		downButton.draw(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		playTexture.dispose();
		pauseTexture.dispose();
		upTexture.dispose();
		downTexture.dispose();

		song.dispose();
		batch.dispose();

	}
}
