package com.miketriana.tankgame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.miketriana.tankgame.GameController;
import com.miketriana.tankgame.objects.EnemyTank;
import com.miketriana.tankgame.objects.Gem;
import com.miketriana.tankgame.objects.Mine;
import com.miketriana.tankgame.objects.PlayerTank;
import com.miketriana.tankgame.utils.Graph;
import com.miketriana.tankgame.utils.Hud;
import com.miketriana.tankgame.utils.AssetManager;

public class GameScreen extends ScreenAdapter {

    GameController game;

    OrthographicCamera camera;

    TiledMap tiledMap;
    TiledMapRenderer tiledMapRenderer;

    SpriteBatch batch;

    RectangleMapObject playerSpawn, enemySpawn;
    Array<RectangleMapObject> walls;
    Array<RectangleMapObject> navZones;	// Rectangles indicating where to place pathfinding nodes

    Graph visibilityGraph;		// Allows tank to perform pathfinding

    PlayerTank playerTank;
    EnemyTank enemyTank;
    Array<Mine> mines;
    Array<Gem> gems;
    Pool<Gem> gemPool;

    Hud hud;

    static final int WORLD_WIDTH = 1024;
    static final int WORLD_HEIGHT = 768;

    boolean followPlayer = false;

    public GameScreen (GameController game) {
        this.game = game;
    }

