package com.matthewgarrison.tools;


import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.matthewgarrison.objects.Ball;
import com.matthewgarrison.objects.Hole;

public class ContactListenerr implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture one = contact.getFixtureA();
		Fixture two = contact.getFixtureB();
		Object e1 = one.getUserData();
		Object e2 = two.getUserData();
		Ball b = null;
		if (e1 instanceof Ball) {
			if (e2 instanceof Hole == false) return;
			b = (Ball)e1;
		} else {
			if (e1 instanceof Hole == false) return;
			b = (Ball)e2;
		}
		b.stop();
		b.enterHole();
	}

	@Override
	public void endContact(Contact contact) {

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
	}
}
