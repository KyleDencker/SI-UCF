package com.matthewgarrison.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Lazer {

	Sprite sprite;
	Texture texture;
	public float life;
	private float speed = 150;
	public Rectangle hitBox;

	public Lazer(float lifeStart, int startX, int startY) {
		life = lifeStart;
		texture = new Texture("spaceship.png");
		sprite = new Sprite(texture);
		sprite.setPosition(startX, startY);
		sprite.setSize(10, 30);
		hitBox = new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
	}

	public void draw(SpriteBatch b) {
		sprite.draw(b);
	}

	public void update() {
		sprite.setPosition(sprite.getX(), sprite.getY()+speed * Gdx.graphics.getDeltaTime());
		hitBox.setPosition(sprite.getX(), sprite.getY());
		life -= Gdx.graphics.getDeltaTime();
	}

}