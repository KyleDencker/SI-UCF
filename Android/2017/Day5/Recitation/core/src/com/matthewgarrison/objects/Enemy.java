package com.matthewgarrison.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

public class Enemy {
	public Texture text;
	public Sprite sprite;
	public Rectangle hitBox;
	float changeX, changeY;
	int movementCountdown;
	Random rand;

	public Enemy(float x, float y) {
		text = new Texture("enemy.png");
		sprite = new Sprite(text);
		sprite.setSize(94, 100);
		sprite.setPosition(x, y);
		hitBox = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
		rand = new Random();
		changeX = (rand.nextFloat() * 10) - 5;
		changeY = (rand.nextFloat() * 10) - 5;
		movementCountdown = rand.nextInt(200) + 50;
	}

	public void draw(SpriteBatch b) {
		sprite.draw(b);
	}

	// Moves the enemy by changeX and changeY. Once the countdown reaches zero, we reset the
	// countdown and re-generate changeX and changeY.
	public void update() {
		setPosition(hitBox.getX() + changeX, hitBox.getY() + changeY);
		if (movementCountdown > 0) {
			movementCountdown--;
		} else {
			changeX = (rand.nextFloat() * 10) - 5;
			changeY = (rand.nextFloat() * 10) - 5;
			movementCountdown = rand.nextInt(200) + 50;
		}
	}

	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
		hitBox.setPosition(x, y);
	}
}
