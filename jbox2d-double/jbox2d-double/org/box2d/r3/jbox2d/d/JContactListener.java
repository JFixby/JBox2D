package org.box2d.r3.jbox2d.d;

public class JContactListener implements org.jbox2d.d.callbacks.ContactListener {

	private org.box2d.jfixby.api.ContactListener contactListener;

	public JContactListener(org.box2d.jfixby.api.ContactListener contactListener) {
		this.contactListener = contactListener;
	}

	@Override
	public void beginContact(org.jbox2d.d.dynamics.contacts.Contact contact) {
		// throw new Error();
	}

	@Override
	public void endContact(org.jbox2d.d.dynamics.contacts.Contact contact) {
		// throw new Error();
	}

	@Override
	public void preSolve(org.jbox2d.d.dynamics.contacts.Contact contact,
			org.jbox2d.d.collision.Manifold oldManifold) {
		// throw new Error();
	}

	@Override
	public void postSolve(org.jbox2d.d.dynamics.contacts.Contact contact,
			org.jbox2d.d.callbacks.ContactImpulse impulse) {
		// throw new Error();
	}

}
