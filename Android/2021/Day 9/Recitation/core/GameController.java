package com.miketriana.tankgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miketriana.tankgame.objects.Mine;
import com.miketriana.tankgame.objects.Tank;
import com.miketriana.tankgame.utils.Graph;
import com.miketriana.tankgame.utils.Node;

public class GameController extends ApplicationAdapter {

	OrthographicCamera camera;

	TiledMap tiledMap;
	TiledMapRenderer tiledMapRenderer;

	SpriteBatch batch;

	RectangleMapObject spawnPoint;
	Array<RectangleMapObject> walls;
	Array<RectangleMapObject> navZones;	// Rectangles indicating where to place pathfinding nodes

	Graph visibilityGraph;		// Allows tank to perform pathfinding

	Tank tank;
	Array<Mine> mines = new Array<Mine>();

	static final int WORLD_WIDTH = 1024;
	static final int WORLD_HEIGHT = 768;

	boolean followPlayer = false;

	@Override
	public void create () {

		camera = new OrthographicCamera();
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera.setToOrtho(false, 400, 400 * (h / w));

		tiledMap = new TmxMapLoader().load("Map.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		batch = new SpriteBatch();

		// Get map elements
		spawnPoint = tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class).get(0);
		walls = tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class);
		navZones = tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class);

		// Setup navigation graph
		setupNavGraph();

		// Spawn player
		tank = new Tank(spawnPoint.getRectangle().getX(), spawnPoint.getRectangle().getY(), walls);
		camera.position.set(tank.getPosition(), 0);

		//addMines();
	}

	@Override
	public void render () {
		// Inputs
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

			// Pathfind to where the player touched
			Queue<Vector2> path = visibilityGraph.findPath(tank.getPosition().x, tank.getPosition().y, touchPos.x, touchPos.y);
			if (path.size > 0)
				tank.setPath(path);
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.MINUS))
			camera.zoom += 0.1f;
		if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS))
			camera.zoom -= 0.1f;

		camera.zoom = MathUtils.clamp(camera.zoom, 0.5f, 2);

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
			camera.position.x -= 10;
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			camera.position.x += 10;
		if (Gdx.input.isKeyPressed(Input.Keys.UP))
			camera.position.y += 10;
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
			camera.position.y -= 10;

		if (Gdx.input.isKeyJustPressed(Input.Keys.Y))
			followPlayer = !followPlayer;


		// Update
		tank.move();
		if (followPlayer) {
			Vector2 tankPosition = tank.getPosition();
			camera.position.set(tankPosition, 0);
		}

		// Check collision
		for (Mine m : mines) {
			if (tank.collidesWithRect(m.getHitBox())) {
				//System.out.println("Hit!");
				tank.damage(1);
			}
		}

		ScreenUtils.clear(0, 0, 0, 1);

		// Draw the game
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		// Debug methods to visualize the pathfinding process
		//tank.drawHitBox(camera);
		//visibilityGraph.drawGraph(camera);
		//tank.drawPath(camera);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Mine m : mines) {
			m.draw(batch);
		}
		tank.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		// Dispose textures
		tank.dispose();
		for (Mine m : mines) {
			m.dispose();
		}
	}

	@Override
	public void resize (int width, int height) {
		camera.viewportWidth = width / 2.0f;
		camera.viewportHeight = height / 2.0f;
	}

	private void addMines () {
		for (int i = 0; i < 50; i++) {
			float x = MathUtils.random() * (WORLD_WIDTH - 50);
			float y = MathUtils.random() * (WORLD_HEIGHT - 50);
			while (x < 200 && y < 200)
				x = MathUtils.random() * (WORLD_WIDTH - 50);
			Mine m = new Mine(x, y);
			mines.add(m);
		}
	}

	private void setupNavGraph () {
		visibilityGraph = new Graph(navZones);
		// Create all nodes in the graph
		visibilityGraph.setUpNodes();
		// Connect nodes to all other nodes line of sight
		visibilityGraph.setUpVisibilityEdges();

		System.out.println("Added " + visibilityGraph.getSize() + " nodes");
	}

}
