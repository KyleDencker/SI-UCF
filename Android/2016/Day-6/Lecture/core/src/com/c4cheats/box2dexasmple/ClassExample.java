package com.c4cheats.box2dexasmple;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by kyledencker on 6/20/16.
 */
public class ClassExample implements Screen{

    // Ratios
    private static final float WORLD_TO_BOX = 0.01f;
    private static final float BOX_TO_WORLD = 100f;

    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;



    @Override
    public void show() {
        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 500, 500);


        BodyDef groundDef = new BodyDef();
        groundDef.position.set(new Vector2((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX));
        Body groundBody = world.createBody(groundDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox((Gdx.graphics.getWidth() / 2) * WORLD_TO_BOX, 16f * WORLD_TO_BOX);

        groundBody.createFixture(groundShape, 0f);
        groundShape.dispose();

        groundDef = new BodyDef();
        groundDef.position.set(new Vector2((16f) * WORLD_TO_BOX, Gdx.graphics.getHeight() / 2 * WORLD_TO_BOX));
        groundBody = world.createBody(groundDef);

        groundShape = new PolygonShape();
        groundShape.setAsBox(16f * WORLD_TO_BOX, (Gdx.graphics.getHeight() / 2) * WORLD_TO_BOX);

        groundBody.createFixture(groundShape, 0f);
        groundShape.dispose();


        groundDef = new BodyDef();
        groundDef.position.set(new Vector2((484f) * WORLD_TO_BOX, Gdx.graphics.getHeight() / 2 * WORLD_TO_BOX));
        groundBody = world.createBody(groundDef);

        groundShape = new PolygonShape();
        groundShape.setAsBox(16f * WORLD_TO_BOX, (Gdx.graphics.getHeight() / 2) * WORLD_TO_BOX);

        groundBody.createFixture(groundShape, 0f);
        groundShape.dispose();
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

            BodyDef playerDef = new BodyDef();
            playerDef.type = BodyDef.BodyType.DynamicBody;
            playerDef.position.set(new Vector2(touchPos.x * WORLD_TO_BOX, touchPos.y * WORLD_TO_BOX));
            Body playerBody = world.createBody(playerDef);

            CircleShape playerShape = new CircleShape();
            playerShape.setRadius(5*WORLD_TO_BOX);

            FixtureDef fixtureDef = new FixtureDef();
            fixtureDef.shape = playerShape;
             fixtureDef.friction = 0.4f;
            fixtureDef.restitution = .9f;

            playerBody.createFixture(fixtureDef);
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
