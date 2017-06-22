package com.matthewgarrison.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Hole {
	Sprite sprite;
	float centerX, centerY, radius;

	public Hole(float x, float y, float r) {
		centerX = x;
		centerY = y;
		radius = r;
		sprite = new Sprite(new Texture(Gdx.files.internal("hole.png")));
		sprite.setPosition(x - r, y - r);
		sprite.setSize(2*r, 2*r);
	}

	public void draw(SpriteBatch batch ) {
		sprite.draw(batch);
	}

	public boolean overlapsCircle(Vector2 pos, float r) {
		float dist = (float)Math.hypot(pos.x - centerX, pos.y - centerY);
		return (dist + r <= radius);
		// return (dist <= r + radius);
		// return (dist + r/2.0f <= radius);
	}
}
