package com.c4cheats.mathyfish.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.mathyfish.GameController;
import com.c4cheats.mathyfish.Resources.TextureManager;
import com.c4cheats.mathyfish.objects.Fish;
import com.c4cheats.mathyfish.tools.FishPool;

import java.util.ArrayList;

/**
 * Created by Kyle Dencker on 6/16/16.
 */
public class MainGame implements Screen {

    GameController myGame;
    SpriteBatch batch;
    OrthographicCamera camera;

    FishPool fishPool;
    ArrayList<Fish> activeFish;
    ArrayList<Fish> deletedFish;

    Rectangle touchRec;
    Vector3 touchPos;

    Sprite jump;

    Vector3 startTouch;
    float direction = 10000;


    public MainGame(GameController g) {
        this.myGame = g;
    }

    @Override
    public void show() {
        TextureManager.loadGame();
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameController.WIDTH, GameController.HEIGHT);

        fishPool = new FishPool();
        activeFish = new ArrayList<Fish>();
        deletedFish = new ArrayList<Fish>();


        activeFish.add(fishPool.obtain());
        jump = new Sprite(TextureManager.platform);
        jump.setPosition(GameController.WIDTH - 60, 10);
        jump.setSize(50, 50);



        touchRec = new Rectangle(-100000, -100000, 6, 6);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Fish f : activeFish) { f.draw(batch); }
        if (myGame.onScreenControls) {
            jump.draw(batch);
        }
        batch.end();

        // move fish;
        for (Fish f : activeFish) {
            f.update(delta);
            if (direction != 10000) {
                f.setPosition(f.getHitBox().x + (float)(4 * Math.cos(direction)), f.getHitBox().y + (float)(4 * Math.sin(direction)));
            }
        }


        if (Gdx.input.justTouched()) {
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            startTouch = touchPos.cpy();

            touchRec.x = touchPos.x - 3;
            touchRec.y = touchPos.y - 3;

            for (Fish f : activeFish) {
                if (f.getHitBox().overlaps(touchRec)) {
                    System.out.println("I touch fish.");
                }
            }
            if (myGame.onScreenControls) {
                // check to see if we touch buttons.
                if (jump.getBoundingRectangle().overlaps(touchRec)) {
                    System.out.println("I touch buttons");
                }
            }
        } else {
            touchRec.x = Float.MIN_VALUE;
            touchRec.y = Float.MIN_VALUE;
        }


        if (Gdx.input.isTouched() && myGame.onScreenControls) {
            touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (startTouch == null) startTouch = touchPos.cpy();
            jump.setPosition(startTouch.x, startTouch.y);
            direction = (float) Math.atan2(touchPos.y - startTouch.y, touchPos.x - startTouch.x);
            System.out.println(Math.toDegrees(direction));
        } else {
            startTouch = null;
            touchRec.x = Float.MIN_VALUE;
            touchRec.y = Float.MIN_VALUE;
            direction = 10000;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
            myGame.onScreenControls = !myGame.onScreenControls;
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
