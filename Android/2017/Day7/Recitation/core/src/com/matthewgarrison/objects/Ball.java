package com.matthewgarrison.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Ball {
	private BodyDef bodyDef;
	private Body body;
	private FixtureDef fixtureDef;
	private Fixture fixture;
	private Sprite sprite;
	private final static float RADIUS = 0.254f, DIAMETER = 2 * RADIUS;
	private boolean isLaunched;

	public Ball(World world, float centerX, float centerY) {
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

		sprite = new Sprite(new Texture(Gdx.files.internal("ball_rotate.png")));
		sprite.setSize(DIAMETER, DIAMETER);
		sprite.setPosition(centerX - RADIUS, centerY - RADIUS);

		isLaunched = false;
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
		sprite.setRotation((float)Math.toDegrees(body.getAngle()));
	}

	public void draw(SpriteBatch batch) {
		sprite.draw(batch);
	}

	public Body getBody() {
		return body;
	}

	public void applyForce(float forceX, float forceY) {
		body.applyForceToCenter(forceX, forceY, true);
	}

	public void applyLinearImpulse(float forceX, float forceY) {
		body.applyLinearImpulse(forceX, forceY, body.getPosition().x, body.getPosition().y, true);
	}

	public boolean getIsLaunched() {
		return isLaunched;
	}

	public void setIsLaunched(boolean launched) {
		isLaunched = launched;
	}

	public float getDiameter() {
		return DIAMETER;
	}

	public Vector3 getVector3Pos() {
		return new Vector3(body.getPosition(), 0);
	}
}