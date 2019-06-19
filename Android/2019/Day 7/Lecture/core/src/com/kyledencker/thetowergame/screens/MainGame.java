package com.kyledencker.thetowergame.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kyledencker.thetowergame.GameController;
import com.kyledencker.thetowergame.manager.ParticlePool;
import com.kyledencker.thetowergame.manager.TextureManager;
import com.kyledencker.thetowergame.objects.Bullet;
import com.kyledencker.thetowergame.objects.FancyTower;
import com.kyledencker.thetowergame.objects.Player;
import com.kyledencker.thetowergame.objects.RapidTower;
import com.kyledencker.thetowergame.objects.Shooter;
import com.kyledencker.thetowergame.objects.Tower;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

public class MainGame implements Screen {

    SpriteBatch batch;
    ArrayList<Player> player;
    OrthographicCamera camera;
    BitmapFont font;
    int score;
    boolean debug = true;

    GameController myGame;
    public static ArrayList<Bullet> bulletList;
    private ArrayList<Shooter> towerList;
    public Sprite[] gridlines;

    ArrayList<ParticleEffect> effect;
    ParticlePool pool;


    public MainGame(GameController g){
        myGame = g;
    }





    Vector2[] path, path2;
    float spawnTime = 1, currentTime;

    @Override
    public void show() {

        gridlines = new Sprite[100];
        Texture temp = new Texture("important.jpg");
        int pos = 0;
        for (int i=0; i<= 1600; i+=50) {
            gridlines[pos] = new Sprite(temp);
            gridlines[pos].setPosition(i, 0);
            gridlines[pos].setSize(4, 1600);
            pos++;
        }
        for (int i=0; i<= 1200; i+=50) {
            gridlines[pos] = new Sprite(temp);
            gridlines[pos].setPosition(0, i);
            gridlines[pos].setSize(1600, 4);
            pos++;
        }

        batch = new SpriteBatch();
        player = new ArrayList<Player>();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 1200);
        bulletList = new ArrayList<Bullet>();
        towerList = new ArrayList<Shooter>();
        score = 5;
        font = new BitmapFont(Gdx.files.internal("fonts/mainFont.fnt"));
        font.setColor(1,1,1,1);
        font.getData().setScale(1);

        effect = new ArrayList<ParticleEffect>();
        pool = new ParticlePool();

        String filename1 = "paths/"+(int)(Math.random()*5+1)+".path";
        FileHandle file = Gdx.files.internal(filename1);
        Scanner scan = new Scanner(file.read());
        int size = scan.nextInt();
        path = new Vector2[size];
        for(int i=0; i<size; i++) {
            path[i] = new Vector2(scan.nextInt(), scan.nextInt());
        }
        filename1 = "paths/"+(int)(Math.random()*5+1)+".path";
        file = Gdx.files.internal(filename1);
        scan = new Scanner(file.read());
        size = scan.nextInt();
        path2 = new Vector2[size];
        for(int i=0; i<size; i++) {
            path2[i] = new Vector2(scan.nextInt(), scan.nextInt());
        }

    }


    @Override
    public void render(float delta) {
// Draw
        Gdx.gl.glClearColor(1, 0, .5f, 1);
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
       // batch.draw(TextureManager.map, 0, 0);

        if (debug == true)
            for (Sprite s : gridlines) {
                if (s != null) {
                    s.draw(batch);
                }
            }


        for (Bullet b : bulletList) {
            b.draw(batch);
        }
        for (Shooter t : towerList) {
            t.draw(batch);
        }
        for (int i=0; i<player.size(); i++) {
            player.get(i).draw(batch);
        }

        for (ParticleEffect e : effect) {
            e.draw(batch);
        }

        font.draw(batch, "Score: " + score + " Next Tower: " + (Math.pow(2, towerList.size())), 10, 40);
        batch.end();

        // update
        for (int i=0; i<effect.size(); i++) {
            effect.get(i).update(Gdx.graphics.getDeltaTime());
            if (effect.get(i).isComplete()){

                pool.free(effect.remove(i));
               // effect.get(i).start();
                i--;
            }
        }

        for (Shooter t : towerList) {
            t.update(Gdx.graphics.getDeltaTime(), player);
        }
        for (Bullet b : bulletList) {
            b.update(Gdx.graphics.getDeltaTime());
        }

        for (int i=0; i<player.size(); i++) {
            player.get(i).update(Gdx.graphics.getDeltaTime());


        }

        goBackPlayers: for (int i=0; i<player.size(); i++) {
            for (int j=0; j<bulletList.size(); j++) {
                if (player.get(i).doesHit(bulletList.get(j).getHitBox())) {
                    ParticleEffect temp = pool.obtain();
                    temp.setPosition(player.get(i).getPosition().x, player.get(i).getPosition().y);
                    temp.start();
                    effect.add(temp);
                    bulletList.remove(j);
                    player.remove(i);
                    j--;
                    i--;

                    score++;
                    continue goBackPlayers;
                }

                if (bulletList.get(j).getHitBox().x < -10 || bulletList.get(j).getHitBox().x > 1620) {
                    bulletList.remove(j);
                    j--;
                }

            }
        }

        currentTime += Gdx.graphics.getDeltaTime();
       // spawnTime = spawnTime * (float)Math.pow(.99f, Gdx.graphics.getDeltaTime());

        if (currentTime >= spawnTime) {
            if (Math.random() > .5) {
                player.add(new Player((int)path[0].x, (int)path[0].y, path));
            } else {
                player.add(new Player((int)path2[0].x, (int)path2[0].y, path2));
            }
            currentTime -= spawnTime;
            spawnTime *= .995f;
        }


        // handle inputs
            if (Gdx.input.justTouched()) {
                Vector3 touched = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touched);
                boolean didFind = false;
                for (Shooter t : towerList) {
                    Rectangle temp = new Rectangle(touched.x-5, touched.y-5, 10, 10);
                    if (temp.overlaps(t.getHitBox())) {
                        didFind = true;
                        t.clicked();
                    }
                }

                if (!didFind) {
                    if (score >= Math.pow(2, towerList.size())) {
                        score -= Math.pow(2, towerList.size());
                        int x = (int) (touched.x) / 50 * 50;
                        int y = (int) (touched.y) / 50 * 50;
                       if (Math.random()>.5)
                           towerList.add(new Tower(x, y));
                        else if (Math.random() > .5) {
                            towerList.add(new RapidTower(x, y));
                       } else
                            towerList.add(new FancyTower(x, y));
                    }
                }
            }


        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            myGame.setScreen(new GameOver(myGame, 1));
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        System.out.println(bulletList.size());
       // System.out.println("Active: " + effect.size() + " Pool: " + pool.getFree() + " Max: " + pool.peak);
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
        batch.dispose();
    }
}
