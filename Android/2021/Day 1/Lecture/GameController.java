package edu.ucf.day1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, face;
	Sprite sprite1, sprite2, myFace;
	OrthographicCamera camera;
	int x = 0;
	
	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 1000);

		batch = new SpriteBatch();
		img = new Texture("b1.jpg");
		face = new Texture("face.jpg");

		sprite1 = new Sprite(img);
		sprite2 = new Sprite(img);
		myFace = new Sprite(face);
		sprite1.setPosition(300, 300);
		sprite1.setSize(100, 200);
		sprite2.setSize(50, 100);
		myFace.setSize(100, 100);
	}

	@Override
	public void render () {
		update();
		input();
		draw();
	}

	public void draw() {
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin(); // beginning of drawing

		sprite1.draw(batch);
		sprite2.draw(batch);
		myFace.draw(batch);

		batch.end();   // end of drawing
	}
	public void update() {
		x++;
		if (x%30 == 0) {
			sprite1.setPosition((float) (Math.random() * 400), (float) (Math.random() * 400));
			myFace.setPosition((float) (Math.random() * 400), (float) (Math.random() * 400));
		}
	}

	public void input() {

	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
