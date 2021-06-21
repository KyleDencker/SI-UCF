package com.fhsps.clicker.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.fhsps.clicker.Bullet;
import com.fhsps.clicker.Controller;
import com.fhsps.clicker.Enemy;
import com.fhsps.clicker.Ship;

import java.util.ArrayList;

import sun.rmi.rmic.Main;

public class SpaceGame implements Screen {
    SpriteBatch batch;
    Ship spaceShip;
    OrthographicCamera camera;
    ArrayList<Bullet> bulletList;
    ArrayList<Enemy> enemyList;
    float timer =0;

    Controller myGame;

    public SpaceGame(Controller g) {
        myGame = g;
    }






    @Override
    public void show() {
        batch = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1000, 1000);
        spaceShip = new Ship(475, 50);
        bulletList = new ArrayList<Bullet>();
        enemyList = new ArrayList<Enemy>();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();


        for (Bullet b : bulletList) {
            b.draw(batch);
        }
        for (Enemy e : enemyList) {
            e.draw(batch);
        }
        spaceShip.draw(batch);
        batch.end();

        spaceShip.update(Gdx.graphics.getDeltaTime());

        for (int i=0; i  < enemyList.size(); i++) {
            enemyList.get(i).update(delta);
            for (Bullet b : bulletList) {
                enemyList.get(i).isHit(b);
            }

            if (enemyList.get(i).isDead()) {
                enemyList.remove(i);
            }
        }

        for (int i=0; i<bulletList.size(); i++) {
            bulletList.get(i).update(delta);

            if (bulletList.get(i).isDead()) {
                bulletList.remove(i);
                i--;
            }
        }


        timer+= delta;
        if (timer > .5) {
            timer-=.5f;
            enemyList.add(new Enemy((int)(Math.random() * 1000), 1010, (int) (Math.random() * 100 + 50) ));
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            spaceShip.input(1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            spaceShip.input(3);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            bulletList.add(new Bullet((int) (spaceShip.getX() + spaceShip.getSize()/2-7), (int) (spaceShip.getY() + spaceShip.getSize()/2-7), 300));
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
        spaceShip.dispose();
    }
}
