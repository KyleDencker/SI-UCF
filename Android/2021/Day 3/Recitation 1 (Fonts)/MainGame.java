package com.libgdxprojects.learning;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sun.org.apache.xpath.internal.operations.Or;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture cookieTexture;
	Sprite cookieSprite;

	Rectangle cookieBox;

	OrthographicCamera camera;

	BitmapFont score;

	int numCookies = 0;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		cookieTexture = new Texture("cookie.jpg");

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 1000);

		cookieSprite = new Sprite(cookieTexture);
		cookieSprite.setSize(200, 200);
		cookieSprite.setCenter(500, 500);

		cookieBox = new Rectangle(cookieSprite.getX(), cookieSprite.getY(), cookieSprite.getWidth(), cookieSprite.getHeight());

		score = new BitmapFont();
		score.setColor(0, 0, 0, 1);


	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		if (Gdx.input.justTouched())
		{
			Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);

			Rectangle touchBox = new Rectangle(touchPos.x, touchPos.y, 1, 1);
			if (touchBox.overlaps(cookieBox))
			{
				System.out.println("Cookie was clicked!");
				numCookies++;
			}
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		cookieSprite.draw(batch);

		score.getData().setScale(4f);
		score.draw(batch, "Score: " + "\nqwertyuioasdfghjkzxc\nvbnm,   wertyuiopfvghj\nklvjkbnsl" + numCookies, 30, 900);


		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		cookieTexture.dispose();
	}
}