    @Override
    public void show () {

        camera = new OrthographicCamera();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera.setToOrtho(false, 400, 400 * (h / w));

        tiledMap = new TmxMapLoader().load("Map.tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

        batch = new SpriteBatch();

        // Get map elements
        for (RectangleMapObject spawn : tiledMap.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            if ((int) spawn.getProperties().get("type") == 0) {
                playerSpawn = spawn;
            } else {
                enemySpawn = spawn;
            }
        }

        walls = tiledMap.getLayers().get(2).getObjects().getByType(RectangleMapObject.class);
        navZones = tiledMap.getLayers().get(4).getObjects().getByType(RectangleMapObject.class);

        // Setup navigation graph
        setupNavGraph();

        // Spawn player
        playerTank = new PlayerTank(playerSpawn.getRectangle().getX(), playerSpawn.getRectangle().getY(), walls);
        enemyTank = new EnemyTank(enemySpawn.getRectangle().getX(), enemySpawn.getRectangle().getY(), walls);
        camera.position.set(playerTank.getPosition(), 0);

        mines = new Array<Mine>();
        gems = new Array<>();
        gemPool = new Pool<Gem>() {
            @Override
            protected Gem newObject() {
                return new Gem(10);
            }
        };
        addMines();
        addGems();

        hud = new Hud();
    }

    @Override
    public void render (float delta) {

        // *** INPUTS ***
        // --------------

        // Touch / mouse input
        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            Vector3 hudPos = hud.getCamera().unproject(new Vector3(touchPos.x, touchPos.y, 0));
            switch (hud.checkInput(hudPos.x, hudPos.y)) {
                case 0:
                    camera.position.x -= 10;
                    break;
                case 1:
                    camera.position.x += 10;
                    break;
                case 2:
                    camera.position.y += 10;
                    break;
                case 3:
                    camera.position.y -= 10;
                    break;
                case 4:
                    if (Gdx.input.justTouched())
                        camera.zoom -= 0.1f;
                    break;
                case 5:
                    if (Gdx.input.justTouched())
                        camera.zoom += 0.1f;
                    break;
                case -1:
                    if (playerTank.getHealth() > 0 && Gdx.input.justTouched()) {
                        Vector3 targetPos = camera.unproject(new Vector3(touchPos.x, touchPos.y, 0));

                        // Pathfind to where the player touched
                        Queue<Vector2> path = visibilityGraph.findPath(playerTank.getPosition().x, playerTank.getPosition().y, targetPos.x, targetPos.y);
                        if (path.size > 0)
                            playerTank.setPath(path);
                        break;
                    }
            }
        }

        // Keyboard input
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

        if (playerTank.getScore() >= 500) {
            game.winScreen.setScore(playerTank.getScore());
            this.dispose();
            game.setScreen(game.winScreen);
        }
        if (enemyTank.getScore() >= 500) {
            game.loseScreen.setScore(playerTank.getScore());
            this.dispose();
            game.setScreen(game.loseScreen);
        }

        // *** GAME UPDATES ***
        // --------------------

        // Enemy AI
        if (enemyTank.getHealth() > 0) {
            enemyTank.findGem(gems, visibilityGraph);
            enemyTank.move(playerTank);
            enemyTank.attack(playerTank, delta);
        } else {
            enemyTank.waitForRespawn(enemySpawn.getRectangle().getX(), enemySpawn.getRectangle().getY(), delta);
        }

        // Move player
        if (playerTank.getHealth() > 0) {
            playerTank.move(enemyTank);
        } else {
            playerTank.waitForRespawn(playerSpawn.getRectangle().getX(), playerSpawn.getRectangle().getY(), delta);
        }

        if (followPlayer) {
            Vector2 tankPosition = playerTank.getPosition();
            camera.position.set(tankPosition, 0);
        }

        // Check collision
        for (Mine m : mines) {
            if (playerTank.getHealth() > 0 && playerTank.collidesWithRect(m.getHitBox())) {
                playerTank.damage(200);
                mines.removeValue(m, true);
                AssetManager.getSound("Explosion").play();
            }
            if (enemyTank.getHealth() > 0 && enemyTank.collidesWithRect(m.getHitBox())) {
                enemyTank.damage(200);
                mines.removeValue(m, true);
                float dstToSound = Vector2.dst(camera.position.x, camera.position.y, enemyTank.getPosition().x, enemyTank.getPosition().y);
                float pan = (enemyTank.getPosition().x - camera.position.x) / 500.0f;
                AssetManager.getSound("Explosion").play(MathUtils.clamp(1 - dstToSound / 500.0f, 0, 1), 1, pan);
            }
        }
        for (Gem g : gems) {
            if (playerTank.getHealth() > 0 && playerTank.getHitBox().overlaps(g.getHitBox())) {
                gemPool.free(g);
                gems.removeValue(g, true);
                spawnGem();
                playerTank.addPoints(g.getValue());
                AssetManager.getSound("Pickup").play();
            }
            if (enemyTank.getHealth() > 0 && enemyTank.getHitBox().overlaps(g.getHitBox())) {
                gemPool.free(g);
                gems.removeValue(g, true);
                spawnGem();
                enemyTank.addPoints(g.getValue());
            }
        }

        hud.update(playerTank, enemyTank);

        // *** DRAW FRAME ***
        // ------------------

        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        tiledMapRenderer.setView(camera);
        tiledMapRenderer.render();

        // Debug methods to visualize the pathfinding process
        //tank.drawHitBox(camera);
        //visibilityGraph.drawGraph(camera);
        //tank.drawPath(camera);
        ///enemyTank.drawPath(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Mine m : mines) {
            m.draw(batch);
        }
        for (Gem g : gems) {
            g.draw(batch);
        }
        if (enemyTank.getHealth() > 0)
            enemyTank.draw(batch);
        if (playerTank.getHealth() > 0)
            playerTank.draw(batch);
        hud.draw(batch);
        batch.end();
    }

    @Override
    public void dispose () {
        tiledMap.dispose();
        batch.dispose();
        visibilityGraph.dispose();
        playerTank.dispose();
        enemyTank.dispose();
        for (Mine m : mines) {
            m.dispose();
        }
        hud.dispose();
    }

    @Override
    public void resize (int width, int height) {
        camera.viewportWidth = width / 2.0f;
        camera.viewportHeight = height / 2.0f;

        hud.resize(width, height);
    }

    private void addMines () {
        for (int i = 0; i < 50; i++) {
            boolean validPlacement;
            float x;
            float y;
            // Don't place mines too close to the spawn point
            do {
                x = MathUtils.random() * (WORLD_WIDTH - 50);
                y = MathUtils.random() * (WORLD_HEIGHT - 50);
                validPlacement = !(new Vector2(x, y).dst(playerSpawn.getRectangle().getX(), playerSpawn.getRectangle().getY()) < 100);
                for (RectangleMapObject navZone : navZones) {
                    if (navZone.getRectangle().contains(x, y)) {
                        validPlacement = false;
                        break;
                    }
                }
            } while (!validPlacement);
            Mine m = new Mine(x, y);
            mines.add(m);
        }
    }

    private void addGems () {
        for (int i = 0; i < 5; i++) {
            gemPool.free(new Gem(25));
            spawnGem();
        }
        for (int i = 0; i < 25; i++) {
            gemPool.free(new Gem(10));
            spawnGem();
        }
    }

    private void spawnGem () {
        boolean validPlacement;
        float x;
        float y;
        // Don't place mines too close to the spawn point
        do {
            x = MathUtils.random() * (WORLD_WIDTH - 50);
            y = MathUtils.random() * (WORLD_HEIGHT - 50);
            validPlacement = !(new Vector2(x, y).dst(playerSpawn.getRectangle().getX(), playerSpawn.getRectangle().getY()) < 100);
            for (RectangleMapObject navZone : navZones) {
                if (navZone.getRectangle().contains(x, y)) {
                    validPlacement = false;
                    break;
                }
            }
        } while (!validPlacement);
        Gem g = gemPool.obtain();
        g.setPosition(x, y);
        gems.add(g);
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
