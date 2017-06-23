package com.matthewgarrison.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.matthewgarrison.GameHandler;
import com.matthewgarrison.objects.Ball;
import com.matthewgarrison.objects.Hole;
import com.matthewgarrison.objects.Wall;
import com.matthewgarrison.tools.ContactListenerr;
import com.matthewgarrison.tools.DragProcessor;

import java.util.ArrayList;

public class MainGame implements Screen {
	private GameHandler game;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private DragProcessor dragProcessor;
	private ShapeRenderer shapeRenderer;
	private ContactListenerr contactListenerr;

	private Ball ball;
	private ArrayList<Ball> otherBalls;
	private Wall floor, right, left, ceiling;
	private Hole hole;

	private Texture backgroundTexture;

	private float timeTaken;

	public MainGame(GameHandler g) {
		game = g;
	}

	public void show() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);

		batch = new SpriteBatch();

		Box2D.init();
		world = new World(new Vector2(0, 0), true);

		debugRenderer = new Box2DDebugRenderer();

		ball = new Ball(this.world, 5, 5);
		otherBalls = new ArrayList<Ball>();
		for (int i = 0; i <5; i++) {
			otherBalls.add(new Ball(this.world, 10 + i, 3 + i));
		}

		floor = new Wall(this.world, 10, 0.25f, 20, 0.5f);
		right = new Wall(this.world, 19.75f, 5.5f, 0.5f, 11);
		left = new Wall(this.world, 0.25f, 5.5f, 0.5f, 11);
		ceiling = new Wall(this.world, 10, 10.75f, 20, 0.5f);

		backgroundTexture = new Texture(Gdx.files.internal("bg.jpg"));
		hole = new Hole(world, 15, 5, 1f - ball.getDiameter(), 1f);

		dragProcessor = new DragProcessor(ball, camera);
		Gdx.input.setInputProcessor(dragProcessor);

		shapeRenderer = new ShapeRenderer();
		timeTaken = 0;

		contactListenerr = new ContactListenerr();
		world.setContactListener(contactListenerr);
	}

	public void render(float delta) {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		batch.draw(backgroundTexture, 0, 0, GameHandler.SCREEN_WIDTH, GameHandler.SCREEN_HEIGHT);
		floor.draw(batch);
		right.draw(batch);
		left.draw(batch);
		ceiling.draw(batch);
		hole.draw(batch);
		ball.draw(batch);
		for (Ball b : otherBalls) b.draw(batch);

		batch.end();

		if (dragProcessor.getIsStarted()) {
			shapeRenderer.setProjectionMatrix(camera.combined);
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(0, 0, 0, 1);
			shapeRenderer.rectLine(ball.getBody().getPosition().x, ball.getBody().getPosition().y,
					dragProcessor.getDragPosForScreen().x, dragProcessor.getDragPosForScreen().y, 0.07f);
			shapeRenderer.end();
		}

		debugRenderer.render(world, camera.combined);

		ball.update();
		for (Ball b : otherBalls) b.update();
		timeTaken += delta;

		if (hole.overlapsCircle(ball.getBody().getPosition(), ball.getRadius())) {
			//ball.enterHole();
			//game.setScreen(new GameOver(game, ball.launchCount, timeTaken));
		}

		if (Gdx.input.isKeyPressed(Input.Keys.R)) {
			ball.reset();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			ball.applyForce(-2.0f, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			ball.applyForce(2.0f, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			ball.applyForce(0, 2.0f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			ball.applyForce(0, -2.0f);
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