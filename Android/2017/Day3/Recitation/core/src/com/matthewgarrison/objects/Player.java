package com.matthewgarrison.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Player {

	public int lives;
	public Texture text;
	public Sprite sprite;
	public Rectangle hitBox;

	public Player(float x, float y) {
		text = new Texture("spaceship.png");
		sprite = new Sprite(text);
		sprite.setSize(50, 75);
		sprite.setPosition(x, y);
		hitBox = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}

	public void draw(SpriteBatch b) {
		sprite.draw(b);
	}

	public void update() {

	}

	public void moveLeft() {
		setPosition(sprite.getX()-250 * Gdx.graphics.getDeltaTime(), sprite.getY());
	}

	public void moveRight() {
		setPosition(sprite.getX()+250 * Gdx.graphics.getDeltaTime(), sprite.getY());
	}

	public void setPosition(float x, float y) {
		sprite.setPosition(x, y);
		hitBox.setPosition(x, y);
	}
}