package com.fhsps.clicker;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;


public class Controller extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Sprite sprite;
	OrthographicCamera camera;
	int frames = 0;
	Rectangle hitBox, touchBox;
	Sound hit, miss;

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		sprite = new Sprite(img);
		sprite.setSize(100, 100);
		sprite.setPosition(375, 375);

		hitBox = new Rectangle(375, 375, 100, 100);
		touchBox = new Rectangle(-100, -100, 10, 10);

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 1000);

		hit = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
		//miss = Gdx.audio.newSound(Gdx.files.internal("slip.wav"));
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		sprite.draw(batch);

		batch.end();

		frames++;
		if (frames%60==0) {
			sprite.setPosition((float)(Math.random()*950), (float) (Math.random()*950));
			hitBox.x = sprite.getX();
			hitBox.y = sprite.getY();
			//miss.play();
		}

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			touchBox.x = touchPos.x;
			touchBox.y = touchPos.y;

			if (touchBox.overlaps(hitBox)) {
				System.out.println("Touched Box");
				hit.play();
				sprite.setPosition((float)(Math.random()*950), (float) (Math.random()*950));
				hitBox.x = sprite.getX();
				hitBox.y = sprite.getY();
			}
		}

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
