package com.c4cheats.spacegame.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.c4cheats.spacegame.GameController;
import com.c4cheats.spacegame.manager.TextureManager;
import com.c4cheats.spacegame.objects.BetterMatthew;
import com.c4cheats.spacegame.objects.BulletPool;
import com.c4cheats.spacegame.objects.Lazer;
import com.c4cheats.spacegame.objects.Player;

import java.util.ArrayList;

/**
 * Created by kyledencker on 6/14/17.
 */

public class MainGame implements Screen {

    SpriteBatch batch;
    Player player;
    ArrayList<Lazer> lazerList;
    ArrayList<Lazer> matthewLazersList;
    BulletPool bpool;
    GameController myGame;
    OrthographicCamera camera;
    BetterMatthew matthew;
    float nextShot = 0, playerNextShot = 0;
    int score1, score2;
    int add = 0;
    BitmapFont font;

    public MainGame(GameController g) {
        myGame = g;
    }

    public void show() {
        TextureManager.loadGame();

        batch = new SpriteBatch();
        player = new Player();
        lazerList = new ArrayList<Lazer>();
        matthewLazersList = new ArrayList<Lazer>();
        bpool = new BulletPool();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);
        matthew = new BetterMatthew();
        score1= 0;
        score2 = 0;
        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().scale(1.25f);

    }

    public void hide() {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void resize(int x, int y) {

    }

    public void render(float delta) {
// Draw
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i=0; i<lazerList.size(); i++) {
            lazerList.get(i).draw(batch);
        }
        for (int i=0; i<matthewLazersList.size(); i++) {
            matthewLazersList.get(i).draw(batch);
        }
        player.draw(batch);
        matthew.draw(batch);
        font.draw(batch, "Welcome to SI@UCF 2017", 30, 200);
        batch.end();

        // Update
        player.update();
        matthew.update();
        for (int i=0; i<lazerList.size(); i++) {
            lazerList.get(i).update();
            if (lazerList.get(i).isDead()){
                bpool.free(lazerList.remove(i));
                i--;
                continue;
            }
            if (matthew.doesHit(lazerList.get(i).getHitBox())) {
                score1++;
                bpool.free(lazerList.remove(i));
                i--;
                continue;
            }
        }
        for (int i=0; i<matthewLazersList.size(); i++) {
            matthewLazersList.get(i).update();
            if (matthewLazersList.get(i).isDead()){
                bpool.free(matthewLazersList.remove(i));
                i--;
                continue;
            }
            if (player.doesHit(matthewLazersList.get(i).getHitBox())) {
                score2++;
                bpool.free(matthewLazersList.remove(i));
                i--;
                continue;
            }
        }
        nextShot -= Gdx.graphics.getDeltaTime();
        if (nextShot <= 0) {
            Lazer temp = bpool.obtain();
            float angle = (float) Math.atan2(
                     (player.sprite.getY()+player.sprite.getHeight()/2)-(matthew.sprite.getY()+matthew.sprite.getHeight()/2),
                     (player.sprite.getX()+player.sprite.getWidth()/2)-(matthew.sprite.getX()+matthew.sprite.getWidth()/2));
            temp.reset(4, (int)( matthew.sprite.getX()+matthew.sprite.getWidth()/2), (int)(matthew.sprite.getY()+matthew.sprite.getHeight()/2), angle+(float)Math.toRadians(add));
            matthewLazersList.add(temp);
            temp = bpool.obtain();
            temp.reset(4, (int)( matthew.sprite.getX()+matthew.sprite.getWidth()/2), (int)(matthew.sprite.getY()+matthew.sprite.getHeight()/2), angle+(float)Math.toRadians(45+add));
            matthewLazersList.add(temp);
            temp = bpool.obtain();
            temp.reset(4, (int)( matthew.sprite.getX()+matthew.sprite.getWidth()/2), (int)(matthew.sprite.getY()+matthew.sprite.getHeight()/2), angle+(float)Math.toRadians(45-add));
            matthewLazersList.add(temp);
            temp = bpool.obtain();
            temp.reset(4, (int)( matthew.sprite.getX()+matthew.sprite.getWidth()/2), (int)(matthew.sprite.getY()+matthew.sprite.getHeight()/2), angle+(float)Math.toRadians(90-add));
            matthewLazersList.add(temp);
            nextShot = 0;
        }
        add++;

        // Handle input
        if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.moveLeft();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.moveRight();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.moveUp();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            player.moveDown();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        playerNextShot -= Gdx.graphics.getDeltaTime();
        if (Gdx.input.isTouched() && playerNextShot <= 0) {
            Lazer temp = bpool.obtain();
            Vector3 touchLoc = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchLoc);

            float angle = (float) Math.atan2(touchLoc.y - (player.sprite.getY()+player.sprite.getHeight()/2),
                                             touchLoc.x - (player.sprite.getX()+player.sprite.getWidth()/2));
            temp.reset(3,(int)(player.sprite.getX()+player.sprite.getWidth()/2),
                         (int)(player.sprite.getY()+player.sprite.getHeight()/2), angle);
            lazerList.add(temp);
            playerNextShot = .1f;
        }

        // chage screens
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        //    myGame.setScreen(new GameOver(myGame));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Q)) {
            myGame.setScreen(new MainGame(myGame));
        }
        System.out.println("Kyle: " + score1 + " Matthew: " + score2);
      //  System.out.println("Array Size: " + lazerList.size() + " Pool Size: " + bpool.getFree() + " Max Pool: " + bpool.peak);
    }

    public void dispose() {
        batch.dispose();
        TextureManager.unloadGame();
    }
}
