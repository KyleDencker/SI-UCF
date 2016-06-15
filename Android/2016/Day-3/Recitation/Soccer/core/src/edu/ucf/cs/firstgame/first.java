package edu.ucf.cs.firstgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class first extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	OrthographicCamera camera;
	Sprite ball;
	Texture BG;
	double x = 400;
	double y = 400;
	double vx = 0;
	double vy = .5;
	double g = -1;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 600);


		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");

		img = new Texture("soccerball.png");
		BG = new Texture("field.jpg");

		ball = new Sprite(img);
	}

	@Override
	public void render () {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		camera.position.x = (float) x;
		camera.position.y = (float) y;
		camera.update();



		batch.begin();

		updateKeys();		// checks to see if WASD keys are pressed

		batch.draw(BG,0,0);
		batch.draw(ball, (int)x, (int) y);
		batch.end();
	}

	public void updateKeys() {
		//checks keys to update position of soccer ball


		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			y++;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			y--;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			x--;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			x++;
		}
	}
}
