package com.matthewgarrison.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {
	public Body body;
	BodyDef bodyDef;
	FixtureDef fixtureDef;
	Fixture fixture;
	private float RADIUS = 0.254f, DIAMETER = 0.508f;
	Sprite sprite;

	public Ball(World world, OrthographicCamera camera, float centerX, float centerY) {
		// Create a BodyDefinition, of Dynamic type.
		bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		bodyDef.position.set(centerX, centerY);
		// Create our body in the world using our body definition.
		body = world.createBody(bodyDef);
		CircleShape circle = new CircleShape();
		circle.setRadius(RADIUS);
		// Create a fixture definition to apply our shape to.
		fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.7f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.8f;
		// Create our fixture and attach it to the body.
		fixture = body.createFixture(fixtureDef);
		// Dispose of the shape, now that we're done with it.
		circle.dispose();

		sprite = new Sprite(new Texture(Gdx.files.internal("golfie/ball_rotate.png")));
		sprite.setPosition(centerX - RADIUS, centerY - RADIUS);
		sprite.setSize(DIAMETER, DIAMETER);
	}

	public void update() {
		setPosition();
		setRotation();
	}

	private void setPosition() {
		sprite.setPosition(body.getPosition().x - RADIUS, body.getPosition().y - RADIUS);
	}

	private void setRotation() {
		sprite.setOriginCenter();
		sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}
}
