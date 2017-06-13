package com.matthewgarrison;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.matthewgarrison.objects.Lazer;
import com.matthewgarrison.objects.Player;

import java.util.ArrayList;
import java.util.Random;

/*
Setup Instructions
https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA
 */

public class GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	ArrayList<Lazer> lazerList;
	ArrayList<Player> enemies;
	Rectangle leftWall, rightWall, topWall, bottomWall;
	int nextEnemyCountdown;
	Random rand;
	Music music;
	Sound enemyGoesBoom;
	float enemyMoveX, enemyMoveY;
	int enemyTimer;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player(400, 10);
		lazerList = new ArrayList<Lazer>();
		enemies = new ArrayList<Player>();
		enemies.add(new Player(200, 200));
		rand = new Random();
		nextEnemyCountdown = rand.nextInt(50) + 50;
		leftWall = new Rectangle(0, 0, 5, 600);
		rightWall = new Rectangle(995, 0, 5, 600);
		topWall = new Rectangle(0, 595, 1000, 5);
		bottomWall = new Rectangle(0, 0, 1000, 5);
		music = Gdx.audio.newMusic(Gdx.files.internal("music/music.mp3"));
		music.play();
		enemyGoesBoom = Gdx.audio.newSound(Gdx.files.internal("sounds/sound.mp3"));
		enemyMoveX = (rand.nextFloat() * 10) - 5;
		enemyMoveY = (rand.nextFloat() * 10) - 5;
		enemyTimer = 0;
	}

	@Override
	public void render () {
		// Draw
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

		// Changes the enemies' direction.
		if (enemyTimer > 200) {
			enemyMoveX = (rand.nextFloat() * 10) - 5;
			enemyMoveY = (rand.nextFloat() * 10) - 5;
			enemyTimer = 0;
			System.out.printf("x: %.3f, y: %.3f\n", enemyMoveX, enemyMoveY);
		} else {
			enemyTimer++;
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
			if (enemies.get(i).hitBox.overlaps(bottomWall)) {
				enemies.get(i).setPosition(enemies.get(i).hitBox.getX(), bottomWall.getY() + bottomWall.height);
			}

			// Updates the enemy's position.
			enemies.get(i).setPosition(enemies.get(i).hitBox.getX() + enemyMoveX,
					enemies.get(i).hitBox.getY() + enemyMoveY);

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
			enemies.add(new Player(rand.nextFloat() * 600, (rand.nextFloat() * 850) + 150));
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
		if (player.hitBox.overlaps(bottomWall)) {
			player.setPosition(player.hitBox.getX(), bottomWall.getY() + bottomWall.height);
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

	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}