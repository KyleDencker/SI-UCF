package com.c4cheats.box2dexasmple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;

/**
 * Created by kyledencker on 6/20/16.
 */
public class MainGame implements Screen{

    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;


    private BodyDef groundDef;
    private Body groundBody;


    private BodyDef playerDef;
    private Body playerBody;



    public MainGame() {
    }

    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        groundDef = new BodyDef();
        groundDef.position.set(new Vector2((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX));
        groundBody = world.createBody(groundDef);
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX);
        groundBody.createFixture(groundShape, 0f);
        groundShape.dispose();


        playerDef = new BodyDef();
        playerDef.type = BodyDef.BodyType.DynamicBody;
        playerDef.position.set(new Vector2(100 * WORLD_TO_BOX, 400 * WORLD_TO_BOX));
        playerBody = world.createBody(playerDef);

        CircleShape playerShape = new CircleShape();
        playerShape.setRadius(10 * WORLD_TO_BOX);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = playerShape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;

        playerBody.createFixture(fixtureDef);

        playerShape.dispose();
    }

    @Override
    public void render(float delta) {
        camera.update();
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        // physics updates

        Matrix4 cameraCopy = camera.combined.cpy();
        debugRenderer.render(world, cameraCopy.scl(BOX_TO_WORLD));

        world.step(1/60f, 6, 2);

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            playerDef = new BodyDef();
            playerDef.type = BodyDef.BodyType.DynamicBody;
            playerDef.position.set(new Vector2(touchPos.x * WORLD_TO_BOX, touchPos.y * WORLD_TO_BOX));
            playerBody = world.createBody(playerDef);

            CircleShape playerShape = new CircleShape();
            playerShape.setRadius(10 * WORLD_TO_BOX);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = playerShape;
            fixtureDef.density = 0.5f;
            fixtureDef.friction = 0.4f;
            fixtureDef.restitution = 0.6f;

            playerBody.createFixture(fixtureDef);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            camera.position.x ++;
            camera.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.x--;
            camera.update();
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


}
