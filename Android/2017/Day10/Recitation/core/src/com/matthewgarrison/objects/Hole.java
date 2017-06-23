package com.matthewgarrison.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Hole {
	private BodyDef bodyDef;
	private Body body;
	private Fixture fixture;
	Sprite sprite;
	float radius;

	public Hole(World world, float x, float y, float sensorR, float spriteR) {
		// Create a BodyDefinition, of Dynamic type.
		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		// Create our body in the world using our body definition.
		body = world.createBody(bodyDef);
		body.setUserData(this);
		body.setGravityScale(0);
		CircleShape circle = new CircleShape();
		circle.setRadius(sensorR);
		// Create our fixture and attach it to the body.
		FixtureDef def = new FixtureDef();
		def.shape = circle;
		def.isSensor = true;
		fixture = body.createFixture(def);
		fixture.setUserData(this);
		// Dispose of the shape, now that we're done with it.
		circle.dispose();

		radius = spriteR;
		sprite = new Sprite(new Texture(Gdx.files.internal("hole.png")));
		sprite.setPosition(x - spriteR, y - spriteR);
		sprite.setSize(2*spriteR, 2*spriteR);
	}

	public void draw(SpriteBatch batch ) {
		sprite.draw(batch);
	}

	public boolean overlapsCircle(Vector2 pos, float r) {
		float dist = (float)Math.hypot(pos.x - body.getPosition().x, pos.y - body.getPosition().y);
		return (dist + r <= radius);
		// return (dist <= r + radius);
		// return (dist + r/2.0f <= radius);
	}
}
