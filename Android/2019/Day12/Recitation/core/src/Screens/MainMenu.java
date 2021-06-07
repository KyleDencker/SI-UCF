package Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.GL20;

import com.mygdx.dungeon.GameController;



import Manager.TextureManager;

public class MainMenu implements Screen {
    SpriteBatch batch;
    OrthographicCamera camera;

    float w = Gdx.graphics.getWidth();
    float h = Gdx.graphics.getHeight();


    GameController myGame;
    Sprite startButton;

    Rectangle  startBox;

    public MainMenu(GameController g){
        myGame = g;
        startButton = new Sprite(TextureManager.startButton);
        startBox = new Rectangle();
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera = new OrthographicCamera(w,h);
        camera.translate(w/2, h/2);
        camera.update();

        startButton.setPosition(w/2 - w/16  , h/2 - h/9);
        startButton.setSize(w/16 *2, h/16*2);

        startBox.setPosition(w/2 - w/16,  h/2 - h/9);
        startBox.setSize(w/16 *2, h/16*2);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        startButton.draw(batch);
        batch.end();

        if(Gdx.input.justTouched()){
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            Rectangle touchBox = new Rectangle(touchPos.x, touchPos.y ,20, 20);

            if(touchBox.overlaps(startBox)){
                myGame.setScreen(new MainGame(myGame));
            }
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
