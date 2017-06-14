package com.matthewgarrison;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.matthewgarrison.objects.Enemy;
import com.matthewgarrison.objects.Lazer;
import com.matthewgarrison.objects.Player;

import java.util.ArrayList;
import java.util.Random;

/*
Setup Instructions
https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA
 */

public class GameController extends ApplicationAdapter {
	OrthographicCamera camera;
	SpriteBatch batch;
	Player player;
	ArrayList<Lazer> lazerList;
	ArrayList<Enemy> enemies;
	Rectangle leftWall, rightWall, topWall, bottomPlayerWall, bottomEnemyWall;
	int nextEnemyCountdown;
	Random rand;
	Music music;
	Sound enemyGoesBoom;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1000, 600);
		batch = new SpriteBatch();
		player = new Player(400, 10);
		lazerList = new ArrayList<Lazer>();
		enemies = new ArrayList<Enemy>();
		rand = new Random();
		nextEnemyCountdown = rand.nextInt(50) + 50;
		leftWall = new Rectangle(0, 0, 5, 600);
		rightWall = new Rectangle(995, 0, 5, 600);
		topWall = new Rectangle(0, 595, 1000, 5);
		bottomPlayerWall = new Rectangle(0, 0, 1000, 5);
		bottomEnemyWall = new Rectangle(0, 150, 1000, 5);
		music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
		music.play();
		enemyGoesBoom = Gdx.audio.newSound(Gdx.files.internal("sounds/sound.mp3"));
	}

	@Override
	public void render () {
		// Draw
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i=0; i<lazerList.size(); i++) {
			lazerList.get(i).draw(batch);
		}
		for (int i=0; i<enemies.size(); i++) {
			enemies.get(i).draw(batch);
		}
		player.draw(batch);
		batch.end();

		// Update the player
		player.update();

		// Update the lazers.
		for (int i=0; i<lazerList.size(); i++) {
			lazerList.get(i).update();
			if (lazerList.get(i).life <= 0){
				lazerList.remove(i);
				i--;
			}
		}

		// Updates the enemies.
		enemyLoop : for (int i=0; i<enemies.size(); i++) {
			enemies.get(i).update();

			// Prevents the enemies from moving off the screen.
			if (enemies.get(i).hitBox.overlaps(leftWall)) {
				enemies.get(i).setPosition(leftWall.getX() + leftWall.width, enemies.get(i).hitBox.getY());
			}
			if (enemies.get(i).hitBox.overlaps(rightWall)) {
				enemies.get(i).setPosition(rightWall.getX() - enemies.get(i).hitBox.width, enemies.get(i).hitBox.getY());
			}
			if (enemies.get(i).hitBox.overlaps(topWall)) {
				enemies.get(i).setPosition(enemies.get(i).hitBox.getX(), topWall.getY() - enemies.get(i).hitBox.height);
			}
			if (enemies.get(i).hitBox.overlaps(bottomEnemyWall)) {
				enemies.get(i).setPosition(enemies.get(i).hitBox.getX(), bottomEnemyWall.getY() + bottomEnemyWall.height);
			}

			// Checks if the enemy was hit by a lazer. If it was, remove the enemy and lazer.
			for (int j=0; j<lazerList.size(); j++) {
				if (enemies.get(i).hitBox.overlaps(lazerList.get(j).hitBox)) {
					enemies.remove(i);
					i--;
					lazerList.remove(j);
					j--;
					enemyGoesBoom.play();
					if (i < 0) break enemyLoop;
				}
			}
		}
		// Counts down until the next enemy will appear.
		if (nextEnemyCountdown > 0) {
			nextEnemyCountdown--;
		} else {
			// Adds a new enemy and resets the counter.
			enemies.add(new Enemy(rand.nextFloat() * 600, (rand.nextFloat() * 850) + 170));
			nextEnemyCountdown = rand.nextInt(50) + 50;
		}

		// Prevents the player from moving off the scrreen.
		if (player.hitBox.overlaps(leftWall)) {
			player.setPosition(leftWall.getX() + leftWall.width, player.hitBox.getY());
		}
		if (player.hitBox.overlaps(rightWall)) {
			player.setPosition(rightWall.getX() - player.hitBox.width, player.hitBox.getY());
		}
		if (player.hitBox.overlaps(topWall)) {
			player.setPosition(player.hitBox.getX(), topWall.getY());
		}
		if (player.hitBox.overlaps(bottomPlayerWall)) {
			player.setPosition(player.hitBox.getX(), bottomPlayerWall.getY() + bottomPlayerWall.height);
		}

		// Handle input
		if (Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			player.moveLeft();
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			player.moveRight();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			lazerList.add(new Lazer(3, (int)(player.sprite.getX() + player.sprite.getWidth()/2), (int)player.sprite.getHeight()));
		}

		// Moves the camera 50 pixel to the right.
		if (Gdx.input.isKeyJustPressed(Input.Keys.T)) {
			camera.translate(50, 0);
			camera.update();
		}

		for (int i = 0; i < 5; i++) {
			if (Gdx.input.isTouched(i)) {
				// Shoots a lazer when you press (x, y), x on [400,600]
				Vector3 touchPos = new Vector3(Gdx.input.getX(i), Gdx.input.getY(i), 0);
				camera.unproject(touchPos);
				if (touchPos.x > 400 && touchPos.x < 600) {
					lazerList.add(new Lazer(3, (int) (player.sprite.getX() + player.sprite.getWidth() / 2), (int) player.sprite.getHeight()));
				}

			}
		}
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}