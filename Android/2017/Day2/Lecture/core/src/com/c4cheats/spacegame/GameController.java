package com.c4cheats.spacegame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.c4cheats.spacegame.objects.Lazer;
import com.c4cheats.spacegame.objects.Player;

import java.util.ArrayList;

/*
Setup Instructions

https://github.com/libgdx/libgdx/wiki/Gradle-and-Intellij-IDEA

 */

public class  GameController extends ApplicationAdapter {
	SpriteBatch batch;
	Player player;
	ArrayList<Lazer> lazerList;

	@Override
	public void create () {
		batch = new SpriteBatch();
		player = new Player();
		lazerList = new ArrayList<Lazer>();
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
		player.draw(batch);
		batch.end();

		// Update
		player.update();
		for (int i=0; i<lazerList.size(); i++) {
			lazerList.get(i).update();
			if (lazerList.get(i).life <= 0){
				lazerList.remove(i);
				i--;
			}
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
