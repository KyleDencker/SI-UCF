package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Controller;

public class BubblePopGame implements Screen {

        SpriteBatch batch;
        Texture img;
        Sprite sprite;
        OrthographicCamera camera;
        int frames = 0, score = 0;
        Rectangle hitBox, touchBox;
        Sound hit, miss;
        float count = 10;

        Controller myGame;

        public BubblePopGame(Controller g) {
            myGame = g;
        }




    @Override
    public void show() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        sprite = new Sprite(img);
        sprite.setSize(100, 100);
        sprite.setPosition(375, 375);

        hitBox = new Rectangle(375, 375, 100, 100);
        touchBox = new Rectangle(-100, -100, 10, 10);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);

        hit = Gdx.audio.newSound(Gdx.files.internal("pop.mp3"));
        //miss = Gdx.audio.newSound(Gdx.files.internal("slip.wav"));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        sprite.draw(batch);

        batch.end();

        frames++;
        if (frames%60==0) {
            sprite.setPosition((float)(Math.random()*950), (float) (Math.random()*950));
            hitBox.x = sprite.getX();
            hitBox.y = sprite.getY();
            //miss.play();
        }

        if (Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            touchBox.x = touchPos.x;
            touchBox.y = touchPos.y;

            if (touchBox.overlaps(hitBox)) {
                System.out.println("Touched Box");
                hit.play();
                sprite.setPosition((float)(Math.random()*950), (float) (Math.random()*950));
                hitBox.x = sprite.getX();
                hitBox.y = sprite.getY();
                score++;
            }
        }

        this.count -= delta;

        if (count < 0) {
            myGame.setScreen(new BPGameOver(myGame, score));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            myGame.setScreen(new MainMenu(myGame));
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

    public void dispose () {
       batch.dispose();
       img.dispose();
    }

}
