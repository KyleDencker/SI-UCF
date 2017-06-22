package com.matthewgarrison.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Wall {
	private BodyDef bodyDef;
	private Body body;
	private Sprite sprite;

	public Wall(World world, float centerX, float centerY, float width, float height) {
		// Create our body definition. By default, it's static.
		bodyDef = new BodyDef();
		bodyDef.position.set(new Vector2(centerX, centerY));
		// Create a body from the definition.
		body = world.createBody(bodyDef);
		// Create a polygon shape, and set it as a box (method takes half-width and half-height).
		PolygonShape groundBox = new PolygonShape();
		groundBox.setAsBox(width / 2.0f, height / 2.0f);
		// Create a fixture from our polygon shape and add it to our ground body.
		body.createFixture(groundBox, 0.0f);
		// Dispose of the shape.
		groundBox.dispose();

		sprite = new Sprite(new Texture(Gdx.files.internal("wall.jpg")));
		sprite.setSize(width, height);
		sprite.setPosition(centerX - width/2.0f, centerY - height/2.0f);
	}

	public Body getBody() {
		return body;
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}