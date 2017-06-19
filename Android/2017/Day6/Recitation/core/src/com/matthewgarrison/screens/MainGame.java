package com.matthewgarrison.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.matthewgarrison.GameController;
import com.matthewgarrison.objects.Ball;
import com.matthewgarrison.objects.Wall;


public class MainGame implements Screen {
	private GameController game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private Ball ball;
	private Wall floor, right, left, ceiling;
	private Texture backgroundTexture;
	private boolean goTo = false;

	public MainGame(GameController g) {
		game = g;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameController.SCREEN_WIDTH, GameController.SCREEN_HEIGHT);

		batch = new SpriteBatch();

		Box2D.init();
		world = new World(new Vector2(0, -10), true);

		debugRenderer = new Box2DDebugRenderer();

		ball = new Ball(this.world, this.camera, 5, 5);
		floor = new Wall(this.world, 10, 0.25f, 20, 0.5f);
		right = new Wall(this.world, 19.75f, 5.5f, 0.5f, 11);
		left = new Wall(this.world, 0.25f, 5.5f, 0.5f, 11);
		ceiling = new Wall(this.world, 10, 10.75f, 20, 0.5f);

		backgroundTexture = new Texture(Gdx.files.internal("golfie/bg.jpg"));
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(backgroundTexture, 0, 0, GameController.SCREEN_WIDTH, GameController.SCREEN_HEIGHT);
		ball.draw(batch);
		floor.draw(batch);
		right.draw(batch);
		left.draw(batch);
		ceiling.draw(batch);

		batch.end();
		//debugRenderer.render(world, camera.combined);


		ball.update();


		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			ball.body.applyForceToCenter(2.0f, 0, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			ball.body.applyForceToCenter(-2.0f, 0, true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			ball.body.applyForceToCenter(0, -2.0f, true);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
			//ball.body.applyLinearImpulse(ball.body.getPosition().x, ball.body.getPosition().y, 0.0f, 2.0f, true);
			goTo = true;
		}

		if (ball.body.getPosition().y < 8 && goTo) {
			ball.body.applyForceToCenter(0, 2.0f, true);
		} else {
			goTo = false;
		}

		world.step(1/60f, 6, 2);
	}

	public void resize(int width, int height) {
	}

	public void hide() {
		this.dispose();
	}

	public void pause() {
	}

	public void resume() {
	}

	public void dispose() {
		batch.dispose();
	}
}